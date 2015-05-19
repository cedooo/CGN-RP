package cn.com.dhcc.rp.machineroom.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.apache.commons.digester3.xmlrules.FromXmlRulesModule;
import org.xml.sax.SAXException;

import cn.com.dhcc.rp.machineroom.factory.MachineRoomFactory;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Equipment;
import cn.com.dhcc.rp.machineroom.networkele.equipment.EquipmentNode;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Group;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Module;
/**
 * @deprecated 仅仅用于转换xml成sql
 * @author PCITECC02
 *
 */
public class ParserSQL {
	public static void main(String[] args) throws IOException, SAXException {
		String[] defs = {
				"cn/com/dhcc/rp/machineroom/networkele/def/gj/dc23/airconditioning-2-def.xml",
				"cn/com/dhcc/rp/machineroom/networkele/def/gj/dc23/airconditioning-def.xml",
				"cn/com/dhcc/rp/machineroom/networkele/def/gj/dc23/airconditioning-simple-def.xml",
				"cn/com/dhcc/rp/machineroom/networkele/def/gj/dc23/door-def.xml",
				"cn/com/dhcc/rp/machineroom/networkele/def/gj/dc23/ele-meter-1-def.xml",
				"cn/com/dhcc/rp/machineroom/networkele/def/gj/dc23/ele-meter-2-def.xml",
				"cn/com/dhcc/rp/machineroom/networkele/def/gj/dc23/ele-meter-def.xml",
				"cn/com/dhcc/rp/machineroom/networkele/def/gj/dc23/leakage-def.xml",
				"cn/com/dhcc/rp/machineroom/networkele/def/gj/dc23/temp-humi-def.xml",
				"cn/com/dhcc/rp/machineroom/networkele/def/gj/dc23/ups-2-def.xml",
				"cn/com/dhcc/rp/machineroom/networkele/def/gj/dc23/ups-def.xml"
		};
		String rules = 
				"cn/com/dhcc/rp/machineroom/networkele/equipment/equipment-def-rules.xml";
		String companyCode = "GJ_1";
		DigesterLoader  digesterLoader = DigesterLoader.newLoader(new XmlRuless(rules));
		Digester digester = digesterLoader.newDigester();
		File file = new File("D:/inSQL1.sql");
		FileWriter fw = new FileWriter(file);
		InputStream input = null;
		for (int i = 0; i < defs.length; i++) {
			input = ParserSQL.class.getClassLoader().getResourceAsStream(defs[i]);
			Equipment equip = digester.parse(input);
			System.out.println(equip.getGroupSet());
			List<Group> listG = equip.getListGroup();
			for (Group group : listG) {
				List<EquipmentNode> listNode = group.getListNode();
				for (EquipmentNode equipmentNode : listNode) {
					StringBuilder strBuil = new StringBuilder("");
					strBuil.append("insert into txEquipTypeNodeName(fNodeName, fEquipType," +
							" fNode, fCompanyCode) values (");
					strBuil.append("'" +  equipmentNode.getName() + "', '"  + equip.getType() + "', '" 
							+ equipmentNode.getId() + "', '" + companyCode + "'");
					strBuil.append(");\n");
					fw.write(strBuil.toString());
				}
			}
			List<Module> listM = equip.getListMode();

			for (Module module : listM) {
				List<EquipmentNode> listNode = module.getListNode();
				for (EquipmentNode equipmentNode : listNode) {
					StringBuilder strBuil = new StringBuilder("");
					strBuil.append("insert into txEquipTypeNodeName(fNodeName, fEquipType," +
							" fNode, fCompanyCode) values (");
					strBuil.append("'" +  equipmentNode.getName() + "', '"  + equip.getType() + "', '" 
							+ equipmentNode.getId() + "', '" + companyCode + "'");
					strBuil.append(");\n");
					fw.write(strBuil.toString());
				}
			}
		}
		fw.close();
		input.close();
		
		String[] defs2 = {
				"cn/com/dhcc/rp/machineroom/networkele/def/gj/ae/airconditioning-def.xml",
				"cn/com/dhcc/rp/machineroom/networkele/def/gj/ae/fire-fight-def.xml",
				"cn/com/dhcc/rp/machineroom/networkele/def/gj/ae/leak-water-def.xml",
				"cn/com/dhcc/rp/machineroom/networkele/def/gj/ae/temp-humi-def.xml",
				"cn/com/dhcc/rp/machineroom/networkele/def/gj/ae/ups-def.xml"
		};
		String rules2 = 
				"cn/com/dhcc/rp/machineroom/networkele/equipment/equipment-def-rules.xml";
		String companyCode2 = "GJ_2";
		DigesterLoader  digesterLoader2 = DigesterLoader.newLoader(new XmlRuless(rules2));
		Digester digester2 = digesterLoader2.newDigester();
		File file2 = new File("D:/inSQL2.sql");
		FileWriter fw2 = new FileWriter(file2);
		InputStream input2 = null;
		for (int i2 = 0; i2 < defs2.length; i2++) {
			input2 = ParserSQL.class.getClassLoader().getResourceAsStream(defs2[i2]);
			Equipment equip2 = digester2.parse(input2);
			System.out.println(equip2.getGroupSet());
			List<Group> listG2 = equip2.getListGroup();
			for (Group group2 : listG2) {
				List<EquipmentNode> listNode2 = group2.getListNode();
				for (EquipmentNode equipmentNode2 : listNode2) {
					StringBuilder strBuil2 = new StringBuilder("");
					strBuil2.append("insert into txEquipTypeNodeName(fNodeName, fEquipType," +
							" fNode, fCompanyCode) values (");
					strBuil2.append("'" +  equipmentNode2.getName() + "', '"  + equip2.getType() + "', '" 
							+ equipmentNode2.getId() + "', '" + companyCode2 + "'");
					strBuil2.append(");\n");
					fw2.write(strBuil2.toString());
				}
			}
			List<Module> listM = equip2.getListMode();

			for (Module module : listM) {
				List<EquipmentNode> listNode = module.getListNode();
				for (EquipmentNode equipmentNode : listNode) {
					StringBuilder strBuil = new StringBuilder("");
					strBuil.append("insert into txEquipTypeNodeName(fNodeName, fEquipType," +
							" fNode, fCompanyCode) values (");
					strBuil.append("'" +  equipmentNode.getName() + "', '"  + equip2.getType() + "', '" 
							+ equipmentNode.getId() + "', '" + companyCode2 + "'");
					strBuil.append(");\n");
					fw2.write(strBuil.toString());
				}
			}
		}
		fw2.close();
		input2.close();
	}
}

class XmlRuless extends FromXmlRulesModule{
	private String xmlPath = null;
	public XmlRuless(String xmlPath){
		this.xmlPath = xmlPath.trim();			
	}
	@Override
	protected void loadRules() {
		InputStream input = MachineRoomFactory.class.getClassLoader().getResourceAsStream(xmlPath);
        this.loadXMLRules(input);
	}
	
}