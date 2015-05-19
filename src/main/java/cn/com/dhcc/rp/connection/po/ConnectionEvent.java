package cn.com.dhcc.rp.connection.po;
/**
 * 连接事件
 * @author PCITECC02
 *
 */
public class ConnectionEvent {
	static public final String LINK_CONNECTED = "connected";
	static public final String LINK_DISCONNECTED = "connected";
	
	private String id = null;
	private String attrName = null;
	private String level = null;
	private String state = null;
	private String value = null;
	private String collectTime = null;
	private String content = null;
	private String companyCode = null;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	@Override
	public String toString() {
		return "ConnectionEvent [id=" + id + ", attrName=" + attrName
				+ ", level=" + level + ", state=" + state + ", value=" + value
				+ ", collectTime=" + collectTime + ", content=" + content
				+ ", companyCode=" + companyCode + "]";
	}
	
	
}
