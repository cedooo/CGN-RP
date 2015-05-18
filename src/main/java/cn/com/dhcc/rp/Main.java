package cn.com.dhcc.rp;

import org.apache.log4j.Logger;

import cn.com.dhcc.rp.col.Collector;
import cn.com.dhcc.rp.connection.SocketConnetionContainer;
import cn.com.dhcc.rp.st.EventConsumer;
/**
 * �ɼ�main class
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
	 * �����ɼ�
	 * 
	 */
	static private void startCollector(){
		Collector collector = new Collector();
		Thread colThread = new Thread(collector);
		colThread.start();
		log.info("�����ɼ��ɹ�");
	}
	/**
	 * ����������socket����
	 */
	static private void buildSocketConnection(){
		SocketConnetionContainer conain = SocketConnetionContainer.getInstance();
		if(conain.init()){
			new Thread(conain).start();
			log.info("���ӳ�ʼ���ɹ�");
		}else{
			log.error("�������ô���,��ʼ��ʧ�ܣ�");
		}
	}
	/**
	 * �������ݿ������
	 */
	static private void initDBManager(){
		try {
			Class.forName("cn.com.dhcc.rp.db.DBDelegate");
			log.info("���� cn.com.dhcc.rp.db.DBDelegate �ɹ�");
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
			log.error("���� cn.com.dhcc.rp.db.DBDelegate ʧ��");
		}
	}
	/**
	 * �����¼�����
	 */
	static private void startEventListener(){
		new Thread(new EventConsumer()).start();
		log.info("�����¼�����");
	}
}
