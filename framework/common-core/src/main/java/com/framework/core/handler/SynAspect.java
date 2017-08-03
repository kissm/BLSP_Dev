package com.framework.core.handler;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.framework.core.utils.AppUtil;

/**
 * 
 * @author wangchaochao
 *
 */
public class SynAspect {

	private static final Logger logger = LoggerFactory
			.getLogger(SynAspect.class);

	private ThreadPoolTaskExecutor taskExecutor;

	private boolean handlerFlag;

	/**
	 * 该方法拦截SynHandler标签
	 * 
	 * @param call
	 * @return
	 * @throws Throwable
	 */
	public Object doAround(ProceedingJoinPoint call) throws Throwable {

		// 1.调用服务方法并获得返回值,该处不捕获异常,由上一层异常AOP处理层捕获

		// 调用target方法
		Object result = call.proceed();

		// 同步开关
		if (!handlerFlag) {
			return result;
		}

		// 目标服务
		Object target = call.getTarget();

		// 目标方法
		Signature signature = call.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		// 方法入参
		Object[] args = call.getArgs();

		// 获取标签参数
		SynStrike synStrike = null;
		if (method.isAnnotationPresent(SynStrike.class)) {
			synStrike = (SynStrike) method.getAnnotation(SynStrike.class);
		} else {
			return result;
		}

		// 同步数据类型
		String type = synStrike.type();

		// 同步处理异步处理,默认为异步处理
		boolean asynFlag = synStrike.asynchronous();
		// handler列表
		String[] handlers = synStrike.handlers();
		if (handlers == null || handlers.length == 0) {
			logger.warn("同步handler列表为空,请检查同步配置");
			return result;
		}

		// 2.process,异常在该代码块中处理
		for (String handler : handlers) {
			try {
				SynHandler synHandler = (SynHandler) AppUtil.getBean(handler);
				if (asynFlag) {
					// 异步
					SynTask synTask = new SynTask(target, args, result,
							synHandler, type);
					taskExecutor.execute(synTask);
				} else {
					// 同步
					synHandler.synData(target, args, result, type);
				}
			} catch (Throwable e) {
				e.printStackTrace();
				logger.error("同步handler[" + handler + "]出错:", e);
			}
		}
		return result;
	}

	public ThreadPoolTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	private class SynTask implements Runnable {

		private Object target;

		private Object[] args;

		private Object data;

		private SynHandler synHandler;

		private String type;

		public SynTask(Object target, Object[] args, Object data,
				SynHandler synHandler, String type) {
			this.target = target;
			this.args = args;
			this.data = data;
			this.synHandler = synHandler;
			this.type = type;
		}

		public void run() {
			synHandler.synData(target, args, data, type);
		}
	}

	public boolean isHandlerFlag() {
		return handlerFlag;
	}

	public void setHandlerFlag(boolean handlerFlag) {
		this.handlerFlag = handlerFlag;
	}

}
