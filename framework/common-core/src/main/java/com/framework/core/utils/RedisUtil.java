package com.framework.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisUtil {
	// @Resource
	private ShardedJedisPool shardedJedisPool;
	private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

	/**
	 * 设置一个key的过期时间（单位：秒）
	 *
	 * @param key
	 *            key值
	 * @param seconds
	 *            多少秒后过期
	 * @return 1：设置了过期时间 0：没有设置过期时间/不能设置过期时间
	 */
	public long expire(String key, int seconds) {
		if (key == null || key.equals("")) {
			return 0;
		}

		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.expire(key, seconds);
		} catch (Exception ex) {
			logger.error("EXPIRE error[key=" + key + " seconds=" + seconds + "]" + ex.getMessage(), ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return 0;
	}

	/**
	 * 设置一个key在某个时间点过期
	 *
	 * @param key
	 *            key值
	 * @param unixTimestamp
	 *            unix时间戳，从1970-01-01 00:00:00开始到现在的秒数
	 * @return 1：设置了过期时间 0：没有设置过期时间/不能设置过期时间
	 */
	public long expireAt(String key, int unixTimestamp) {
		if (key == null || key.equals("")) {
			return 0;
		}

		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.expireAt(key, unixTimestamp);
		} catch (Exception ex) {
			logger.error("EXPIRE error[key=" + key + " unixTimestamp=" + unixTimestamp + "]" + ex.getMessage(), ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return 0;
	}

	/**
	 * 截断一个List
	 *
	 * @param key
	 *            列表key
	 * @param start
	 *            开始位置 从0开始
	 * @param end
	 *            结束位置
	 * @return 状态码
	 */
	public String trimList(String key, long start, long end) {
		if (key == null || key.equals("")) {
			return "-";
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.ltrim(key, start, end);
		} catch (Exception ex) {
			logger.error("LTRIM 出错[key=" + key + " start=" + start + " end=" + end + "]" + ex.getMessage(), ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return "-";
	}

	/**
	 * 检查Set长度
	 *
	 * @param key
	 * @return
	 */
	public long countSet(String key) {
		if (key == null) {
			return 0;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.scard(key);
		} catch (Exception ex) {
			logger.error("countSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return 0;
	}

	/**
	 * 添加到Set中（同时设置过期时间）
	 *
	 * @param key
	 *            key值
	 * @param seconds
	 *            过期时间 单位s
	 * @param value
	 * @return
	 */
	public boolean addSet(String key, int seconds, String value) {
		boolean result = addSet(key, value);
		if (result) {
			long i = expire(key, seconds);
			return i == 1;
		}
		return false;
	}

	/**
	 * 添加到Set中（同时设置过期时间）
	 *
	 * @param key
	 *            key值
	 * @param seconds
	 *            过期时间 单位s
	 * @param values
	 * @return
	 */
	public boolean addSet(String key, int seconds, List<String> values) {
		boolean result = addSet(key, values);
		if (result) {
			long i = expire(key, seconds);
			return i == 1;
		}
		return false;
	}

	/**
	 * 批量添加到set中
	 *
	 * @param key
	 * @param values
	 * @return
	 */
	public boolean addSet(String key, List<String> values) {
		if (key == null || values == null || values.size() <= 0) {
			return false;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.sadd(key, values.toArray(new String[0]));
			return true;
		} catch (Exception ex) {
			logger.error("setList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return false;
	}

	/**
	 * 添加到Set中
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean addSet(String key, String value) {
		if (key == null || value == null) {
			return false;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.sadd(key, value);
			return true;
		} catch (Exception ex) {
			logger.error("setList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return false;
	}

	/**
	 * @param key
	 * @param value
	 * @return 判断值是否包含在set中
	 */
	public boolean containsInSet(String key, String value) {
		if (key == null || value == null) {
			return false;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.sismember(key, value);
		} catch (Exception ex) {
			logger.error("setList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return false;
	}

	/**
	 * 获取Set
	 *
	 * @param key
	 * @return
	 */
	public Set<String> getSet(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.smembers(key);
		} catch (Exception ex) {
			logger.error("getList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return null;
	}

	/**
	 * 从set中删除value
	 *
	 * @param key
	 * @return
	 */
	public boolean removeSetValue(String key, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.srem(key, value);
			return true;
		} catch (Exception ex) {
			logger.error("getList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return false;
	}

	/**
	 * 从list中删除value 默认count 1
	 *
	 * @param key
	 * @param values
	 *            值list
	 * @return
	 */
	public int removeListValue(String key, List<String> values) {
		return removeListValue(key, 1, values);
	}

	/**
	 * 从list中删除value
	 *
	 * @param key
	 * @param count
	 * @param values
	 *            值list
	 * @return
	 */
	public int removeListValue(String key, long count, List<String> values) {
		int result = 0;
		if (values != null && values.size() > 0) {
			for (String value : values) {
				if (removeListValue(key, count, value)) {
					result++;
				}
			}
		}
		return result;
	}

	/**
	 * 从list中删除value
	 *
	 * @param key
	 * @param count
	 *            要删除个数
	 * @param value
	 * @return
	 */
	public boolean removeListValue(String key, long count, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.lrem(key, count, value);
			return true;
		} catch (Exception ex) {
			logger.error("getList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return false;
	}

	/**
	 * 截取List
	 *
	 * @param key
	 * @param start
	 *            起始位置
	 * @param end
	 *            结束位置
	 * @return
	 */
	public List<String> rangeList(String key, long start, long end) {
		if (key == null || key.equals("")) {
			return null;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.lrange(key, start, end);
		} catch (Exception ex) {
			logger.error("rangeList 出错[key=" + key + " start=" + start + " end=" + end + "]" + ex.getMessage(), ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return null;
	}

	/**
	 * 检查List长度
	 *
	 * @param key
	 * @return
	 */
	public long countList(String key) {
		if (key == null) {
			return 0;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.llen(key);
		} catch (Exception ex) {
			logger.error("countList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return 0;
	}

	/**
	 * 添加到List中（同时设置过期时间）
	 *
	 * @param key
	 *            key值
	 * @param seconds
	 *            过期时间 单位s
	 * @param value
	 * @return
	 */
	public boolean addList(String key, int seconds, String value) {
		boolean result = addList(key, value);
		if (result) {
			long i = expire(key, seconds);
			return i == 1;
		}
		return false;
	}

	/**
	 * 添加到List
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean addList(String key, String value) {
		if (key == null || value == null) {
			return false;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.lpush(key, value);
			return true;
		} catch (Exception ex) {
			logger.error("setList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return false;
	}

	/**
	 * 添加到List(只新增)
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean addList(String key, List<String> list) {
		if (key == null || list == null || list.size() == 0) {
			return false;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.lpush(key, list.toArray(new String[0]));
			return true;
		} catch (Exception ex) {
			logger.error("setList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return true;
	}

	/**
	 * 获取List
	 *
	 * @param key
	 * @return
	 */
	public List<String> getList(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.lrange(key, 0, -1);
		} catch (Exception ex) {
			logger.error("getList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return null;
	}

	/**
	 * 获取List
	 *
	 * @param key
	 * @return
	 */
	public List<String> getList(String key, int start, int end) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.lrange(key, start, end);
		} catch (Exception ex) {
			logger.error("getList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return null;
	}

	/**
	 * 获取List
	 *
	 * @param key
	 * @return
	 */
	public Long getListLength(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.llen(key);
		} catch (Exception ex) {
			logger.error("getList error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return null;
	}

	/**
	 * 设置HashSet对象
	 *
	 * @param domain
	 *            域名
	 * @param key
	 *            键值
	 * @param value
	 *            Json String or String value
	 * @return
	 */
	public boolean setHSet(String domain, String key, String value) {
		if (value == null) {
			return false;
		}
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.hset(domain, key, value);
			return true;
		} catch (Exception ex) {
			logger.error("setHSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return false;
	}

	/**
	 * 获得HashSet对象
	 *
	 * @param domain
	 *            域名
	 * @param key
	 *            键值
	 * @return Json String or String value
	 */
	public String getHSet(String domain, String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.hget(domain, key);
		} catch (Exception ex) {
			logger.error("getHSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return null;
	}

	/**
	 * 删除HashSet对象
	 *
	 * @param domain
	 *            域名
	 * @param key
	 *            键值
	 * @return 删除的记录数
	 */
	public long delHSet(String domain, String key) {
		ShardedJedis shardedJedis = null;
		long count = 0;
		try {
			shardedJedis = shardedJedisPool.getResource();
			count = shardedJedis.hdel(domain, key);
		} catch (Exception ex) {
			logger.error("delHSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return count;
	}

	/**
	 * 判断key是否存在
	 *
	 * @param domain
	 *            域名
	 * @param key
	 *            键值
	 * @return
	 */
	public boolean existsHSet(String domain, String key) {
		ShardedJedis shardedJedis = null;
		boolean isExist = false;
		try {
			shardedJedis = shardedJedisPool.getResource();
			isExist = shardedJedis.hexists(domain, key);
		} catch (Exception ex) {
			logger.error("existsHSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return isExist;
	}

	/**
	 * 返回 domain 指定的哈希集中所有字段的value值
	 *
	 * @param domain
	 * @return
	 */

	public List<String> hvals(String domain) {
		ShardedJedis shardedJedis = null;
		List<String> retList = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			retList = shardedJedis.hvals(domain);
		} catch (Exception ex) {
			logger.error("hvals error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return retList;
	}

	/**
	 * 返回 domain 指定的哈希集中所有字段的key值
	 *
	 * @param domain
	 * @return
	 */

	public Set<String> hkeys(String domain) {
		ShardedJedis shardedJedis = null;
		Set<String> retList = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			retList = shardedJedis.hkeys(domain);
		} catch (Exception ex) {
			logger.error("hkeys error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return retList;
	}

	/**
	 * 返回 domain 指定的哈希key值总数
	 *
	 * @param domain
	 * @return
	 */
	public long lenHset(String domain) {
		ShardedJedis shardedJedis = null;
		long retList = 0;
		try {
			shardedJedis = shardedJedisPool.getResource();
			retList = shardedJedis.hlen(domain);
		} catch (Exception ex) {
			logger.error("hkeys error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return retList;
	}

	/**
	 * 设置排序集合
	 *
	 * @param key
	 * @param score
	 * @param value
	 * @return
	 */
	public boolean setSortedSet(String key, long score, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.zadd(key, score, value);
			return true;
		} catch (Exception ex) {
			logger.error("setSortedSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return false;
	}

	/**
	 * 获得排序集合
	 *
	 * @param key
	 * @param startScore
	 * @param endScore
	 * @param orderByDesc
	 * @return
	 */
	public Set<String> getSoredSet(String key, long startScore, long endScore, boolean orderByDesc) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			if (orderByDesc) {
				return shardedJedis.zrevrangeByScore(key, endScore, startScore);
			} else {
				return shardedJedis.zrangeByScore(key, startScore, endScore);
			}
		} catch (Exception ex) {
			logger.error("getSoredSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return null;
	}

	/**
	 * 计算排序长度
	 *
	 * @param key
	 * @param startScore
	 * @param endScore
	 * @return
	 */
	public long countSoredSet(String key, long startScore, long endScore) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			Long count = shardedJedis.zcount(key, startScore, endScore);
			return count == null ? 0L : count;
		} catch (Exception ex) {
			logger.error("countSoredSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return 0L;
	}

	/**
	 * 删除排序集合
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean delSortedSet(String key, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			long count = shardedJedis.zrem(key, value);
			return count > 0;
		} catch (Exception ex) {
			logger.error("delSortedSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return false;
	}

	/**
	 * 获得排序集合
	 *
	 * @param key
	 * @param startRange
	 * @param endRange
	 * @param orderByDesc
	 * @return
	 */
	public Set<String> getSoredSetByRange(String key, int startRange, int endRange, boolean orderByDesc) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			if (orderByDesc) {
				return shardedJedis.zrevrange(key, startRange, endRange);
			} else {
				return shardedJedis.zrange(key, startRange, endRange);
			}
		} catch (Exception ex) {
			logger.error("getSoredSetByRange error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return null;
	}

	/**
	 * 获得排序打分
	 *
	 * @param key
	 * @return
	 */
	public Double getScore(String key, String member) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.zscore(key, member);
		} catch (Exception ex) {
			logger.error("getSoredSet error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return null;
	}

	public boolean set(String key, String value, int second) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.set(key, value);
			shardedJedis.expire(key, second);

			// shardedJedis.setex(key, second, value);
			// shardedJedis.expire(key, second);
			return true;
		} catch (Exception ex) {
			logger.error("set error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return false;
	}

	public boolean set(String key, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.set(key, value);
			return true;
		} catch (Exception ex) {
			logger.error("set error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return false;
	}

	public String get(String key, String defaultValue) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.get(key) == null ? defaultValue : shardedJedis.get(key);
		} catch (Exception ex) {
			logger.error("get error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return defaultValue;
	}

	public boolean del(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.del(key);
			return true;
		} catch (Exception ex) {
			logger.error("del error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return false;
	}

	public long incr(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.incr(key);
		} catch (Exception ex) {
			logger.error("incr error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return 0;
	}

	public long decr(String key) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.decr(key);
		} catch (Exception ex) {
			logger.error("incr error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return 0;
	}

	private void returnBrokenResource(ShardedJedis shardedJedis) {
		try {
			shardedJedis.close();
		} catch (Exception e) {
			logger.error("returnBrokenResource error.", e);
		}
	}

	/**
	 * @author Alex 加入域内多值存取
	 */
	public boolean hmset(String key, Map<String, String> hash) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			shardedJedis.hmset(key, hash);
			return true;
		} catch (Exception ex) {
			logger.error("set error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return false;
	}

	public List<String> hmget(String key, String... fields) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.hmget(key, fields);
		} catch (Exception ex) {
			logger.error("get error.", ex);
			returnBrokenResource(shardedJedis);
		} finally {
			shardedJedis.close();
		}
		return new ArrayList<String>();
	}

}
