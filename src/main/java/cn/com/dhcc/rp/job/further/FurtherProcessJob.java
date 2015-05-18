package cn.com.dhcc.rp.job.further;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
/**
 * 处理任务
 * @author PCITECC02
 *
 */
public abstract class FurtherProcessJob implements Job {
	static final protected Logger log = Logger.getLogger(FurtherProcessJob.class.getClass());
	
	/**
	 * 打印任务基本信息
	 * @param content job执行上下文
	 */
	static public void printJobInfo(JobExecutionContext content){
		JobDetail detail = content.getJobDetail();
		StringBuffer logStr = new StringBuffer("\n====================作业信息===================\n");
		logStr.append("描述:" + detail.getDescription() + "\n" 
				+ "时间:"+ new Date().toString()
				+ "\n============================================");
		log.info(logStr);
	}
}
