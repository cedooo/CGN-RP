package cn.com.dhcc.rp.connection;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.com.dhcc.rp.connection.maintenance.ContainerMaintenanceJob;
import cn.com.dhcc.rp.connection.maintenance.impl.CheckConnectionValidJob;
import cn.com.dhcc.rp.persistence.po.RoomInterfaceConf;
import cn.com.dhcc.rp.roomInterConf.IntfsConfLoader;


/**
 * 连接容器-单例
 * @author PCITECC02
 *
 */
public class SocketConnetionContainer implements Runnable{
	static protected final Logger log = Logger.getLogger(SocketConnetionContainer.class.getClass());
	static final private long MAINTENANCE_INTERVAL_CHECK = 300*1000;    //维护任务间隔时间，单位: s
	
	static private SocketConnetionContainer container = new SocketConnetionContainer();
	//Map<'连接CODE', 连接对象>
	static private Map<String, SocketConnection> connectionMap = new HashMap<String, SocketConnection>();
	
	static private List<ContainerMaintenanceJob> listMaintenanceJob = new ArrayList<ContainerMaintenanceJob>();
	
	/**
	 * 初始化‘容器’
	 * @return 成功返回true，否则返回false
	 */
	public boolean init(){
		try {
			List<RoomInterfaceConf> listConfig = new ArrayList<RoomInterfaceConf>();
			
			//从数据库获取配置文件
			/*SqlSession ses = DBDelegate.getSqlSessionFactory().openSession(true);
			try{
				listConfig = ses.selectList("cn.com.dhcc.rp.connector.select_connectors");
			}finally{
				ses.close();
			}*/
			
			listConfig.addAll(IntfsConfLoader.getConnectionRoomInterfaceConf());
			
			for (RoomInterfaceConf roomInterfaceConf : listConfig) {
				log.debug("连接配置: " + roomInterfaceConf);
				SocketConnection socketConnection;
				socketConnection = (SocketConnection)Class.forName(roomInterfaceConf.getConnectionClass()).newInstance();
				int port = Integer.parseInt(roomInterfaceConf.getPort());
				InetSocketAddress inetSocketAddr = new InetSocketAddress(roomInterfaceConf.getIp(), port);
				boolean connected = socketConnection.init(roomInterfaceConf.getCompanyCode(), inetSocketAddr);
				if(connected){
					log.info("建立连接成功:" + socketConnection);
				}else{
					//连接建立失败告警
					log.warn("建立连接失败:" + socketConnection);
				}
				connectionMap.put(roomInterfaceConf.getCompanyCode(), socketConnection);
			}
			listMaintenanceJob.add(new CheckConnectionValidJob());    //添加容器维护任务 - 检查连接有效性
			return true;
		} catch (InstantiationException e) {
			e.printStackTrace();
			log.error("连接容器初始化失败");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			log.error("连接容器初始化失败");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error("连接容器初始化失败");
		}
		return false;
	}
	
	private SocketConnetionContainer(){}
	/**
	 * 得到‘容器’实例
	 * @return  ‘容器’实例
	 */
	static public SocketConnetionContainer getInstance(){
		return container;
	}
	/**
	 * 得到实时数据
	 * @param code 编码
	 * @return 实时数据集合， 不存在时，返回空的Set集合。
	 */
	static public Set<RealTimeData> getRealData(String code){
		SocketConnection connection = getInstance().getConnection(code);
		if(connection!=null){
			return connection.getRealDataSetISO();
		}else {
			return new HashSet<RealTimeData>();
		}
	}

	/**
	 * 得到连接
	 * @param code 任务编码
	 * @return 连接
	 */
	public SocketConnection getConnection(String code){
		SocketConnection socketConnection = connectionMap.get(code);
		return socketConnection;
	}
	@Override
	public void run() {
		for (Map.Entry<String, SocketConnection> map : connectionMap.entrySet()) {
			new Thread(map.getValue()).start();
		}
		try {
			while(true){
				Thread.sleep(MAINTENANCE_INTERVAL_CHECK);
				try{
					for (ContainerMaintenanceJob job : listMaintenanceJob) {
						job.doJob();
					}
				}catch(Exception e){
					log.error("容器管理任务出现错误！ " + e.getMessage());
					continue;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, SocketConnection> getConnectionMap() {
		return connectionMap;
	}
	
}
