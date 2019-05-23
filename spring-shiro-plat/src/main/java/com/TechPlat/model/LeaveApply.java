package com.TechPlat.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author carlson
 * @since 2019-04-29
 */
@TableName("bpm_leaveapply")
public class LeaveApply extends Model<LeaveApply> {

	private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("process_instance_id")
	private String processInstanceId;
	@TableField("user_id")
	private String userId;
	@TableField("start_leave_time")
	private String startLeaveTime;
	@TableField("end_leave_time")
	private String endLeaveTime;
	@TableField("leave_type")
	private String leaveType;

	@TableField("reason")
	private String reason;
	@TableField("apply_time")
	private String applyTime;
	@TableField("reality_start_time")
	private String realityStartTime;
	@TableField("reality_end_time")
	private String realityEndTime;
	@TableField("memo")
	private String memo;
	
	
	private org.activiti.engine.task.Task task;
	




	public org.activiti.engine.task.Task getTask() {
		return task;
	}

	public void setTask(org.activiti.engine.task.Task task) {
		this.task = task;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
