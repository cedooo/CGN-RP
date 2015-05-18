package cn.com.dhcc.rp.job.collect.impl;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.dhcc.rp.client.CorbaClient;
import cn.com.dhcc.rp.job.collect.CGNCollectJob;
import cn.com.dhcc.rp.persistence.po.RoomInterfaceConf;
import cn.com.dhcc.rp.st.STCorbaClient;

public class STCollectJob extends CGNCollectJob {
	
	@Override
	public void execute(JobExecutionContext cont) throws JobExecutionException {

		String info = printJobInfo(cont);
		
		RoomInterfaceConf interfaceConfig = getRoomInterfaceConf(cont);
//System.out.println("数据库配置" + interfaceConfig);
		CorbaClient client = new STCorbaClient(interfaceConfig);
		log.info(info);
		boolean col = client.doCollect();
		log.info("采集" + (col?"成功":"失败"));
		client = null;
	}
	
}
