package cn.com.dhcc.rp.col.listener;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

/**
 * 采集监听器
 * @author PCITECC02
 *
 */
public class CollectorListener implements SchedulerListener {
	static private final Logger log = Logger.getLogger(CollectorListener.class.getClass());
	@Override
	public void jobAdded(JobDetail arg0) {

	}

	@Override
	public void jobDeleted(JobKey arg0) {

	}

	@Override
	public void jobPaused(JobKey arg0) {

	}

	@Override
	public void jobResumed(JobKey arg0) {
	}

	@Override
	public void jobScheduled(Trigger arg0) {

	}

	@Override
	public void jobUnscheduled(TriggerKey arg0) {

	}

	@Override
	public void jobsPaused(String arg0) {

	}

	@Override
	public void jobsResumed(String arg0) {

	}

	@Override
	public void schedulerError(String arg0, SchedulerException arg1) {

	}

	@Override
	public void schedulerInStandbyMode() {
		log.info("scheduler stand by");
	}

	@Override
	public void schedulerShutdown() {
		log.info("机房动力采集停止成功");
	}

	@Override
	public void schedulerShuttingdown() {
		log.info("机房动力采集正在停止...");
	}

	@Override
	public void schedulerStarted() {
		log.info("机房动力采集启动成功");
	}

	@Override
	public void schedulerStarting() {
		log.info("机房动力采集正在启动...");

	}

	@Override
	public void schedulingDataCleared() {

	}

	@Override
	public void triggerFinalized(Trigger arg0) {

	}

	@Override
	public void triggerPaused(TriggerKey arg0) {

	}

	@Override
	public void triggerResumed(TriggerKey arg0) {

	}

	@Override
	public void triggersPaused(String arg0) {

	}

	@Override
	public void triggersResumed(String arg0) {

	}

}
