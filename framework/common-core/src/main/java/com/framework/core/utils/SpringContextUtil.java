/**
 * @Probject Name: common-core
 * @Path: com.bailian.core.utilsSpringContextUtil.java
 * @Create By wangfei
 * @Create In 2014年11月28日 下午2:10:55
 */
package com.framework.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spirngContext工具类，用于方便的获取spring容器里的bean
 * 
 * @Class Name SpringContextUtil
 * @Author wangfei
 * @Create In 2014年11月28日
 */
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		context = ctx;

	}

	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	/**
	 * 根据Bean名称返回String类型的BEAN
	 * 
	 * @param beanName
	 * @return
	 */
	public static String getStringBean(String beanName) {
		return context.getBean(beanName, String.class);
	}
}
