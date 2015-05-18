package cn.com.dhcc.rp.connection.maintenance.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cn.com.dhcc.rp.connection.SocketConnection;
import cn.com.dhcc.rp.connection.SocketConnetionContainer;
import cn.com.dhcc.rp.connection.maintenance.ContainerMaintenanceJob;
/**
 * ����������ݼ��ϴ�С��������С������ա�
 * ��¼�¼�
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
			log.info("����������ݼ��ϴ�С:" + connCount);
			if(connCount>CONN_DATA_SET_MAX_SIZE){
				//TODO ʵ��conn.clearData();
				log.warn("��ʱ���ݼ��ϴ�С������ռ��ϡ�");
			}
		}
	
	}
	

}
