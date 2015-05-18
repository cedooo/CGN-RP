package cn.com.dhcc.rp.machineroom.networkele.equipment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 设备节点集合
 * @author PCITECC02
 *
 */
public class Group implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4343330184116606245L;
	static public String TYPE_STATE = "0";
	static public String TYPE_VALUE = "1";
	private String refTab = null;
	private String collect = null;
	private String type = null;
	private List<EquipmentNode> listNode = new ArrayList<EquipmentNode>();
	public void addNode(EquipmentNode equipNode){
		this.listNode.add(equipNode);
	}
	
	public List<EquipmentNode> getListNode() {
		return listNode;
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

	@Override
	public String toString() {
		return "Group [refTab=" + refTab + ", collect=" + collect + ", type="
				+ type + ", listNode=" + listNode + "]";
	}

}
