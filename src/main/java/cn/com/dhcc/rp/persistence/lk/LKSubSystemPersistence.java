package cn.com.dhcc.rp.persistence.lk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import cn.com.dhcc.rp.connection.lk.LKData;
import cn.com.dhcc.rp.db.DBDelegate;
import cn.com.dhcc.rp.machineroom.networkele.NetworkElement;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Equipment;
import cn.com.dhcc.rp.machineroom.networkele.equipment.EquipmentNode;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Group;
import cn.com.dhcc.rp.machineroom.networkele.subsystem.SubSystem;
import cn.com.dhcc.rp.persistence.Persistenceable;
import cn.com.dhcc.rp.persistence.po.RoomCommState;
import cn.com.dhcc.rp.persistence.po.RoomInterfaceConf;
import cn.com.dhcc.rp.persistence.po.TxTemHumCommPerf;
import cn.com.dhcc.rp.pojo.ConfFormatConstants;


public class LKSubSystemPersistence implements Persistenceable{
	static private Logger log = Logger.getLogger(LKSubSystemPersistence.class.getClass());

	static protected SimpleDateFormat dateFormat = new SimpleDateFormat(ConfFormatConstants.DATE_FORMAT);
	protected RoomInterfaceConf interfaceConf = null;
	public LKSubSystemPersistence(RoomInterfaceConf roomInterfaceConf) {
		this.interfaceConf = roomInterfaceConf;
	}

	@Override
	public boolean persistance(NetworkElement networkElement) {
//System.out.println(">>>龙控子系统入库<<<");
		log.info("入库子系统ID：" + networkElement.getId() + ", 名称：" + networkElement.getName());
		if(networkElement instanceof SubSystem){
			SubSystem subSys = (SubSystem)networkElement;
//System.out.println(subSys);
			List<Equipment> listE = subSys.getListEquip();
//System.out.println(listE);
			List<TxTemHumCommPerf> listTemHumPerf = new ArrayList<TxTemHumCommPerf>();
			List<RoomCommState> listRoomState = new ArrayList<RoomCommState>();    //状态量
			for (Equipment equipment : listE) {
//System.out.println(equipment.getListGroup());
				List<Group> listGroup = equipment.getListGroup();
				String equipID = this.interfaceConf.getCompanyCode() + "_" + equipment.getName();//equipment.getId();
				for (Group group : listGroup) {
					if(Group.TYPE_STATE.equals(group.getType())){
						if("txRoomCommState".equals(group.getRefTab())){    //状态量 处理
							listRoomState.addAll(getListCommState(equipID, group.getListNode()));
						}
					}else if(Group.TYPE_VALUE.equals(group.getType())){    //数值量 处理
						if("txTemHumCommPerf".equals(group.getRefTab())){    //配电柜数据指标
							listTemHumPerf.addAll(getTxTemHumCommPerf(equipID, group.getListNode()));
						}
					}
				}
			}
			

			SqlSession session = DBDelegate.getSqlSessionFactory().openSession(ExecutorType.BATCH, true);
			for (RoomCommState state : listRoomState) {//状态量入库
				session.update("cn.com.dhcc.rp.state.update_insert_comm_state", state);
//System.out.println("状态量入库>>>" + state);
			}
			for (TxTemHumCommPerf temHumPerf : listTemHumPerf) {//空调数值量入库
				session.update("cn.com.dhcc.rp.temhum.update_insert_TxTemHumCommPerf", temHumPerf);
//System.out.println("温湿度入库数值>>>" + commTotalPerf);
			}
			session.commit();
			session.close();
		}
		return false;
	}
	/**
	 * 
	 * @param equipID
	 * @param listNode
	 * @return
	 */
	private List<TxTemHumCommPerf> getTxTemHumCommPerf(
			String equipID, List<EquipmentNode> listNode) {
		List<TxTemHumCommPerf> listPerf = new ArrayList<TxTemHumCommPerf>();
		try {
			TxTemHumCommPerf temHumCommPerf = new TxTemHumCommPerf();
			temHumCommPerf.setCompanyCode(this.interfaceConf.getCompanyCode());
			temHumCommPerf.setPartID(cn.com.dhcc.rp.persistence.po.Constants.DEFAULT_PART_ID);
			temHumCommPerf.setCollectTime(dateFormat.format(new Date()));
			for (EquipmentNode equipmentNode : listNode) {
//System.out.println("空条空调》》》》数值 " +equipmentNode);
				if(EquipmentNode.COLLECT.equals(equipmentNode.getCollect())){
					LKData lkData = (LKData)equipmentNode.getRealData();
					if(lkData != null ){
						String ref = equipmentNode.getRef();
						String setMethName = "set" + ref.substring(1);
						Method method =  TxTemHumCommPerf.class.getDeclaredMethod(setMethName, String.class);
						method.invoke(temHumCommPerf, lkData.getV());
					}
				}
			}
			boolean dataValid = temHumCommPerf.getCollectTime()!=null && temHumCommPerf.getCompanyCode() !=null;
			if(dataValid){
				temHumCommPerf.setId(equipID);
				listPerf.add(temHumCommPerf);
//System.out.println(powerBoxCommTotalPerf);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return listPerf;
	}

	/**
	 * 得到状态量
	 * @param equipID	设备ID
	 * @param listNode    设备节点
	 * @return
	 */
	protected List<RoomCommState> getListCommState(String equipID, List<EquipmentNode> listNode){
		List<RoomCommState> listCoommState = new ArrayList<RoomCommState>();
		for (EquipmentNode equipmentNode : listNode) {
			if(EquipmentNode.COLLECT.equals(equipmentNode.getCollect())){
				LKData lkData = (LKData)equipmentNode.getRealData();
				if(lkData!=null ){
					RoomCommState state = new RoomCommState();
					state.setCompanyCode(this.interfaceConf.getCompanyCode());
					state.setPartID(equipmentNode.getName());
					state.setStateValue(lkData.getV());
					state.setCollectTime(dateFormat.format(new Date()));
					/*if(equipmentNode.getGrState().equals(lkData.getV())){
						state.setGrState(RoomCommState.STATE_GREEN);
					}else{
						state.setGrState(RoomCommState.STATE_RED);
					}*/
					boolean dataValid = state.getCollectTime()!=null && state.getCompanyCode() !=null;
					if(dataValid){
						state.setId(equipID);
						listCoommState.add(state);
					}
				}
			}
		}
		return listCoommState;
	}
}
