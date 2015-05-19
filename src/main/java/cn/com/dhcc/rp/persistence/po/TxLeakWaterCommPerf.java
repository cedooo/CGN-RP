package cn.com.dhcc.rp.persistence.po;

public class TxLeakWaterCommPerf  extends POObj{
	private String id = null;
	private String partID = null;
	private String collectTime = null;
	private String leakPosition = null;
	private String lineResistance = null;
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

	public String getLeakPosition() {
		return leakPosition;
	}

	public void setLeakPosition(String leakPosition) {
		this.leakPosition = leakPosition;
	}

	public String getLineResistance() {
		return lineResistance;
	}

	public void setLineResistance(String lineResistance) {
		this.lineResistance = lineResistance;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

}
