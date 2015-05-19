package cn.com.dhcc.rp.job.further.impl;

import org.apache.ibatis.session.SqlSession;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.dhcc.rp.db.DBDelegate;
import cn.com.dhcc.rp.job.further.FurtherProcessJob;
/**
 * 删除未读的告警
 * @author PCITECC02
 *
 */
public final class ClearUnreadEventsJob extends FurtherProcessJob{
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		printJobInfo(context);
		SqlSession sess = DBDelegate.getSqlSessionFactory().openSession(true);
		int deleteCount = -1;
		try{
			deleteCount = sess.delete("cn.com.dhcc.rp.job.delete_unread_event");
		}finally{
			sess.close();
		}
		if(deleteCount>0){
			log.warn("删除未读的告警" + deleteCount + "条");
		}else{
			log.info("未读告警为0条");
		}
	}
	/*public static void main(String[] args) {
		SqlSession sess = DBDelegate.getSqlSessionFactory().openSession();
		String deleteCount = sess.selectOne("cn.com.dhcc.rp.job.test_datesel");
		sess.commit();
		sess.close();
		System.out.println(deleteCount);
	}*/
}
