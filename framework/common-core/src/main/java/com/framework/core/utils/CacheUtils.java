package com.framework.core.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.framework.core.cache.MongodbVo;
import com.framework.core.cache.RedisVo;

public class CacheUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(CacheUtils.class);

	/**
	 * redis 缓存标记
	 */
	public static final String REDIS = "redis";

	/**
	 * mongodb 缓存标记
	 */
	public static final String MONGODB = "mongodb";

	/**
	 * redis 线程数据 key
	 */
	public static final ThreadLocal<List<RedisVo>> redisCache = new ThreadLocal<List<RedisVo>>();

	/**
	 * mongodb 线程数据 key
	 */
	public static final ThreadLocal<List<MongodbVo>> mongodbCache = new ThreadLocal<List<MongodbVo>>();

	@Autowired
	private static ClusterRedisUtil redisUtil;

	/**
	 * spring mongodb　集成操作类　
	 */
	@Autowired(required = false)
	protected static MongoTemplate mongoTemplate;

	public static final int SET = 0;

	public static final int HSET = 1;

	public static final int LSET = 2;

	public static final int HDEL = 3;

	public static final int INCR = 4;

	public static final int DECR = 5;

	public static final int SADD = 6;

	public static final int SDEL = 7;

	public static final int LDEL = 8;

	private static boolean cacheFlag = true;

	/**
	 * 获取线程内redis数据
	 * 
	 * @return
	 */
	public static List<RedisVo> getRedisData() {
		return redisCache.get();
	}

	/**
	 * 获取redis缓存内数据
	 * 
	 * @return
	 */
	public static <T> List<T> getRedisSetByList(String key, Class<T> claz) {
		Set<String> values = redisUtil.getSet(key);
		if (values == null || values.size() <= 0) {
			return null;
		}
		List<T> ts = new ArrayList<T>();
		for (String value : values) {
			T t = JsonUtil.getDTO(value, claz);
			ts.add(t);
		}
		return ts;
	}

	/**
	 * 获取redis缓存内数据
	 * 
	 * @return
	 */
	public static <T> Set<T> getRedisSet(String key, Class<T> claz) {
		Set<String> values = redisUtil.getSet(key);
		if (values == null || values.size() <= 0) {
			return null;
		}
		Set<T> ts = new HashSet<T>();
		for (String value : values) {
			T t = JsonUtil.getDTO(value, claz);
			ts.add(t);
		}

		return ts;
	}

	/**
	 * 获取redis缓存内数据
	 * 
	 * @return
	 */
	public static <T> List<T> getRedisList(String key, Class<T> claz) {
		List<String> values = redisUtil.getList(key);
		if (values == null || values.size() <= 0) {
			return null;
		}
		List<T> ts = new ArrayList<T>();
		for (String value : values) {
			T t = JsonUtil.getDTO(value, claz);
			ts.add(t);
		}

		return ts;
	}

	/**
	 * 获取redis缓存内数据
	 * 
	 * @return
	 */
	public static <T> List<T> getRedisList(String key, Class<T> claz,
			int start, int end) {
		List<String> values = redisUtil.getList(key, start - 1, end - 1);
		if (values == null || values.size() <= 0) {
			return null;
		}
		List<T> ts = new ArrayList<T>();
		for (String value : values) {
			T t = JsonUtil.getDTO(value, claz);
			ts.add(t);
		}

		return ts;
	}

	/**
	 * 获取redis缓存内数据
	 * 
	 * @return
	 */
	public static Long getRedisListLength(String key) {
		return redisUtil.getListLength(key);
	}

	/**
	 * 获取redis缓存内数据
	 * 
	 * @return
	 */
	public static <T> T getRedisObject(String domain, String key, Class<T> claz) {
		String value = redisUtil.getHSet(domain, key);
		if (value == null) {
			return null;
		}
		T t = JsonUtil.getDTO(value, claz);
		return t;
	}

	/**
	 * 获取redis缓存内数据
	 * 
	 * @return
	 */
	public static String getRedisString(String domain, String key) {
		return redisUtil.getHSet(domain, key);
	}

	/**
	 * 保存redis缓存数据到ThreadLocal
	 * 
	 * @param list
	 */
	public static void setRedisData(RedisVo redisVo) {
		if (redisVo == null) {
			return;
		}
		List<RedisVo> redisVos = getRedisData();
		if (redisVos == null) {
			redisVos = new ArrayList<RedisVo>();
		}
		redisVos.add(redisVo);
		redisCache.set(redisVos);
	}

	/**
	 * 保存redis缓存数据到ThreadLocal
	 * 
	 * @param list
	 */
	public static void setRedisData(List<RedisVo> list) {
		if (list == null || list.size() <= 0) {
			return;
		}
		List<RedisVo> redisVos = getRedisData();
		if (redisVos == null) {
			redisVos = new ArrayList<RedisVo>();
		}
		redisVos.addAll(list);
		redisCache.set(redisVos);
	}

	/**
	 * 清除线程内redis数据,缓存被写入后应该调用改方法清空数据
	 */
	public static void removeRedisData() {
		redisCache.remove();
	}

	/**
	 * 获取线程内mongodb数据
	 * 
	 * @return
	 */
	public static List<MongodbVo> getMongodbData() {
		return mongodbCache.get();
	}

	/**
	 * 保存Mongodb缓存数据到ThreadLocal
	 * 
	 * @param list
	 */
	public static void setMongodbData(MongodbVo mongodbVo) {
		if (mongodbVo == null) {
			return;
		}
		List<MongodbVo> mongodbVos = getMongodbData();
		if (mongodbVos == null) {
			mongodbVos = new ArrayList<MongodbVo>();
		}
		mongodbVos.add(mongodbVo);
		mongodbCache.set(mongodbVos);
	}

	/**
	 * 保存Mongodb缓存数据到ThreadLocal
	 * 
	 * @param list
	 */
	public static void setMongodbData(List<MongodbVo> list) {
		if (list == null || list.size() <= 0) {
			return;
		}
		List<MongodbVo> mongodbVos = getMongodbData();
		if (mongodbVos == null) {
			mongodbVos = new ArrayList<MongodbVo>();
		}
		mongodbVos.addAll(list);
		mongodbCache.set(mongodbVos);
	}

	/**
	 * 清除线程内Mongodb数据,缓存被写入后应该调用改方法清空数据
	 */
	public static void removeMongodbData() {
		mongodbCache.remove();
	}

	/**
	 * 提交 redis 数据到缓存
	 */
	public static void flushRedisData() {
		logger.debug("flush data to redis");
		List<RedisVo> list = redisCache.get();
		if (list == null || list.size() <= 0) {
			return;
		}
		for (RedisVo redisVo : list) {
			if (HSET == redisVo.getType()) {
				redisUtil.setHSet(redisVo.getField(), redisVo.getKey(),
						redisVo.getValue());
			} else if (LSET == redisVo.getType()) {
				logger.debug("flush List to redis:key[" + redisVo.getKey()
						+ "]");
				redisUtil.addList(redisVo.getKey(), redisVo.getValues());
			} else if (HDEL == redisVo.getType()) {
				redisUtil.delHSet(redisVo.getField(), redisVo.getKey());
			} else if (LDEL == redisVo.getType()) {
				redisUtil.del(redisVo.getKey());
			} else if (INCR == redisVo.getType()) {
				redisUtil.incr(redisVo.getKey());
			} else if (DECR == redisVo.getType()) {
				redisUtil.decr(redisVo.getKey());
			} else if (SADD == redisVo.getType()) {
				redisUtil.addSet(redisVo.getKey(), redisVo.getValues());
			} else if (SDEL == redisVo.getType()) {
				redisUtil.del(redisVo.getKey());
			}
		}
		removeRedisData();
	}

	/**
	 * 提交 mongodb 数据到缓存
	 */
	public static void flushMongodbData() {
		logger.debug("flush data to mongodb");
		List<MongodbVo> list = mongodbCache.get();
		if (list == null || list.size() <= 0) {
			return;
		}
		for (MongodbVo mongodbVo : list) {
			if (SADD == mongodbVo.getType()) {
				mongoTemplate.save(mongodbVo.getValueObject());
			} else if (SDEL == mongodbVo.getType()) {
				mongoTemplate.remove(mongodbVo.getValueObject());
			}
		}
		removeMongodbData();
	}

	/**
	 * 提交 redis和mongodb 数据到缓存
	 */
	public static void flushAll() {
		if (cacheFlag) {
			logger.debug("flush data to cache");
			flushRedisData();
			flushMongodbData();
		} else {
			logger.debug("cache is disable cacheFlag:" + cacheFlag);
		}
	}

	/**
	 * 清空 redis和mongodb 数据缓存
	 */
	public static void removeAll() {
		removeRedisData();
		removeMongodbData();
	}

	public static ClusterRedisUtil getRedisUtil() {
		return redisUtil;
	}

	public static void setRedisUtil(ClusterRedisUtil redisUtil) {
		CacheUtils.redisUtil = redisUtil;
	}

	public static boolean isCacheFlag() {
		return cacheFlag;
	}

	public static void setCacheFlag(boolean cacheFlag) {
		CacheUtils.cacheFlag = cacheFlag;
	}

}
