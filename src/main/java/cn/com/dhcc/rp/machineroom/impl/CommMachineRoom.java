package cn.com.dhcc.rp.machineroom.impl;

import cn.com.dhcc.rp.machineroom.MachineRoom;

public class CommMachineRoom extends MachineRoom{
	
	@Override
	public boolean doCollectJob() {
		return client.doCollect(networkElementList);
	}

}
