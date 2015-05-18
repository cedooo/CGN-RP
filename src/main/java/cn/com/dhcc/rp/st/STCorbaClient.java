package cn.com.dhcc.rp.st;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import cn.com.dhcc.rp.client.CorbaClient;
import cn.com.dhcc.rp.db.DBDelegate;
import cn.com.dhcc.rp.persistence.po.RoomCommState;
import cn.com.dhcc.rp.persistence.po.RoomInterfaceConf;
import cn.com.dhcc.rp.persistence.po.TxUpsCommPerf;
import cn.com.dhcc.rp.pojo.InterfaceConnectResult;
import cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisher;
import cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisherHelper;
import cn.com.dhcc.rp.st.def.LoadDef;
import cn.com.dhcc.rp.st.def.StUpsDef;
import cn.com.dhcc.rp.st.def.StUpsNodeAttrDef;
import cn.com.dhcc.rp.st.def.StUpsNodeDef;
import cn.com.dhcc.rp.st.orb.OrbConstants;
import cn.com.dhcc.rp.utils.CodeUtils;

public final class STCorbaClient extends CorbaClient {
	private static final Logger log = Logger.getLogger(STCorbaClient.class.getClass());
	private String[] corbaLinkArgs = null;
	
	static final private StUpsDef stupsDef = LoadDef.getStups();

	private DataPublisher dataPublisher = null;
	private ORB orb = null;

	private Map<String, List<UPSNode>> upsPerfNodeMap = new HashMap<String,List<UPSNode>>();
	private Map<String, List<UPSNode>> upsStateNodeMap = new HashMap<String,List<UPSNode>>();

	private List<TxUpsCommPerf> listUpsPerf = new ArrayList<TxUpsCommPerf>();
	private List<RoomCommState> listUpsState = new ArrayList<RoomCommState>();
	
	private static String[] deviceIDs = null;
	static{
		Properties pro = new Properties();
		try {
			pro.load(STCorbaClient.class.getClassLoader().getResourceAsStream("cn/com/dhcc/rp/st/st-ups-collect.propertites"));
			deviceIDs = pro.getProperty("upssid").split(",");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		SqlSession sess = DBDelegate.getSqlSessionFactory().openSession();
		RoomInterfaceConf roomInterfaceCof = sess.selectOne("cn.com.dhcc.rp.comm.select_interface_info_by_company_code", "ST");
		sess.close();
		STCorbaClient cli = new STCorbaClient(roomInterfaceCof);
		cli.doCollect();
				
	}
	public STCorbaClient(RoomInterfaceConf roomInterfaceCof) {
		this.roomInterfaceCof = roomInterfaceCof;
		corbaLinkArgs = new String[] {
				"-ORBInitRef",
				"NameService=corbaloc::"
						+ this.roomInterfaceCof.getIp() + ":"
						+ this.roomInterfaceCof.getPort() };
	}

	@Override
	protected boolean persistent() throws SQLException {
		String remindInfo = "������⿪ʼ..";
		log.info(remindInfo);
		Date start = new Date();
		// �������
		SqlSessionFactory sessionFactory = DBDelegate.getSqlSessionFactory();
		SqlSession session = sessionFactory.openSession(ExecutorType.BATCH, false);
		try {
			// ����ɼ����� ����ֵ������
			for (TxUpsCommPerf entry : listUpsPerf) {
				session.update("cn.com.dhcc.rp.ups.update_insert_comm_ups_perf", entry);
			}
			session.commit();
			session.clearCache();
			// ����ɼ����� ��״̬������
			for (RoomCommState entry : listUpsState) {
				session.update("cn.com.dhcc.rp.state.update_insert_comm_state", entry);
			}
			session.commit();
		} catch (Exception e) {
			//e.printStackTrace();
			log.debug(e);
			log.warn("��ͨ�������ʧ��");
			return false;
		} finally {
			session.close();
		}
		remindInfo = "����������, ��ʱ:" + (new Date().getTime() - start.getTime()) + "ms";
		log.info(remindInfo);
		return true;
	}

	@Override
	protected boolean getData() throws Exception{
		String remindInfo = "ͨ���ӿڻ�ȡ���ݿ�ʼ..";
		Date start = new Date();
		log.info(remindInfo);
		//��ȡ����
		StringHolder strHolder = new StringHolder();
		List<StUpsNodeDef> listNodeDef = stupsDef.getListNodeDef();
		try{
			for (int i = 0; i < deviceIDs.length; i++) {
				List<UPSNode> listUPSPerfNode = new ArrayList<UPSNode>();    //ups���ݽڵ� ����
				List<UPSNode> listUPSStateNode = new ArrayList<UPSNode>();    //UPS״̬�ڵ�����
				for (StUpsNodeDef nodeDef : listNodeDef) {
					if(StUpsNodeDef.COLLECT_TRUE.equals(nodeDef.getCollect())){   //�ڵ�
						List<StUpsNodeAttrDef> listNodeAttrDef = nodeDef.getListAttr();
						UPSNode upsNode = new UPSNode();
						for (StUpsNodeAttrDef nodeAttrDef: listNodeAttrDef) {    //�ڵ�����
							if(StUpsNodeAttrDef.COLLECT_TRUE.equals(nodeAttrDef.getCollect())){
								String globalID = deviceIDs[i] + "." + nodeDef.getId() + "." + nodeAttrDef.getName();
								dataPublisher.GetData(globalID, strHolder);
								String value = CodeUtils.parse(strHolder.value, "iso8859_1", "GB2312");
								String methods = "set" + nodeAttrDef.getName();
								upsNode.setID(nodeDef.getId());
								try {
									UPSNode.class.getDeclaredMethod(methods, String.class).invoke(upsNode, value);
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (SecurityException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								} catch (NoSuchMethodException e) {
									e.printStackTrace();
								}
							}else{continue;}
						}
						if(StUpsNodeDef.TYPE_STATE_VALUE.equals(nodeDef.getType())){
							listUPSStateNode.add(upsNode);
						}else if(StUpsNodeDef.TYPE_PERF_VALUE.equals(nodeDef.getType())){
							listUPSPerfNode.add(upsNode);
						}
					}else{continue;}
				}
				upsPerfNodeMap.put(deviceIDs[i], listUPSPerfNode);
				upsStateNodeMap.put(deviceIDs[i], listUPSStateNode);
			}
		}catch(Exception e){
			throw new Exception("��ͨ��ȡ����ʧ��");
		}
		remindInfo = "ͨ���ӿڻ�ȡ�������, ��ʱ:"
				+ (new Date().getTime() - start.getTime()) + "ms";
		log.info(remindInfo);
		return true;
	}

	@Override
	protected boolean process() {
		String remindInfo = "�����ȡ�����ݿ�ʼ..";
		Date start = new Date();
		log.info(remindInfo);
		Date colDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String colDateStr = sdf.format(colDate);
		//�����ȡ������
			//������ֵ��
			for (Map.Entry<String, List<UPSNode>> listRecords : upsPerfNodeMap.entrySet()) {
				TxUpsCommPerf tups = new TxUpsCommPerf();
				String deviceID = listRecords.getKey();
				tups.setId(deviceID);
				tups.setCollectTime(colDateStr);
				tups.setCompanyCode(this.roomInterfaceCof.getCompanyCode());
				List<UPSNode> listUPSNode = listRecords.getValue();
				for (UPSNode upsNode : listUPSNode) {
					List<StUpsNodeDef> listUpsNodeDef = stupsDef.getListNodeDef();
					String mapName = null;
					for (StUpsNodeDef stUpsNodeDef : listUpsNodeDef) {
						if(stUpsNodeDef.getId().equals(upsNode.getID())){
							mapName = stUpsNodeDef.getRef();
							mapName = mapName.substring(0, 1).toUpperCase() + mapName.substring(1);
							try {
								String methodName = "set" + mapName;
								TxUpsCommPerf.class.getDeclaredMethod(methodName, String.class).invoke(tups, upsNode.getValue() + upsNode.getUnit());
							} catch (IllegalArgumentException e) {
							} catch (SecurityException e) {
							} catch (IllegalAccessException e) {
							} catch (InvocationTargetException e) {
							} catch (NoSuchMethodException e) {
							}
							break;
						}else{continue;}
					}
				}
				listUpsPerf.add(tups);
			}
			//����״̬��
			for (Map.Entry<String, List<UPSNode>> listRecords : upsStateNodeMap.entrySet()) {
				String deviceID = listRecords.getKey();
				List<UPSNode> listUPSNode = listRecords.getValue();
				for (UPSNode upsNode : listUPSNode) {
					RoomCommState roomState = new RoomCommState();
					roomState.setId(deviceID);
					roomState.setCollectTime(colDateStr);
					roomState.setPartID(upsNode.getName());
					roomState.setStateValue(upsNode.getValue());
					roomState.setCompanyCode(this.roomInterfaceCof.getCompanyCode());
					listUpsState.add(roomState);
					//����UPS����״̬
					/*List<StUpsNodeDef> listUpsNodeDef = stupsDef.getListNodeDef();
					for (StUpsNodeDef stUpsNodeDef : listUpsNodeDef) {
						if(stUpsNodeDef.getId().equals(upsNode.getID())){
							// �жϺ���״̬
							boolean isGreen = stUpsNodeDef.getGrState()!=null &&
									stUpsNodeDef.getGrState().equals(upsNode.getValue());
							if(isGreen){
								roomState.setGrState("1");
							}else{
								roomState.setGrState("0");
							}
						}else{continue;}
					}*/
				}
			}
		
		remindInfo = "�����ȡ���������, "
				+ ("��ʱ:" + (new Date().getTime() - start.getTime()) + "ms");
		
		log.info(remindInfo);
		return true;
	}
	@Override
	protected InterfaceConnectResult connectInterface() {
		InterfaceConnectResult initResult = null;
		log.debug("�ӿ�������Ϣ:" + roomInterfaceCof);
		try {
			orb = ORB.init(corbaLinkArgs, null);
			org.omg.CORBA.Object objRef = orb
					.resolve_initial_references(OrbConstants.ORB_INIT_REF);
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			NameComponent path[] = ncRef.to_name(OrbConstants.ORB_OBJ_REF_NAME);
			dataPublisher = DataPublisherHelper.narrow(ncRef.resolve(path));
			initResult = InterfaceConnectResult.CONNECTED;
			log.debug("�ӿ����ӽ����" + initResult);
		} catch (InvalidName e) {
			initResult = InterfaceConnectResult.REFUSED_CONNECT;
			log.warn("��ͨ�ӿڲ������ӣ�" + initResult);
		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			initResult = InterfaceConnectResult.REFUSED_CONNECT;
			log.warn("��ͨ�ӿڲ������ӣ�" + initResult);
		} catch (NotFound e) {
			initResult = InterfaceConnectResult.HOST_NOT_FOUND;
			log.warn("��ͨ�ӿڲ������ӣ�" + initResult);
		} catch (CannotProceed e) {
			initResult = InterfaceConnectResult.REFUSED_CONNECT;
			log.warn("��ͨ�ӿڲ������ӣ�" + initResult);
		} catch (Exception e) {
			initResult = InterfaceConnectResult.CONNECT_TIME_OUT;
			log.warn("��ͨ�ӿڲ������ӣ�" + initResult);
		} 
		return initResult;
	}

	@Override
	protected boolean releaseSource() {
		if(this.orb!=null){
			this.orb.shutdown(false);
			this.orb.destroy();
			dataPublisher._release();
			return true;
		}else {
			log.warn("��ͨ�ӿ��ͷ���Դʱ�� orbΪnull.");
			return false;
		}
	}

}