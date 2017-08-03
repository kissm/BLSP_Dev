package com.lpcode.modules.blsp.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 我的代办列表DTO
 *
 * @author wangyazhou
 *
 */
public class PrjTaskPauseTimesVO implements Serializable {

	private static final long serialVersionUID = 2509007578349600599L;
	private Long taskInstId;
	private String taskName;
	private int pauseNumMat;
	private Date pauseStartTime;
	private Date provideEndTime;
	private Date pauseEndTime;
	public Long getTaskInstId() {
		return taskInstId;
	}
	public void setTaskInstId(Long taskInstId) {
		this.taskInstId = taskInstId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Date getPauseStartTime() {
		return pauseStartTime;
	}
	public void setPauseStartTime(Date pauseStartTime) {
		this.pauseStartTime = pauseStartTime;
	}
	public Date getProvideEndTime() {
		return provideEndTime;
	}
	public void setProvideEndTime(Date provideEndTime) {
		this.provideEndTime = provideEndTime;
	}
	public Date getPauseEndTime() {
		return pauseEndTime;
	}
	public void setPauseEndTime(Date pauseEndTime) {
		this.pauseEndTime = pauseEndTime;
	}
	public int getPauseNumMat() {
		return pauseNumMat;
	}
	public void setPauseNumMat(int pauseNumMat) {
		this.pauseNumMat = pauseNumMat;
	}
	


}
