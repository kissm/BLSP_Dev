package com.framework.core.cache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.core.utils.JsonUtil;
import com.framework.core.utils.RedisUtil;

/**
 * 对加注了@RedisCache 标注的方法进行拦截，先查询缓存
 *
 *
 */
public class RedisCacheInterceptor implements AfterReturningAdvice {

	private static Logger log = LoggerFactory.getLogger("RedisCacheInterceptor");
	@Autowired
	private RedisUtil redisUtil;

	// 添加缓存标志
	private static final String ADD = "ADD";
	// 更新缓存标志
	private static final String UPDATE = "UPDATE";
	// 删除缓存标志
	private static final String DEL = "DEL";

	@Override
	public void afterReturning(Object result, Method method, Object[] parameter, Object target) throws Throwable {
		String flag = "";
		if (parameter.length > 0) {
			if (parameter[0].getClass().isAssignableFrom(RedisCacheBean.class)) {
				RedisCacheBean redisCacheBean = (RedisCacheBean) parameter[0];
				if (!redisCacheBean.isEmpty()) {
					Annotation[] annotations = method.getAnnotations();
					for (Annotation annotation : annotations) {
						if (annotation.annotationType().isAssignableFrom(RedisCache.class)) {
							RedisCache redisCache = (RedisCache) annotation;
							flag = redisCache.flag();
							break;
						}
					}
					try {
						if (ADD.equals(flag)) {
							redisUtil.setHSet(redisCacheBean.getRedisName(), redisCacheBean.getRedisKey(),
									JsonUtil.getJSONString(redisCacheBean.getObj()));
						} else if (UPDATE.equals(flag)) {
							redisUtil.delHSet(redisCacheBean.getRedisName(), redisCacheBean.getRedisKey());
							redisUtil.setHSet(redisCacheBean.getRedisName(), redisCacheBean.getRedisKey(),
									JsonUtil.getJSONString(redisCacheBean.getObj()));
						} else if (DEL.equals(flag)) {
							redisUtil.delHSet(redisCacheBean.getRedisName(), redisCacheBean.getRedisKey());
						}
					} catch (Exception e) {
						log.error(e.getMessage(), e);
						redisUtil.delHSet(redisCacheBean.getRedisName(), redisCacheBean.getRedisKey());
					}

				}
			}
		}
	}

}
