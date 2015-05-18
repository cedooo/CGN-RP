package cn.com.dhcc.rp.job.further.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.dhcc.rp.connection.RealTimeData;
import cn.com.dhcc.rp.connection.gj.GJData;
import cn.com.dhcc.rp.db.DBDelegate;
import cn.com.dhcc.rp.job.further.FurtherProcessJob;
import cn.com.dhcc.rp.job.po.TxRoomPowerUsedPerf;
import cn.com.dhcc.rp.persistence.po.Constants;
/**
 * 计算PUE 
 * 
 * （AP1电量仪的三相视在总功率+AP4电量仪的三相视在功率）/（AP2电量仪三相视在功率+ AP3电量仪三相视在功率）
 * @author PCITECC02
 *
 */
public final class ComputePUEJob extends FurtherProcessJob {

	static final protected SimpleDateFormat dateFormat = new SimpleDateFormat(cn.com.dhcc.rp.pojo.ConfFormatConstants.DATE_FORMAT);
	static final private String ap1id = "S0-E61-A16" + GJData.ATTR_VALUE_TAG;
	static final private String ap2id = "S0-E11-A31" + GJData.ATTR_VALUE_TAG;
	static final private String ap3id = "S0-E13-A31" + GJData.ATTR_VALUE_TAG;
	static final private String ap4id = "S0-E94-A31" + GJData.ATTR_VALUE_TAG;
	static final private String COMPANY_CODE = "GJ_1";
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		printJobInfo(context);
		//Set<RealTimeData> dataSet = SocketConnetionContainer.getRealData(COMPANY_CODE);
		Set<RealTimeData> dataSet = new HashSet<RealTimeData>();
		SqlSession ses = DBDelegate.getSqlSessionFactory().openSession(ExecutorType.BATCH, true);
		try{
			List<GJData> listData = 
					ses.selectList("cn.com.dhcc.rp.realtimedata.select_gj_data_with_company_code",
							ComputePUEJob.COMPANY_CODE);
			dataSet.addAll(new HashSet<RealTimeData>(listData));
		}finally{
            if(ses!=null){
            	ses.commit();
            	ses.clearCache();
            	ses.close();
            }
        }
		String ap1val = null;
		String ap2val = null;
		String ap3val = null;
		String ap4val = null;
		for (RealTimeData realTimeData : dataSet) {
			if(realTimeData instanceof GJData){
				GJData gjData = (GJData)realTimeData;
				if(ap1id.equals(gjData.getKey())){
					ap1val = gjData.getValue();
				}else if(ap2id.equals(gjData.getKey())){
					ap2val = gjData.getValue();
				}else if(ap3id.equals(gjData.getKey())){
					ap3val = gjData.getValue();
				}else if(ap4id.equals(gjData.getKey())){
					ap4val = gjData.getValue();
				}
			}else {
				//do nothing
				return;
			}
		}
		double ap1dval = 0.0;
		double ap2dval = 0.0;
		double ap3dval = 0.0;
		double ap4dval = 0.0;
		try{
			ap1dval = Double.parseDouble(ap1val);
			ap2dval = Double.parseDouble(ap2val);
			ap3dval = Double.parseDouble(ap3val);
			ap4dval = Double.parseDouble(ap4val);
		}catch(NullPointerException e){
			return ;
		}catch(NumberFormatException e){
			return ;
		}
		
		boolean valValid = (ap1dval>0 && ap2dval>0 && ap3dval>0 && ap4dval>0);
		if(valValid){
			double ap14val = ap1dval + ap4dval;
			double ap23val = ap2dval + ap3dval;
			double pue = ap14val/ap23val;
			
			TxRoomPowerUsedPerf perf = new TxRoomPowerUsedPerf();
			perf.setPowerUsed(pue+"");
			perf.setId(context.getMergedJobDataMap().getString("pueID"));
			perf.setPartID(Constants.DEFAULT_PART_ID);
			perf.setCompanyCode(COMPANY_CODE);
			String collectTime = dateFormat.format(new Date());
			perf.setCollectTime(collectTime);
			log.info("计算PUE，pue = " + perf.getPowerUsed());

			SqlSession sess = DBDelegate.getSqlSessionFactory().openSession();
			try{
				sess.update("cn.com.dhcc.rp.job.update_insert_TxRoomPowerUsedPerf", perf);
				sess.commit();
				log.debug("pue入库成功");
			}finally{
				sess.close();
			}
		}
		
	}

}
