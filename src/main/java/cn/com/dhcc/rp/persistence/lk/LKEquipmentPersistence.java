package cn.com.dhcc.rp.persistence.lk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.dhcc.rp.connection.lk.LKData;
import cn.com.dhcc.rp.machineroom.networkele.NetworkElement;
import cn.com.dhcc.rp.machineroom.networkele.equipment.EquipmentNode;
import cn.com.dhcc.rp.persistence.Comm2EquipmentPersistence;
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


public class LKEquipmentPersistence extends Comm2EquipmentPersistence{

	public LKEquipmentPersistence(RoomInterfaceConf interfaceConf){
		this.interfaceConf = interfaceConf;
	}
	
	/**
	 * 配电柜分路电流
	 * @param listNode
	 * @return
	 */
	protected List<TxPowerBoxCommBranchCurrPerf> getListPowerBoxCommBranchCurrPerf(
			List<EquipmentNode> listNode) {
		List<TxPowerBoxCommBranchCurrPerf> listPowerBoxBranch = new ArrayList<TxPowerBoxCommBranchCurrPerf>();
		for (EquipmentNode equipmentNode : listNode) {
			if(EquipmentNode.COLLECT.equals(equipmentNode.getCollect())){
				LKData lkData = (LKData)equipmentNode.getRealData();
				if(lkData!=null ){
					TxPowerBoxCommBranchCurrPerf branchPerf = new TxPowerBoxCommBranchCurrPerf();
					branchPerf.setCompanyCode(this.interfaceConf.getCompanyCode());
					branchPerf.setPartID(equipmentNode.getPartID());
					//branchPerf.setCollectTime(dateFormat.format(lkData.getColDate()));
					branchPerf.setCollectTime(dateFormat.format(new Date()));
					branchPerf.setBranchCurrency(lkData.getV());
					boolean dataValid = branchPerf.getCollectTime()!=null && branchPerf.getCompanyCode() !=null;
					if(dataValid){
						listPowerBoxBranch.add(branchPerf);
					}
				}
			}
		}
		return listPowerBoxBranch;
	}
	/**
	 * 得到状态PO
	 * @param listNode 状态节点集合
	 * @return
	 */
	protected List<RoomCommState> getListCommState(List<EquipmentNode> listNode){
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
					/*if(null!= equipmentNode.getGrState() && equipmentNode.getGrState().equals(lkData.getV())){
						state.setGrState(RoomCommState.STATE_GREEN);
					}else{
						state.setGrState(RoomCommState.STATE_RED);
					}*/
					boolean dataValid = state.getCollectTime()!=null && state.getCompanyCode() !=null;
					if(dataValid){
						listCoommState.add(state);
					}
				}
			}
		}
		return listCoommState;
	}
	/**
	 * 得到UPS数值PO
	 * @param listNode
	 * @return
	 */
	protected List<TxUpsCommPerf> getListTxUpsCommPerf(List<EquipmentNode> listNode){
		List<TxUpsCommPerf> listUpsCommPerf = new ArrayList<TxUpsCommPerf>();
		try {
			TxUpsCommPerf commPerf = new TxUpsCommPerf();
			commPerf.setPartID(cn.com.dhcc.rp.persistence.po.Constants.DEFAULT_PART_ID);
			commPerf.setCompanyCode(this.interfaceConf.getCompanyCode());
			commPerf.setCollectTime(dateFormat.format(new Date()));
			for (EquipmentNode equipmentNode : listNode) {
				if(EquipmentNode.COLLECT.equals(equipmentNode.getCollect())){
					LKData lkData = (LKData)equipmentNode.getRealData();
					if(lkData!=null ){
						String ref = equipmentNode.getRef();
						String setMethName = "set" + ref.substring(1);
						Method method = TxUpsCommPerf.class.getDeclaredMethod(setMethName, String.class);
						method.invoke(commPerf, lkData.getV());
					}
				}
			}
			boolean dataValid = commPerf.getCollectTime()!=null && commPerf.getCompanyCode() !=null;
			if(dataValid){
				listUpsCommPerf.add(commPerf);
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
		return listUpsCommPerf;
	}
	/**
	 * 得到空调PO
	 * @param listNode
	 * @return
	 */
	protected List<TxAirConditionCommThPerf> getListAirCommPerf(List<EquipmentNode> listNode){
		List<TxAirConditionCommThPerf> listAirConditionCommTP = new ArrayList<TxAirConditionCommThPerf>();
		try {
			TxAirConditionCommThPerf airConCommThPerf = new TxAirConditionCommThPerf();
			airConCommThPerf.setCompanyCode(this.interfaceConf.getCompanyCode());
			airConCommThPerf.setPartID(cn.com.dhcc.rp.persistence.po.Constants.DEFAULT_PART_ID);
			airConCommThPerf.setCollectTime(dateFormat.format(new Date()));
			for (EquipmentNode equipmentNode : listNode) {
//System.out.println("空条空调》》》》数值 " +equipmentNode);
				if(EquipmentNode.COLLECT.equals(equipmentNode.getCollect())){
					LKData lkData = (LKData)equipmentNode.getRealData();
					if(lkData!=null ){
						String ref = equipmentNode.getRef();
						String setMethName = "set" + ref.substring(1);
						Method method =  TxAirConditionCommThPerf.class.getDeclaredMethod(setMethName, String.class);
						method.invoke(airConCommThPerf, lkData.getV());
					}
				}
			}
			boolean dataValid = airConCommThPerf.getCollectTime()!=null && airConCommThPerf.getCompanyCode() !=null;
			if(dataValid){
				listAirConditionCommTP.add(airConCommThPerf);
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
		return listAirConditionCommTP;
	}
	/**
	 * 得到空调的模块PO
	 * @param listNode
	 * @return
	 */
	protected List<TxUpsCommDirPerf> getListAirDirPerf(String modulePartID, List<EquipmentNode> listNode){
		List<TxUpsCommDirPerf> listCommDirP = new ArrayList<TxUpsCommDirPerf>();
		try {
			TxUpsCommDirPerf commDirPerf = new TxUpsCommDirPerf();
			commDirPerf.setCompanyCode(this.interfaceConf.getCompanyCode());
			commDirPerf.setPartID(modulePartID);
			commDirPerf.setCollectTime(dateFormat.format(new Date()));
			for (EquipmentNode equipmentNode : listNode) {
				if(EquipmentNode.COLLECT.equals(equipmentNode.getCollect())){
					LKData lkData = (LKData)equipmentNode.getRealData();
					if(lkData!=null&&equipmentNode.getRef()!=null){
						String ref = equipmentNode.getRef();
						String setMethName = "set" + ref.substring(1);
						Method method = TxUpsCommDirPerf.class.getDeclaredMethod(setMethName, String.class);
						method.invoke(commDirPerf, lkData.getV());
						
					}
				}
			}
			boolean dataValid = modulePartID!=null && commDirPerf.getCollectTime()!=null && commDirPerf.getCompanyCode() !=null;
			if(dataValid){
				listCommDirP.add(commDirPerf);
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
		return listCommDirP;
	}
	/**
	 * 
	 * @param modulePartID
	 * @param listNode
	 * @return
	 */
	protected List<TxAirConditionCommConf> getListAirConCommConf(List<EquipmentNode> listNode) {
		List<TxAirConditionCommConf> listConf = new ArrayList<TxAirConditionCommConf>();
		try {
			TxAirConditionCommConf commConf = new TxAirConditionCommConf();
			commConf.setCompanyCode(this.interfaceConf.getCompanyCode());
			commConf.setCollectTime(dateFormat.format(new Date()));
			commConf.setPartID(cn.com.dhcc.rp.persistence.po.Constants.DEFAULT_PART_ID);
			for (EquipmentNode equipmentNode : listNode) {
				if(EquipmentNode.COLLECT.equals(equipmentNode.getCollect())){
					LKData lkData = (LKData)equipmentNode.getRealData();
					if(lkData!=null ){
						String ref = equipmentNode.getRef();
						String setMethName = "set" + ref.substring(1);
						Method method = TxAirConditionCommConf.class.getDeclaredMethod(setMethName, String.class);
						method.invoke(commConf, lkData.getV());
					}
				}
			}
			boolean dataValid = commConf.getCollectTime()!=null && commConf.getCompanyCode() !=null;
			if(dataValid){
				listConf.add(commConf);
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
		return listConf;
	}

	protected List<TxAirConditiomCommWorkTimePerf> getAirConditionWorkTime(List<EquipmentNode> listNode) {
		List<TxAirConditiomCommWorkTimePerf> listAirConWorkTime = new ArrayList<TxAirConditiomCommWorkTimePerf>();
		try {
			TxAirConditiomCommWorkTimePerf commWrokTime = new TxAirConditiomCommWorkTimePerf();
			commWrokTime.setCompanyCode(this.interfaceConf.getCompanyCode());
			commWrokTime.setPartID(cn.com.dhcc.rp.persistence.po.Constants.DEFAULT_PART_ID);
			commWrokTime.setCollectTime(dateFormat.format(new Date()));
			for (EquipmentNode equipmentNode : listNode) {
				if(EquipmentNode.COLLECT.equals(equipmentNode.getCollect())){
					LKData lkData = (LKData)equipmentNode.getRealData();
					if(lkData != null ){
						String ref = equipmentNode.getRef();
						String setMethName = "set" + ref.substring(1);
						Method method = TxAirConditiomCommWorkTimePerf.class.getDeclaredMethod(setMethName, String.class);
						method.invoke(commWrokTime, lkData.getV());
					}
				}
			}
			boolean dataValid = commWrokTime.getCollectTime()!=null && commWrokTime.getCompanyCode() !=null;
			if(dataValid){
				listAirConWorkTime.add(commWrokTime);
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
		return listAirConWorkTime;
	}
	/**
	 * 配电柜模块值
	 * @param partID
	 * @param listNode
	 * @return
	 */
	protected List<TxPowerBoxDirPerf> getListPowerBoxDirPerf(
			String partID, List<EquipmentNode> listNode) {
		List<TxPowerBoxDirPerf> listPowerBoxDirPerf = new ArrayList<TxPowerBoxDirPerf>();
		try {
			TxPowerBoxDirPerf commWrokTime = new TxPowerBoxDirPerf();
			commWrokTime.setCompanyCode(this.interfaceConf.getCompanyCode());
			commWrokTime.setPartID(partID);
			commWrokTime.setCollectTime(dateFormat.format(new Date()));
			for (EquipmentNode equipmentNode : listNode) {
				if(EquipmentNode.COLLECT.equals(equipmentNode.getCollect())){
					LKData lkData = (LKData)equipmentNode.getRealData();
					if(lkData != null ){
						String ref = equipmentNode.getRef();
						String setMethName = "set" + ref.substring(1);
						Method method = TxPowerBoxDirPerf.class.getDeclaredMethod(setMethName, String.class);
						method.invoke(commWrokTime, lkData.getV());
					}
				}
			}
			boolean dataValid = commWrokTime.getCollectTime()!=null && commWrokTime.getCompanyCode() !=null;
			if(dataValid){
				listPowerBoxDirPerf.add(commWrokTime);
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
		
		return listPowerBoxDirPerf;
	}
	/**
	 * 配电柜通用
	 * @param listNode
	 * @return
	 */
	protected List<TxPowerBoxCommTotalPerf> getPowerBoxCommTotalPerf(
			List<EquipmentNode> listNode) {
		List<TxPowerBoxCommTotalPerf> listPowerBoxComm = new ArrayList<TxPowerBoxCommTotalPerf>();
		try {
			TxPowerBoxCommTotalPerf powerBoxCommTotalPerf = new TxPowerBoxCommTotalPerf();
			powerBoxCommTotalPerf.setCompanyCode(this.interfaceConf.getCompanyCode());
			powerBoxCommTotalPerf.setPartID(cn.com.dhcc.rp.persistence.po.Constants.DEFAULT_PART_ID);
			powerBoxCommTotalPerf.setCollectTime(dateFormat.format(new Date()));
			for (EquipmentNode equipmentNode : listNode) {
//System.out.println("空条空调》》》》数值 " +equipmentNode);
				if(EquipmentNode.COLLECT.equals(equipmentNode.getCollect())){
					LKData lkData = (LKData)equipmentNode.getRealData();
					if(lkData != null ){
						String ref = equipmentNode.getRef();
						String setMethName = "set" + ref.substring(1);
						Method method =  TxPowerBoxCommTotalPerf.class.getDeclaredMethod(setMethName, String.class);
						method.invoke(powerBoxCommTotalPerf, lkData.getV());
					}
				}
			}
			boolean dataValid = powerBoxCommTotalPerf.getCollectTime()!=null && powerBoxCommTotalPerf.getCompanyCode() !=null;
			if(dataValid){
				listPowerBoxComm.add(powerBoxCommTotalPerf);
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
		return listPowerBoxComm;
	}

	@Override
	protected String getPrePersisID(NetworkElement ne) {
		return "";
	}

	@Override
	protected List<TxTemHumCommPerf> getTxTemHumCommPerf(
			List<EquipmentNode> listNode) {
		
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

	@Override
	protected List<TxLeakWaterCommPerf> getTxLeakWaterCommPerf(
			List<EquipmentNode> listNode) {
		List<TxLeakWaterCommPerf> listLeak = new ArrayList<TxLeakWaterCommPerf>();
		try {
			TxLeakWaterCommPerf leakWater = new TxLeakWaterCommPerf();
			leakWater.setCompanyCode(this.interfaceConf.getCompanyCode());
			leakWater.setPartID(cn.com.dhcc.rp.persistence.po.Constants.DEFAULT_PART_ID);
			leakWater.setCollectTime(dateFormat.format(new Date()));
			for (EquipmentNode equipmentNode : listNode) {
//System.out.println("空条空调》》》》数值 " +equipmentNode);
				if(EquipmentNode.COLLECT.equals(equipmentNode.getCollect())){
					LKData lkData = (LKData)equipmentNode.getRealData();
					if(lkData != null ){
						String ref = equipmentNode.getRef();
						String setMethName = "set" + ref.substring(1);
						Method method =  TxLeakWaterCommPerf.class.getDeclaredMethod(setMethName, String.class);
						method.invoke(leakWater, lkData.getV());
					}
				}
			}
			boolean dataValid = leakWater.getCollectTime()!=null && leakWater.getCompanyCode() !=null;
			if(dataValid){
				listLeak.add(leakWater);
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
		return listLeak;
	}
}
