package cn.com.dhcc.rp.job.collect.impl;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.dhcc.rp.job.collect.CGNCollectJob;
import cn.com.dhcc.rp.machineroom.MachineRoom;
import cn.com.dhcc.rp.machineroom.factory.MachineRoomFactory;

/**
 * 通用采集任务-适用于共济、龙控机房
 * @author PCITECC02
 *
 */
public class CommCollectJob extends CGNCollectJob {

	@Override
	public void execute(JobExecutionContext jobExeContent) throws JobExecutionException {
		String info = printJobInfo(jobExeContent);
		MachineRoom machineRoom = MachineRoomFactory.getMachineRoomInstance(jobExeContent);
		log.info(info + machineRoom);
		if(machineRoom!=null){
			machineRoom.doCollectJob();
		}
		machineRoom = null;
	}

}
