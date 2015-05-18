package cn.com.dhcc.rp.connection.maintenance.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cn.com.dhcc.rp.connection.SocketConnection;
import cn.com.dhcc.rp.connection.SocketConnetionContainer;
import cn.com.dhcc.rp.connection.maintenance.ContainerMaintenanceJob;
/**
 * 监测连接数据集合大小，超过大小进行清空。
 * 记录事件
 * @author PCITECC02
 *
 */
public class CheckConnectionSetSizeJob extends ContainerMaintenanceJob{
	private static final int CONN_DATA_SET_MAX_SIZE = 2000;
	@Override
	public void doJob() {
		Map<String, SocketConnection> connectionMap = SocketConnetionContainer.getConnectionMap();
		Iterator<Entry<String, SocketConnection>> iterator = connectionMap.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, SocketConnection> connEnt = iterator.next();
			SocketConnection conn = connEnt.getValue();
			int connCount = conn.getSetSize();
			log.info("监测连接数据集合大小:" + connCount);
			if(connCount>CONN_DATA_SET_MAX_SIZE){
				//TODO 实现conn.clearData();
				log.warn("临时数据集合大小过大，清空集合。");
			}
		}
	
	}
	

}
