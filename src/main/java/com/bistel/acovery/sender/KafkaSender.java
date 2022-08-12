package com.bistel.acovery.sender;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

import com.bistel.acovery.config.ConfigManager;
import com.bistel.acovery.message.ApmMessage;

public class KafkaSender implements Runnable {
	private static Logger logger = Logger.getLogger(KafkaSender.class);

	private BlockingQueue<ApmMessage> blockingQueue;
	private KafkaProducer<String, byte[]> kafkaProducer;
	private String topic;
	private long sendInterval;

	public KafkaSender(BlockingQueue<ApmMessage> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	public void initialize(Properties senderConfig) throws Exception {
		Properties kafkaInitConfig = ConfigManager.getKafkaInitConfig();
		kafkaProducer = new KafkaProducer<>(kafkaInitConfig);
		topic = senderConfig.getProperty("sender.topic");
		if (StringUtils.isBlank(topic)) {
			throw new Exception("'sender.topic' is not defined.");
		}
		String tempSendInterval = senderConfig.getProperty("sender.sendInterval");
		try {
			sendInterval = Long.parseLong(tempSendInterval);
		} catch (Exception e) {
			logger.warn("'sender.sendInterval' is invalid or not defined, use default value(1000)");
			sendInterval = 1000;
		}
	}

	public void sendMessage(String message) throws Exception {
		ProducerRecord<String, byte[]> producerRecord = new ProducerRecord<>(topic, "", message.getBytes());
		kafkaProducer.send(producerRecord);
		logger.info("SEND Message : " + message);
		Thread.sleep(sendInterval);
	}

	@Override
	public void run() {
		while (true) {
			try {
				ApmMessage message = blockingQueue.take();
				sendMessage(message.toString());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

}
