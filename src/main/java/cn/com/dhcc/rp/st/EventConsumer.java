package cn.com.dhcc.rp.st;

import org.apache.log4j.Logger;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import cn.com.dhcc.rp.persistence.po.RoomInterfaceConf;
import cn.com.dhcc.rp.roomInterConf.IntfsConfLoader;
import cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisher;
import cn.com.dhcc.rp.st.RK_ICorbaRDS.DataPublisherHelper;
import cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcher;
import cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcherHelper;
import cn.com.dhcc.rp.st.RK_ICorbaRDS.EventWatcherPOA;
import cn.com.dhcc.rp.st.orb.OrbConstants;

/**
 * ��ͨ �¼� �ص� �����
 * @author PCITECC02
 *
 */
public class EventConsumer implements Runnable{
	static private final Logger log = Logger.getLogger(EventConsumer.class.getClass());
	
	static private final String CODE = "ST";
	static private RoomInterfaceConf roomInterConf = null;
	static private String[] defaultArgs = null;
	static{
		/*SqlSession ses = DBDelegate.getSqlSessionFactory().openSession();
		try{
			roomInterConf = ses.selectOne("cn.com.dhcc.rp.comm.select_interface_info_by_company_code", CODE);
		}finally{
			ses.close();
		}*/
		roomInterConf = IntfsConfLoader.getRoomInterfaceConf(CODE);
		defaultArgs = new String[]{ "-ORBInitRef", 
				"NameService=corbaloc::" + roomInterConf.getIp() + ":" + roomInterConf.getPort()};
	}
	private boolean startWatch(){
		try {
		    	
	        ORB orb = ORB.init(defaultArgs, null);
	       	log.info("��ͨ�¼�������ʼ��");
	        //Instantiate Servant and create reference
	        POA rootPOA = POAHelper.narrow(
	            orb.resolve_initial_references("RootPOA"));
	        EventWatcherPOA watcher  = new EventWatcherImpl();
	        rootPOA.activate_object(watcher);
	        EventWatcher ref = EventWatcherHelper.narrow(
	            rootPOA.servant_to_reference(watcher));
	
	        //Resolve MessageServer
	        org.omg.CORBA.Object objRef = orb
					.resolve_initial_references(OrbConstants.ORB_INIT_REF);
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			NameComponent path[] = ncRef.to_name(OrbConstants.ORB_OBJ_REF_NAME);
	
			DataPublisher dataPublisher = DataPublisherHelper.narrow(ncRef.resolve(path));
	
	        //Register listener reference (callback object) with MessageServer
	        dataPublisher.RegisterEventWatcher(ref);
	        log.debug("��ͨ��EventWatcher registered with DataPublisher");
	
	        //Activate rootpoa
	        rootPOA.the_POAManager().activate();
	
	        //Wait for messages
	        log.info("���������ɹ����ȴ������¼���");
	        
	        orb.run();
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public static void main(String[] args) {
		new Thread(new EventConsumer()).start();
	}
	@Override
	public void run() {
		startWatch();		
	}
	
}