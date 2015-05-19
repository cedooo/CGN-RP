package cn.com.dhcc.rp.persistence.po;
/**
 * 设备状态
 * @author pcitecc02
 *
 */
public class RoomCommState  extends POObj{
	public static String STATE_GREEN = "1";    //绿色状态
	public static String STATE_RED = "0";    //红色状态
	
	private String collectTime = null;    //采集时间
	private String partID = null;    //模块ID
	private String stateValue = null;    //状态值
	private String id = null;    //设备ID
	private String companyCode = null;    //公司编码
	private String grState = null;    //正常状态值
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
	public String getStateValue() {
		return stateValue;
	}
	public void setStateValue(String stateValue) {
		this.stateValue = stateValue;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getGrState() {
		return grState;
	}
	public void setGrState(String grState) {
		this.grState = grState;
	}
	@Override
	public String toString() {
		return "RoomCommState [collectTime=" + collectTime + ", partID="
				+ partID + ", stateValue=" + stateValue + ", id=" + id
				+ ", companyCode=" + companyCode + ", grState=" + grState + "]";
	}

	
}
