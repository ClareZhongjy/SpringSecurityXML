package com.cn.clare.dto;

public class MqSenderRequest {
	//队列名称
	private String mqType;
	
	private String message;

	public String getMqType() {
		return mqType;
	}

	public void setMqType(String mqType) {
		this.mqType = mqType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
