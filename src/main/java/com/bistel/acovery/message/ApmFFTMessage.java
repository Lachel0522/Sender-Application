package com.bistel.acovery.message;

import java.util.Map;

public class ApmFFTMessage extends ApmMessage {

	public ApmFFTMessage(String assetId, String sensorType, String sensorId, long time, Map<String, String> dataMap) {
		super(assetId, sensorType, sensorId, time, dataMap);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append('"').append("event_time").append('"').append(":").append('"').append(dtf.print(time)).append('"')
				.append(",").append('"').append("assetId").append('"').append(":").append('"').append(assetId)
				.append('"').append(",").append('"').append("sensorType").append('"').append(":").append('"')
				.append(sensorType).append('"').append(",").append('"').append("sensorId").append('"').append(":")
				.append('"').append(sensorId).append('"').append(",");
		for (String key : dataMap.keySet()) {
			String value = dataMap.get(key);
			if (value != null)
				sb.append('"').append(key).append('"').append(":").append(value).append(",");
		}
		sb.setLength(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}
}
