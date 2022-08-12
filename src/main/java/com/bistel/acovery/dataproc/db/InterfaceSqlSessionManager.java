package com.bistel.acovery.dataproc.db;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.log4j.Logger;

import com.bistel.acovery.config.ConfigManager;

public class InterfaceSqlSessionManager {
	private static Logger logger = Logger.getLogger(InterfaceSqlSessionManager.class);

	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			Properties interfaceConfig = ConfigManager.getInterfaceConfig();
			String driverClassName = interfaceConfig.getProperty("interface.datasource.driverClassName");
			String jdbcurl = interfaceConfig.getProperty("interface.datasource.jdbc-url");
			String username = interfaceConfig.getProperty("interface.datasource.username");
			String password = interfaceConfig.getProperty("interface.datasource.password");
			String mapperClassPath = interfaceConfig.getProperty("interface.datasource.mapper.classpath");
			if (StringUtils.isAnyBlank(driverClassName, jdbcurl, username, password, mapperClassPath)) {
				throw new Exception("'interface.datasource' properties is unvalid.");
			}
			DataSource dataSource = new PooledDataSource(driverClassName, jdbcurl, username, password);
			TransactionFactory transactionFactory = new JdbcTransactionFactory();
			Environment environment = new Environment("development", transactionFactory, dataSource);
			Class<?> mapperClass = Class.forName(mapperClassPath);
			Configuration configuration = new Configuration(environment);
			configuration.addMapper(mapperClass);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}
}
