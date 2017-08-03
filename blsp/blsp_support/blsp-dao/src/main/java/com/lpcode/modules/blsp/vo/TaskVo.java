package com.lpcode.modules.blsp.vo;

import java.io.Serializable;
import java.util.Date;

public class TaskVo implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;


	public String taskName;
	public String depId;
	public String depName;
	public Date startTime;
	public Date endTime;
	public Date needEndTime;
	public String status;
	public String taskid;
	public Long taskInstanceId;//事项记录的ID
	public String projectName;
	private String userName; // 当前处理人
	private String overDate; // 超时天数
	private Date taskPauseStartTime; // 暂停时间
	private String taskPauseType; // 暂停类型
	private Long prjStageInstId; // 获取项目事项操作列表依赖ID

	public Long getTaskInstanceId() {
		return taskInstanceId;
	}

	public void setTaskInstanceId(Long taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOverDate() {
		return overDate;
	}

	public void setOverDate(String overDate) {
		this.overDate = overDate;
	}

	public Date getTaskPauseStartTime() {
		return taskPauseStartTime;
	}

	public void setTaskPauseStartTime(Date taskPauseStartTime) {
		this.taskPauseStartTime = taskPauseStartTime;
	}

	public String getTaskPauseType() {
		return taskPauseType;
	}

	public void setTaskPauseType(String taskPauseType) {
		this.taskPauseType = taskPauseType;
	}

	public Long getPrjStageInstId() {
		return prjStageInstId;
	}

	public void setPrjStageInstId(Long prjStageInstId) {
		this.prjStageInstId = prjStageInstId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getNeedEndTime() {
		return needEndTime;
	}

	public void setNeedEndTime(Date needEndTime) {
		this.needEndTime = needEndTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
