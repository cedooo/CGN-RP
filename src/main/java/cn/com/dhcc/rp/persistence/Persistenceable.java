package cn.com.dhcc.rp.persistence;

import cn.com.dhcc.rp.machineroom.networkele.NetworkElement;

/**
 * �־û�����
 * Created by CeDo on 14-4-26.
 */
public interface Persistenceable {
	/**
	 * ������ ���
	 * @param networkElement ������Ԫ�豸
	 * @return ���ɹ�����true�����򷵻�false
	 */
    public boolean persistance(NetworkElement networkElement);
}
