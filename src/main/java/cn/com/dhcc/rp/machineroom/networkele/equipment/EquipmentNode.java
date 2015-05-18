package cn.com.dhcc.rp.machineroom.networkele.equipment;

import java.io.Serializable;

import cn.com.dhcc.rp.connection.RealTimeData;


public class EquipmentNode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4864482298787377701L;
	public static String TYPE_STATE = "0";    //状态类型变量
	public static String TYPE_VALUE = "1";    //数值类型变量
	static public String COLLECT = "1";   
	
	public static String SUB_TYPE_NORMAL = "0";    //子类型：一般类型
	public static String SUB_TYPE_PART = "1";    //子类型：模块类型
	public static String SUB_TYPE_CONF = "2";    //子类型：设定类型
	public static String SUB_TYPE_RAISE = "3";    //子类型：自动增长类型,如：运行时间
	
	private String id = null;
	private String name = null;
	private String ref = null;
	private String refTab = null;
	private String partID = null;
	private String type = null;
	private String subtype = null;
	private String collect = null;
	private String grState = null;
	private String queryPartID = null;
	private RealTimeData realData = null;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getRefTab() {
		return refTab;
	}
	public void setRefTab(String refTab) {
		this.refTab = refTab;
	}
	public String getPartID() {
		return partID;
	}
	public void setPartID(String partID) {
		this.partID = partID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public String getCollect() {
		return collect;
	}
	public void setCollect(String collect) {
		this.collect = collect;
	}
	public String getGrState() {
		return grState;
	}
	public void setGrState(String grState) {
		this.grState = grState;
	}
	
	public RealTimeData getRealData() {
		return realData;
	}
	public void setRealData(RealTimeData realData) {
		this.realData = realData;
	}

	public String getQueryPartID() {
		return queryPartID;
	}
	public void setQueryPartID(String queryPartID) {
		this.queryPartID = queryPartID;
	}
	@Override
	public String toString() {
		return "EquipmentNode [id=" + id + ", name=" + name + ", ref=" + ref
				+ ", refTab=" + refTab + ", partID=" + partID + ", type="
				+ type + ", subtype=" + subtype + ", collect=" + collect
				+ ", grState=" + grState + ", realData=" + realData + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((collect == null) ? 0 : collect.hashCode());
		result = prime * result + ((grState == null) ? 0 : grState.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((partID == null) ? 0 : partID.hashCode());
		result = prime * result + ((ref == null) ? 0 : ref.hashCode());
		result = prime * result + ((refTab == null) ? 0 : refTab.hashCode());
		result = prime * result + ((subtype == null) ? 0 : subtype.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EquipmentNode other = (EquipmentNode) obj;
		if (collect == null) {
			if (other.collect != null)
				return false;
		} else if (!collect.equals(other.collect))
			return false;
		if (grState == null) {
			if (other.grState != null)
				return false;
		} else if (!grState.equals(other.grState))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (partID == null) {
			if (other.partID != null)
				return false;
		} else if (!partID.equals(other.partID))
			return false;
		if (ref == null) {
			if (other.ref != null)
				return false;
		} else if (!ref.equals(other.ref))
			return false;
		if (refTab == null) {
			if (other.refTab != null)
				return false;
		} else if (!refTab.equals(other.refTab))
			return false;
		if (subtype == null) {
			if (other.subtype != null)
				return false;
		} else if (!subtype.equals(other.subtype))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
}
