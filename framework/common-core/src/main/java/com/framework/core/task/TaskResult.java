package com.framework.core.task;

import com.framework.core.result.AbstractResult;

public class TaskResult extends AbstractResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3962274350541934690L;
	
	private Integer taskId;

	protected Integer getTaskId() {
		return taskId;
	}

	protected void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	@Override
	public String toString() {
		return "TaskResult [taskId=" + taskId + ", resCode=" + resCode
				+ ", msg=" + msg + "]";
	}
	
	
	
	
	

}
