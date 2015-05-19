package cn.com.dhcc.rp.connection.lk;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import cn.com.dhcc.rp.db.DBDelegate;

public class LKUtils {
	static private final Logger log = Logger.getLogger(LKUtils.class.getClass());
	static private final String REGEX_A = "A##(.)*[\\^]{1,2}";
	static private final String REGEX_B = "B##(.)*[\\^]{1,2}";
	static private final String REGEX_KEY_VALUE = "#(Agent|D|ID|C|V|T|Z|U|M|EV|Class|Alarm)=[^#]{1,}#";
	static private final Pattern PATTERN_KEY_VAL = Pattern.compile(REGEX_KEY_VALUE);
	
	static private final String REGEX_POWERBOX = "[1-4]号配电柜PART[1-5]$";
	static private final Pattern PATTERN_POWERBOX = Pattern.compile(REGEX_POWERBOX);

	static private final Pattern PATTERN_INFO = Pattern.compile(REGEX_A);
	static private final Pattern PATTERN_EVENT = Pattern.compile(REGEX_B);
	
	public static Pattern getPatternInfo() {
		return PATTERN_INFO;
	}

	public static Pattern getPatternEvent() {
		return PATTERN_EVENT;
	}
	public static void main(String[] args) {
		/*String data = "A##Agent=10##D=主机房漏水监测##ID=1801027##V=0##T=1##C=漏水定位7##E=##AddrId=##AddrName=##NumCV=##^^";
		LKData lkdata = parseToLKData(data);
		System.out.println(lkdata);
		
		String event = "B##Agent=10##D=门禁系统2##ID=1301000##C=通讯##V=91300000##EV=1##T=1##Z=1##U=##M=门禁主机通讯中断##Class=##Alarm=1##AddrId=##AddrName=##MgrObjTypeId=11111111-1111-1111-1111-111111111111##MgrObjTypeName=##";
		LKData ddata = parseToLKDataEvent("LK", event);
		System.out.println(data);*/
		
		String s = "1号配电柜PART3";
		Matcher matcher = PATTERN_POWERBOX.matcher(s);
		System.out.println(matcher.matches());
		System.out.println(s.substring(0, 5));

	}
	/**
	 * 将字符串转换为数据对象
	 * @param data 原始字符串
	 * @return ‘龙控’数据对象
	 */
	static public LKData parseToLKData(String data){
		LKData lkData = new LKData();
		boolean isInfoData = PATTERN_INFO.matcher(data).matches();
		boolean isEventData = PATTERN_EVENT.matcher(data).matches();
		if(isInfoData){
			lkData.setType(LKData.TYPE_DATA);
		}else if(isEventData){
			lkData.setType(LKData.TYPE_EVENT);
		}
		Matcher matcher = PATTERN_KEY_VAL.matcher(data);
		try {
			while(matcher.find()){
				String ms = matcher.group();
				String keyVal = ms.substring(1, ms.length()-1);
				String[] keyvals = keyVal.split("=");
				String key = keyvals[0];
				String value = keyvals[1];
				if("Class".equals(key)){
					lkData.setClas(value);
				}else {
					key = key.toLowerCase();
					key = key.substring(0,1).toUpperCase() + key.substring(1);
					lkData.getClass().getDeclaredMethod("set" + key, String.class).invoke(lkData, value);
				}
			}
			return lkData;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} 
		return null;
	}
	/**
	 * 将数据转换为事件对象
	 * @param code 编码
	 * @param data 数据字符串
	 * @return 返回事件对象，如果不是事件 返回null
	 */
	static public LKData parseToLKDataEvent(String code, String data){
		LKData eventData = LKUtils.parseToLKData(data);
		if(eventData.getId() == null){
			return null;
		}
		log.info(code + ",事件===>" + eventData);	
		boolean eventAlarm = eventData!=null     //事件有效 条件
				//&& eventData.getClas()!=null
				&& eventData.getId() != null
				&& eventData.getCollectTime() != null
				&& eventData.getEv() != null;
		if(!eventAlarm){
			return null;
		}
		if(eventData.getClas()==null){
			SqlSession sess = DBDelegate.getSqlSessionFactory().openSession(true);
			try{
				String level = sess.selectOne("cn.com.dhcc.rp.event.select_lk_event_level", eventData.getV());
				log.info("事件级别：" + level);
				if(level!=null){
					eventData.setClas(level);
				}else{
					return null;
				}
			}finally{
				sess.close();
			}
		}
		if(eventData.getC()==null){
			return null;
		}
		String eventD = eventData!=null?eventData.getD():null;
		if(eventD!=null && "温湿度监测".equals(eventD)){
			eventData.setD(code + "_" + eventD);
		}else if(eventD!=null && PATTERN_POWERBOX.matcher(eventD).matches()){
			String fid = eventD.substring(0, 5);
			eventData.setD(fid);
		}
		eventData.setCompanyCode(code);
		return eventData;
	}
}