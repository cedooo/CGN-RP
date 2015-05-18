package cn.com.dhcc.rp.st;

public class UPSNode {
	private String ID = "";
	private String name = "";
	private String value = "";
	private String unit = "";
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Override
	public String toString() {
		return "UPSNode [ID=" + ID + ", name=" + name + ", value=" + value
				+ ", unit=" + unit + "]";
	}
	
}
