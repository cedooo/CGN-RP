package cn.com.dhcc.rp.machineroom.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.apache.commons.digester3.xmlrules.FromXmlRulesModule;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.xml.sax.SAXException;

import cn.com.dhcc.rp.client.RPClient;
import cn.com.dhcc.rp.machineroom.MachineRoom;
import cn.com.dhcc.rp.machineroom.networkele.NetworkElement;
import cn.com.dhcc.rp.machineroom.networkele.equipment.Equipment;
import cn.com.dhcc.rp.machineroom.networkele.subsystem.SubSystem;
import cn.com.dhcc.rp.persistence.po.RoomInterfaceConf;
import cn.com.dhcc.rp.pojo.XmlConfig;
import cn.com.dhcc.rp.roomInterConf.IntfsConfLoader;


public final class MachineRoomFactory {
	static private Logger log = Logger.getLogger(MachineRoomFactory.class.getClass());
	static public Set<MachineRoom> listMachineRoom = new HashSet<MachineRoom>();
	
	static public MachineRoom getMachineRoomInstance(JobExecutionContext jobExeContent){
		XmlConfig xmlConfig = new XmlConfig(jobExeContent);
		Iterator<MachineRoom> iter = listMachineRoom.iterator();
		while(iter.hasNext()){
			MachineRoom maro = iter.next();
			if(xmlConfig!=null && xmlConfig.getCode().equals(maro.getCode())){
				return maro;
			}
		}
		MachineRoom machineRoom = getMachineRoom(xmlConfig);
		if(machineRoom!=null){
			listMachineRoom.add(machineRoom);
		}
		return machineRoom;
	}
	
	static private MachineRoom getMachineRoom(XmlConfig xmlConfig){

		MachineRoom machineRoom = null;
		
		//XmlConfig xmlConfig = new XmlConfig(jobExeContent);

		InputStream input = null;
		try {
			DigesterLoader  digesterLoader = DigesterLoader.newLoader(new XmlRules(xmlConfig.getRoomDefRules()));
			Digester digester = digesterLoader.newDigester();
			input = MachineRoomFactory.class.getClassLoader().getResourceAsStream(xmlConfig.getRoomDef());
			machineRoom = digester.parse(input);
//System.out.println(machineRoom);
//System.out.println(machineRoom.getNetworkElementList().size() + ">>>>>>>" +machineRoom.getNetworkElementList());

			/*
			 * 设备
			 */
			
			//网元 map<'定义规则xml'， 定义xml[]>
			Map<String, String[]> mapNetworkEle = xmlConfig.getMapNetworkEleXml();
			List<NetworkElement> listEquipment = new ArrayList<NetworkElement>();
			for (Entry<String, String[]> maps : mapNetworkEle.entrySet()) {
				DigesterLoader  rulesLoader = DigesterLoader.newLoader(new XmlRules(maps.getKey()));
				String[] vals = maps.getValue();
				Digester equipDigester = rulesLoader.newDigester();
				for (String val : vals) {
					log.debug("设备定义xml>>>" + val.trim() + "<<<<<<<");
					input = MachineRoomFactory.class.getClassLoader().getResourceAsStream(val.trim());
					NetworkElement networkElement = equipDigester.parse(input);
					log.debug(networkElement);
					boolean defValid = networkElement!=null;
					if(defValid){
						//所有网元设备
						List<NetworkElement> networkElementList = machineRoom.getNetworkElementList();
						for (NetworkElement ne2 : networkElementList) {
							if(ne2.getType().equals(networkElement.getType())){
								networkElement.setId(ne2.getId());
								networkElement.setName(ne2.getName());
								listEquipment.add(networkElement.deepClone());
								log.debug("-------------------克隆的对象--------\n" + networkElement.deepClone());
							}
						}
					}
					
				}
			}
			
			/*
			 * 子系统
			 */
			List<NetworkElement> listSubSys = new ArrayList<NetworkElement>();
			Map<String, String[]> mapSubSystem = xmlConfig.getMapSubSystemXml();
			for (Entry<String, String[]> maps : mapSubSystem.entrySet()) {
				DigesterLoader  rulesLoader = DigesterLoader.newLoader(new XmlRules(maps.getKey()));
				String[] vals = maps.getValue();
				Digester equipDigester = rulesLoader.newDigester();
				for (String val : vals) {
					log.debug("子系统定义xml>>>" + val.trim() + "<<<<<");
					input = MachineRoomFactory.class.getClassLoader().getResourceAsStream(val.trim());
					SubSystem subDef = equipDigester.parse(input);
					log.debug("子系统定义: " + subDef);
					boolean defValid = subDef!=null;
					if(defValid){
						//子系统 设备节点 处理：加入到设备中
						List<Equipment> listSubEquip  = subDef.getListEquip();
						List<Equipment> listSubEquipDef  = subDef.getListEquipDef();
						
						for (Equipment equipment : listSubEquip) {
							for (Equipment equipmentDef : listSubEquipDef) {
								if(equipment.getType()!=null && equipment.getType().equals(equipmentDef.getType())){
									equipment.setGroupSet(((Equipment)equipmentDef.deepClone()).getGroupSet());
									equipment.setModeset(((Equipment)equipmentDef.deepClone()).getModeset());
									break;
								}else{
									continue;
								}
							}
						}
						
						List<NetworkElement> listSubSystem = machineRoom.getNetworkElementList();
						log.debug("机房子系统列表：" + listSubSystem);				
						for (NetworkElement sys : listSubSystem) {
							if(sys.getType().equals(subDef.getType())){
								subDef.setId(sys.getId());
								subDef.setName(sys.getName());
								listSubSys.add(subDef.deepClone());
							}
						}
					}
				}
			}
			machineRoom.getNetworkElementList().clear();
			machineRoom.getNetworkElementList().addAll(listEquipment);
			machineRoom.getNetworkElementList().addAll(listSubSys);
			log.debug("机房信息==>" + machineRoom);			
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

		//客户端 配置信息
		try {
			RPClient client = (RPClient) Class.forName(xmlConfig.getClientClass()).newInstance();
			//从数据库获取配置
			/*SqlSession ses = DBDelegate.getSqlSessionFactory().openSession();
			RoomInterfaceConf conf = ses.selectOne("select_interface_info_by_company_code", xmlConfig.getCode());
			ses.close();*/
			
			RoomInterfaceConf conf = IntfsConfLoader.getRoomInterfaceConf(xmlConfig.getCode());
			
			client.setRoomInterfaceCof(conf);
			machineRoom.setClient(client);
			log.debug("---client----" + client);
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		return machineRoom;
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