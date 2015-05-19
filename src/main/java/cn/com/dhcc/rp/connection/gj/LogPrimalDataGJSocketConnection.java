package cn.com.dhcc.rp.connection.gj;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by CeDo on 14-4-23.
 */
public final class LogPrimalDataGJSocketConnection implements Runnable {

	protected InetSocketAddress inetSocketAddr = null; // 地址
	private SocketChannel socketChannel = null;
	private ByteBuffer buffer = ByteBuffer
			.allocate(1024);

	public boolean init(InetSocketAddress inetSocketAddr) {
		this.inetSocketAddr = inetSocketAddr;
		try {
			socketChannel = SocketChannel.open();
			socketChannel.connect(inetSocketAddr);
			if (socketChannel.isConnected()) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		LogPrimalDataGJSocketConnection gjConnection = new LogPrimalDataGJSocketConnection();
		new Thread(gjConnection).start();
	}

	@Override
	public void run() {
		String[] ips = { "10.0.17.30", "10.28.2.15" };
		InetSocketAddress isAddr = new InetSocketAddress(ips[0], 7000);
		this.init(isAddr);
		FileChannel fileChannel = null;
		try {
			fileChannel = new FileOutputStream("d:/primal.data").getChannel();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		boolean keepRun = true;
		try {
			Thread.sleep(30000);
	        while(keepRun){
	        	long bc = -1l;
				try {
					buffer.clear();
					bc = socketChannel.read(buffer);
					if(bc!=-1){
						buffer.flip();
						fileChannel.write(buffer);
					}else{
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}    //读入
	        }  
        } catch (InterruptedException e1) {
			e1.printStackTrace();
		} finally {
            try {
                if(socketChannel!=null){
                	socketChannel.close();
                }
                if(fileChannel!=null){
                	fileChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

}
