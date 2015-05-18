package cn.com.dhcc.rp.db;
import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import cn.com.dhcc.rp.persistence.po.RoomInterfaceConf;


public final class DBDelegate {
	static final private Logger log = Logger.getLogger(DBDelegate.class.getClass());
	static private SqlSessionFactory sqlSessionFactory;
	static private final String resource = "cn/com/dhcc/rp/db/mybatis-config.xml";
	static{
		log.info("==============数据库连接初始化==============");
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			log.fatal("采集数据库连接出错，无法采集！");
		}finally{
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Configuration conf = sqlSessionFactory.getConfiguration();
		log.info("数据库环境：" + conf.getEnvironment().getId());
		log.info("===========数据库连接初始化--完成！===========");
	}
	private DBDelegate(){}
	static public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
	public static void main(String[] args) {
		SqlSession s = DBDelegate.getSqlSessionFactory().openSession();
		RoomInterfaceConf conf = null;
		try{
			conf = s.selectOne("select_interface_info_by_company_code", "ST");
		}finally{
			s.close();
		}
		System.out.println(conf);
	}
}
