package cn.com.dhcc.rp.persistence.po;
/**
 * �豸״̬
 * @author pcitecc02
 *
 */
public class RoomCommState  extends POObj{
	public static String STATE_GREEN = "1";    //��ɫ״̬
	public static String STATE_RED = "0";    //��ɫ״̬
	
	private String collectTime = null;    //�ɼ�ʱ��
	private String partID = null;    //ģ��ID
	private String stateValue = null;    //״ֵ̬
	private String id = null;    //�豸ID
	private String companyCode = null;    //��˾����
	private String grState = null;    //����״ֵ̬
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
