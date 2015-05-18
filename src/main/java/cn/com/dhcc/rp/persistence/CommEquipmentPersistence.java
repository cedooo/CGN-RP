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
System.out.println("����豸ID��" + equip.getId() + ", ���ƣ�" + equip.getName());
		List<Group> listGroup = equip.getListGroup();
		List<RoomCommState> listRoomState = new ArrayList<RoomCommState>();    //״̬��

		List<TxUpsCommPerf> listUpsCommPerf = new ArrayList<TxUpsCommPerf>();    //ups��ֵ��
		
		List<TxAirConditionCommThPerf> listAirConditionThPerf = new ArrayList<TxAirConditionCommThPerf>();    //���ܿյ�
		
		List<TxAirConditiomCommWorkTimePerf> listAirConWorkTime = new ArrayList<TxAirConditiomCommWorkTimePerf>();    //�յ��������ʱ��
		
		List<TxAirConditionCommConf> listAirConditionConf= new ArrayList<TxAirConditionCommConf>();   //�յ�����
		
		List<TxPowerBoxCommTotalPerf> listPowerBoxComm = new ArrayList<TxPowerBoxCommTotalPerf>();    //����
		
		List<TxTemHumCommPerf> listTemHumPerf = new ArrayList<TxTemHumCommPerf>();    //��ʪ��
		
		List<TxLeakWaterCommPerf> listLeakWater = new ArrayList<TxLeakWaterCommPerf>();
		
		for (Group group : listGroup) {
//System.out.println("<��>������===>" + group.getRefTab());
			if(Group.TYPE_STATE.equals(group.getType())){
				if("txRoomCommState".equals(group.getRefTab())){    //״̬�� ����
					listRoomState.addAll(getListCommState(group.getListNode()));
				}
			}else if(Group.TYPE_VALUE.equals(group.getType())){    //��ֵ�� ����
				if("txUpsCommPerf".equals(group.getRefTab())){    //UPSͨ�ñ����
					listUpsCommPerf.addAll(getListTxUpsCommPerf(group.getListNode()));
				}else if("txAirConditionCommThPerf".equals(group.getRefTab())){    //�յ�
					listAirConditionThPerf.addAll(getListAirCommPerf(group.getListNode()));
				}else if("txAirConditionCommConf".equals(group.getRefTab())){
					listAirConditionConf.addAll(getListAirConCommConf(group.getListNode()));
				}else if("txAirConditiomCommWorkTimePerf".equals(group.getRefTab())){    //�յ�����ʱ��
					listAirConWorkTime.addAll(getAirConditionWorkTime(group.getListNode()));
				}else if("txPowerBoxCommTotalPerf".equals(group.getRefTab())){    //��������ָ��
					listPowerBoxComm.addAll(getPowerBoxCommTotalPerf(group.getListNode()));
				}else if("txTemHumCommPerf".equals(group.getRefTab())){    //��������ָ��
					listTemHumPerf.addAll(getTxTemHumCommPerf(group.getListNode()));
				}else if("txLeakWaterCommPerf".equals(group.getRefTab())){    //��������ָ��
					listLeakWater.addAll(getTxLeakWaterCommPerf(group.getListNode()));
				}
			}
		}
		List<Module> listModule = equip.getListMode();
		List<TxUpsCommDirPerf> listUpsCDP = new ArrayList<TxUpsCommDirPerf>();    //UPSģ��
		
		List<TxPowerBoxDirPerf> listPowerBoxDirPerf = new ArrayList<TxPowerBoxDirPerf>();    //����
		
		List<TxPowerBoxCommBranchCurrPerf> listPowerBoxBranch = new ArrayList<TxPowerBoxCommBranchCurrPerf>();    //�����·����
		for (Module module : listModule) {
//System.out.println("<ģ��>������===>" + module.getRefTab());
			boolean moduleValid = Module.COLLECT.equals(module.getCollect());
			if(moduleValid && Module.TYPE_NORMAL.equals(module.getType())){
				if("txUpsCommDirPerf".equals(module.getRefTab())){
					listUpsCDP.addAll(this.getListAirDirPerf(module.getPartID(), module.getListNode()));
				}else if("txPowerBoxDirPerf".equals(module.getRefTab())){
					listPowerBoxDirPerf.addAll(getListPowerBoxDirPerf(module.getPartID(), module.getListNode()));
				}else if("txAirConditionCommThPerf".equals(module.getRefTab())){    //�յ�
					listAirConditionThPerf.addAll(getListAirCommPerf(module.getPartID(), module.getListNode()));
				}else if("txAirConditionCommConf".equals(module.getRefTab())){
					listAirConditionConf.addAll(getListAirConCommConf(module.getPartID(), module.getListNode()));
				}else if("txAirConditiomCommWorkTimePerf".equals(module.getRefTab())){    //�յ�����ʱ��
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
			for (RoomCommState state : listRoomState) {//״̬�����
				state.setId(equipId);
				session.update("cn.com.dhcc.rp.state.update_insert_comm_state", state);
//System.out.println("״̬�����>>>" + state);
			}
			for (TxUpsCommPerf perf : listUpsCommPerf) {//ups��ֵ�����
				perf.setId(equipId);
				session.update("cn.com.dhcc.rp.ups.update_insert_comm_ups_perf", perf);
//System.out.println("UPS�����ֵ>>>" + perf);
			}
			for (TxAirConditionCommThPerf thPerf : listAirConditionThPerf) {//�յ���ֵ�����
				thPerf.setId(equipId);
				session.update("cn.com.dhcc.rp.aircond.update_insert_TxAirConditionCommThPerf", thPerf);
//System.out.println("���ܿյ������ֵ>>>" + thPerf);
			}
			for (TxAirConditiomCommWorkTimePerf commWorkTime : listAirConWorkTime) {//�յ���ֵ�����
				commWorkTime.setId(equipId);
				session.update("cn.com.dhcc.rp.aircond.update_insert_TxAirConditiomCommWorkTimePerf", commWorkTime);
//System.out.println("�յ�<����ʱ��>��ֵ�������ֵ>>>" + commWorkTime);
			}
			for (TxAirConditionCommConf commConf : listAirConditionConf) {//�յ���ֵ�����
				commConf.setId(equipId);
				session.update("cn.com.dhcc.rp.aircond.update_insert_TxAirConditionCommConf", commConf);
//System.out.println("�յ�<����>ģ���������ֵ>>>" + commConf);
			}

			for (TxPowerBoxCommTotalPerf commTotalPerf : listPowerBoxComm) {//�յ���ֵ�����
				commTotalPerf.setId(equipId);
//System.out.println("���������ֵ>>>" + commTotalPerf);
				session.update("cn.com.dhcc.rp.powerbox.update_insert_TxPowerBoxCommTotalPerf", commTotalPerf);
			}
			for (TxTemHumCommPerf temHumPerf : listTemHumPerf) {//�յ���ֵ�����
				temHumPerf.setId(equipId);
				session.update("cn.com.dhcc.rp.temhum.update_insert_TxTemHumCommPerf", temHumPerf);
//System.out.println("��ʪ�������ֵ>>>" + temHumPerf);
			}

			for (TxLeakWaterCommPerf leakWaterPerf : listLeakWater) {//�յ���ֵ�����
				leakWaterPerf.setId(equipId);
				session.update("cn.com.dhcc.rp.leak.update_insert_TxLeakWaterCommPerf", leakWaterPerf);
//System.out.println("��ʪ�������ֵ>>>" + temHumPerf);
			}
			//====================module=====================
			for (TxUpsCommDirPerf dirPerf : listUpsCDP) {//�յ���ֵ�����
				dirPerf.setId(equipId);
				session.update("cn.com.dhcc.rp.ups.update_insert_TxUpsCommDirPerf", dirPerf);
//System.out.println("UPSģ���������ֵ>>>" + dirPerf);
			}
			for (TxPowerBoxDirPerf boxDirPerf : listPowerBoxDirPerf) {//�յ���ֵ�����
				boxDirPerf.setId(equipId);
				session.update("cn.com.dhcc.rp.powerbox.update_insert_TxPowerBoxDirPerf", boxDirPerf);
//System.out.println("����ģ���������ֵ>>>" + boxDirPerf);
			}

			for (TxPowerBoxCommBranchCurrPerf branchPerf : listPowerBoxBranch) {//�յ���ֵ�����
				branchPerf.setId(equipId);
				session.update("cn.com.dhcc.rp.powerbox.update_insert_TxPowerBoxCommBranchCurrPerf", branchPerf);
//System.out.println("�����·����ģ���������ֵ>>>" + branchPerf);
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
