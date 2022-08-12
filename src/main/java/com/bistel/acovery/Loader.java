package com.bistel.acovery;

import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import com.bistel.acovery.asset.model.Asset;
import com.bistel.acovery.asset.service.AssetService;
import com.bistel.acovery.config.ConfigManager;
import com.bistel.acovery.dataproc.DataProc;
import com.bistel.acovery.dataproc.DataProcFactory;
import com.bistel.acovery.message.ApmMessage;
import com.bistel.acovery.sender.KafkaSender;

public class Loader {
	private static Logger logger = Logger.getLogger(Loader.class);

	private final String assetId;
	private final BlockingQueue<ApmMessage> blockingQueue;

	private DataProc dataProc;
	private KafkaSender kafkaSender;

	public Loader(String assetId, int queueSize) {
		this.assetId = assetId;
		blockingQueue = new ArrayBlockingQueue<ApmMessage>(queueSize);
	}

	public void initialize() throws Exception {
		initializeDataProc();

		/*
		 * 2022/01/22 Asset asset = AssetService.getAsset(assetId);
		initializeSender();
		 */
	}

	public void initializeDataProc() throws Exception {
		try {
			/*
			 * 2022/01/22 
			 * Asset asset = AssetService.getAsset(assetId);
			 */
			Asset asset = new Asset("6c5a7151-087a-4138-8db0-595ccc7697ee", "Test", new HashMap() {
				{
					put("ddd772svsg76gjjd","PARAMETER_ID_001");
					put("ff235yjr456hdsff","PARAMETER_ID_002");
					//put("C","c");
				}
			});
			

			dataProc = DataProcFactory.makeDataProc(asset, blockingQueue);
//			System.out.println("asset id = "+asset.getId());
//			System.out.println("asset name = "+asset.getName());
//			System.out.println("asset parameteridmap key = "+asset.getParameterIdMap().keySet());
//			System.out.println("asset parameteridmap value = "+asset.getParameterIdMap().values());
//			System.out.println("asset key = "+asset.getParameterIdMap().keySet());

		} catch (Exception e) {
			logger.error("");
			throw e;
		}
	}

	public void initializeSender() throws Exception {
		try {
			Properties senderConfig = ConfigManager.getSenderConfig();
			kafkaSender = new KafkaSender(blockingQueue);
			kafkaSender.initialize(senderConfig);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	public void start() {
		Thread dpThread = new Thread(dataProc);
		dpThread.setName("DataProc-" + assetId);
		dpThread.start();
		
//		Thread kThread = new Thread(kafkaSender); kThread.setName("Kafka-" + assetId); kThread.start();
		/*
		 * 2022/01/22 Thread kThread = new Thread(kafkaSender); kThread.setName("Kafka-"
		 * + assetId); kThread.start();
		 */
	}

	public DataProc getDataProc() {
		return dataProc;
	}

	public void setDataProc(DataProc dataProc) {
		this.dataProc = dataProc;
	}

	public KafkaSender getKafkaSender() {
		return kafkaSender;
	}

	public void setKafkaSender(KafkaSender kafkaSender) {
		this.kafkaSender = kafkaSender;
	}

	public String getAssetId() {
		return assetId;
	}

	public BlockingQueue<ApmMessage> getBlockingQueue() {
		return blockingQueue;
	}
}
