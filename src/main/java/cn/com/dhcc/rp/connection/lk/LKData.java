package cn.com.dhcc.rp.connection.lk;

import cn.com.dhcc.rp.connection.RealTimeData;

public class LKData extends RealTimeData{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3914352627184236441L;
	static public final int TYPE_DATA = 0;
	static public final int TYPE_EVENT = 1;
	
	private int type = -1;    //�������ͣ�0 ����, 1 �¼�
	
	private String agent = null;    //��������
	private String d = null;	//�豸����
	private String id = null;   //�豸����ID
	private String v = null;    //�豸����ֵ  ���� �¼�����
	private String t = null;    //�Ƿ��Ƕ�ʱת������
	private String e = null;    //�¼�����
	private String c = null;    //�豸��������
	
	private String m = null;    //��������
	private String ev = null;    //����״̬ 1 ����  0 ���
	private String clas = null;    //�¼��ȼ�
	private String alarm = null;    //�Ƿ�澯 1 �澯 0����
	private String z = null;   //����ֵ
	private String u = null;    //��λ
	
	private String companyCode = null;     //��˾����

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public String getEv() {
		return ev;
	}


	public void setEv(String ev) {
		this.ev = ev;
	}


	public String getClas() {
		return clas;
	}


	public void setClas(String clas) {
		this.clas = clas;
	}


	public String getAlarm() {
		return alarm;
	}


	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}


	public String getZ() {
		return z;
	}

	public void setZ(String z) {
		this.z = z;
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((d == null) ? 0 : d.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		LKData other = (LKData) obj;
		if (agent == null) {
			if (other.agent != null)
				return false;
		} else if (!agent.equals(other.agent))
			return false;
		if (d == null) {
			if (other.d != null)
				return false;
		} else if (!d.equals(other.d))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LKData [type=" + type + ", agent=" + agent + ", d=" + d
				+ ", id=" + id + ", v=" + v + ", t=" + t + ", e=" + e + ", c="
				+ c + ", m=" + m + ", ev=" + ev + ", clas=" + clas + ", alarm="
				+ alarm + ", z=" + z + ", u=" + u + ", collectTime="
				+ collectTime + "]";
	}

}
