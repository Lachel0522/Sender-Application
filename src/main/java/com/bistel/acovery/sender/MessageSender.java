package com.bistel.acovery.sender;

import java.util.Properties;

public interface MessageSender {

	public void initialize(final Properties senderConfig) throws Exception;
	public String sendMessage(String message) throws Exception;
}
