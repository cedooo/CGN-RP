package cn.com.dhcc.rp.col;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import cn.com.dhcc.rp.col.listener.CollectorListener;
/**
 * 采集器
 * @author PCITECC02
 *
 */
public class Collector implements Runnable{
	static private final Logger log = Logger.getLogger(Collector.class.getClass());
	private boolean keepRun = true;
	@Override
	public void run() {
		Scheduler scheduler = null;
		 try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.getListenerManager().addSchedulerListener(new CollectorListener());
			scheduler.start();
			log.info("采集调度线程启动");
			try {
				while(keepRun){
					Thread.sleep(60*1000);
				}
			} catch (InterruptedException e) {
				log.fatal("采集器线程Interrupted");
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		} finally{
			if(scheduler!=null){
				try {
					scheduler.shutdown();
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
			}else{}
		}	
	}
	/**
	 * 停止采集
	 */
	public void stopCollector(){
		this.keepRun = false;
	}
}
