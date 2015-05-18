package cn.com.dhcc.rp.job.collect;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;

import cn.com.dhcc.rp.persistence.po.RoomInterfaceConf;
import cn.com.dhcc.rp.pojo.ConfFormatConstants;
import cn.com.dhcc.rp.roomInterConf.IntfsConfLoader;
/**
 * �ɼ�����
 * @author PCITECC02
 *
 */
public abstract class CGNCollectJob implements Job {
	static final protected Logger log = Logger.getLogger(CGNCollectJob.class.getClass());
	/**
	 * ��ʼ��JOB
	 * @param content
	 */
	static public RoomInterfaceConf getRoomInterfaceConf(JobExecutionContext content){
		String companyCode = content.getMergedJobDataMap().getString(ConfFormatConstants.COMPANY_CODE);
		/*SqlSession ses = DBDelegate.getSqlSessionFactory().openSession(true);
		try{
			RoomInterfaceConf interfaceConfig = ses.selectOne("cn.com.dhcc.rp.comm.select_interface_info_by_company_code", companyCode);
			return interfaceConfig;
		}finally{
			ses.close();
		}*/
		return IntfsConfLoader.getRoomInterfaceConf(companyCode);
	}
	/**
	 * ��ӡ�ɼ�������Ϣ
	 * @param content jobִ��������
	 */
	static public String printJobInfo(JobExecutionContext content){
		JobDetail detail = content.getJobDetail();
		StringBuffer info = new StringBuffer("\n===================�ɼ���ҵ��Ϣ==================\n");
		info.append("����:" + detail.getDescription() + "\n" 
				+ "ʱ��:"+ new Date().toString());
		return info.toString();
	}
}
