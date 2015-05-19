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
 * corba客户端 抽象类<br />
 * <strong>模版方法 模式</strong>
 * @author PCITECC02
 *
 */
public abstract class CorbaClient extends RPClient {
	
	@Override
	public boolean doCollect(List<NetworkElement> networkElementList) {
		return doCollect();
	}
	/**
	 * 采集
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
								log.warn("数据入库失败！");
								return false;
							}
						}else{
							log.warn("转换数据失败！");
							return false;
						}
					}else{
						log.warn("获取数据失败！");
						return false;
					}
				}catch(Exception e){
					//生成连接异常告警和状态
					RoomCommState exState = RoomCommStateFactory.getExceptionRoomCommState(code);
					sess.insert("cn.com.dhcc.rp.connector.update_insert_comm_state", exState);
					sess.commit();
					/*String disconContent = "接口断开，地址信息：" + addrInfo;
					ConnectionEvent disConnectedEvent = 
							ConnectionEventFactory.getEvent(code, disconContent, ConnectionEvent.LINK_DISCONNECTED);
					sess.insert("cn.com.dhcc.rp.event.insert_connection_TxEvents", disConnectedEvent);
					sess.commit();*/
					log.warn(e.getMessage());
				}
				return true;
			default:
				//生成连接异常告警和状态
				RoomCommState exState = RoomCommStateFactory.getExceptionRoomCommState(code);
				sess.insert("cn.com.dhcc.rp.connector.update_insert_comm_state", exState);
				sess.commit();
				/*String disconContent = "接口断开，地址信息：" + addrInfo;
				ConnectionEvent disConnectedEvent = 
						ConnectionEventFactory.getEvent(code, disconContent, ConnectionEvent.LINK_DISCONNECTED);
				sess.insert("cn.com.dhcc.rp.event.insert_connection_TxEvents", disConnectedEvent);
				sess.commit();*/
				log.warn("corba接口连接异常!" + this.roomInterfaceCof);
				return false;
			}
		}finally{
			sess.close();
			this.releaseSource();
		}
	}
	/**
	 * 连接接口
	 * @return
	 */
	abstract protected InterfaceConnectResult connectInterface();
	/**
	 * 获取数据,存入数据库或者集合中。
	 * @return
	 */
	abstract protected boolean getData() throws Exception;
	/**
	 * 转换 获得的数据
	 * @return
	 */
	abstract protected boolean process();
	/**
	 * 持久化 ||入"临时"库
	 * @return
	 * @throws SQLException 
	 */
	abstract protected boolean persistent() throws SQLException;
	/**
	 *  关闭corba orb 释放相关资源
	 * @return 
	 */
	abstract protected boolean releaseSource();
}
