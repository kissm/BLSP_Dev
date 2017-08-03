package com.lpcode.modules.blsp.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 我的代办列表DTO
 * 
 * @author wangyazhou
 *
 */
public class PrjTaskTodoListVO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2509007578349600599L;
	private String prjTaskInstId;
	private String prjCode;
	private String prjName;
	private String taskName;
	private String taskStatus;
	private Date taskStartTime;
	private Date taskEndTime;
	private Date taskRealEndTime;
	private int taskRemainTime;//剩余办结时间
	private String taskStageName;// 阶段名称
	private String userId;
	private String company; // 项目单位
	private String timeType;
	
	private String orderBy;
	
	private Date beginCreateDate;// 查询开始时间
	private Date endCreateDate;// 查询截至时间
	
	private String passType;// 1：已办；2：办结
	
	private List<Long> prjTaskInstIds;
	
	private String auditAttachAddr;// 附件上传
	private String auditAttachName;// 附件名称
	private String auditAttachCodeAddr;// 加码附件上传
	private String auditAttachCodeName;// 加码附件名称
	
	private String userName;// 为显示当前事项审批人加的用户名
	private String taskPauseDesc;// 事项暂停的暂停原因
	private Date taskPauseStartTime;// 事项暂停的停止时间
	private String taskPauseType;// 事项暂停的暂停类型
	
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
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
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
	public int getTaskRemainTime() {
		return taskRemainTime;
	}
	public void setTaskRemainTime(int taskRemainTime) {
		this.taskRemainTime = taskRemainTime;
	}
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}
	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	public Date getEndCreateDate() {
		return endCreateDate;
	}
	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getPrjTaskInstId() {
		return prjTaskInstId;
	}
	public void setPrjTaskInstId(String prjTaskInstId) {
		this.prjTaskInstId = prjTaskInstId;
	}
	public List<Long> getPrjTaskInstIds() {
		return prjTaskInstIds;
	}
	public void setPrjTaskInstIds(List<Long> prjTaskInstIds) {
		this.prjTaskInstIds = prjTaskInstIds;
	}
	public Date getTaskRealEndTime() {
		return taskRealEndTime;
	}
	public void setTaskRealEndTime(Date taskRealEndTime) {
		this.taskRealEndTime = taskRealEndTime;
	}
	public String getPassType() {
		return passType;
	}
	public void setPassType(String passType) {
		this.passType = passType;
	}
	public String getTaskStageName() {
		return taskStageName;
	}
	public void setTaskStageName(String taskStageName) {
		this.taskStageName = taskStageName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public String getAuditAttachAddr() {
		return auditAttachAddr;
	}
	public void setAuditAttachAddr(String auditAttachAddr) {
		this.auditAttachAddr = auditAttachAddr;
	}
	public String getAuditAttachName() {
		return auditAttachName;
	}
	public void setAuditAttachName(String auditAttachName) {
		this.auditAttachName = auditAttachName;
	}
	public String getAuditAttachCodeAddr() {
		return auditAttachCodeAddr;
	}
	public void setAuditAttachCodeAddr(String auditAttachCodeAddr) {
		this.auditAttachCodeAddr = auditAttachCodeAddr;
	}
	public String getAuditAttachCodeName() {
		return auditAttachCodeName;
	}
	public void setAuditAttachCodeName(String auditAttachCodeName) {
		this.auditAttachCodeName = auditAttachCodeName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTaskPauseDesc() {
		return taskPauseDesc;
	}
	public void setTaskPauseDesc(String taskPauseDesc) {
		this.taskPauseDesc = taskPauseDesc;
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

}
