package cn.com.dhcc.rp.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import cn.com.dhcc.rp.db.DBDelegate;
import cn.com.dhcc.rp.machineroom.networkele.NetworkElement;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Equipment;
import cn.com.dhcc.rp.machineroom.networkele.equipment.EquipmentNode;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Group;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Module;
import cn.com.dhcc.rp.persistence.Persistenceable;
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


public abstract class CommEquipmentPersistence implements Persistenceable{

	static protected SimpleDateFormat dateFormat = new SimpleDateFormat(ConfFormatConstants.DATE_FORMAT);
	protected RoomInterfaceConf interfaceConf = null;
	
	@Override
	public boolean persistance(NetworkElement networkElement) {
		
		Equipment equip = (Equipment)networkElement;
System.out.println("入库设备ID：" + equip.getId() + ", 名称：" + equip.getName());
		List<Group> listGroup = equip.getListGroup();
		List<RoomCommState> listRoomState = new ArrayList<RoomCommState>();    //状态量

		List<TxUpsCommPerf> listUpsCommPerf = new ArrayList<TxUpsCommPerf>();    //ups数值量
		
		List<TxAirConditionCommThPerf> listAirConditionThPerf = new ArrayList<TxAirConditionCommThPerf>();    //精密空调
		
		List<TxAirConditiomCommWorkTimePerf> listAirConWorkTime = new ArrayList<TxAirConditiomCommWorkTimePerf>();    //空调相关运行时间
		
		List<TxAirConditionCommConf> listAirConditionConf= new ArrayList<TxAirConditionCommConf>();   //空调设置
		
		List<TxPowerBoxCommTotalPerf> listPowerBoxComm = new ArrayList<TxPowerBoxCommTotalPerf>();    //配电柜
		
		List<TxTemHumCommPerf> listTemHumPerf = new ArrayList<TxTemHumCommPerf>();    //温湿度
		
		List<TxLeakWaterCommPerf> listLeakWater = new ArrayList<TxLeakWaterCommPerf>();
		
		for (Group group : listGroup) {
//System.out.println("<组>入库表名===>" + group.getRefTab());
			if(Group.TYPE_STATE.equals(group.getType())){
				if("txRoomCommState".equals(group.getRefTab())){    //状态量 处理
					listRoomState.addAll(getListCommState(group.getListNode()));
				}
			}else if(Group.TYPE_VALUE.equals(group.getType())){    //数值量 处理
				if("txUpsCommPerf".equals(group.getRefTab())){    //UPS通用表入库
					listUpsCommPerf.addAll(getListTxUpsCommPerf(group.getListNode()));
				}else if("txAirConditionCommThPerf".equals(group.getRefTab())){    //空调
					listAirConditionThPerf.addAll(getListAirCommPerf(group.getListNode()));
				}else if("txAirConditionCommConf".equals(group.getRefTab())){
					listAirConditionConf.addAll(getListAirConCommConf(group.getListNode()));
				}else if("txAirConditiomCommWorkTimePerf".equals(group.getRefTab())){    //空调运行时间
					listAirConWorkTime.addAll(getAirConditionWorkTime(group.getListNode()));
				}else if("txPowerBoxCommTotalPerf".equals(group.getRefTab())){    //配电柜数据指标
					listPowerBoxComm.addAll(getPowerBoxCommTotalPerf(group.getListNode()));
				}else if("txTemHumCommPerf".equals(group.getRefTab())){    //配电柜数据指标
					listTemHumPerf.addAll(getTxTemHumCommPerf(group.getListNode()));
				}else if("txLeakWaterCommPerf".equals(group.getRefTab())){    //配电柜数据指标
					listLeakWater.addAll(getTxLeakWaterCommPerf(group.getListNode()));
				}
			}
		}
		List<Module> listModule = equip.getListMode();
		List<TxUpsCommDirPerf> listUpsCDP = new ArrayList<TxUpsCommDirPerf>();    //UPS模块
		
		List<TxPowerBoxDirPerf> listPowerBoxDirPerf = new ArrayList<TxPowerBoxDirPerf>();    //配电柜
		
		List<TxPowerBoxCommBranchCurrPerf> listPowerBoxBranch = new ArrayList<TxPowerBoxCommBranchCurrPerf>();    //配电柜分路电流
		for (Module module : listModule) {
//System.out.println("<模块>入库表名===>" + module.getRefTab());
			boolean moduleValid = Module.COLLECT.equals(module.getCollect());
			if(moduleValid && Module.TYPE_NORMAL.equals(module.getType())){
				if("txUpsCommDirPerf".equals(module.getRefTab())){
					listUpsCDP.addAll(this.getListAirDirPerf(module.getPartID(), module.getListNode()));
				}else if("txPowerBoxDirPerf".equals(module.getRefTab())){
					listPowerBoxDirPerf.addAll(getListPowerBoxDirPerf(module.getPartID(), module.getListNode()));
				}else if("txAirConditionCommThPerf".equals(module.getRefTab())){    //空调
					listAirConditionThPerf.addAll(getListAirCommPerf(module.getPartID(), module.getListNode()));
				}else if("txAirConditionCommConf".equals(module.getRefTab())){
					listAirConditionConf.addAll(getListAirConCommConf(module.getPartID(), module.getListNode()));
				}else if("txAirConditiomCommWorkTimePerf".equals(module.getRefTab())){    //空调运行时间
					listAirConWorkTime.addAll(getAirConditionWorkTime(module.getPartID(), module.getListNode()));
				}
			}else if(moduleValid && Module.TYPE_POWERBOX_LIKE.equals(module.getType())){
				if("txPowerBoxCommBranchCurrPerf".equals(module.getRefTab())){
					listPowerBoxBranch.addAll(getListPowerBoxCommBranchCurrPerf(module.getListNode()));
				}
			}
		}
		
		
		SqlSession session = DBDelegate.getSqlSessionFactory().openSession(ExecutorType.BATCH, true);
		try{
			String equipId = getPrePersisID(networkElement) + equip.getId();
			//====================group=====================
			for (RoomCommState state : listRoomState) {//状态量入库
				state.setId(equipId);
				session.update("cn.com.dhcc.rp.state.update_insert_comm_state", state);
//System.out.println("状态量入库>>>" + state);
			}
			for (TxUpsCommPerf perf : listUpsCommPerf) {//ups数值量入库
				perf.setId(equipId);
				session.update("cn.com.dhcc.rp.ups.update_insert_comm_ups_perf", perf);
//System.out.println("UPS入库数值>>>" + perf);
			}
			for (TxAirConditionCommThPerf thPerf : listAirConditionThPerf) {//空调数值量入库
				thPerf.setId(equipId);
				session.update("cn.com.dhcc.rp.aircond.update_insert_TxAirConditionCommThPerf", thPerf);
//System.out.println("精密空调入库数值>>>" + thPerf);
			}
			for (TxAirConditiomCommWorkTimePerf commWorkTime : listAirConWorkTime) {//空调数值量入库
				commWorkTime.setId(equipId);
				session.update("cn.com.dhcc.rp.aircond.update_insert_TxAirConditiomCommWorkTimePerf", commWorkTime);
//System.out.println("空调<运行时间>数值量入库数值>>>" + commWorkTime);
			}
			for (TxAirConditionCommConf commConf : listAirConditionConf) {//空调数值量入库
				commConf.setId(equipId);
				session.update("cn.com.dhcc.rp.aircond.update_insert_TxAirConditionCommConf", commConf);
//System.out.println("空调<配置>模块量入库数值>>>" + commConf);
			}

			for (TxPowerBoxCommTotalPerf commTotalPerf : listPowerBoxComm) {//空调数值量入库
				commTotalPerf.setId(equipId);
//System.out.println("配电柜入库数值>>>" + commTotalPerf);
				session.update("cn.com.dhcc.rp.powerbox.update_insert_TxPowerBoxCommTotalPerf", commTotalPerf);
			}
			for (TxTemHumCommPerf temHumPerf : listTemHumPerf) {//空调数值量入库
				temHumPerf.setId(equipId);
				session.update("cn.com.dhcc.rp.temhum.update_insert_TxTemHumCommPerf", temHumPerf);
//System.out.println("温湿度入库数值>>>" + temHumPerf);
			}

			for (TxLeakWaterCommPerf leakWaterPerf : listLeakWater) {//空调数值量入库
				leakWaterPerf.setId(equipId);
				session.update("cn.com.dhcc.rp.leak.update_insert_TxLeakWaterCommPerf", leakWaterPerf);
//System.out.println("温湿度入库数值>>>" + temHumPerf);
			}
			//====================module=====================
			for (TxUpsCommDirPerf dirPerf : listUpsCDP) {//空调数值量入库
				dirPerf.setId(equipId);
				session.update("cn.com.dhcc.rp.ups.update_insert_TxUpsCommDirPerf", dirPerf);
//System.out.println("UPS模块量入库数值>>>" + dirPerf);
			}
			for (TxPowerBoxDirPerf boxDirPerf : listPowerBoxDirPerf) {//空调数值量入库
				boxDirPerf.setId(equipId);
				session.update("cn.com.dhcc.rp.powerbox.update_insert_TxPowerBoxDirPerf", boxDirPerf);
//System.out.println("配电柜模块量入库数值>>>" + boxDirPerf);
			}

			for (TxPowerBoxCommBranchCurrPerf branchPerf : listPowerBoxBranch) {//空调数值量入库
				branchPerf.setId(equipId);
				session.update("cn.com.dhcc.rp.powerbox.update_insert_TxPowerBoxCommBranchCurrPerf", branchPerf);
//System.out.println("配电柜分路电流模块量入库数值>>>" + branchPerf);
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
	protected List<TxAirConditiomCommWorkTimePerf>  getAirConditionWorkTime(
			String partID, List<EquipmentNode> listNode) {
		List<TxAirConditiomCommWorkTimePerf> listPerf = this.getAirConditionWorkTime(listNode);
		for (TxAirConditiomCommWorkTimePerf tx : listPerf) {
			tx.setPartID(partID);
		}
		
		return listPerf;
	}
	protected List<TxAirConditionCommConf> getListAirConCommConf(
			String partID, List<EquipmentNode> listNode) {
		List<TxAirConditionCommConf> listConf = this.getListAirConCommConf(listNode);
		for (TxAirConditionCommConf tx : listConf) {
			tx.setPartID(partID);
		}
		return listConf;
	}
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
