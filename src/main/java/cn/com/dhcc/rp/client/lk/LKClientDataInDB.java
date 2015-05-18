package cn.com.dhcc.rp.client.lk;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import cn.com.dhcc.rp.client.RPClient;
import cn.com.dhcc.rp.connection.RealTimeData;
import cn.com.dhcc.rp.connection.lk.LKData;
import cn.com.dhcc.rp.db.DBDelegate;
import cn.com.dhcc.rp.machineroom.networkele.NetworkElement;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Equipment;
import cn.com.dhcc.rp.machineroom.networkele.equipment.EquipmentNode;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Group;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Module;
import cn.com.dhcc.rp.machineroom.networkele.subsystem.SubSystem;
import cn.com.dhcc.rp.persistence.Persistenceable;
import cn.com.dhcc.rp.persistence.factory.PersistenceFactory;


/**
 * ���زɼ� �ͻ���
 * @author PCITECC02
 *
 */
public final class LKClientDataInDB extends RPClient{
	@Override
	public boolean doCollect(List<NetworkElement> networkElementList) {
		this.networkElementList.addAll(networkElementList);
		getRealTimeData();
		process();
		persistence(networkElementList);
		return false;
	}
	private boolean persistence(List<NetworkElement> networkElementList){
		String remindInfo = "������⿪ʼ..";
		log.info(remindInfo);
		Date start = new Date();
		for (NetworkElement networkElement : networkElementList) {
			Persistenceable persis = 
					PersistenceFactory.getLKPersistenceInstance(networkElement.getClass(), roomInterfaceCof);
			if(persis!=null) {
				persis.persistance(networkElement);
			}else{
				log.warn("���ʧ�ܣ�" + networkElement);
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
		//�����ݿ��ȡʵʱ����
		SqlSession sess = DBDelegate.getSqlSessionFactory().openSession(ExecutorType.BATCH, true);
		int dataSize = 0;
    	try{
    		String comCode = this.roomInterfaceCof.getCompanyCode();
    		List<LKData> listData = 
    				sess.selectList("cn.com.dhcc.rp.realtimedata.select_lk_data_with_company_code",
    				comCode);
    		dataSize = listData!=null?listData.size():0;
    		this.realTimeDataSet = new HashSet<RealTimeData>(listData);
    	}finally{
            if(sess!=null){
            	sess.commit();
            	sess.clearCache();
            	sess.close();
            }
        }
    	//������������ȡ�ڴ���ʵʱ����
		//this.realTimeDataSet = SocketConnetionContainer.getRealData(this.roomInterfaceCof.getCompanyCode());
		log.info("���ݴ�С��" + dataSize + ", ȡ��ʵʱ�������");
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
			if(networkElement instanceof Equipment){    // �豸ʵ��
				Equipment equip = (Equipment)networkElement;
				List<Group> listGroup = equip.getListGroup();
				
				for (Group group : listGroup) {    //�ڵ��� 
					List<EquipmentNode> listNodes = group.getListNode();
					if(Group.TYPE_STATE.equals(group.getType())){    //״̬
						for (EquipmentNode equipmentNode : listNodes) {
							String queryID = networkElement.getId();
							LKData lkData = searchData(queryID, equipmentNode);
							equipmentNode.setRealData(lkData);
						}
					}else if(Group.TYPE_VALUE.equals(group.getType())){    //��ֵ
						for (EquipmentNode equipmentNode : listNodes) {
							String queryID = networkElement.getId() + 
									(equipmentNode.getQueryPartID()!=null?equipmentNode.getQueryPartID():"");
	//System.out.println("=======>queryID" + queryID);
							LKData lkData = searchData(queryID, equipmentNode);
							equipmentNode.setRealData(lkData);
						}
					}
				}
				
				
				List<Module> listModule = equip.getListMode();
				for (Module module : listModule) {    //ģ��
					List<EquipmentNode> listNodes = module.getListNode();
					if(Module.TYPE_NORMAL.equals(module.getType())){    //һ��ģ��
						for (EquipmentNode equipmentNode : listNodes) {
							String queryID = networkElement.getId() + 
									(module.getQueryPartID()!=null?module.getQueryPartID():"");
							LKData lkData = searchData(queryID, equipmentNode);
							equipmentNode.setRealData(lkData);
						}
					}else if(Module.TYPE_POWERBOX_LIKE.equals(module.getType())){    //��������ģ��
						String queryEquipID = networkElement.getId() + 
								(module.getPartID()!=null?module.getPartID():"");
	//System.out.println("=======>queryID = " + queryEquipID);
						for (EquipmentNode equipmentNode : listNodes) {
							LKData lkData = searchData(queryEquipID, equipmentNode);
							equipmentNode.setRealData(lkData);
						}
					}
				}
			}else if(networkElement instanceof SubSystem){    //��ϵͳʵ��
//System.out.println(">>>>>>>>��ϵͳ:" + networkElement);
				List<Equipment> listEquip = ((SubSystem) networkElement).getListEquip();
//System.out.println(">>>��ϵͳ�豸��" + listEquip);
/*for (Equipment equipment : listEquip) {
	System.out.println(equipment.getGroupSet());
}*/
				//���� ��ϵͳID �� �豸ID +�ڵ�ID
				String queryID = networkElement.getId();
				for (Equipment equipment : listEquip) {
					List<Group> listGroup = equipment.getListGroup();
					String equipmentID = equipment.getId();
					for (Group group : listGroup) {    //�ڵ��� 
						List<EquipmentNode> listNodes = group.getListNode();
						if(Group.TYPE_STATE.equals(group.getType()) || Group.TYPE_VALUE.equals(group.getType())){    //״̬or��ֵ
							for (EquipmentNode equipmentNode : listNodes) {
								String equipGid = equipmentID + 
										(equipmentNode.getId()==null?"":equipmentNode.getId());
								LKData lkData = searchData(queryID, equipGid);
//System.out.println("�ҵ���ϵͳ�е����ݣ�������" + lkData);
								equipmentNode.setRealData(lkData);
							}
						}
					}
				}
				
			}
			
		}
		log.info("�����ȡ���������");
		return true;
	}
	/**
	 * ��������
	 * @param node �ڵ�
	 * @return
	 */
	private LKData searchData(String searchID, EquipmentNode equipmentNode){
		LKData lkData = null;
		for (RealTimeData realData : realTimeDataSet) {
			LKData data = (LKData)realData;
			boolean isNodeData = searchID.equals(data.getD()) && equipmentNode.getId().equals(data.getId());
			if(isNodeData){
				lkData = data;
				break;
			}else {
				continue;
			}
		}
		
		return lkData;
	}
	/**
	 * ��������
	 * @param neID  ��ԪID
	 * @param nodeID    �ڵ�ID
	 * @return
	 */
	private LKData searchData(String neID, String nodeID){
		LKData lkData = null;
		for (RealTimeData realData : realTimeDataSet) {
			LKData data = (LKData)realData;
			boolean isNodeData = neID.equals(data.getD()) && nodeID.equals(data.getId());
			if(isNodeData){
				lkData = data;
				break;
			}else {
				continue;
			}
		}
		
		return lkData;
	}
}