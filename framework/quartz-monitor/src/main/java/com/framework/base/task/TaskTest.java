package com.framework.base.task;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 任务测试
 * 
 * @Class Name TaskTest
 * @Author mogu
 * @Create In 2014年12月18日
 */
@Component
public class TaskTest {
	// 日志记录器
	private static final Logger LOG = LoggerFactory.getLogger(TaskTest.class);

	public void run() {
		for (int i = 0; i < 1; i++) {
			LOG.debug(i + " run......................................"
					+ (new Date()));
		}

	}

	public void run1() {
		for (int i = 0; i < 1; i++) {
			LOG.debug(i + " run1......................................"
					+ (new Date()));
		}
	}

	public static void main(String[] args) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(new Runnable() {

			@Override
			public void run() {
				System.out.println("in here.");
			}

		});
		
		executor.shutdown();

	}
}
