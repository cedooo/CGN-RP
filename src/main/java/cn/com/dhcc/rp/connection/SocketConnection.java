package cn.com.dhcc.rp.connection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public abstract class SocketConnection implements Runnable{
    protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static protected final Logger log = Logger.getLogger(SocketConnection.class.getClass());
	protected InetSocketAddress inetSocketAddr = null;    //地址
	private  Set<RealTimeData> realDataSet = null;    //实时数据集合
	protected String companyCode = null;

	protected boolean keepRun = true;
	public SocketConnection(){
		realDataSet = Collections.synchronizedSet(new HashSet<RealTimeData>());
	}
	/**
	 * 初始化连接
	 * @param inetSocketAddr
	 * @return 初始化成功返回true，否则返回false
	 */
	abstract public boolean init(InetSocketAddress inetSocketAddr);
	/**
	 * 初始化连接
	 * @param companyCode 编码
	 * @param inetSocketAddr
	 * @return 初始化成功返回true，否则返回false
	 */
	public boolean init(String companyCode, InetSocketAddress inetSocketAddr){
		this.companyCode = companyCode;
		return this.init(inetSocketAddr);
	}
	/**
	 * 连接有效性验证
	 * @return 有效返回true，否则返回false
	 */
	abstract public boolean valid();
	/**
	 * 停止当前线程
	 * @return 停止成功返回true 否则返回false
	 */
	public boolean stopThread(){
		this.keepRun = false;
		return true;		
	};
	/**
	 * 将实时数据加入集合中
	 * @param real 实时数据
	 */
	public void putRealDataSet(RealTimeData real){
		this.realDataSet.add(real);
	}
	/**
	 * 得到实时数据‘镜像’
	 * @return 返回实时数据Set集合
	 */
	public Set<RealTimeData> getRealDataSetISO(){
		return deepCloneSet();
	}
	public int getSetSize(){
		return this.realDataSet.size();
	}
	/**
	 * 清空先有数据，重新建立连接
	 * @return
	 */
	public boolean reConnect(){
		this.realDataSet.clear();
		if(init(inetSocketAddr)){
			this.keepRun = true;
			return true;
		}else{
			return false;
		}
	}
	@Override
	public String toString() {
		return  "code = " + this.companyCode + ", inetSocketAddr = " + inetSocketAddr.toString()  ;
	}
	/**
	 * <li><b>线程安全</b></li>
	 * <li>返回一个当前实时数据集合的'镜像'拷贝</li>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private synchronized Set<RealTimeData> deepCloneSet(){
		Set<RealTimeData> clonedSet = null;
		synchronized(realDataSet){
	        try {
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            ObjectOutputStream oos = new ObjectOutputStream(baos);
	            oos.writeObject(this.realDataSet);
	            oos.close();
	            
	            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
	            ObjectInputStream bis = new ObjectInputStream(bais);
				clonedSet = (Set<RealTimeData>)bis.readObject();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
        return clonedSet;
	}
	
	public InetSocketAddress getInetSocketAddr() {
		return inetSocketAddr;
	}
	/**
	 * 清空过期的数据,
	 * <b>过期，只返回-1；</b>
	 * @return 清空成功成功 返回 情况个数, 失败返回-1
	 * @deprecated
	 */
	public int clearDeprecatedData(long deprecatedTime) {
		/*
		int count = -1;
		//Set<RealTimeData> deprecatedSet = new HashSet<RealTimeData>();
		synchronized(realDataSet){
			for (RealTimeData realTimeData : this.realDataSet) {
				String colTime = realTimeData.getCollectTime();
				boolean isDeprecated = false;
				if(isDeprecated){
					count++;
				}else{
					continue;
				}
			}
		}
		return count!=-1?count:-1;
		*/
		return -1;
	}
}
