package cn.com.dhcc.rp.connection.maintenance;

import org.apache.log4j.Logger;

/**
 * ����'����'����ά������
 * @author PCITECC02
 *
 */
public abstract class ContainerMaintenanceJob {
	static protected final Logger log = Logger.getLogger(ContainerMaintenanceJob.class.getClass());
	/**
	 * ִ������ά������
	 */
	abstract public void doJob() throws Exception;
}
