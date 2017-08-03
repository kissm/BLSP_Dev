package com.framework.core.cache;

/**
 *
 * @Class Name RedisCacheBean
 * @Author guowenqin
 * @Create In 2014年10月10日 缓存bean
 */
public class RedisCacheBean {
	private String redisName;
	private String redisKey;
	private Object obj;

	public String getRedisName() {
		return redisName;
	}

	public void setRedisName(String redisName) {
		this.redisName = redisName;
	}

	public String getRedisKey() {
		return redisKey;
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isEmpty() {
		boolean rs = true;
		if (!redisName.isEmpty() && obj != null) {
			rs = false;
		}
		return rs;
	}
}
