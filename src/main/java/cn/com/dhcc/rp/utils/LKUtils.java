package cn.com.dhcc.rp.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.apache.commons.digester3.xmlrules.FromXmlRulesModule;
import org.xml.sax.SAXException;

import cn.com.dhcc.rp.machineroom.MachineRoom;


public class LKUtils {
	
	/**
	 * 得到机房信息
	 * @param machineRoomrulesXml    机房规则xml路径
	 * @param machineRoomXml    机房xml路径
	 * @return
	 * @deprecated
	 */
	static public MachineRoom getMachineRoom(String machineRoomrulesXml, String machineRoomXml){
		boolean argsRight = machineRoomXml!=null && !machineRoomXml.trim().equals("") && 
				machineRoomrulesXml!=null && !machineRoomrulesXml.trim().equals("") ;
		MachineRoom equips = null;
		if(argsRight){
			String def = machineRoomXml.trim();
			String defRules = machineRoomrulesXml.trim();
			DigesterLoader  digesterLoader = DigesterLoader.newLoader(new XmlRules(defRules));
			Digester digester = digesterLoader.newDigester();
			InputStream input = LKUtils.class.getClassLoader().getResourceAsStream(def);
			try {
				equips = digester.parse(input);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}finally{
				if(input!=null){
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}else{
			System.err.println("定义文件 不能为 空");
		}
		return equips;
	}

}
class XmlRules extends FromXmlRulesModule{
	private String xmlPath = null;
	public XmlRules(String xmlPath){
		this.xmlPath = xmlPath;			
	}
	@Override
	protected void loadRules() {
		InputStream input = LKUtils.class.getClassLoader().getResourceAsStream(xmlPath);
        this.loadXMLRules(input);
	}
	
}
