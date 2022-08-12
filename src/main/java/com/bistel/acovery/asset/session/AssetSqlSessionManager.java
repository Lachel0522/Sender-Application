package com.bistel.acovery.asset.session;

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

import com.bistel.acovery.asset.mapper.AssetMapper;
import com.bistel.acovery.config.ConfigManager;

public class AssetSqlSessionManager {
	private static Logger logger = Logger.getLogger(AssetSqlSessionManager.class);
	private static final String ASSET_DATASOURCE_CONFIG_PREFIX = "asset.datasource.";
	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			Properties assetConfig = ConfigManager.getAssetConfig();
			String driverClassName = assetConfig.getProperty(ASSET_DATASOURCE_CONFIG_PREFIX + "driverClassName");
			String jdbcurl = assetConfig.getProperty(ASSET_DATASOURCE_CONFIG_PREFIX + "jdbc-url");
			String username = assetConfig.getProperty(ASSET_DATASOURCE_CONFIG_PREFIX + "username");
			String password = assetConfig.getProperty(ASSET_DATASOURCE_CONFIG_PREFIX + "password");
			if (StringUtils.isAnyBlank(driverClassName, jdbcurl, username, password)) {
				throw new Exception("'" + ASSET_DATASOURCE_CONFIG_PREFIX + "' properties is unvalid.");
			}
			DataSource dataSource = new PooledDataSource(driverClassName, jdbcurl, username, password);
			TransactionFactory transactionFactory = new JdbcTransactionFactory();
			Environment environment = new Environment("development", transactionFactory, dataSource);
			Configuration configuration = new Configuration(environment);
			configuration.addMapper(AssetMapper.class);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}

}
