package com.bistel.acovery.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

	private static final String APP_CONFIG_PREFIX = "acovery.";
	private static final String KAFKA_CONFIG_PREFIX = "kafka.";
	private static final String ASSET_CONFIG_PREFIX = "asset.";
	private static final String INTERFACE_CONFIG_PREFIX = "interface.";
	private static final String SENDER_CONFIG_PREFIX = "sender.";

	private static Properties configProperties;

	private static Properties getConfigProperties() throws FileNotFoundException, IOException {
		if (configProperties == null) {
			synchronized (ConfigManager.class) {
				if (configProperties == null) {
					try (FileInputStream fis = new FileInputStream("./config/config.properties")) {
						configProperties = new Properties();
						configProperties.load(fis);
					} catch (FileNotFoundException e) {
						throw e;
					} catch (IOException e) {
						throw e;
					}
				}
			}
		}
		return configProperties;
	}

	public static Properties getKafkaInitConfig() throws FileNotFoundException, IOException {
		return classifyPropertiesWithDeletePrefix(getConfigProperties(), KAFKA_CONFIG_PREFIX);
	}

	public static Properties getAssetConfig() throws FileNotFoundException, IOException {
		return classifyProperties(getConfigProperties(), ASSET_CONFIG_PREFIX);
	}

	public static Properties getInterfaceConfig() throws FileNotFoundException, IOException {
		return classifyProperties(getConfigProperties(), INTERFACE_CONFIG_PREFIX);
	}

	public static Properties getSenderConfig() throws FileNotFoundException, IOException {
		return classifyProperties(getConfigProperties(), SENDER_CONFIG_PREFIX);
	}

	public static Properties getAppConfig() throws FileNotFoundException, IOException {
		return classifyProperties(getConfigProperties(), APP_CONFIG_PREFIX);
	}

	private static Properties classifyProperties(Properties origin, String prefix) {
		Properties copy = new Properties();
		for (String key : origin.stringPropertyNames()) {
			if (key.startsWith(prefix)) {
				copy.put(key, origin.getProperty(key));
			}
		}
		return copy;
	}
	
	private static Properties classifyPropertiesWithDeletePrefix(Properties origin, String prefix) {
		Properties copy = new Properties();
	    for (String key : origin.stringPropertyNames()) {
	      if (key.startsWith(prefix)) {
	        String value = origin.getProperty(key);
	        key = key.substring(prefix.length());
	        copy.put(key, value);
	      } 
	    } 
	    return copy;
	}
}
