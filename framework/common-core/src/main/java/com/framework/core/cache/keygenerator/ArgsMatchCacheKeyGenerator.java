package com.framework.core.cache.keygenerator;

import java.lang.reflect.Method;

public class ArgsMatchCacheKeyGenerator implements CacheKeyGenerator {
    
	@Override
	public String generateKey(Method method, Object[] args, Object target, String key) {
		String result = key;
		if (result != null && result.indexOf("@args") > -1) {
			for (int i = 0; i < args.length; i++) {
				result = result.replaceAll("@args" + i, String.valueOf(args[i]));
			}
		}
		return result;
	}

}
