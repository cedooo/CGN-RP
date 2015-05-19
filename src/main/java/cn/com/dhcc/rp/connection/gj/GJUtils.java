package cn.com.dhcc.rp.connection.gj;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;

import cn.com.dhcc.rp.connection.po.SelectGJNodePara;
import cn.com.dhcc.rp.db.DBDelegate;

public final class GJUtils {
	static final private String REGEX_EVENT = "[^\\|]{1,}";
	static final private Pattern PATTERN_EVENT  = Pattern.compile(REGEX_EVENT);
	public static void main(String[] args) {
		String s = "S0-E161-D5-EV||科技大厦23楼数据中心机房|通讯间精密空调|1#压缩机1状态运行|1|3|2014-04-21 15:13:33|3ED642FB-4632-422F-A0B5-037A8CB1261A|";
		GJEvent event = GJUtils.parseToGJEvent("GJ_1",s);
		System.out.println(event.getId() + "  " + event.getAttrName());
		SqlSession sess = DBDelegate.getSqlSessionFactory().openSession();
		//事件入库  
		//sess.insert("cn.com.dhcc.rp.event.insert_gj_TxEvents", event);
		sess.commit();
		sess.close();
	}
	/**
	 * 将字符串转换为事件对象
	 * @param packageBody 数据字符串
	 * @return 事件对象
	 */
	public static GJEvent parseToGJEvent(String code, String packageBody) {
		GJEvent event = new GJEvent();
		Matcher mr = PATTERN_EVENT.matcher(packageBody);
		int counter = 0;
		while(mr.find()){
			counter++;
			if(1==counter){
				event.setKey(mr.group());
			}else if(2==counter){
				event.setStateName(mr.group());
			}else if(3==counter){
				event.setEventSource(mr.group());
			}else if(4==counter){
				event.setEventContent(mr.group());
			}else if(5==counter){
				event.setLevel(mr.group());
			}else if(6==counter){
				event.setType(mr.group());
			}else if(7==counter){
				event.setDate(mr.group());
			}else if(8==counter){
				event.setEventId(mr.group());
			}else{
				break;
			}
		}

		event.setCompanyCode(code);
		boolean parseSuccess = parseKey(event);
		if(parseSuccess){
			return event;
		}else{
			return null;
		}
	}
	/**
	 * 将事件key转换成设备的ID 和 指标名称
	 * @param event
	 */
	private static boolean parseKey(GJEvent event) {
		String[] keyVal = event.getKey().split("-");
		String fid = "";
		if(keyVal.length == 3){
			fid = keyVal[1];
			SelectGJNodePara selectPara = new SelectGJNodePara();
			selectPara.setCompanyCode(event.getCompanyCode());
			selectPara.setEventKey(fid);
			SqlSession sess = DBDelegate.getSqlSessionFactory().openSession(true);
			try{
				String type = sess.selectOne("cn.com.dhcc.rp.event.select_gj_euipType", selectPara);
				if(type==null){
					return false;
				}else{
					event.setAttrName(event.getEventSource());
					event.setId(parseEquipId(event.getCompanyCode(), fid));
					return true;
				}
			}finally{
				sess.close();
			}
		}else if(keyVal.length ==4){
			fid = keyVal[1] + "-" + keyVal[2];
			SqlSession sess = DBDelegate.getSqlSessionFactory().openSession();
			SelectGJNodePara selectPara = new SelectGJNodePara();
			selectPara.setCompanyCode(event.getCompanyCode());
			selectPara.setEventKey(fid);
			String attrName = null;
			try{
				String type = sess.selectOne("cn.com.dhcc.rp.event.select_gj_euipType", selectPara);
				if(type==null){
					fid =  keyVal[1];
					selectPara.setEventKey(fid);
					selectPara.setNodeID(keyVal[2]);
					type = sess.selectOne("cn.com.dhcc.rp.event.select_gj_euipType", selectPara);
				}
				if(type==null){
					return false;
				}
	//System.out.println(type);
				selectPara.setEquipType(type);
				attrName = sess.selectOne("cn.com.dhcc.rp.event.select_gj_nodeName", selectPara);
			}finally{
				sess.close();
			}
			event.setAttrName(attrName);
			event.setId(parseEquipId(event.getCompanyCode(), fid));
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 将fid转换为设备ID
	 * @param CompanyCode
	 * @param fid
	 * @return
	 */
	static public String parseEquipId(String companyCode, String fid){
		return companyCode + "_" + fid;
	}
	
}
