package com.bistel.acovery.asset.model;

import java.util.Map;

public class Asset {
	private String id;
	private String name;
	private Map<String, String> parameterIdMap;

	public Asset(String id, String name, Map<String, String> parameterIdMap) {
		this.id = id;
		this.name = name;
		this.parameterIdMap = parameterIdMap;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getParameterIdMap() {
		return parameterIdMap;
	}

	public void setParameterIdMap(Map<String, String> parameterIdMap) {
		this.parameterIdMap = parameterIdMap;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.toString();
	}

}
