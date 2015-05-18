package cn.com.dhcc.rp.persistence.factory;

import cn.com.dhcc.rp.machineroom.networkele.equipment.Equipment;
import cn.com.dhcc.rp.machineroom.networkele.subsystem.SubSystem;
import cn.com.dhcc.rp.persistence.Persistenceable;
import cn.com.dhcc.rp.persistence.gj.GJEquipmentPersistence;
import cn.com.dhcc.rp.persistence.lk.LKEquipmentPersistence;
import cn.com.dhcc.rp.persistence.lk.LKSubSystemPersistence;
import cn.com.dhcc.rp.persistence.po.RoomInterfaceConf;
/**
 * 持久化 工厂
 * @author PCITECC02
 *
 */
public class PersistenceFactory {
	/**
	 * 得到龙控的持久化对象
	 * @param networkElement 入库网元
	 * @param roomInterfaceCof 接口配置信息
	 * @return
	 */
	static public Persistenceable getLKPersistenceInstance(Class<?> clazz, RoomInterfaceConf roomInterfaceCof ){
		Persistenceable persis = null;
		if(clazz == Equipment.class){
			persis = new LKEquipmentPersistence(roomInterfaceCof);
		}else if (clazz == SubSystem.class){
			persis = new LKSubSystemPersistence(roomInterfaceCof);
		}
		return persis;
	}
	/**
	 * 得到共济的持久化对象
	 * @param networkElement 入库网元
	 * @param roomInterfaceCof 接口配置信息
	 * @return
	 */
	static public Persistenceable getGJPersistenceInstance(Class<?> clazz, RoomInterfaceConf roomInterfaceCof ){
		Persistenceable persis = null;
		if(clazz == Equipment.class){
			persis = new GJEquipmentPersistence(roomInterfaceCof);
		}else if (clazz == SubSystem.class){
			//实例化 子系统持久化对象
		}
		return persis;
	}
}
