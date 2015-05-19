package cn.com.dhcc.rp.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.com.dhcc.rp.connection.RealTimeData;
import cn.com.dhcc.rp.machineroom.networkele.NetworkElement;
import cn.com.dhcc.rp.persistence.po.RoomInterfaceConf;

/**
 * 抽象 机房动力 客户端
 * @author PCITECC02
 *
 */
public abstract class RPClient {
	static protected final Logger log = Logger.getLogger(RPClient.class.getClass());
	protected RoomInterfaceConf roomInterfaceCof = null;
	protected List<NetworkElement> networkElementList = new ArrayList<NetworkElement>();
	protected Set<RealTimeData> realTimeDataSet = null;
	public RoomInterfaceConf getRoomInterfaceCof() {
		return roomInterfaceCof;
	}
	public void setRoomInterfaceCof(RoomInterfaceConf roomInterfaceCof) {
		this.roomInterfaceCof = roomInterfaceCof;
	}
	@Override
	public String toString() {
		return "" + roomInterfaceCof;
	}
	abstract public boolean doCollect(List<NetworkElement> networkElementList);
	
}
