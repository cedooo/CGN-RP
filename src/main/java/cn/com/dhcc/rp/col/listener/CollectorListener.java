package cn.com.dhcc.rp.col.listener;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

/**
 * �ɼ�������
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
		log.info("���������ɼ�ֹͣ�ɹ�");
	}

	@Override
	public void schedulerShuttingdown() {
		log.info("���������ɼ�����ֹͣ...");
	}

	@Override
	public void schedulerStarted() {
		log.info("���������ɼ������ɹ�");
	}

	@Override
	public void schedulerStarting() {
		log.info("���������ɼ���������...");

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
