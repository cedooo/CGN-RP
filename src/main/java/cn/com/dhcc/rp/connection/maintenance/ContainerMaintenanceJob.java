package cn.com.dhcc.rp.connection.maintenance;

import org.apache.log4j.Logger;

/**
 * 单例'连接'容器维护任务
 * @author PCITECC02
 *
 */
public abstract class ContainerMaintenanceJob {
	static protected final Logger log = Logger.getLogger(ContainerMaintenanceJob.class.getClass());
	/**
	 * 执行容器维护任务
	 */
	abstract public void doJob() throws Exception;
}
