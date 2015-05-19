package cn.com.dhcc.rp.connection.gj;

import cn.com.dhcc.rp.connection.RealTimeData;

public class GJData extends RealTimeData{
	private static final long serialVersionUID = -1497300440071524142L;
	
	static final public String ATTR_VALUE_TAG = "-VA";
	static final public String EVENT_TAG = "-EV";
	private String key = null;
	private String value = null;
	private String cd = null;
	public GJData(){}
	public GJData(String key, String value){
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		GJData other = (GJData) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	
}
