package cn.com.dhcc.rp.job.po;

public class TxRoomPowerUsedPerf {
	private String id = null;
	private String collectTime = null;
	private String partID = null;
	private String powerUsed = null;
	private String companyCode = null;
	
	@Override
	public String toString() {
		return "TxRoomPowerUsedPerf [id=" + id + ", collectTime=" + collectTime
				+ ", partID=" + partID + ", powerUsed=" + powerUsed
				+ ", companyCode=" + companyCode + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}
	public String getPartID() {
		return partID;
	}
	public void setPartID(String partID) {
		this.partID = partID;
	}
	public String getPowerUsed() {
		return powerUsed;
	}
	public void setPowerUsed(String powerUsed) {
		this.powerUsed = powerUsed;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
}
