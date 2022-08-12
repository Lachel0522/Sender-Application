package com.bistel.acovery.message;

import java.util.Map;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ApmMessage {

	protected DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZoneUTC();
	protected String assetId;
	protected String sensorType;
	protected String sensorId;
	protected long time;
	protected Map<String, String> dataMap;

	public ApmMessage(String assetId, String sensorType, String sensorId, long time, Map<String, String> dataMap) {
		this.assetId = assetId;
		this.sensorType = sensorType;
		this.sensorId = sensorId;
		this.time = time;
		this.dataMap = dataMap;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Map<String, String> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append('"').append("sensorType").append('"').append(":").append('"').append("Distribution_BaeBang").append('"')
				.append(",").append('"').append("sensorId").append('"').append(":").append('"')
				.append("BB_Distribution_001").append('"').append(",").append('"').append("longitude").append('"')
				.append(":").append("0.0").append(",").append('"').append("latitude").append('"').append(":")
				.append("0.0").append(",").append('"').append("assetId").append('"').append(":").append('"')
				.append(this.assetId).append('"').append(",").append('"').append("event_time").append('"').append(":")
				.append('"').append(dtf.print(time)).append('"').append(",");

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
