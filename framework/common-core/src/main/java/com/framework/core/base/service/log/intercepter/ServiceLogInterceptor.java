package com.framework.core.base.service.log.intercepter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.amqp.core.AmqpTemplate;

import com.framework.core.base.service.log.annotation.InterfaceLog;
import com.framework.core.base.service.log.dto.LogDTO;
import com.framework.core.result.AbstractResult;
import com.framework.core.utils.StringUtil;
import com.google.gson.Gson;

/**
 * 对加注了@RedisCacheGet 标注的方法进行拦截，先查询缓存
 *
 *
 */
public class ServiceLogInterceptor implements MethodInterceptor {

	Gson gson = new Gson();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	@Resource(name = "orderLogTemplate")
	private AmqpTemplate orderLogTemplate;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// 得到切点处的方法
		Method method = invocation.getMethod();
		Object[] obj = invocation.getArguments();
		Object requestObj = obj[0];
		LogDTO log = new LogDTO();
		String requestMsg = gson.toJson(obj[0] == null ? "" : requestObj);
		log.setRequestMsg(requestMsg);
		InterfaceLog logAnnotation = null;
		// 从方法中得到该方法的注解
		Annotation[] annotations = method.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().isAssignableFrom(InterfaceLog.class)) {
				logAnnotation = (InterfaceLog) annotation;
			}
		}
		log.setStartTime(new Date());
		Object returnObject = invocation.proceed();
		log.setEndTime(new Date());
		AbstractResult result = (AbstractResult) returnObject;
		log.setResCode(result.getResCode());
		log.setResponseMsg(gson.toJson(returnObject));
		if (logAnnotation != null) {
			log.setIntfCode(logAnnotation.code());
			if (StringUtil.isNotEmpty(logAnnotation.inChannelProperty())) {
				log.setChannelId(
						(String) PropertyUtils.getNestedProperty(requestObj, logAnnotation.inChannelProperty()));
			}
			if (StringUtil.isNotEmpty(logAnnotation.inMemberTokenProperty())) {
				log.setMemberToken(
						(String) PropertyUtils.getNestedProperty(requestObj, logAnnotation.inMemberTokenProperty()));
			}
			if (StringUtil.isNotEmpty(logAnnotation.inSysIdProperty())) {
				log.setSysid((String) PropertyUtils.getNestedProperty(requestObj, logAnnotation.inSysIdProperty()));
			}

		}
		orderLogTemplate.convertAndSend(log);
		return returnObject;
	}

}
