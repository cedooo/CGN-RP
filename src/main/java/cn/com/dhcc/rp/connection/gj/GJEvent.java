package cn.com.dhcc.rp.connection.gj;

/**
 * key||վ������|�¼���Դ|�¼�����|�¼�����|�¼�����|����ʱ��|�¼�ID����event_guid��|
 * @author PCITECC02
 *
 */
public class GJEvent {
	
	private String key = null;
	private String stateName= null;   //վ������
	private String eventSource= null;    //�¼���Դ
	private String eventContent= null;    //�¼�����
	private String level= null;    //�¼�����
	private String type = null;    //�¼�����
	private String date= null;    //����ʱ��
	private String eventId = null;    //�¼�ID����event_guid��
	
	
	private String companyCode = null;     //��˾����
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
