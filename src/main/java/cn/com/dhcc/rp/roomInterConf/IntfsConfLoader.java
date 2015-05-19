package cn.com.dhcc.rp.roomInterConf;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.apache.commons.digester3.xmlrules.FromXmlRulesModule;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import cn.com.dhcc.rp.machineroom.factory.MachineRoomFactory;
import cn.com.dhcc.rp.persistence.po.RoomInterfaceConf;

public class IntfsConfLoader {
	static final private Logger log = Logger.getLogger(IntfsConfLoader.class.getClass());
	static final private String CONF_RULES_XML = "cn/com/dhcc/rp/roomInterConf/room-inf-conf-rules.xml";
	static final private String CONF_XML = "room-inf-conf.xml";

	public final static List<RoomInterfaceConf> getConnectionRoomInterfaceConf(){
		List<RoomInterfaceConf> listConf = IntfsConfLoader.getRoomInterfaceConf();
		List<RoomInterfaceConf> listConn = new ArrayList<RoomInterfaceConf>();
		for (RoomInterfaceConf roomInterfaceConf : listConf) {
			if("1".equals(roomInterfaceConf.getIsConn())){
				listConn.add(roomInterfaceConf);
			}
		}
		return listConn;
	}
	
	public final static RoomInterfaceConf getRoomInterfaceConf(String code){
		List<RoomInterfaceConf> listConf = IntfsConfLoader.getRoomInterfaceConf();
		RoomInterfaceConf rtConf = null;
		for (RoomInterfaceConf roomInterfaceConf : listConf) {
			if(roomInterfaceConf.getCompanyCode().equals(code)){
				rtConf = roomInterfaceConf;
				break;
			}else{
				continue;
			}
		}
		return rtConf;
	}
	
	public final static List<RoomInterfaceConf> getRoomInterfaceConf(){
		List<RoomInterfaceConf> listConf = new ArrayList<RoomInterfaceConf>();
		InputStream input = null;
		DigesterLoader  digesterLoader = DigesterLoader.newLoader(new XmlRules(CONF_RULES_XML));
		Digester digester = digesterLoader.newDigester();
		input = MachineRoomFactory.class.getClassLoader().getResourceAsStream(CONF_XML);
		Infs conf = null;
		try {
			conf = digester.parse(input);
			if(conf!=null){
				listConf.addAll(conf.getListCnf());
			}
		} catch (IOException e) {
			log.fatal("接口配置错误!");
			e.printStackTrace();
		} catch (SAXException e) {
			log.fatal("接口配置错误!");
			e.printStackTrace();
		}
		log.info("读取接口配置..");
		return listConf;
	}
	public static void main(String[] args) {
		System.out.println(IntfsConfLoader.getRoomInterfaceConf("ST"));
		System.out.println(IntfsConfLoader.getConnectionRoomInterfaceConf());
	}
}

class XmlRules extends FromXmlRulesModule{
	private String xmlPath = null;
	public XmlRules(String xmlPath){
		this.xmlPath = xmlPath.trim();			
	}
	@Override
	protected void loadRules() {
		InputStream input = MachineRoomFactory.class.getClassLoader().getResourceAsStream(xmlPath);
        this.loadXMLRules(input);
	}
	
}