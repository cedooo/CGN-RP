package cn.com.dhcc.rp.client.gj;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cn.com.dhcc.rp.client.RPClient;
import cn.com.dhcc.rp.connection.RealTimeData;
import cn.com.dhcc.rp.connection.SocketConnetionContainer;
import cn.com.dhcc.rp.connection.gj.GJData;
import cn.com.dhcc.rp.machineroom.networkele.NetworkElement;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Equipment;
import cn.com.dhcc.rp.machineroom.networkele.equipment.EquipmentNode;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Group;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Module;
import cn.com.dhcc.rp.persistence.Persistenceable;
import cn.com.dhcc.rp.persistence.factory.PersistenceFactory;


/**
 * ���� �ɼ� �ͻ���
 * @author PCITECC02
 *
 */
public final class GJClient extends RPClient{
	static private final String EQUIP_NODE_DECOLLATOR = "-";
	static private final String EQUIP_PRE_TAG = "S0" + EQUIP_NODE_DECOLLATOR;
	@Override
	public boolean doCollect(List<NetworkElement> networkElementList) {
		this.networkElementList.addAll(networkElementList);
		getRealTimeData();
		process();
		persistence(this.networkElementList);
		return false;
	}
	private boolean persistence(List<NetworkElement> networkElementList){
		String remindInfo = "������⿪ʼ..";
		log.info(remindInfo);
		Date start = new Date();
		for (NetworkElement networkElement : networkElementList) {
			Persistenceable persis = 
					PersistenceFactory.getGJPersistenceInstance(networkElement.getClass(), roomInterfaceCof);
			if(persis!=null) {
				persis.persistance(networkElement);
			}else{
				log.error("���ʧ�ܣ�" + networkElement);
			}
		}
		
		remindInfo = "����������:" + networkElementList.size() + "��, ��ʱ:" + (new Date().getTime() - start.getTime()) + "ms";
		log.info(remindInfo);
		return false;
	}
	
	/**
	 * �õ�ʵʱ����
	 * @return
	 */
	private boolean getRealTimeData(){
		String remindInfo = "ȡ��ʵʱ����..";
		log.info(remindInfo);
		this.realTimeDataSet = 
				SocketConnetionContainer.getRealData(roomInterfaceCof.getCompanyCode());
		log.info("set.size=" + realTimeDataSet.size() + ", ȡ��ʵʱ�������");
		if(this.realTimeDataSet.size()>0){
			return true;
		}else {
			return false;
		}
	}
	/**
	 * ��������
	 * ��ʵʱ���� ��ע�롯���豸������
	 * @return
	 */
	private boolean process() {
		String remindInfo = "�����ȡ�����ݿ�ʼ..";
		log.info(remindInfo);
		
		List<NetworkElement> listElement = this.networkElementList;
		for (NetworkElement networkElement : listElement) {
			Equipment equip = (Equipment)networkElement;
			List<Group> listGroup = equip.getListGroup();

			String queryID = EQUIP_PRE_TAG + equip.getId();
			for (Group group : listGroup) {    //�ڵ��� 
				List<EquipmentNode> listNodes = group.getListNode();
				if(Group.TYPE_STATE.equals(group.getType())){    //״̬
					for (EquipmentNode equipmentNode : listNodes) {
						GJData gjData = searchData(queryID, equipmentNode);
						equipmentNode.setRealData(gjData);
					}
				}else if(Group.TYPE_VALUE.equals(group.getType())){    //��ֵ
					for (EquipmentNode equipmentNode : listNodes) {
						log.debug("queryID=======>" + queryID);
						GJData gjData = searchData(queryID, equipmentNode);
						equipmentNode.setRealData(gjData);
						log.debug("ʵʱ����========>" + gjData);
					}
				}
			}
			
			
			List<Module> listModule = equip.getListMode();
			for (Module module : listModule) {    //ģ��
				List<EquipmentNode> listNodes = module.getListNode();
				if(Module.TYPE_NORMAL.equals(module.getType())){    //һ��ģ��
					for (EquipmentNode equipmentNode : listNodes) {
						log.debug("queryID=======>" + queryID);
						GJData gjData = searchData(queryID, equipmentNode);
						equipmentNode.setRealData(gjData);
						log.debug("ʵʱ����========>" + gjData);
					}
				}
			}
			
		}
		
		remindInfo = "�����ȡ���������";
		log.info(remindInfo);
		return true;
	}
	/**
	 * ��������
	 * @param node �ڵ�
	 * @return
	 */
	private GJData searchData(String searchID, EquipmentNode equipmentNode){
		GJData gjData = null;
		Iterator<RealTimeData> iterator = realTimeDataSet.iterator();
		while(iterator.hasNext()){
			GJData data = (GJData)iterator.next();
			String key = searchID +  
					(equipmentNode.getId()==null?"":EQUIP_NODE_DECOLLATOR + equipmentNode.getId()) 
					+ GJData.ATTR_VALUE_TAG;
			//log.debug("��ѯ���ݹؼ��֡�key�� = " + key);
			boolean isNodeData = data.getKey().equals(key);
			if(isNodeData){
				gjData = data;
				break;
			}else {
				continue;
			}
		}
		return gjData;
	}
}