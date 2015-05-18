package cn.com.dhcc.rp.roomInterConf;

import java.util.ArrayList;
import java.util.List;

import cn.com.dhcc.rp.persistence.po.RoomInterfaceConf;

public class Infs {
	private List<RoomInterfaceConf> listCnf = new ArrayList<RoomInterfaceConf>();

	public List<RoomInterfaceConf> getListCnf() {
		return listCnf;
	}

	public void setListCnf(List<RoomInterfaceConf> listCnf) {
		this.listCnf = listCnf;
	}

	public void addCnf(RoomInterfaceConf conf){
		this.listCnf.add(conf);
	}
}
