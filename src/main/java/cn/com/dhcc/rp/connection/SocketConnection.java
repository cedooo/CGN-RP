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
	protected InetSocketAddress inetSocketAddr = null;    //��ַ
	private  Set<RealTimeData> realDataSet = null;    //ʵʱ���ݼ���
	protected String companyCode = null;

	protected boolean keepRun = true;
	public SocketConnection(){
		realDataSet = Collections.synchronizedSet(new HashSet<RealTimeData>());
	}
	/**
	 * ��ʼ������
	 * @param inetSocketAddr
	 * @return ��ʼ���ɹ�����true�����򷵻�false
	 */
	abstract public boolean init(InetSocketAddress inetSocketAddr);
	/**
	 * ��ʼ������
	 * @param companyCode ����
	 * @param inetSocketAddr
	 * @return ��ʼ���ɹ�����true�����򷵻�false
	 */
	public boolean init(String companyCode, InetSocketAddress inetSocketAddr){
		this.companyCode = companyCode;
		return this.init(inetSocketAddr);
	}
	/**
	 * ������Ч����֤
	 * @return ��Ч����true�����򷵻�false
	 */
	abstract public boolean valid();
	/**
	 * ֹͣ��ǰ�߳�
	 * @return ֹͣ�ɹ�����true ���򷵻�false
	 */
	public boolean stopThread(){
		this.keepRun = false;
		return true;		
	};
	/**
	 * ��ʵʱ���ݼ��뼯����
	 * @param real ʵʱ����
	 */
	public void putRealDataSet(RealTimeData real){
		this.realDataSet.add(real);
	}
	/**
	 * �õ�ʵʱ���ݡ�����
	 * @return ����ʵʱ����Set����
	 */
	public Set<RealTimeData> getRealDataSetISO(){
		return deepCloneSet();
	}
	public int getSetSize(){
		return this.realDataSet.size();
	}
	/**
	 * ����������ݣ����½�������
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
	 * <li><b>�̰߳�ȫ</b></li>
	 * <li>����һ����ǰʵʱ���ݼ��ϵ�'����'����</li>
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
	 * ��չ��ڵ�����,
	 * <b>���ڣ�ֻ����-1��</b>
	 * @return ��ճɹ��ɹ� ���� �������, ʧ�ܷ���-1
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
