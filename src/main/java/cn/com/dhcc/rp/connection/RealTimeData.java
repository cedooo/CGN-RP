package cn.com.dhcc.rp.connection;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.dhcc.rp.pojo.ConfFormatConstants;
/**
 * 实时数据
 * @author PCITECC02
 *
 */
public abstract class RealTimeData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6627303613940644009L;
	static public final SimpleDateFormat DATEFORMATE = new SimpleDateFormat(ConfFormatConstants.DATE_FORMAT);
	public String collectTime = null;    //采集时间
	public String cd = null;    //接口编码
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public RealTimeData(){
		this.collectTime = DATEFORMATE.format(new Date());
	}
	public String getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}
	
}
