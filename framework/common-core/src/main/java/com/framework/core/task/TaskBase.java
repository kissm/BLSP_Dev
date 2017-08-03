package com.framework.core.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.framework.core.utils.HttpUtil;
import com.google.gson.Gson;

public abstract class TaskBase implements Runnable {
	Logger logger = LoggerFactory.getLogger(TaskBase.class);
	private Integer taskId;
	private TaskResult result;
	
	@Value("${scheduler.receiptURL}")
	private String schedulerReceiptURL;
	
	public TaskBase(Integer taskId){
		this.taskId = taskId;
	}
	
	public TaskBase(){
		
	}

	@Override
	public void run() {
		result = execute();
		sendResult();
	}
	
	public  abstract TaskResult execute();
	
	public void sendResult(){
		logger.debug("begin to send task result,task id:"+result.getTaskId());
		result.setTaskId(taskId);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		HttpUtil.doPost(schedulerReceiptURL, json);
		logger.info("task result sent:{}",result);
		
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	
	
	public static void main(String[] args) {
		Gson gson = new Gson();
		 TaskResult a = new TaskResult();
		 a.setMsg("msg");
		 a.setResCode("code");
		 a.setTaskId(1);
		String json = gson.toJson(a);
		System.out.println(json);
		
	}
	
	
	
	

}
