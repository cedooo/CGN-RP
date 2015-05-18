package cn.com.dhcc.rp.job.further.impl;

import org.apache.ibatis.session.SqlSession;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.dhcc.rp.db.DBDelegate;
import cn.com.dhcc.rp.job.further.FurtherProcessJob;
/**
 * ɾ����������
 * @author PCITECC02
 * 
 */
public final class ClearDeprecatedDataJob extends FurtherProcessJob{
	static final String DEPRECATED_TIME = "deprecatedTime";
	//private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//TODO ɾ����ʱ���� ���ڵ�����
		printJobInfo(context);
		long deprecatedTime = context.getMergedJobDataMap().getLong(DEPRECATED_TIME);
		int deleteCount = -1;
		SqlSession sess = DBDelegate.getSqlSessionFactory().openSession();
		try{
			deleteCount += sess.delete("", deprecatedTime);
			deleteCount += sess.delete("", deprecatedTime);
		}finally{
			if(sess!=null){
				sess.commit();
				sess.close();
			}
		}
		System.out.println("ɾ�����ڵ����� " + deleteCount + " ��");
	}
}
