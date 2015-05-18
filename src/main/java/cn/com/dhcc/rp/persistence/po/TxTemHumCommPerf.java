package cn.com.dhcc.rp.persistence.po;

public class TxTemHumCommPerf  extends POObj{
	private String id = null;
	private String partID = null;
	private String collectTime = null;
	private String temperature = null;
	private String huminity = null;
	private String companyCode = null;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPartID() {
		return partID;
	}
	public void setPartID(String partID) {
		this.partID = partID;
	}
	public String getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getHuminity() {
		return huminity;
	}
	public void setHuminity(String huminity) {
		this.huminity = huminity;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	
}
