package com.frame.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Query extends LinkedHashMap<String ,Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int offset;
	
	private int limit;

	public Query(Map<String,Object> params) {
		this.putAll(params);
		this.offset = Integer.parseInt(params.get("offset").toString());
		this.limit = Integer.parseInt(params.get("limit").toString());
		this.put("offset", offset);
		this.put("page", offset/limit+1);
		this.put("limit", limit);
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
	
	
	
}
