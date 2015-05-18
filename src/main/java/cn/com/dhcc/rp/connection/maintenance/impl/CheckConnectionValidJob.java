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
 * ���������Ч�ԣ������Ч������������
 * ��¼�¼�
 * @author PCITECC02
 *
 */
public class CheckConnectionValidJob extends ContainerMaintenanceJob{

	@Override
	public void doJob() {
		Map<String, SocketConnection> connectionMap = SocketConnetionContainer.getConnectionMap();
		String connCount = connectionMap.size() + "";
		log.info("���Ӽ��, ��ǰ���Ӹ���:" + connCount);
		SqlSession sess = DBDelegate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		try{
			for (Map.Entry<String, SocketConnection> map : connectionMap.entrySet()) {
				SocketConnection conn = map.getValue();
				if(conn!=null && conn.valid()){
					log.info(conn.toString() + "�� ����Ԫ�ظ�����" + conn.getSetSize() + ", ����״̬:*��Ч*");
					//��������״̬
					RoomCommState state = RoomCommStateFactory.getNormalRoomCommState(map.getKey());
					sess.insert("cn.com.dhcc.rp.connector.update_insert_comm_state", state);
					sess.commit();
				}else{
					log.warn(conn.toString() +  "���ӶϿ�");
					//�����쳣״̬
					RoomCommState state = RoomCommStateFactory.getExceptionRoomCommState(map.getKey());
					sess.insert("cn.com.dhcc.rp.connector.update_insert_comm_state", state);
					sess.commit();
					
					/*String eventContent = "";
					eventContent = "�ӿڶϿ�����ַ��Ϣ��" + conn.toString();
					//���ӶϿ��澯
					ConnectionEvent disConnectedEvent = 
							ConnectionEventFactory.getEvent(map.getKey(), eventContent, ConnectionEvent.LINK_DISCONNECTED);
					sess.insert("cn.com.dhcc.rp.event.insert_connection_TxEvents", disConnectedEvent);
					sess.commit();*/
					
					map.getValue().stopThread();
					boolean reconnectSuccess = map.getValue().reConnect();
					if(reconnectSuccess){
						new Thread(map.getValue()).start();
						log.warn(conn.toString() + "�� ���ӶϿ��������ɹ�!");
						//����״̬
						RoomCommState commstate = RoomCommStateFactory.getNormalRoomCommState(map.getKey());
						sess.insert("cn.com.dhcc.rp.connector.update_insert_comm_state", commstate);
						sess.commit();
						/*
						//���ӶϿ��ָ�
						eventContent = "�ӿ�ͨѶ�ָ�";
						ConnectionEvent reConnectedEvent = 
								ConnectionEventFactory.getEvent(map.getKey(), eventContent, ConnectionEvent.LINK_CONNECTED);
						sess.insert("cn.com.dhcc.rp.event.insert_connection_TxEvents", reConnectedEvent);
						sess.commit();
						*/
						
					}else{
						log.warn(conn.toString() + "�� ���ӶϿ�������ʧ��!");
					}
				}
			}
		}finally{
			sess.close();
		}
	}
	

}
