package cn.com.dhcc.rp.machineroom.networkele.subsystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.apache.commons.digester3.xmlrules.FromXmlRulesModule;
import org.xml.sax.SAXException;

import cn.com.dhcc.rp.machineroom.factory.MachineRoomFactory;
import cn.com.dhcc.rp.machineroom.networkele.NetworkElement;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Equipment;


/**
 * Created by CeDo on 14-4-26.
 */
public class SubSystem extends NetworkElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1995638645180915335L;

	private List<Equipment> listEquip = new ArrayList<Equipment>();
	private List<Equipment> listEquipDef = new ArrayList<Equipment>();
	public void add(Equipment e){
		listEquip.add(e);
	}
	public void addDef(Equipment e){
		listEquipDef.add(e);
	}
	public List<Equipment> getListEquip() {
		return listEquip;
	}
	
	public static void main(String[] args) {
		InputStream input = null;
		DigesterLoader  digesterLoader = DigesterLoader.newLoader(
				new XmlRules("rebuild/machineroom/networkele/subsystem/subsystem-def-rules.xml"));
		Digester digester = digesterLoader.newDigester();
		input = SubSystem.class.getClassLoader()
				.getResourceAsStream("rebuild/machineroom/networkele/def/lk/caiwu/sub-temp-humi-def.xml");
		try {
			SubSystem subSys = digester.parse(input);
			//System.out.println(subSys.getListEquip());
			//System.out.println(subSys.getListEquipDef());
			List<Equipment> listEquipment  = subSys.getListEquip();
			List<Equipment> listEquipmentDef  = subSys.listEquipDef;
			
			for (Equipment equipment : listEquipment) {
				for (Equipment equipmentDef : listEquipmentDef) {
					if(equipment.getType().equals(equipmentDef.getType())){
						equipment.setGroupSet(equipmentDef.getGroupSet());
						equipment.setModeset(equipmentDef.getModeset());
						break;
					}else{
						continue;
					}
				}
			}
			for (Equipment equipment : listEquipment) {
				System.out.println(equipment.getId() + "," +equipment.getGroupSet());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	
	public List<Equipment> getListEquipDef() {
		return listEquipDef;
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