package com.TechPlat.model;

import java.util.Date;

/**
 * 流程模板
 * @author kk
 *
 */
public class ActivitiTask {
	int id;
	private String processInstanceId;
	private String userId;
	private String startTime;
	private String endTime;
	private String leaveType;
	private String reason;
	private String applyTime;
	private String realityStartTime;
	private String realityEndTime;
	private String taskId;
	private String taskName;
	private String processDefId;
	private Date taskCreateTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getRealityStartTime() {
		return realityStartTime;
	}
	public void setRealityStartTime(String realityStartTime) {
		this.realityStartTime = realityStartTime;
	}
	public String getRealityEndTime() {
		return realityEndTime;
	}
	public void setRealityEndTime(String realityEndTime) {
		this.realityEndTime = realityEndTime;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getProcessDefId() {
		return processDefId;
	}
	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}
	public Date getTaskCreateTime() {
		return taskCreateTime;
	}
	public void setTaskCreateTime(Date taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
	}
	
	
	
}
