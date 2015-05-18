package cn.com.dhcc.rp.connection.gj;

/**
 * key||站点名称|事件来源|事件内容|事件级别|事件类型|发生时间|事件ID（即event_guid）|
 * @author PCITECC02
 *
 */
public class GJEvent {
	
	private String key = null;
	private String stateName= null;   //站点名称
	private String eventSource= null;    //事件来源
	private String eventContent= null;    //事件内容
	private String level= null;    //事件级别
	private String type = null;    //事件类型
	private String date= null;    //发生时间
	private String eventId = null;    //事件ID（即event_guid）
	
	
	private String companyCode = null;     //公司编码
	private String id = null;
	private String attrName = null;
	
	
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getEventSource() {
		return eventSource;
	}

	public void setEventSource(String eventSource) {
		this.eventSource = eventSource;
	}

	public String getEventContent() {
		return eventContent;
	}

	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Override
	public String toString() {
		return "GJEvent [key=" + key + ", stateName=" + stateName
				+ ", eventSource=" + eventSource + ", eventContent="
				+ eventContent + ", level=" + level + ", type=" + type
				+ ", date=" + date + ", eventId=" + eventId + ", companyCode="
				+ companyCode + ", id=" + id + ", attrName=" + attrName + "]";
	}
	
}
