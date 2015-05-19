package cn.com.dhcc.rp.st.def;

import java.util.ArrayList;
import java.util.List;

public class StUpsDef {
	private List<StUpsNodeDef> listNodeDef = new ArrayList<StUpsNodeDef>();


	public List<StUpsNodeDef> getListNodeDef() {
		return listNodeDef;
	}

	public void setListNodeDef(List<StUpsNodeDef> listNodeDef) {
		this.listNodeDef = listNodeDef;
	}

	public void addNode(StUpsNodeDef node){
		this.listNodeDef.add(node);
	}

	@Override
	public String toString() {
		return "个数:" + this.listNodeDef.size() + "\nSTUPS [listNode=" + listNodeDef + "]";
	}
	
}
