package cn.com.dhcc.rp.connection.gj;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

import org.apache.ibatis.session.SqlSession;

import cn.com.dhcc.rp.connection.RealTimeData;
import cn.com.dhcc.rp.connection.SocketConnection;
import cn.com.dhcc.rp.connection.gj.protocol.Package;
import cn.com.dhcc.rp.connection.gj.protocol.Body;
import cn.com.dhcc.rp.connection.gj.protocol.Head;
import cn.com.dhcc.rp.db.DBDelegate;
import cn.com.dhcc.rp.utils.ByteUtils;

/**
 * @deprecated
 * Created by CeDo on 14-4-23.
 */
public final class GJSocketConnection2 extends SocketConnection{
    
	private SocketChannel socketChannel = null;
	private ByteBuffer[] headerBuffers = null; 
	private ByteBuffer groupSizeBuffer = ByteBuffer.allocate(Body.GROUP_SIZE_LENGTH);
	@Override
	public boolean init(InetSocketAddress inetSocketAddr){
		this.inetSocketAddr = inetSocketAddr;
		headerBuffers = new ByteBuffer[Head.HEAD_ELE_COUNT];
        for(int i=0; i< Head.HEAD_ELE_COUNT; i++){
            headerBuffers[i] = ByteBuffer.allocate(Head.HEAD_ELE_LENGTH);
        }
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
	/**
	 * 清空缓冲区
	 * @return
	 */
	private boolean clearHeadBuffers(){
        for(int i=0; i< Head.HEAD_ELE_COUNT; i++){
            headerBuffers[i].clear();
        }
        return true;		
	}
	@Override
	public void run() {
		this.keepRun = true;
		try {
			Thread.sleep(30000);
	        while(this.keepRun){
	        	try{
		        	clearHeadBuffers();
		            long rc = socketChannel.read(headerBuffers);    //读入
		            if(rc==-1){
		                break;
		            }
		            /**
		             * 包头判断
		             */
		            ByteBuffer startBuffer = headerBuffers[0];
		            startBuffer.flip();
		            byte[] headTag = new byte[Head.HEAD_ELE_LENGTH];
		            startBuffer.get(headTag);
		            boolean  isHeadTag = Package.isHeadTag(headTag);
		            if(!isHeadTag){
		            	log.debug(Arrays.toString(headTag));
		            	log.debug("包头错了");
		                continue;
		            }else{
		            	//log.debug("包头标记: " + Arrays.toString(headTag));
		            }
		
		            /**
		             * 协议版本
		             */
		            ByteBuffer versionBuffer = headerBuffers[1];
		            byte[] versionBytes = new byte[Head.HEAD_ELE_LENGTH];
		            versionBuffer.flip();
		            versionBuffer.get(versionBytes);
		            //log.debug("版本号：" + Arrays.toString(versionBytes));
		            
		            /**
		             * 命令类型
		             */
		            ByteBuffer commandBuffer = headerBuffers[2];
		            byte[] commandBytes = new byte[Head.HEAD_ELE_LENGTH];
		            commandBuffer.flip();
		            commandBuffer.get(commandBytes);
		            int packageType = Package.dataType(commandBytes);
		            //log.debug(Arrays.toString(commandBytes) + ":" + (packageType==Package.TYPE_DATA?"数据包":"非数据包") );
		
		            /**
		             * 包体长度
		             */
		            int messageLength = 0;
		            ByteBuffer bodyLengthBuffer = headerBuffers[Head.BODY_LENGTH_POSITION-1];
		            byte[] bodyLengthBytes = new byte[Head.HEAD_ELE_LENGTH];
		            bodyLengthBuffer.flip();
		            bodyLengthBuffer.get(bodyLengthBytes);
		            messageLength = (int)ByteUtils.getLong4(bodyLengthBytes);
		            //log.debug("包体长度: " + messageLength + "(byte)");
		
		            //读取包体
		 
		            int dealCount = 0;
		            short groupSize = -1;    //组数据长度
		            
		            while(dealCount<messageLength){
		                groupSizeBuffer.clear();
		                int r = socketChannel.read(groupSizeBuffer);
		                if(r==-1){
		                    break;
		                }
		                groupSizeBuffer.flip();
		                byte[] groupSizeBytes = new byte[Body.GROUP_SIZE_LENGTH];
		                groupSizeBuffer.get(groupSizeBytes);
		                groupSize = (short)ByteUtils.getShort(groupSizeBytes);
	//System.out.print("组长度:" + groupSize);
		                
		                ByteBuffer groupBodyBuffer = ByteBuffer.allocate(groupSize);
		                socketChannel.read(groupBodyBuffer);
		                byte[] groupBodyBytes = new byte[groupSize];
		                groupBodyBuffer.flip();
		                groupBodyBuffer.get(groupBodyBytes);
		                
		                String packageBody = new String(groupBodyBytes, Package.ENCODE);
		                String[] dataArray = packageBody.split("\\|");
		                if(dataArray.length==2 
		                		&& dataArray[0].endsWith(GJData.ATTR_VALUE_TAG)){
		                	RealTimeData data = new GJData(dataArray[0], dataArray[1]);
		                	this.putRealDataSet(data);
		                }else if(packageType!=Package.TYPE_DATA && dataArray[0].endsWith(GJData.EVENT_TAG)){
		                	log.info("事件=>" + dataArray[0] + " : " + Arrays.toString(dataArray));
							try{
								GJEvent event = GJUtils.parseToGJEvent(this.companyCode, packageBody);
								if(event!=null){
									SqlSession sess = DBDelegate.getSqlSessionFactory().openSession();
									//事件入库  
									try{
										int colum = sess.insert("cn.com.dhcc.rp.event.insert_gj_TxEvents", event);
										sess.commit();
										log.info("入库" + (colum==1?"成功":"失败"));
									}finally{
										sess.close();
									}
								}else {
									log.info("事件=>" + dataArray[0] + " : " + Arrays.toString(dataArray) +
											"===该事件无效===");
								}
							}catch(Exception e){
								e.printStackTrace();
								log.error("告警处理失败");
							}
		                }
		                dealCount += Body.GROUP_SIZE_LENGTH + groupSize;
		            }
		            
	        	} catch (IOException e1) {
	        		log.debug("共济接口[" + this.inetSocketAddr + "]读取过程IO错误");
				} catch(BufferUnderflowException be){
					log.warn("共济接口[" + this.inetSocketAddr + "]出现异常断开");
					log.debug(be.toString());
				}finally{}
	
	        }  
        } catch (InterruptedException e1) {
			e1.printStackTrace();
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
		}
		return false;
	}
   public static void main(String[] args) {
	   GJSocketConnection2 gjConnection = new GJSocketConnection2();
	   String[] ips = {"10.0.17.30", "10.28.2.15"};
	   InetSocketAddress i = new InetSocketAddress(ips[0], 7000);

	   gjConnection.init(i);
		new Thread(gjConnection).start();
		try {
			while(true){
				Thread.sleep(5000);
				int size  = gjConnection.getRealDataSetISO().size();
				System.out.println("大小:" + size);
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
   }
   
}
