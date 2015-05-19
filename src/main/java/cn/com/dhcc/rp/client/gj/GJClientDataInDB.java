package cn.com.dhcc.rp.client.gj;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import cn.com.dhcc.rp.client.RPClient;
import cn.com.dhcc.rp.connection.RealTimeData;
import cn.com.dhcc.rp.connection.gj.GJData;
import cn.com.dhcc.rp.db.DBDelegate;
import cn.com.dhcc.rp.machineroom.networkele.NetworkElement;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Equipment;
import cn.com.dhcc.rp.machineroom.networkele.equipment.EquipmentNode;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Group;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Module;
import cn.com.dhcc.rp.persistence.Persistenceable;
import cn.com.dhcc.rp.persistence.factory.PersistenceFactory;


/**
 * 共济 采集 客户端
 * @author PCITECC02
 *
 */
public final class GJClientDataInDB extends RPClient{
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
		String remindInfo = "数据入库开始..";
		log.info(remindInfo);
		Date start = new Date();
		for (NetworkElement networkElement : networkElementList) {
			Persistenceable persis = 
					PersistenceFactory.getGJPersistenceInstance(networkElement.getClass(), roomInterfaceCof);
			if(persis!=null) {
				persis.persistance(networkElement);
			}else{
				log.error("入库失败：" + networkElement);
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
    	try{
    		String comCode = this.roomInterfaceCof.getCompanyCode();
    		List<GJData> listData = 
    				sess.selectList("cn.com.dhcc.rp.realtimedata.select_gj_data_with_company_code",
    				comCode);
    		this.realTimeDataSet = new HashSet<RealTimeData>(listData);
    	}finally{
            if(sess!=null){
            	sess.commit();
            	sess.clearCache();
            	sess.close();
            }
        }
    	//从连接容器获取内存中实时数据
		//this.realTimeDataSet = SocketConnetionContainer.getRealData(roomInterfaceCof.getCompanyCode());
		log.info("set.size=" + realTimeDataSet.size() + ", 取得实时数据完成");
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
			Equipment equip = (Equipment)networkElement;
			List<Group> listGroup = equip.getListGroup();

			String queryID = EQUIP_PRE_TAG + equip.getId();
			for (Group group : listGroup) {    //节点组 
				List<EquipmentNode> listNodes = group.getListNode();
				if(Group.TYPE_STATE.equals(group.getType())){    //状态
					for (EquipmentNode equipmentNode : listNodes) {
						GJData gjData = searchData(queryID, equipmentNode);
						equipmentNode.setRealData(gjData);
					}
				}else if(Group.TYPE_VALUE.equals(group.getType())){    //数值
					for (EquipmentNode equipmentNode : listNodes) {
						log.debug("queryID=======>" + queryID);
						GJData gjData = searchData(queryID, equipmentNode);
						equipmentNode.setRealData(gjData);
						log.debug("实时数据========>" + gjData);
					}
				}
			}
			
			
			List<Module> listModule = equip.getListMode();
			for (Module module : listModule) {    //模块
				List<EquipmentNode> listNodes = module.getListNode();
				if(Module.TYPE_NORMAL.equals(module.getType())){    //一般模块
					for (EquipmentNode equipmentNode : listNodes) {
						log.debug("queryID=======>" + queryID);
						GJData gjData = searchData(queryID, equipmentNode);
						equipmentNode.setRealData(gjData);
						log.debug("实时数据========>" + gjData);
					}
				}
			}
			
		}
		
		remindInfo = "处理获取的数据完成";
		log.info(remindInfo);
		return true;
	}
	/**
	 * 搜索数据
	 * @param node 节点
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
			//log.debug("查询数据关键字【key】 = " + key);
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
/*	public static void main(String[] args) {
		SqlSession sess = DBDelegate.getSqlSessionFactory().openSession(ExecutorType.BATCH, true);
    	try{
    		String comCode = "GJ_1";
    		List<GJData> listData = 
    				sess.selectList("cn.com.dhcc.rp.realtimedata.select_gj_data_with_company_code",
    				comCode);
    		System.out.println("list.size = " + listData.size());
    		for (GJData gjData : listData) {
				
			}
    		Set<RealTimeData> realTimeDataSet = new HashSet<RealTimeData>(listData);
    		System.out.println(realTimeDataSet.size());
    		for (RealTimeData realTimeData : realTimeDataSet) {
				System.out.println(realTimeData);
			}
    	}finally{
            if(sess!=null){
            	sess.commit();
            	sess.clearCache();
            	sess.close();
            }
        }
	}*/
}