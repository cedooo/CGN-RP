package cn.com.dhcc.rp.connection.lk;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import cn.com.dhcc.rp.connection.SocketConnection;
import cn.com.dhcc.rp.db.DBDelegate;
import cn.com.dhcc.rp.pojo.ConfFormatConstants;


/**
 * 龙控Socket连接
 * @author PCITECC02
 *
 */
public class LKSocketConnection extends SocketConnection{
    
	static private final int BYTE_BUFFER_SIZE = 1024;    //缓冲大小
	static protected SimpleDateFormat dateFormat = new SimpleDateFormat(ConfFormatConstants.DATE_FORMAT);
	
	private SocketChannel socketChannel = null;
	private final ByteBuffer buffer = ByteBuffer.allocate(BYTE_BUFFER_SIZE); 
	
	@Override
	public boolean init(InetSocketAddress inetSocketAddr){
		this.inetSocketAddr = inetSocketAddr;
        try {
			socketChannel = SocketChannel.open();
	        socketChannel.connect(inetSocketAddr);
			if(socketChannel.isConnected()){
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void run() {
		this.keepRun = true;
		try {
	        while(this.keepRun){
	        	buffer.clear();
	        	long rc = socketChannel.read(buffer);    //读入
	            if(rc==-1){
	                break;
	            }
	            buffer.flip();
	            int size = buffer.limit();
	            byte[] data = new byte[size];
	            buffer.get(data);
	            String dataStr = new String(data);

				SqlSession sess = DBDelegate.getSqlSessionFactory().openSession(ExecutorType.BATCH);
	    		Matcher matcherInfo = LKUtils.getPatternInfo().matcher(dataStr);
	    		Matcher matcherEvent = LKUtils.getPatternEvent().matcher(dataStr);
	    		try{
		    		while(matcherInfo.find()){
		    			String infoA = matcherInfo.group();
		    			LKData lkData = LKUtils.parseToLKData(infoA);
		    			if(lkData!=null){
		    				lkData.setCd(this.companyCode);
		    				lkData.setCollectTime(sdf.format(new java.util.Date()));
		    				if(lkData.getD()!=null && lkData.getId() !=null){
		    					sess.update("cn.com.dhcc.rp.realtimedata.update_insert_lk_data", lkData);
		    					//log.info(lkData);
		    				}else{
			    				log.info("设备为NULL：" + lkData);
		    				}
		    			}
		    		}
		    		while(matcherEvent.find()){
		    			String infoB = matcherEvent.group();
		    			LKData eventData = LKUtils.parseToLKDataEvent(this.companyCode, infoB);
						// 事件入库
						if(eventData != null){
							sess.insert("cn.com.dhcc.rp.event.insert_lk_TxEvents", eventData);
							log.info("事件入库成功");
						}
		    		}
					sess.commit();
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					sess.close();
				}
	        }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(socketChannel!=null){
                	socketChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	@Override
	public boolean valid() {
		if(this.socketChannel.isConnected()){
			return true;
		}else{
			return false;
		}
	}

	public static void main(String[] args) {
		InetSocketAddress i = new InetSocketAddress("10.10.6.21", 1034);
		LKSocketConnection lkc = new LKSocketConnection();
		lkc.init(i);
		new Thread(lkc).start();
		try {
			while(true){
				Thread.sleep(5000);
				int size  = lkc.getRealDataSetISO().size();
				System.out.println("大小" + size);
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean stopThread() {
		this.keepRun = false;
		return true;
	}
}
