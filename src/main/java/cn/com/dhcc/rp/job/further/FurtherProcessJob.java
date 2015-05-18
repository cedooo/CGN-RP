package cn.com.dhcc.rp.job.further;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
/**
 * ��������
 * @author PCITECC02
 *
 */
public abstract class FurtherProcessJob implements Job {
	static final protected Logger log = Logger.getLogger(FurtherProcessJob.class.getClass());
	
	/**
	 * ��ӡ���������Ϣ
	 * @param content jobִ��������
	 */
	static public void printJobInfo(JobExecutionContext content){
		JobDetail detail = content.getJobDetail();
		StringBuffer logStr = new StringBuffer("\n====================��ҵ��Ϣ===================\n");
		logStr.append("����:" + detail.getDescription() + "\n" 
				+ "ʱ��:"+ new Date().toString()
				+ "\n============================================");
		log.info(logStr);
	}
}
