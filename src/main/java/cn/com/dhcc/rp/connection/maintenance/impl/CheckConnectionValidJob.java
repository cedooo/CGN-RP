package cn.com.dhcc.rp.connection.maintenance.impl;

import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import cn.com.dhcc.rp.connection.SocketConnection;
import cn.com.dhcc.rp.connection.SocketConnetionContainer;
import cn.com.dhcc.rp.connection.maintenance.ContainerMaintenanceJob;
import cn.com.dhcc.rp.connection.po.RoomCommStateFactory;
import cn.com.dhcc.rp.db.DBDelegate;
import cn.com.dhcc.rp.persistence.po.RoomCommState;
/**
 * 监测连接有效性，如果无效，进行重连。
 * 记录事件
 * @author PCITECC02
 *
 */
public class CheckConnectionValidJob extends ContainerMaintenanceJob{

	@Override
	public void doJob() {
		Map<String, SocketConnection> connectionMap = SocketConnetionContainer.getConnectionMap();
		String connCount = connectionMap.size() + "";
		log.info("连接检测, 当前连接个数:" + connCount);
		SqlSession sess = DBDelegate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		try{
			for (Map.Entry<String, SocketConnection> map : connectionMap.entrySet()) {
				SocketConnection conn = map.getValue();
				if(conn!=null && conn.valid()){
					log.info(conn.toString() + "。 集合元素个数：" + conn.getSetSize() + ", 连接状态:*有效*");
					//连接正常状态
					RoomCommState state = RoomCommStateFactory.getNormalRoomCommState(map.getKey());
					sess.insert("cn.com.dhcc.rp.connector.update_insert_comm_state", state);
					sess.commit();
				}else{
					log.warn(conn.toString() +  "连接断开");
					//连接异常状态
					RoomCommState state = RoomCommStateFactory.getExceptionRoomCommState(map.getKey());
					sess.insert("cn.com.dhcc.rp.connector.update_insert_comm_state", state);
					sess.commit();
					
					/*String eventContent = "";
					eventContent = "接口断开，地址信息：" + conn.toString();
					//连接断开告警
					ConnectionEvent disConnectedEvent = 
							ConnectionEventFactory.getEvent(map.getKey(), eventContent, ConnectionEvent.LINK_DISCONNECTED);
					sess.insert("cn.com.dhcc.rp.event.insert_connection_TxEvents", disConnectedEvent);
					sess.commit();*/
					
					map.getValue().stopThread();
					boolean reconnectSuccess = map.getValue().reConnect();
					if(reconnectSuccess){
						new Thread(map.getValue()).start();
						log.warn(conn.toString() + "。 连接断开，重连成功!");
						//正常状态
						RoomCommState commstate = RoomCommStateFactory.getNormalRoomCommState(map.getKey());
						sess.insert("cn.com.dhcc.rp.connector.update_insert_comm_state", commstate);
						sess.commit();
						/*
						//连接断开恢复
						eventContent = "接口通讯恢复";
						ConnectionEvent reConnectedEvent = 
								ConnectionEventFactory.getEvent(map.getKey(), eventContent, ConnectionEvent.LINK_CONNECTED);
						sess.insert("cn.com.dhcc.rp.event.insert_connection_TxEvents", reConnectedEvent);
						sess.commit();
						*/
						
					}else{
						log.warn(conn.toString() + "。 连接断开，重连失败!");
					}
				}
			}
		}finally{
			sess.close();
		}
	}
	

}
