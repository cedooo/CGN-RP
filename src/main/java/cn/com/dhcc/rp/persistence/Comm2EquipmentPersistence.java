package cn.com.dhcc.rp.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import cn.com.dhcc.rp.db.DBDelegate;
import cn.com.dhcc.rp.machineroom.networkele.NetworkElement;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Equipment;
import cn.com.dhcc.rp.machineroom.networkele.equipment.EquipmentNode;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Group;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Module;
import cn.com.dhcc.rp.persistence.po.POObj;
import cn.com.dhcc.rp.persistence.po.RoomCommState;
import cn.com.dhcc.rp.persistence.po.RoomInterfaceConf;
import cn.com.dhcc.rp.persistence.po.TxAirConditiomCommWorkTimePerf;
import cn.com.dhcc.rp.persistence.po.TxAirConditionCommConf;
import cn.com.dhcc.rp.persistence.po.TxAirConditionCommThPerf;
import cn.com.dhcc.rp.persistence.po.TxLeakWaterCommPerf;
import cn.com.dhcc.rp.persistence.po.TxPowerBoxCommBranchCurrPerf;
import cn.com.dhcc.rp.persistence.po.TxPowerBoxCommTotalPerf;
import cn.com.dhcc.rp.persistence.po.TxPowerBoxDirPerf;
import cn.com.dhcc.rp.persistence.po.TxTemHumCommPerf;
import cn.com.dhcc.rp.persistence.po.TxUpsCommDirPerf;
import cn.com.dhcc.rp.persistence.po.TxUpsCommPerf;
import cn.com.dhcc.rp.pojo.ConfFormatConstants;

public abstract class Comm2EquipmentPersistence implements Persistenceable{
	static private Logger log = Logger.getLogger(Comm2EquipmentPersistence.class.getClass());
	static protected SimpleDateFormat dateFormat = new SimpleDateFormat(ConfFormatConstants.DATE_FORMAT);
	protected RoomInterfaceConf interfaceConf = null;
	@Override
	public boolean persistance(NetworkElement networkElement) {

		Equipment equip = (Equipment)networkElement;
		log.info("入库设备ID：" + equip.getId() + ", 名称：" + equip.getName());
		
		List<POObj> listPO = new ArrayList<POObj>();
		
		/***
		 * Group
		 */
		List<Group> listGroup = equip.getListGroup();
		
		for (Group group : listGroup) {
			log.debug("<组>入库表名===>" + group.getRefTab());
			if(Group.TYPE_STATE.equals(group.getType())){
				if("txRoomCommState".equals(group.getRefTab())){    //状态量 处理
					listPO.addAll(getListCommState(group.getListNode()));
				}
			}else if(Group.TYPE_VALUE.equals(group.getType())){    //数值量 处理
				if("txUpsCommPerf".equals(group.getRefTab())){    //UPS通用表入库
					listPO.addAll(getListTxUpsCommPerf(group.getListNode()));
				}else if("txAirConditionCommThPerf".equals(group.getRefTab())){    //空调
					listPO.addAll(getListAirCommPerf(group.getListNode()));
				}else if("txAirConditionCommConf".equals(group.getRefTab())){
					listPO.addAll(getListAirConCommConf(group.getListNode()));
				}else if("txAirConditiomCommWorkTimePerf".equals(group.getRefTab())){    //空调运行时间
					listPO.addAll(getAirConditionWorkTime(group.getListNode()));
				}else if("txPowerBoxCommTotalPerf".equals(group.getRefTab())){    //配电柜数据指标
					listPO.addAll(getPowerBoxCommTotalPerf(group.getListNode()));
				}else if("txTemHumCommPerf".equals(group.getRefTab())){    //配电柜数据指标
					listPO.addAll(getTxTemHumCommPerf(group.getListNode()));
				}else if("txLeakWaterCommPerf".equals(group.getRefTab())){    //配电柜数据指标
					listPO.addAll(getTxLeakWaterCommPerf(group.getListNode()));
				}
			}
		}
		/**
		 * Module
		 */
		List<Module> listModule = equip.getListMode();
		
		for (Module module : listModule) {
			log.debug("<模块>入库表名===>" + module.getRefTab());
			boolean moduleValid = Module.COLLECT.equals(module.getCollect());
			if(moduleValid && Module.TYPE_NORMAL.equals(module.getType())){
				if("txUpsCommDirPerf".equals(module.getRefTab())){
					listPO.addAll(this.getListAirDirPerf(module.getPartID(), module.getListNode()));
				}else if("txPowerBoxDirPerf".equals(module.getRefTab())){
					listPO.addAll(getListPowerBoxDirPerf(module.getPartID(), module.getListNode()));
				}else if("txAirConditionCommThPerf".equals(module.getRefTab())){    //空调
					listPO.addAll(getListAirCommPerf(module.getPartID(), module.getListNode()));
				}else if("txAirConditionCommConf".equals(module.getRefTab())){
					listPO.addAll(getListAirConCommConf(module.getPartID(), module.getListNode()));
				}else if("txAirConditiomCommWorkTimePerf".equals(module.getRefTab())){    //空调运行时间
					listPO.addAll(getAirConditionWorkTime(module.getPartID(), module.getListNode()));
				}
			}else if(moduleValid && Module.TYPE_POWERBOX_LIKE.equals(module.getType())){
				if("txPowerBoxCommBranchCurrPerf".equals(module.getRefTab())){
					listPO.addAll(getListPowerBoxCommBranchCurrPerf(module.getListNode()));
				}
			}
		}
		
		/**
		 * 入库
		 */
		SqlSession session = DBDelegate.getSqlSessionFactory().openSession(ExecutorType.BATCH, true);
		try{
			String equipId = getPrePersisID(networkElement) + equip.getId();
			for (POObj poobj : listPO) {
				if(poobj instanceof RoomCommState){
					RoomCommState state = (RoomCommState)poobj;
					state.setId(equipId);
					session.update("cn.com.dhcc.rp.state.update_insert_comm_state", state);
				}else if(poobj instanceof TxUpsCommPerf){
					TxUpsCommPerf perf = (TxUpsCommPerf)poobj;
					perf.setId(equipId);
					session.update("cn.com.dhcc.rp.ups.update_insert_comm_ups_perf", perf);
				}else if(poobj instanceof TxAirConditionCommThPerf){
					TxAirConditionCommThPerf thPerf = (TxAirConditionCommThPerf)poobj;
					thPerf.setId(equipId);
					session.update("cn.com.dhcc.rp.aircond.update_insert_TxAirConditionCommThPerf", thPerf);
				}else if(poobj instanceof TxAirConditiomCommWorkTimePerf){
					TxAirConditiomCommWorkTimePerf commWorkTime = (TxAirConditiomCommWorkTimePerf)poobj;
					commWorkTime.setId(equipId);
					session.update("cn.com.dhcc.rp.aircond.update_insert_TxAirConditiomCommWorkTimePerf", commWorkTime);
				}else if(poobj instanceof TxAirConditionCommConf){
					TxAirConditionCommConf commConf = (TxAirConditionCommConf)poobj;
					commConf.setId(equipId);
					session.update("cn.com.dhcc.rp.aircond.update_insert_TxAirConditionCommConf", commConf);
				}else if(poobj instanceof TxPowerBoxCommTotalPerf){
					TxPowerBoxCommTotalPerf commTotalPerf = (TxPowerBoxCommTotalPerf)poobj;
					commTotalPerf.setId(equipId);
					session.update("cn.com.dhcc.rp.powerbox.update_insert_TxPowerBoxCommTotalPerf", commTotalPerf);
				}else if(poobj instanceof TxTemHumCommPerf){
					TxTemHumCommPerf temHumPerf = (TxTemHumCommPerf)poobj;
					temHumPerf.setId(equipId);
					session.update("cn.com.dhcc.rp.temhum.update_insert_TxTemHumCommPerf", temHumPerf);
				}else if(poobj instanceof TxLeakWaterCommPerf){
					TxLeakWaterCommPerf leakWaterPerf = (TxLeakWaterCommPerf)poobj;
					leakWaterPerf.setId(equipId);
					session.update("cn.com.dhcc.rp.leak.update_insert_TxLeakWaterCommPerf", leakWaterPerf);
				}else if(poobj instanceof TxUpsCommDirPerf){
					TxUpsCommDirPerf dirPerf = (TxUpsCommDirPerf)poobj;
					dirPerf.setId(equipId);
					session.update("cn.com.dhcc.rp.ups.update_insert_TxUpsCommDirPerf", dirPerf);
				}else if(poobj instanceof TxPowerBoxDirPerf){
					TxPowerBoxDirPerf boxDirPerf = (TxPowerBoxDirPerf)poobj;
					boxDirPerf.setId(equipId);
					session.update("cn.com.dhcc.rp.powerbox.update_insert_TxPowerBoxDirPerf", boxDirPerf);
				}else if(poobj instanceof TxPowerBoxCommBranchCurrPerf){
					TxPowerBoxCommBranchCurrPerf branchPerf = (TxPowerBoxCommBranchCurrPerf)poobj;
					branchPerf.setId(equipId);
					session.update("cn.com.dhcc.rp.powerbox.update_insert_TxPowerBoxCommBranchCurrPerf", branchPerf);
				}
				log.debug("===入库设备===\n" + poobj);
			}
		}catch(Exception e){
			e.printStackTrace();
			session.rollback();
			return false;
		}finally{
			session.commit();
			session.close();
		}
		return true;
	}
	/**
	 * 得到空调运行时间
	 * @param partID
	 * @param listNode
	 * @return
	 */
	protected List<TxAirConditiomCommWorkTimePerf>  getAirConditionWorkTime(
			String partID, List<EquipmentNode> listNode) {
		List<TxAirConditiomCommWorkTimePerf> listPerf = this.getAirConditionWorkTime(listNode);
		for (TxAirConditiomCommWorkTimePerf tx : listPerf) {
			tx.setPartID(partID);
		}
		
		return listPerf;
	}
	/**
	 * 得到空调配置
	 * @param partID
	 * @param listNode
	 * @return
	 */
	protected List<TxAirConditionCommConf> getListAirConCommConf(
			String partID, List<EquipmentNode> listNode) {
		List<TxAirConditionCommConf> listConf = this.getListAirConCommConf(listNode);
		for (TxAirConditionCommConf tx : listConf) {
			tx.setPartID(partID);
		}
		return listConf;
	}
	/**
	 * 得到空调 指标
	 * @param partID
	 * @param listNode
	 * @return
	 */
	protected List<TxAirConditionCommThPerf> getListAirCommPerf(
			String partID, List<EquipmentNode> listNode) {
		List<TxAirConditionCommThPerf> listPerf = this.getListAirCommPerf(listNode);
		for (TxAirConditionCommThPerf tx : listPerf) {
			tx.setPartID(partID);
		}
		return listPerf;
	}
	/**
	 * 得到温湿度
	 * @param listNode 节点集合
	 * @return 
	 */
	abstract protected  List<TxTemHumCommPerf> getTxTemHumCommPerf(List<EquipmentNode> listNode);
	/**
	 * 配电柜分路电流
	 * @param listNode 设备节点集合
	 * @return 配电柜分路电流 集合
	 */
	abstract protected  List<TxPowerBoxCommBranchCurrPerf> getListPowerBoxCommBranchCurrPerf(
			List<EquipmentNode> listNode);
	/**
	 * 得到状态PO
	 * @param listNode 设备节点集合 状态节点集合
	 * @return 设备状态集合
	 */
	abstract protected  List<RoomCommState> getListCommState(List<EquipmentNode> listNode);
	/**
	 * 得到UPS数值PO 
	 * @param listNode 设备节点集合
	 * @return UPS数值指标集合
	 */
	abstract protected  List<TxUpsCommPerf> getListTxUpsCommPerf(List<EquipmentNode> listNode);
	/**
	 * 得到空调PO
	 * @param listNode 设备节点集合
	 * @return 空调指标 集合
	 */
	abstract protected  List<TxAirConditionCommThPerf> getListAirCommPerf(List<EquipmentNode> listNode);
	/**
	 * 得到空调的模块PO
	 * @param listNode 设备节点集合
	 * @return 空调模块PO对象集合
	 */
	abstract protected  List<TxUpsCommDirPerf> getListAirDirPerf(String modulePartID, List<EquipmentNode> listNode);
	/**
	 * 空调设置指标
	 * @param modulePartID
	 * @param listNode 设备节点集合
	 * @return 空调设置PO集合
	 */
	abstract protected  List<TxAirConditionCommConf> getListAirConCommConf(List<EquipmentNode> listNode);
	/**
	 * 空调运行时间
	 * @param listNode 设备节点集合
	 * @return 空调运行时间PO集合
	 */
	abstract protected  List<TxAirConditiomCommWorkTimePerf> getAirConditionWorkTime(List<EquipmentNode> listNode);
	/**
	 * 配电柜模块值
	 * @param partID
	 * @param listNode 设备节点集合
	 * @return 配电柜模块PO集合
	 */
	abstract protected  List<TxPowerBoxDirPerf> getListPowerBoxDirPerf(String partID, List<EquipmentNode> listNode);
	/**
	 * 配电柜通用
	 * @param listNode 设备节点集合
	 * @return 配电柜通用指标PO集合
	 */
	abstract protected  List<TxPowerBoxCommTotalPerf> getPowerBoxCommTotalPerf(List<EquipmentNode> listNode);
	/**
	 * @param netEle 网元
	 * @return 入库ID前缀
	 */
	abstract protected  String getPrePersisID(NetworkElement netEle);
	/**
	 * 得到漏水状态
	 * @param listNode
	 * @return
	 */
	abstract protected  List<TxLeakWaterCommPerf> getTxLeakWaterCommPerf(List<EquipmentNode> listNode);
}
