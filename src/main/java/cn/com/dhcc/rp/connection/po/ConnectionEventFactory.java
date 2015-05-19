package cn.com.dhcc.rp.connection.po;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.dhcc.rp.pojo.ConfFormatConstants;

public class ConnectionEventFactory {
	static private final SimpleDateFormat sdf = new SimpleDateFormat(ConfFormatConstants.DATE_FORMAT);

	static public final String CODE = "CGN";
	/**
	 * 改为从界面配置， 该配置失效。
	 * @deprecated
	 */
	static public final String EVE_LEVEL = "5";    //事件级别
	static public final String STATE_ALARM = "1";   //报警
	static public final String STATE_ALARM_RECOVER = "0";    //报警恢复
	static public final String ATTR_NAME = "接口通信";    //属性名称
	
	static public ConnectionEvent getEvent(String code, String content, String state){
		ConnectionEvent connEvent = new ConnectionEvent();
		if(ConnectionEvent.LINK_CONNECTED.equals(state)){
			connEvent.setState(STATE_ALARM_RECOVER);
		}else if(ConnectionEvent.LINK_DISCONNECTED.equals(state)){
			connEvent.setState(STATE_ALARM);
		}else{
			return null;
		}
		connEvent.setId(code);
		connEvent.setCompanyCode(CODE);
		connEvent.setCollectTime(sdf.format(new Date()));
		connEvent.setAttrName(ATTR_NAME);
		connEvent.setLevel(EVE_LEVEL);
		connEvent.setContent(content);
		//connEvent.setValue("接口通信");
		return connEvent;
	}
	
}
