package cn.com.dhcc.rp.machineroom;


import java.util.ArrayList;
import java.util.List;

import cn.com.dhcc.rp.client.RPClient;
import cn.com.dhcc.rp.machineroom.networkele.NetworkElement;


/**
 * Created by CeDo on 14-4-26.
 */
public abstract class MachineRoom {
	protected String name = null;    //名称
	protected String location = null;    //地点
	protected String id = null;    //id
	protected String code = null;    //编码
	protected List<NetworkElement> networkElementList = new ArrayList<NetworkElement>();
	protected RPClient client = null;    //采集‘客户端’
    
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
    public List<NetworkElement> getNetworkElementList() {
		return networkElementList;
	}
	public RPClient getClient() {
		return client;
	}
	public void setClient(RPClient client) {
		this.client = client;
	}
	public void addNetworkElement(NetworkElement ne){
    	this.networkElementList.add(ne);
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public abstract boolean doCollectJob();
	@Override
	public String toString() {
		return "机房:" + name + ",机房位置:" + location
				+ "\n接口信息:" + client
				+ "\n网元:" + networkElementList 
				 + "\n" + "============================================";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MachineRoom other = (MachineRoom) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
    
    
}
