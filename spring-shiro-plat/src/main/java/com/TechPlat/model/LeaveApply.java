package com.TechPlat.model;

import java.io.Serializable;

public class LeaveApply implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String leaveType;
	String startLeaveTime;
	String endLeaveTime;
	String reason;
	String memo;

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getStartLeaveTime() {
		return startLeaveTime;
	}

	public void setStartLeaveTime(String startLeaveTime) {
		this.startLeaveTime = startLeaveTime;
	}

	public String getEndLeaveTime() {
		return endLeaveTime;
	}

	public void setEndLeaveTime(String endLeaveTime) {
		this.endLeaveTime = endLeaveTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
