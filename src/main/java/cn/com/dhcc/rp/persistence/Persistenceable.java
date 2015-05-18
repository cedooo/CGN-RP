package cn.com.dhcc.rp.persistence;

import cn.com.dhcc.rp.machineroom.networkele.NetworkElement;

/**
 * 持久化对象
 * Created by CeDo on 14-4-26.
 */
public interface Persistenceable {
	/**
	 * 将对象 入库
	 * @param networkElement 机房网元设备
	 * @return 入库成功返回true，否则返回false
	 */
    public boolean persistance(NetworkElement networkElement);
}
