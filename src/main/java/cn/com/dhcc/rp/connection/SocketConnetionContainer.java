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
 * ��������-����
 * @author PCITECC02
 *
 */
public class SocketConnetionContainer implements Runnable{
	static protected final Logger log = Logger.getLogger(SocketConnetionContainer.class.getClass());
	static final private long MAINTENANCE_INTERVAL_CHECK = 300*1000;    //ά��������ʱ�䣬��λ: s
	
	static private SocketConnetionContainer container = new SocketConnetionContainer();
	//Map<'����CODE', ���Ӷ���>
	static private Map<String, SocketConnection> connectionMap = new HashMap<String, SocketConnection>();
	
	static private List<ContainerMaintenanceJob> listMaintenanceJob = new ArrayList<ContainerMaintenanceJob>();
	
	/**
	 * ��ʼ����������
	 * @return �ɹ�����true�����򷵻�false
	 */
	public boolean init(){
		try {
			List<RoomInterfaceConf> listConfig = new ArrayList<RoomInterfaceConf>();
			
			//�����ݿ��ȡ�����ļ�
			/*SqlSession ses = DBDelegate.getSqlSessionFactory().openSession(true);
			try{
				listConfig = ses.selectList("cn.com.dhcc.rp.connector.select_connectors");
			}finally{
				ses.close();
			}*/
			
			listConfig.addAll(IntfsConfLoader.getConnectionRoomInterfaceConf());
			
			for (RoomInterfaceConf roomInterfaceConf : listConfig) {
				log.debug("��������: " + roomInterfaceConf);
				SocketConnection socketConnection;
				socketConnection = (SocketConnection)Class.forName(roomInterfaceConf.getConnectionClass()).newInstance();
				int port = Integer.parseInt(roomInterfaceConf.getPort());
				InetSocketAddress inetSocketAddr = new InetSocketAddress(roomInterfaceConf.getIp(), port);
				boolean connected = socketConnection.init(roomInterfaceConf.getCompanyCode(), inetSocketAddr);
				if(connected){
					log.info("�������ӳɹ�:" + socketConnection);
				}else{
					//���ӽ���ʧ�ܸ澯
					log.warn("��������ʧ��:" + socketConnection);
				}
				connectionMap.put(roomInterfaceConf.getCompanyCode(), socketConnection);
			}
			listMaintenanceJob.add(new CheckConnectionValidJob());    //�������ά������ - ���������Ч��
			return true;
		} catch (InstantiationException e) {
			e.printStackTrace();
			log.error("����������ʼ��ʧ��");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			log.error("����������ʼ��ʧ��");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error("����������ʼ��ʧ��");
		}
		return false;
	}
	
	private SocketConnetionContainer(){}
	/**
	 * �õ���������ʵ��
	 * @return  ��������ʵ��
	 */
	static public SocketConnetionContainer getInstance(){
		return container;
	}
	/**
	 * �õ�ʵʱ����
	 * @param code ����
	 * @return ʵʱ���ݼ��ϣ� ������ʱ�����ؿյ�Set���ϡ�
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
	 * �õ�����
	 * @param code �������
	 * @return ����
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
					log.error("��������������ִ��� " + e.getMessage());
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
