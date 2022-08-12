package com.bistel.acovery;

import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.bistel.acovery.config.ConfigManager;

public class MainService {

	private static Logger logger = Logger.getLogger(MainService.class);
	private static final String ASSET_ID_DELIMITER = ";";

	private final String[] assetIdArr;
	private final int queueSize;

	public MainService(final Properties appConfig) {

		String assetIds = appConfig.getProperty("acovery.asset.id");

		if (StringUtils.isBlank(assetIds))
			throw new IllegalArgumentException("'acovery.asset.id' is invalid.");

		String[] tempAssetIdArr = assetIds.split(ASSET_ID_DELIMITER, assetIds.length() + 1);
		assetIdArr = removeBlankArray(tempAssetIdArr);

		int itempQueueSize;
		try {
			String stempQueueSize = appConfig.getProperty("acovery.queue.size");
			itempQueueSize = Integer.parseInt(stempQueueSize);
		} catch (NumberFormatException e) {
			logger.warn("'acovery.queue.size' is invalid or not defined, use default value(60)");
			itempQueueSize = 60;
		}
		queueSize = itempQueueSize;
	}

	private String[] removeBlankArray(final String[] origin) {
		return Arrays.stream(origin).filter(x -> !StringUtils.isBlank(x)).toArray(size -> new String[size]);
	}

	public static void main(String[] args) {
		logger.info("Launching Acovery...");
		try {
			final Properties appConfig = ConfigManager.getAppConfig();
			final MainService mainService = new MainService(appConfig);
			mainService.start();
		} catch (Exception e) {
			logger.error("Failure to launch Acovery due to " + e, e);
		}
	}

	private void start() throws Exception {
		for (final String assetId : assetIdArr) {
			intializeAndStartLoader(assetId);
		}
	}

	public void intializeAndStartLoader(String assetId) throws Exception {
		Loader loader = new Loader(assetId, queueSize);
		loader.initialize();
		loader.start();
	}

	public String[] getAssetIdArr() {
		return assetIdArr;
	}

	public int getQueueSize() {
		return queueSize;
	}

}
