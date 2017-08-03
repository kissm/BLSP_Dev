package com.framework.core.cache.keygenerator;

import java.lang.reflect.Method;

public interface CacheKeyGenerator {

	public String generateKey(Method method, Object[] args, Object target, String key);

}
