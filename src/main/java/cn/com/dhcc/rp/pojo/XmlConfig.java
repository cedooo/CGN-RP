package cn.com.dhcc.rp.pojo;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;

public class XmlConfig {
	static final protected Logger log = Logger.getLogger(XmlConfig.class.getClass());
	
	static final public String EQUIPMENT_XML = "equipment";
	static final public String SUB_SYSTEM_XML = "sub_system";
	static final public String MACHINE_ROOM_DEF_RULES_XML = "machineroom_rules_xml";    //机房规则xml映射名称
	static final public String MACHINE_ROOM_DEF_XML = "machineroom_xml";    //机房 xml映射名称
	
	static final public String XML_REGEX = "(.)*\\.xml$";
	
	static final public String CLIENT_CLASS = "client_class";
	static final public String CODE = "code";
	
	static final public int XML_MIN_LENGTH = 3;
	
	private Map<String, String[]> mapNetworkEleXml = new HashMap<String, String[]>();
	private Map<String, String[]> mapSubSystemXml = new HashMap<String, String[]>();
	
	
	private String roomDefRules = null;
	private String roomDef = null;
	private String clientClass = null;
	private String code = null;
	
	/**
	 * 加载配置xml信息
	 * @return
	 */
	public XmlConfig(JobExecutionContext context){
		String neworkElementXml = context.getMergedJobDataMap().getString(EQUIPMENT_XML);
		log.debug("加载网元xml" + neworkElementXml.trim() + "<>" +neworkElementXml.trim().matches(XML_REGEX));
		if(neworkElementXml!=null&& neworkElementXml.length() > XML_MIN_LENGTH){//&&neworkElementXml.trim().matches(XML_REGEX)) {
			String[] xmlGroups = neworkElementXml.trim().split(";");
			for (String string : xmlGroups) {
				String[] sa = string.trim().split(":");
				String rules = sa[0].trim();
				String[]  defs = sa[1].split(",");
				mapNetworkEleXml.put(rules, defs);
			}
		}
		String subSystemXml = context.getMergedJobDataMap().getString(SUB_SYSTEM_XML);
		log.debug("加载子系统xml = " + subSystemXml);
		if(subSystemXml!=null&& subSystemXml.length() > XML_MIN_LENGTH){//&&neworkElementXml.trim().matches(XML_REGEX)) {
			String[] xmlGroups = subSystemXml.trim().split(";");
			for (String string : xmlGroups) {
				String[] sa = string.trim().split(":");
				String rules = sa[0].trim();
				String[]  defs = sa[1].split(",");
				mapSubSystemXml.put(rules, defs);
			}
		}
		roomDefRules = context.getMergedJobDataMap().getString(MACHINE_ROOM_DEF_RULES_XML);
		roomDef = context.getMergedJobDataMap().getString(MACHINE_ROOM_DEF_XML);
		clientClass = context.getMergedJobDataMap().getString(CLIENT_CLASS);
		code = context.getMergedJobDataMap().getString(CODE);
	}

	
	public String getRoomDefRules() {
		return roomDefRules;
	}

	public void setRoomDefRules(String roomDefRules) {
		this.roomDefRules = roomDefRules;
	}

	public String getRoomDef() {
		return roomDef;
	}

	public void setRoomDef(String roomDef) {
		this.roomDef = roomDef;
	}

	public String getClientClass() {
		return clientClass;
	}

	public void setClientClass(String clientClass) {
		this.clientClass = clientClass;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public Map<String, String[]> getMapNetworkEleXml() {
		return mapNetworkEleXml;
	}


	public void setMapNetworkEleXml(Map<String, String[]> mapNetworkEleXml) {
		this.mapNetworkEleXml = mapNetworkEleXml;
	}


	public Map<String, String[]> getMapSubSystemXml() {
		return mapSubSystemXml;
	}
	
	
}
