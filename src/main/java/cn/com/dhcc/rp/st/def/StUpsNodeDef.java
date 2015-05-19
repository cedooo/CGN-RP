package cn.com.dhcc.rp.st.def;

import java.util.ArrayList;
import java.util.List;

public class StUpsNodeDef {
	public static String COLLECT_TRUE = "1";
	public static String TYPE_PERF_VALUE = "1";
	public static String TYPE_STATE_VALUE = "0";
	
	private String id = null;
	private String name = null;
	private String ref = null;
	private String type = null;
	private String collect = null;
	private String grState = null;
	private List<StUpsNodeAttrDef> listAttr = new ArrayList<StUpsNodeAttrDef>();
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public List<StUpsNodeAttrDef> getListAttr() {
		return listAttr;
	}
	public void setListAttr(List<StUpsNodeAttrDef> listAttr) {
		this.listAttr = listAttr;
	}
	public void addAttr(StUpsNodeAttrDef attr){
		this.listAttr.add(attr);
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
	@Override
	public String toString() {
		return "Node [id=" + id + ", name=" + name + ", ref=" + ref + ", type="
				+ type + ", listAttr=" + listAttr + "]";
	}
	
}
