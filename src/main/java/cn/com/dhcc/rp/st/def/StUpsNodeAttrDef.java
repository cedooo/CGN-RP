package cn.com.dhcc.rp.st.def;

public class StUpsNodeAttrDef {
	public static String COLLECT_TRUE = "1";
	private String name = null;
	private String collect = null;
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCollect() {
		return collect;
	}

	public void setCollect(String collect) {
		this.collect = collect;
	}

	@Override
	public String toString() {
		return "StUpsNodeAttrDef [name=" + name + ", collect=" + collect + "]";
	}

	
}
