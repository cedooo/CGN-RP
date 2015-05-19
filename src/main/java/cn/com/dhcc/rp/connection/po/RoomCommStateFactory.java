package cn.com.dhcc.rp.connection.po;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.dhcc.rp.persistence.po.RoomCommState;
import cn.com.dhcc.rp.pojo.ConfFormatConstants;

/**
 * 得到接口正常PO
 * @author PCITECC02
 *
 */
public class RoomCommStateFactory {
	static private final SimpleDateFormat sdf = new SimpleDateFormat(ConfFormatConstants.DATE_FORMAT);
	static public final String ATTR_NAME = "通信接口状态";
	static public final String STATE_NORMAL = "正常";
	static public final String STATE_EXCEPTION = "异常";
	static public final String PRE_ID = "tran_";
	
	static public RoomCommState getNormalRoomCommState(String code){
		RoomCommState state = new RoomCommState();
		state.setCollectTime(sdf.format(new Date()));
		state.setCompanyCode(ConnectionEventFactory.CODE);
		state.setId(PRE_ID + code);
		state.setPartID(ATTR_NAME);
		state.setStateValue(STATE_NORMAL);
		return state;
	}
	static public RoomCommState getExceptionRoomCommState(String code){
		RoomCommState state = new RoomCommState();
		state.setCollectTime(sdf.format(new Date()));
		state.setCompanyCode(ConnectionEventFactory.CODE);
		state.setId(PRE_ID + code);
		state.setPartID(ATTR_NAME);
		state.setStateValue(STATE_EXCEPTION);
		return state;
	}
}
