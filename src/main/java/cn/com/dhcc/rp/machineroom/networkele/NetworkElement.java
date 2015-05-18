package cn.com.dhcc.rp.machineroom.networkele;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import cn.com.dhcc.rp.machineroom.networkele.equipment.Equipment;



/**
 * Created by CeDo on 14-4-26.
 */
public abstract class NetworkElement implements Serializable,Cloneable{
	public static void main(String[] args) {
		NetworkElement ne = new Equipment();
		ne.setId("111");
		ne.setName("2222");
		NetworkElement ne2 = ne.deepClone();
		ne2.setName("陈东");
		System.out.println(ne);
		System.out.println(ne2);
		NetworkElement ne3 = ne;
		ne3.setName("1122");
		System.out.println(ne);
		System.out.println(ne3);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;    //id
	private String name = null;    //名称
	private String type = null;    //设备类型
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 深度克隆
	 * @return
	 */
	public NetworkElement deepClone(){
		NetworkElement dc = null;
		ByteArrayOutputStream baos = null;
		ByteArrayInputStream bais = null;
		try {
	        baos = new ByteArrayOutputStream();
	        ObjectOutputStream oos = new ObjectOutputStream(baos);
	        oos.writeObject(this);
	        bais = new ByteArrayInputStream(baos.toByteArray());
	        ObjectInputStream bis = new ObjectInputStream(bais);
	        dc = (NetworkElement)bis.readObject();	
        } catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
			try {
				if(baos!=null){
					baos.close();
				}
				if(bais!=null){
					bais.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dc;
	}
	
	@Override
	public String toString() {
		return "\n\tNetworkElement [id=" + id + ", name=" + name + ", type=" + type
				+ "]\n";
	}
	
}
