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
 * 龙控采集 客户端
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
		String remindInfo = "数据入库开始..";
		log.info(remindInfo);
		Date start = new Date();
		for (NetworkElement networkElement : networkElementList) {
			Persistenceable persis = 
					PersistenceFactory.getLKPersistenceInstance(networkElement.getClass(), roomInterfaceCof);
			if(persis!=null) {
				persis.persistance(networkElement);
			}else{
				log.warn("入库失败：" + networkElement);
			}
		}
		
		remindInfo = "数据入库完成:" + networkElementList.size() + "个, 用时:" + (new Date().getTime() - start.getTime()) + "ms";
		log.info(remindInfo);
		return false;
	}
	
	/**
	 * 得到实时数据
	 * @return
	 */
	private boolean getRealTimeData(){
		String remindInfo = "取得实时数据..";
		log.info(remindInfo);
		//从数据库读取实时数据
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
    	//从连接容器获取内存中实时数据
		//this.realTimeDataSet = SocketConnetionContainer.getRealData(this.roomInterfaceCof.getCompanyCode());
		log.info("数据大小：" + dataSize + ", 取得实时数据完成");
		if(this.realTimeDataSet.size()>0){
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 处理数据
	 * 将实时数据 ‘注入’到设备对象中
	 * @return
	 */
	private boolean process() {
		String remindInfo = "处理获取的数据开始..";
		log.info(remindInfo);
		
		List<NetworkElement> listElement = this.networkElementList;
		for (NetworkElement networkElement : listElement) {
			if(networkElement instanceof Equipment){    // 设备实例
				Equipment equip = (Equipment)networkElement;
				List<Group> listGroup = equip.getListGroup();
				
				for (Group group : listGroup) {    //节点组 
					List<EquipmentNode> listNodes = group.getListNode();
					if(Group.TYPE_STATE.equals(group.getType())){    //状态
						for (EquipmentNode equipmentNode : listNodes) {
							String queryID = networkElement.getId();
							LKData lkData = searchData(queryID, equipmentNode);
							equipmentNode.setRealData(lkData);
						}
					}else if(Group.TYPE_VALUE.equals(group.getType())){    //数值
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
				for (Module module : listModule) {    //模块
					List<EquipmentNode> listNodes = module.getListNode();
					if(Module.TYPE_NORMAL.equals(module.getType())){    //一般模块
						for (EquipmentNode equipmentNode : listNodes) {
							String queryID = networkElement.getId() + 
									(module.getQueryPartID()!=null?module.getQueryPartID():"");
							LKData lkData = searchData(queryID, equipmentNode);
							equipmentNode.setRealData(lkData);
						}
					}else if(Module.TYPE_POWERBOX_LIKE.equals(module.getType())){    //配电柜类似模块
						String queryEquipID = networkElement.getId() + 
								(module.getPartID()!=null?module.getPartID():"");
	//System.out.println("=======>queryID = " + queryEquipID);
						for (EquipmentNode equipmentNode : listNodes) {
							LKData lkData = searchData(queryEquipID, equipmentNode);
							equipmentNode.setRealData(lkData);
						}
					}
				}
			}else if(networkElement instanceof SubSystem){    //子系统实例
//System.out.println(">>>>>>>>子系统:" + networkElement);
				List<Equipment> listEquip = ((SubSystem) networkElement).getListEquip();
//System.out.println(">>>子系统设备：" + listEquip);
/*for (Equipment equipment : listEquip) {
	System.out.println(equipment.getGroupSet());
}*/
				//根据 子系统ID 》 设备ID +节点ID
				String queryID = networkElement.getId();
				for (Equipment equipment : listEquip) {
					List<Group> listGroup = equipment.getListGroup();
					String equipmentID = equipment.getId();
					for (Group group : listGroup) {    //节点组 
						List<EquipmentNode> listNodes = group.getListNode();
						if(Group.TYPE_STATE.equals(group.getType()) || Group.TYPE_VALUE.equals(group.getType())){    //状态or数值
							for (EquipmentNode equipmentNode : listNodes) {
								String equipGid = equipmentID + 
										(equipmentNode.getId()==null?"":equipmentNode.getId());
								LKData lkData = searchData(queryID, equipGid);
//System.out.println("找到子系统中的数据：：：：" + lkData);
								equipmentNode.setRealData(lkData);
							}
						}
					}
				}
				
			}
			
		}
		log.info("处理获取的数据完成");
		return true;
	}
	/**
	 * 搜索数据
	 * @param node 节点
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
	 * 搜索数据
	 * @param neID  网元ID
	 * @param nodeID    节点ID
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