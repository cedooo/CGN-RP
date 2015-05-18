package cn.com.dhcc.rp;

import org.apache.log4j.Logger;

import cn.com.dhcc.rp.col.Collector;
import cn.com.dhcc.rp.connection.SocketConnetionContainer;
import cn.com.dhcc.rp.st.EventConsumer;
/**
 * 采集main class
 * @author PCITECC02
 *
 */
public final class Main {
	private static Logger log = Logger.getLogger(Main.class.getClass());
	static public void main(String[] args) {
		initDBManager();
		buildSocketConnection();
		startCollector();
		startEventListener();
	}
	/**
	 * 启动采集
	 * 
	 */
	static private void startCollector(){
		Collector collector = new Collector();
		Thread colThread = new Thread(collector);
		colThread.start();
		log.info("启动采集成功");
	}
	/**
	 * 启动、建立socket连接
	 */
	static private void buildSocketConnection(){
		SocketConnetionContainer conain = SocketConnetionContainer.getInstance();
		if(conain.init()){
			new Thread(conain).start();
			log.info("连接初始化成功");
		}else{
			log.error("连接配置错误,初始化失败！");
		}
	}
	/**
	 * 加载数据库管理类
	 */
	static private void initDBManager(){
		try {
			Class.forName("cn.com.dhcc.rp.db.DBDelegate");
			log.info("加载 cn.com.dhcc.rp.db.DBDelegate 成功");
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
			log.error("加载 cn.com.dhcc.rp.db.DBDelegate 失败");
		}
	}
	/**
	 * 启动事件监听
	 */
	static private void startEventListener(){
		new Thread(new EventConsumer()).start();
		log.info("启动事件监听");
	}
}
