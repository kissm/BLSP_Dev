package com.lpcode.modules.blsp.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 我的代办列表DTO
 *
 * @author wangyazhou
 *
 */
public class PrjTaskPauseDueVO implements Serializable {

	private static final long serialVersionUID = 2509007578349600599L;
	private Long taskInstId;
	private Long pauseInstId;
	private String prjCode;
	private String prjName;
	private String taskName;
	private String taskCode;
	private Date taskStartTime;
	private Date taskEndTime;
	private Integer taskPauseDuration;
	private String timeType;
	private Integer taskDueDuration;
	private Integer pauseDuration;

	private String userId;
	private String mobile;

	public Long getTaskInstId() {
		return taskInstId;
	}

	public void setTaskInstId(Long taskInstId) {
		this.taskInstId = taskInstId;
	}

	public Long getPauseInstId() {
		return pauseInstId;
	}

	public void setPauseInstId(Long pauseInstId) {
		this.pauseInstId = pauseInstId;
	}

	public String getPrjCode() {
		return prjCode;
	}

	public void setPrjCode(String prjCode) {
		this.prjCode = prjCode;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public Date getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	public Date getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public Integer getTaskPauseDuration() {
		return taskPauseDuration;
	}

	public void setTaskPauseDuration(Integer taskPauseDuration) {
		this.taskPauseDuration = taskPauseDuration;
	}

	public Integer getTaskDueDuration() {
		return taskDueDuration;
	}

	public void setTaskDueDuration(Integer taskDueDuration) {
		this.taskDueDuration = taskDueDuration;
	}

	public Integer getPauseDuration() {
		return pauseDuration;
	}

	public void setPauseDuration(Integer pauseDuration) {
		this.pauseDuration = pauseDuration;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

}
