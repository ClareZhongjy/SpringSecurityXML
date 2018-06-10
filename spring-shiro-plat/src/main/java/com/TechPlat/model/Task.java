package com.TechPlat.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 7168 kB
 * </p>
 *
 * @author zjy
 * @since 2018-05-27
 */
public class Task extends Model<Task> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	@TableField("task_name")
	private String taskName;
	@TableField("task_schedule")
	private String taskSchedule;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskSchedule() {
		return taskSchedule;
	}

	public void setTaskSchedule(String taskSchedule) {
		this.taskSchedule = taskSchedule;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
