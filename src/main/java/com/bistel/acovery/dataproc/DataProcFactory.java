package com.bistel.acovery.dataproc;

import java.lang.reflect.Constructor;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.lang3.StringUtils;

import com.bistel.acovery.asset.model.Asset;
import com.bistel.acovery.config.ConfigManager;
import com.bistel.acovery.message.ApmMessage;

public class DataProcFactory {

	public static DataProc makeDataProc(Asset asset, BlockingQueue<ApmMessage> blockingQueue) throws Exception {
		Properties interfaceConfig = ConfigManager.getInterfaceConfig();

		String dataProcClasspath = interfaceConfig.getProperty("interface.classpath");
		if (StringUtils.isBlank(dataProcClasspath))
			throw new Exception("'interface.classpath' is undefined.");

		Class<?> clazz = Class.forName(dataProcClasspath);
		Constructor<?> ctor = clazz.getConstructor(Asset.class, BlockingQueue.class);
		DataProc dataProc = (DataProc) ctor.newInstance(new Object[] { asset, blockingQueue });
		dataProc.initialize(interfaceConfig);

		return dataProc;
	}
}
