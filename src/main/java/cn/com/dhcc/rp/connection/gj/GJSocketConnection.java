package cn.com.dhcc.rp.connection.gj;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Date;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import cn.com.dhcc.rp.connection.RealTimeData;
import cn.com.dhcc.rp.connection.SocketConnection;
import cn.com.dhcc.rp.connection.gj.protocol.Package;
import cn.com.dhcc.rp.connection.gj.protocol.Body;
import cn.com.dhcc.rp.connection.gj.protocol.Head;
import cn.com.dhcc.rp.db.DBDelegate;
import cn.com.dhcc.rp.utils.ByteUtils;

/**
 * Created by CeDo on 14-4-23.
 */
public final class GJSocketConnection extends SocketConnection{
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
	 * ��ջ�����
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
		    	SqlSession sess = DBDelegate.getSqlSessionFactory().openSession(ExecutorType.BATCH);
	        	try{
		        	clearHeadBuffers();
		            long rc = socketChannel.read(headerBuffers);    //����
		            if(rc==-1){
		                break;
		            }
		            /**
		             * ��ͷ�ж�
		             */
		            ByteBuffer startBuffer = headerBuffers[0];
		            startBuffer.flip();
		            byte[] headTag = new byte[Head.HEAD_ELE_LENGTH];
		            startBuffer.get(headTag);
		            boolean  isHeadTag = Package.isHeadTag(headTag);
		            if(!isHeadTag){
		            	log.debug(Arrays.toString(headTag));
		            	log.debug("��ͷ����");
		                continue;
		            }else{
		            	//log.debug("��ͷ���: " + Arrays.toString(headTag));
		            }
		
		            /**
		             * Э��汾
		             */
		            ByteBuffer versionBuffer = headerBuffers[1];
		            byte[] versionBytes = new byte[Head.HEAD_ELE_LENGTH];
		            versionBuffer.flip();
		            versionBuffer.get(versionBytes);
		            //log.debug("�汾�ţ�" + Arrays.toString(versionBytes));
		            
		            /**
		             * ��������
		             */
		            ByteBuffer commandBuffer = headerBuffers[2];
		            byte[] commandBytes = new byte[Head.HEAD_ELE_LENGTH];
		            commandBuffer.flip();
		            commandBuffer.get(commandBytes);
		            int packageType = Package.dataType(commandBytes);
		            //log.debug(Arrays.toString(commandBytes) + ":" + (packageType==Package.TYPE_DATA?"���ݰ�":"�����ݰ�") );
		
		            /**
		             * ���峤��
		             */
		            int messageLength = 0;
		            ByteBuffer bodyLengthBuffer = headerBuffers[Head.BODY_LENGTH_POSITION-1];
		            byte[] bodyLengthBytes = new byte[Head.HEAD_ELE_LENGTH];
		            bodyLengthBuffer.flip();
		            bodyLengthBuffer.get(bodyLengthBytes);
		            messageLength = (int)ByteUtils.getLong4(bodyLengthBytes);
		            //log.debug("���峤��: " + messageLength + "(byte)");
		
		            //��ȡ����
		 
		            int dealCount = 0;
		            short groupSize = -1;    //�����ݳ���

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
	//System.out.print("�鳤��:" + groupSize);
		                
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
		                	//this.putRealDataSet(data);
		                	data.setCd(this.companyCode);
		                	data.setCollectTime(sdf.format(new Date()));
							//ʵʱ�������  
							sess.update("cn.com.dhcc.rp.realtimedata.update_insert_gj_data", data);
							sess.commit();
							/*if(((GJData)data).getKey().equals("S0-E6-A1-VA")){
								log.info(new Date() + "--" + colum + this.companyCode + ",  �����������¶�: " +((GJData)data).getValue() );
							}*/
		                }else if(packageType!=Package.TYPE_DATA && dataArray[0].endsWith(GJData.EVENT_TAG)){
		                	log.info("�¼�=>" + dataArray[0] + " : " + Arrays.toString(dataArray));
							try{
								GJEvent event = GJUtils.parseToGJEvent(this.companyCode, packageBody);
								if(event!=null){
									//�¼����  
									int colum = sess.insert("cn.com.dhcc.rp.event.insert_gj_TxEvents", event);
									sess.commit();
									log.info("�¼����" + (colum==1?"�ɹ�":"ʧ��"));
								}else {
									log.info("�¼�=>" + dataArray[0] + " : " + Arrays.toString(dataArray) +
											"===���¼���Ч===");
								}
							}catch(Exception e){
								e.printStackTrace();
								log.error("�澯����ʧ��");
							}
		                }
		                dealCount += Body.GROUP_SIZE_LENGTH + groupSize;
		            }
	        	} catch (IOException e1) {
	        		log.debug("���ýӿ�[" + this.inetSocketAddr + "]��ȡ����IO����");
				} catch(BufferUnderflowException be){
					log.warn("���ýӿ�[" + this.inetSocketAddr + "]�����쳣�Ͽ�");
					log.debug(be.toString());
				}finally{
	                if(sess!=null){
	                	sess.commit();
	                	sess.clearCache();
	                	sess.close();
	                }
	            }
	
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
	   GJSocketConnection gjConnection = new GJSocketConnection();
	   String[] ips = {"10.0.17.30", "10.28.2.15"};
	   InetSocketAddress i = new InetSocketAddress(ips[0], 7000);

	   gjConnection.init(i);
		new Thread(gjConnection).start();
		try {
			while(true){
				Thread.sleep(5000);
				int size  = gjConnection.getRealDataSetISO().size();
				System.out.println("��С:" + size);
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
   }
   
}
