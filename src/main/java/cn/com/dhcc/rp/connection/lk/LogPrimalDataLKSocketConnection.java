package cn.com.dhcc.rp.connection.lk;

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
public final class LogPrimalDataLKSocketConnection implements Runnable {

	protected InetSocketAddress inetSocketAddr = null; // µÿ÷∑
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
		LogPrimalDataLKSocketConnection gjConnection = new LogPrimalDataLKSocketConnection();
		new Thread(gjConnection).start();
	}

	@Override
	public void run() {
		String[] ips = { "10.10.6.21", "10.10.6.22" };
		InetSocketAddress isAddr = new InetSocketAddress(ips[0], 1034);
		this.init(isAddr);
		FileChannel fileChannel = null;
		try {
			fileChannel = new FileOutputStream("d:/primal-lk.data").getChannel();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		boolean keepRun = true;
		try {
			Thread.sleep(3000);
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
				}    //∂¡»Î
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
