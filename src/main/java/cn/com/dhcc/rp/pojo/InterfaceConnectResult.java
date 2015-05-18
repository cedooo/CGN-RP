package cn.com.dhcc.rp.pojo;

public enum InterfaceConnectResult {
	CONNECTED("���ӳɹ�"){}, UNKNOW_HOST("δ֪����"){}, HOST_NOT_FOUND("�Ҳ�������"){}, REFUSED_CONNECT("�ܾ�����"){}
	, CONNECT_TIME_OUT("���ӳ�ʱ"){}, CONNECT_OTHER_EXCEPTION("����ʱ����λ�ô���"){};
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