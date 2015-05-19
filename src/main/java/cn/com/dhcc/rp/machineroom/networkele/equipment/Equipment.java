package cn.com.dhcc.rp.machineroom.networkele.equipment;

import java.util.ArrayList;
import java.util.List;

import cn.com.dhcc.rp.machineroom.networkele.NetworkElement;



/**
 * Created by CeDo on 14-4-26.
 */
public class Equipment extends NetworkElement {
	/**
	 * 
	 */
	private static final long serialVersionUID = 649655598519743731L;
	private List<Group> groupSet = new ArrayList<Group>();
	private List<Module> modeset = new ArrayList<Module>();

	public void addAllNode(Group nodeGroup){
		this.groupSet.add(nodeGroup);
	}
	public void addMode(Module mode){
		this.modeset.add(mode);
	}
	public List<Group> getListGroup() {
		return groupSet;
	}
	public List<Module> getListMode() {
		return modeset;
	}
	public List<Group> getGroupSet() {
		return groupSet;
	}
	public void setGroupSet(List<Group> groupSet) {
		this.groupSet = groupSet;
	}
	public List<Module> getModeset() {
		return modeset;
	}
	public void setModeset(List<Module> modeset) {
		this.modeset = modeset;
	}

	
}
