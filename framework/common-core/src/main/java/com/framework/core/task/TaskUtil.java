package com.framework.core.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TaskUtil {
	
	public static void executeTask(TaskBase task,Integer taskId){
		ExecutorService executor = Executors.newSingleThreadExecutor();
		task.setTaskId(taskId);
		executor.submit(task);
		executor.shutdown();
	}
	

}
