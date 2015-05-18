package cn.com.dhcc.rp.client;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import cn.com.dhcc.rp.connection.po.RoomCommStateFactory;
import cn.com.dhcc.rp.db.DBDelegate;
import cn.com.dhcc.rp.machineroom.networkele.NetworkElement;
import cn.com.dhcc.rp.persistence.po.RoomCommState;
import cn.com.dhcc.rp.pojo.InterfaceConnectResult;
/**
 * corba�ͻ��� ������<br />
 * <strong>ģ�淽�� ģʽ</strong>
 * @author PCITECC02
 *
 */
public abstract class CorbaClient extends RPClient {
	
	@Override
	public boolean doCollect(List<NetworkElement> networkElementList) {
		return doCollect();
	}
	/**
	 * �ɼ�
	 * @return
	 */
	public boolean doCollect(){
		InterfaceConnectResult connectResult = this.connectInterface();
		log.info(connectResult);
		SqlSession sess = DBDelegate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		String code = this.roomInterfaceCof.getCompanyCode();
		//String addrInfo = this.roomInterfaceCof.getIp() + ": "+ this.roomInterfaceCof.getPort();
		try{
			switch(connectResult){
			case CONNECTED:
				
				RoomCommState state = RoomCommStateFactory.getNormalRoomCommState(code);
				sess.insert("cn.com.dhcc.rp.connector.update_insert_comm_state", state);
				sess.commit();

				try{
					boolean getSuccess = this.getData();
					if(getSuccess){
						boolean parseSuccess = this.process();
						if(parseSuccess){
							try {
								return this.persistent();
							} catch (SQLException e) {
								log.warn("�������ʧ�ܣ�");
								return false;
							}
						}else{
							log.warn("ת������ʧ�ܣ�");
							return false;
						}
					}else{
						log.warn("��ȡ����ʧ�ܣ�");
						return false;
					}
				}catch(Exception e){
					//���������쳣�澯��״̬
					RoomCommState exState = RoomCommStateFactory.getExceptionRoomCommState(code);
					sess.insert("cn.com.dhcc.rp.connector.update_insert_comm_state", exState);
					sess.commit();
					/*String disconContent = "�ӿڶϿ�����ַ��Ϣ��" + addrInfo;
					ConnectionEvent disConnectedEvent = 
							ConnectionEventFactory.getEvent(code, disconContent, ConnectionEvent.LINK_DISCONNECTED);
					sess.insert("cn.com.dhcc.rp.event.insert_connection_TxEvents", disConnectedEvent);
					sess.commit();*/
					log.warn(e.getMessage());
				}
				return true;
			default:
				//���������쳣�澯��״̬
				RoomCommState exState = RoomCommStateFactory.getExceptionRoomCommState(code);
				sess.insert("cn.com.dhcc.rp.connector.update_insert_comm_state", exState);
				sess.commit();
				/*String disconContent = "�ӿڶϿ�����ַ��Ϣ��" + addrInfo;
				ConnectionEvent disConnectedEvent = 
						ConnectionEventFactory.getEvent(code, disconContent, ConnectionEvent.LINK_DISCONNECTED);
				sess.insert("cn.com.dhcc.rp.event.insert_connection_TxEvents", disConnectedEvent);
				sess.commit();*/
				log.warn("corba�ӿ������쳣!" + this.roomInterfaceCof);
				return false;
			}
		}finally{
			sess.close();
			this.releaseSource();
		}
	}
	/**
	 * ���ӽӿ�
	 * @return
	 */
	abstract protected InterfaceConnectResult connectInterface();
	/**
	 * ��ȡ����,�������ݿ���߼����С�
	 * @return
	 */
	abstract protected boolean getData() throws Exception;
	/**
	 * ת�� ��õ�����
	 * @return
	 */
	abstract protected boolean process();
	/**
	 * �־û� ||��"��ʱ"��
	 * @return
	 * @throws SQLException 
	 */
	abstract protected boolean persistent() throws SQLException;
	/**
	 *  �ر�corba orb �ͷ������Դ
	 * @return 
	 */
	abstract protected boolean releaseSource();
}
