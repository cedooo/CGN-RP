package cn.com.dhcc.rp.persistence.po;

public class TxPowerBoxCommBranchCurrPerf  extends POObj{
	private String id = null;
	private String collectTime = null;
	private String partID = null;
	private String branchCurrency = null;
	private String companyCode = null;
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

	public String getBranchCurrency() {
		return branchCurrency;
	}

	public void setBranchCurrency(String branchCurrency) {
		this.branchCurrency = branchCurrency;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
}
