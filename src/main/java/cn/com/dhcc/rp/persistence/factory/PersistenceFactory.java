package cn.com.dhcc.rp.persistence.factory;

import cn.com.dhcc.rp.machineroom.networkele.equipment.Equipment;
import cn.com.dhcc.rp.machineroom.networkele.subsystem.SubSystem;
import cn.com.dhcc.rp.persistence.Persistenceable;
import cn.com.dhcc.rp.persistence.gj.GJEquipmentPersistence;
import cn.com.dhcc.rp.persistence.lk.LKEquipmentPersistence;
import cn.com.dhcc.rp.persistence.lk.LKSubSystemPersistence;
import cn.com.dhcc.rp.persistence.po.RoomInterfaceConf;
/**
 * �־û� ����
 * @author PCITECC02
 *
 */
public class PersistenceFactory {
	/**
	 * �õ����صĳ־û�����
	 * @param networkElement �����Ԫ
	 * @param roomInterfaceCof �ӿ�������Ϣ
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
	 * �õ����õĳ־û�����
	 * @param networkElement �����Ԫ
	 * @param roomInterfaceCof �ӿ�������Ϣ
	 * @return
	 */
	static public Persistenceable getGJPersistenceInstance(Class<?> clazz, RoomInterfaceConf roomInterfaceCof ){
		Persistenceable persis = null;
		if(clazz == Equipment.class){
			persis = new GJEquipmentPersistence(roomInterfaceCof);
		}else if (clazz == SubSystem.class){
			//ʵ���� ��ϵͳ�־û�����
		}
		return persis;
	}
}
