package cn.com.dhcc.rp.machineroom.networkele.equipment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Module implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3937236697721647600L;
	static public String TYPE_NORMAL = "2";
	static public String TYPE_POWERBOX_LIKE = "3";
	
	
	static public final String COLLECT = "1";
	
	private String partID = null;
	private String refTab = null;
	private String collect = null;
	private String type = null;
	private String queryPartID = null;
	
	private List<EquipmentNode> listNode = new ArrayList<EquipmentNode>();
	public void add(EquipmentNode node){
		this.listNode.add(node);
	}

	@Override
	public String toString() {
		return "ModuleDef [partID=" + partID + ", NodeDefset=" + listNode + "]";
	}

	public List<EquipmentNode> getListNode() {
		return listNode;
	}

	public String getPartID() {
		return partID;
	}

	public void setPartID(String partID) {
		this.partID = partID;
	}

	public String getRefTab() {
		return refTab;
	}

	public void setRefTab(String refTab) {
		this.refTab = refTab;
	}

	public String getCollect() {
		return collect;
	}

	public void setCollect(String collect) {
		this.collect = collect;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getQueryPartID() {
		return queryPartID;
	}

	public void setQueryPartID(String queryPartID) {
		this.queryPartID = queryPartID;
	}
	
}
