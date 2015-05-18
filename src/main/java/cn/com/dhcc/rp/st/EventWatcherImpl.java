package cn.com.dhcc.rp.st;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import cn.com.dhcc.rp.db.DBDelegate;
import cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcherPOA;


public class EventWatcherImpl extends EventWatcherPOA{
	static final Logger log = Logger.getLogger(EventWatcherImpl.class.getClass());
	
	@Override
	public void OnEvent(int lType, int lTimeStamp, int lLevel, String strID,
			String strFullName, String strDesc, String strAdvice) {
		System.err.println("告警信息：\n\t"  +  lType + ", " + lTimeStamp + ", " + lLevel
				+ ", " + strID + ", " + strFullName + ", " +  strDesc + ", " + strAdvice);
		StEvent ste = new StEvent();
		ste.setlLevel(lLevel);
		ste.setlTimeStamp(lTimeStamp);
		ste.setlType(lType);
		ste.setStrAdvice(strAdvice);
		ste.setStrDesc(strDesc);
		ste.setStrFullName(strFullName);
		ste.setStrID(strID);
		/**
		 * 告警入库
		 */
		SqlSession sess = DBDelegate.getSqlSessionFactory().openSession(true);
		//入库 
		try{
			sess.insert("cn.com.dhcc.rp.event.insert_st_TxEvents", ste);
			log.warn("四通的告警信息： " + ste);
		}finally{
			sess.close();
		}
	}


}
class StEvent{
	private int lType;
	private int lTimeStamp;
	private int lLevel;
	private String strID;
	private String strFullName;
	private String strDesc;
	private String strAdvice;
	
	private String companyCode = null;
	public int getlType() {
		return lType;
	}
	public void setlType(int lType) {
		this.lType = lType;
	}
	public int getlTimeStamp() {
		return lTimeStamp;
	}
	public void setlTimeStamp(int lTimeStamp) {
		this.lTimeStamp = lTimeStamp;
	}
	public int getlLevel() {
		return lLevel;
	}
	public void setlLevel(int lLevel) {
		this.lLevel = lLevel;
	}
	public String getStrID() {
		return strID;
	}
	public void setStrID(String strID) {
		this.strID = strID;
	}
	public String getStrFullName() {
		return strFullName;
	}
	public void setStrFullName(String strFullName) {
		this.strFullName = strFullName;
	}
	public String getStrDesc() {
		return strDesc;
	}
	public void setStrDesc(String strDesc) {
		this.strDesc = strDesc;
	}
	public String getStrAdvice() {
		return strAdvice;
	}
	public void setStrAdvice(String strAdvice) {
		this.strAdvice = strAdvice;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	@Override
	public String toString() {
		return "StEvent [lType=" + lType + ", lTimeStamp=" + lTimeStamp
				+ ", lLevel=" + lLevel + ", strID=" + strID + ", strFullName="
				+ strFullName + ", strDesc=" + strDesc + ", strAdvice="
				+ strAdvice + ", companyCode=" + companyCode + "]";
	}
	
}
