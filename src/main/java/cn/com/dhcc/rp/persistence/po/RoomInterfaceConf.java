package cn.com.dhcc.rp.persistence.po;

public class RoomInterfaceConf {
	private String companyCode = null;
	private String ip = null;
	private String port = null;
	private String name = null;
	private String connectionClass = null;
	private String isConn = null;
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getConnectionClass() {
		return connectionClass;
	}
	public void setConnectionClass(String connectionClass) {
		this.connectionClass = connectionClass;
	}
	public String getIsConn() {
		return isConn;
	}
	public void setIsConn(String isConn) {
		this.isConn = isConn;
	}
	@Override
	public String toString() {
		return "接口配置[Code:" + companyCode + ", ip:" + ip
				+ ", 端口:" + port + ", 接口描述:" + name + "]";
	}
	
	
}
