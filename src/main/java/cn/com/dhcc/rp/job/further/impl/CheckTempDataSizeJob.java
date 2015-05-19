package cn.com.dhcc.rp.job.further.impl;

import org.apache.ibatis.session.SqlSession;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.dhcc.rp.db.DBDelegate;
import cn.com.dhcc.rp.job.further.FurtherProcessJob;
/**
 *  检查临时表大小,如果超过大小,清空临时表
 * @author PCITECC02
 * 
 */
public final class CheckTempDataSizeJob extends FurtherProcessJob{
	public static final String MAX_SIZE = "maxRecords";
	public static final String TEMP_TABS = "tempTabs";
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 数据超过大小时，删除临时表的数据
		printJobInfo(context);
		long maxSize = context.getMergedJobDataMap().getLong(MAX_SIZE);
		String tables = context.getMergedJobDataMap().getString(TEMP_TABS);
		String tablesa[] = tables.trim().split(",");
		SqlSession sess = DBDelegate.getSqlSessionFactory().openSession();
		try{
			for (String tableName : tablesa) {
				int deletedCount = 0;
				Integer count = sess.selectOne("cn.com.dhcc.rp.job.select_columns_by_name", tableName);
				if(count>maxSize){
					deletedCount += sess.delete("cn.com.dhcc.rp.job.delete_temp_tab_data", tableName);
					log.warn("临时表" + tableName +  "大小[" + count + "]超过大小[" + maxSize + "]限制，清空表中记录[" + deletedCount + "]条。");
				}else{
					log.info("临时表" + tableName +  "大小正常");
				}
			}
		}finally{
			if(sess!=null){
				sess.commit();
				sess.close();
			}
		}
	}
}
