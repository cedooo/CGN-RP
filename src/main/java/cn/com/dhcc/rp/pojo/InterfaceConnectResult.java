package cn.com.dhcc.rp.pojo;

public enum InterfaceConnectResult {
	CONNECTED("连接成功"){}, UNKNOW_HOST("未知主机"){}, HOST_NOT_FOUND("找不到主机"){}, REFUSED_CONNECT("拒绝连接"){}
	, CONNECT_TIME_OUT("连接超时"){}, CONNECT_OTHER_EXCEPTION("连接时发生位置错误"){};
	private String info = null;
	private InterfaceConnectResult(String info){
		this.info = info;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
}