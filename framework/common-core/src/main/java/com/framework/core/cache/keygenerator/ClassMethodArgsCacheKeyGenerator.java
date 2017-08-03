package com.framework.core.cache.keygenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * 如果“key == null || key.trim().isEmpty()”就使用“类名 + 方法名 + 参数值列表”作为缓存的key，
 * 否则使用用户给的key值作为key
 */
public class ClassMethodArgsCacheKeyGenerator implements CacheKeyGenerator {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public String generateKey(Method interfaceMethod, Object[] args,
			Object target, String key) {
		if (key != null && !"".equals(key)) {
			return key;
		}

		Method implMethod = null;
		// 使用具体的实现类作为key的前缀，而不使用接口最为前缀，因为接口有多个实现
		if (!Proxy.isProxyClass(target.getClass())) {
			try {
				implMethod = target.getClass().getMethod(
						interfaceMethod.getName(),
						interfaceMethod.getParameterTypes());
			} catch (NoSuchMethodException e) {
				logger.error("获取实现类的方法出错，method：" + interfaceMethod, e);
			}
		}
		Method methodForKey = (implMethod != null ? implMethod
				: interfaceMethod);
		key = methodForKey.getDeclaringClass().getName() + "."
				+ methodForKey.getName();

		key += ":" + Arrays.toString(args);
		logger.debug(">>>>>>>>generated rediskey:" + key + ",real key:"
				+ key.hashCode());

		return key.hashCode() + "";
	}

}