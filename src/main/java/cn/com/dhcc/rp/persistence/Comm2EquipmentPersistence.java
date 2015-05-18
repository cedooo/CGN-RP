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
		log.info("����豸ID��" + equip.getId() + ", ���ƣ�" + equip.getName());
		
		List<POObj> listPO = new ArrayList<POObj>();
		
		/***
		 * Group
		 */
		List<Group> listGroup = equip.getListGroup();
		
		for (Group group : listGroup) {
			log.debug("<��>������===>" + group.getRefTab());
			if(Group.TYPE_STATE.equals(group.getType())){
				if("txRoomCommState".equals(group.getRefTab())){    //״̬�� ����
					listPO.addAll(getListCommState(group.getListNode()));
				}
			}else if(Group.TYPE_VALUE.equals(group.getType())){    //��ֵ�� ����
				if("txUpsCommPerf".equals(group.getRefTab())){    //UPSͨ�ñ����
					listPO.addAll(getListTxUpsCommPerf(group.getListNode()));
				}else if("txAirConditionCommThPerf".equals(group.getRefTab())){    //�յ�
					listPO.addAll(getListAirCommPerf(group.getListNode()));
				}else if("txAirConditionCommConf".equals(group.getRefTab())){
					listPO.addAll(getListAirConCommConf(group.getListNode()));
				}else if("txAirConditiomCommWorkTimePerf".equals(group.getRefTab())){    //�յ�����ʱ��
					listPO.addAll(getAirConditionWorkTime(group.getListNode()));
				}else if("txPowerBoxCommTotalPerf".equals(group.getRefTab())){    //��������ָ��
					listPO.addAll(getPowerBoxCommTotalPerf(group.getListNode()));
				}else if("txTemHumCommPerf".equals(group.getRefTab())){    //��������ָ��
					listPO.addAll(getTxTemHumCommPerf(group.getListNode()));
				}else if("txLeakWaterCommPerf".equals(group.getRefTab())){    //��������ָ��
					listPO.addAll(getTxLeakWaterCommPerf(group.getListNode()));
				}
			}
		}
		/**
		 * Module
		 */
		List<Module> listModule = equip.getListMode();
		
		for (Module module : listModule) {
			log.debug("<ģ��>������===>" + module.getRefTab());
			boolean moduleValid = Module.COLLECT.equals(module.getCollect());
			if(moduleValid && Module.TYPE_NORMAL.equals(module.getType())){
				if("txUpsCommDirPerf".equals(module.getRefTab())){
					listPO.addAll(this.getListAirDirPerf(module.getPartID(), module.getListNode()));
				}else if("txPowerBoxDirPerf".equals(module.getRefTab())){
					listPO.addAll(getListPowerBoxDirPerf(module.getPartID(), module.getListNode()));
				}else if("txAirConditionCommThPerf".equals(module.getRefTab())){    //�յ�
					listPO.addAll(getListAirCommPerf(module.getPartID(), module.getListNode()));
				}else if("txAirConditionCommConf".equals(module.getRefTab())){
					listPO.addAll(getListAirConCommConf(module.getPartID(), module.getListNode()));
				}else if("txAirConditiomCommWorkTimePerf".equals(module.getRefTab())){    //�յ�����ʱ��
					listPO.addAll(getAirConditionWorkTime(module.getPartID(), module.getListNode()));
				}
			}else if(moduleValid && Module.TYPE_POWERBOX_LIKE.equals(module.getType())){
				if("txPowerBoxCommBranchCurrPerf".equals(module.getRefTab())){
					listPO.addAll(getListPowerBoxCommBranchCurrPerf(module.getListNode()));
				}
			}
		}
		
		/**
		 * ���
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
				log.debug("===����豸===\n" + poobj);
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
	 * �õ��յ�����ʱ��
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
	 * �õ��յ�����
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
	 * �õ��յ� ָ��
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
	 * �õ���ʪ��
	 * @param listNode �ڵ㼯��
	 * @return 
	 */
	abstract protected  List<TxTemHumCommPerf> getTxTemHumCommPerf(List<EquipmentNode> listNode);
	/**
	 * �����·����
	 * @param listNode �豸�ڵ㼯��
	 * @return �����·���� ����
	 */
	abstract protected  List<TxPowerBoxCommBranchCurrPerf> getListPowerBoxCommBranchCurrPerf(
			List<EquipmentNode> listNode);
	/**
	 * �õ�״̬PO
	 * @param listNode �豸�ڵ㼯�� ״̬�ڵ㼯��
	 * @return �豸״̬����
	 */
	abstract protected  List<RoomCommState> getListCommState(List<EquipmentNode> listNode);
	/**
	 * �õ�UPS��ֵPO 
	 * @param listNode �豸�ڵ㼯��
	 * @return UPS��ֵָ�꼯��
	 */
	abstract protected  List<TxUpsCommPerf> getListTxUpsCommPerf(List<EquipmentNode> listNode);
	/**
	 * �õ��յ�PO
	 * @param listNode �豸�ڵ㼯��
	 * @return �յ�ָ�� ����
	 */
	abstract protected  List<TxAirConditionCommThPerf> getListAirCommPerf(List<EquipmentNode> listNode);
	/**
	 * �õ��յ���ģ��PO
	 * @param listNode �豸�ڵ㼯��
	 * @return �յ�ģ��PO���󼯺�
	 */
	abstract protected  List<TxUpsCommDirPerf> getListAirDirPerf(String modulePartID, List<EquipmentNode> listNode);
	/**
	 * �յ�����ָ��
	 * @param modulePartID
	 * @param listNode �豸�ڵ㼯��
	 * @return �յ�����PO����
	 */
	abstract protected  List<TxAirConditionCommConf> getListAirConCommConf(List<EquipmentNode> listNode);
	/**
	 * �յ�����ʱ��
	 * @param listNode �豸�ڵ㼯��
	 * @return �յ�����ʱ��PO����
	 */
	abstract protected  List<TxAirConditiomCommWorkTimePerf> getAirConditionWorkTime(List<EquipmentNode> listNode);
	/**
	 * ����ģ��ֵ
	 * @param partID
	 * @param listNode �豸�ڵ㼯��
	 * @return ����ģ��PO����
	 */
	abstract protected  List<TxPowerBoxDirPerf> getListPowerBoxDirPerf(String partID, List<EquipmentNode> listNode);
	/**
	 * ����ͨ��
	 * @param listNode �豸�ڵ㼯��
	 * @return ����ͨ��ָ��PO����
	 */
	abstract protected  List<TxPowerBoxCommTotalPerf> getPowerBoxCommTotalPerf(List<EquipmentNode> listNode);
	/**
	 * @param netEle ��Ԫ
	 * @return ���IDǰ׺
	 */
	abstract protected  String getPrePersisID(NetworkElement netEle);
	/**
	 * �õ�©ˮ״̬
	 * @param listNode
	 * @return
	 */
	abstract protected  List<TxLeakWaterCommPerf> getTxLeakWaterCommPerf(List<EquipmentNode> listNode);
}
