package cn.com.dhcc.rp.connection.po;
/**
 * 查询共济节点名称 参数
 * @author PCITECC02
 *
 */
public class SelectGJNodePara {
	private String companyCode = null;
	private String eventKey = null;
	private String nodeID = null;
	private String equipType = null;
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	public String getNodeID() {
		return nodeID;
	}
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	public String getEquipType() {
		return equipType;
	}
	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}
	
}
