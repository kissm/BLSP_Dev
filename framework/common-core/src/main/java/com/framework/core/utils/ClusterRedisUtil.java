package com.framework.core.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import redis.clients.jedis.JedisCluster;

public class ClusterRedisUtil extends RedisUtil {
	@Resource
	private JedisCluster jedisCluster;

	/**
	 * 设置一个key的过期时间（单位：秒）
	 * 
	 * @param key
	 *            key值
	 * @param seconds
	 *            多少秒后过期
	 * @return 1：设置了过期时间 0：没有设置过期时间/不能设置过期时间
	 */
	@Override
	public long expire(String key, int seconds) {
		if (key == null || key.equals("")) {
			return 0;
		}

		return jedisCluster.expire(key, seconds);
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
	@Override
	public long expireAt(String key, int unixTimestamp) {
		if (key == null || key.equals("")) {
			return 0;
		}

		return jedisCluster.expireAt(key, unixTimestamp);
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
	@Override
	public String trimList(String key, long start, long end) {
		if (key == null || key.equals("")) {
			return "-";
		}
		return jedisCluster.ltrim(key, start, end);
	}

	/**
	 * 检查Set长度
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public long countSet(String key) {
		if (key == null) {
			return 0;
		}
		return jedisCluster.scard(key);
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
	@Override
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
	@Override
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
	@Override
	public boolean addSet(String key, List<String> values) {
		if (key == null || values == null || values.size() <= 0) {
			return false;
		}
		Long ret = jedisCluster.sadd(key, values.toArray(new String[0]));
		if (ret > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 添加到Set中
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public boolean addSet(String key, String value) {
		if (key == null || value == null) {
			return false;
		}
		Long ret = jedisCluster.sadd(key, value);
		if (ret > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param key
	 * @param value
	 * @return 判断值是否包含在set中
	 */
	@Override
	public boolean containsInSet(String key, String value) {
		if (key == null || value == null) {
			return false;
		}
		return jedisCluster.sismember(key, value);
	}

	/**
	 * 获取Set
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public Set<String> getSet(String key) {
		return jedisCluster.smembers(key);
	}

	/**
	 * 从set中删除value
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public boolean removeSetValue(String key, String value) {
		Long ret = jedisCluster.srem(key, value);
		if (ret > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 从list中删除value 默认count 1
	 * 
	 * @param key
	 * @param values
	 *            值list
	 * @return
	 */
	@Override
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
	@Override
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
	@Override
	public boolean removeListValue(String key, long count, String value) {
		Long ret = jedisCluster.lrem(key, count, value);
		if (ret > 0) {
			return true;
		} else {
			return false;
		}
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
	@Override
	public List<String> rangeList(String key, long start, long end) {
		if (key == null || key.equals("")) {
			return null;
		}
		return jedisCluster.lrange(key, start, end);
	}

	/**
	 * 检查List长度
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public long countList(String key) {
		if (key == null) {
			return 0;
		}
		return jedisCluster.llen(key);
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
	@Override
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
	@Override
	public boolean addList(String key, String value) {
		if (key == null || value == null) {
			return false;
		}
		Long ret = jedisCluster.lpush(key, value);
		if (ret > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 添加到List(只新增)
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public boolean addList(String key, List<String> list) {
		if (key == null || list == null || list.size() == 0) {
			return false;
		}
		Long ret = jedisCluster.lpush(key, list.toArray(new String[0]));
		if (ret > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取List
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public List<String> getList(String key) {
		return jedisCluster.lrange(key, 0, -1);
	}

	/**
	 * 获取List
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public List<String> getList(String key, int start, int end) {
		return jedisCluster.lrange(key, start, end);
	}

	/**
	 * 获取List
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public Long getListLength(String key) {
		return jedisCluster.llen(key);
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
	@Override
	public boolean setHSet(String domain, String key, String value) {
		if (value == null) {
			return false;
		}
		Long ret = jedisCluster.hset(domain, key, value);
		if (ret > 0) {
			return true;
		} else {
			return false;
		}
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
	@Override
	public String getHSet(String domain, String key) {
		return jedisCluster.hget(domain, key);
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
	@Override
	public long delHSet(String domain, String key) {
		return jedisCluster.hdel(domain, key);
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
	@Override
	public boolean existsHSet(String domain, String key) {
		return jedisCluster.hexists(domain, key);
	}

	/**
	 * 返回 domain 指定的哈希集中所有字段的value值
	 *
	 * @param domain
	 * @return
	 */

	@Override
	public List<String> hvals(String domain) {
		return jedisCluster.hvals(domain);
	}

	/**
	 * 返回 domain 指定的哈希集中所有字段的key值
	 *
	 * @param domain
	 * @return
	 */

	@Override
	public Set<String> hkeys(String domain) {
		return jedisCluster.hkeys(domain);
	}

	/**
	 * 返回 domain 指定的哈希key值总数
	 *
	 * @param domain
	 * @return
	 */
	@Override
	public long lenHset(String domain) {
		return jedisCluster.hlen(domain);
	}

	/**
	 * 设置排序集合
	 *
	 * @param key
	 * @param score
	 * @param value
	 * @return
	 */
	@Override
	public boolean setSortedSet(String key, long score, String value) {
		Long ret = jedisCluster.zadd(key, score, value);
		if (ret > 0) {
			return true;
		} else {
			return false;
		}
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
	@Override
	public Set<String> getSoredSet(String key, long startScore, long endScore, boolean orderByDesc) {
		if (orderByDesc) {
			return jedisCluster.zrevrangeByScore(key, endScore, startScore);
		} else {
			return jedisCluster.zrangeByScore(key, startScore, endScore);
		}
	}

	/**
	 * 计算排序长度
	 *
	 * @param key
	 * @param startScore
	 * @param endScore
	 * @return
	 */
	@Override
	public long countSoredSet(String key, long startScore, long endScore) {
		return jedisCluster.zcount(key, startScore, endScore);
	}

	/**
	 * 删除排序集合
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public boolean delSortedSet(String key, String value) {
		return jedisCluster.zrem(key, value) > 0;
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
	@Override
	public Set<String> getSoredSetByRange(String key, int startRange, int endRange, boolean orderByDesc) {
		if (orderByDesc) {
			return jedisCluster.zrevrange(key, startRange, endRange);
		} else {
			return jedisCluster.zrange(key, startRange, endRange);
		}
	}

	/**
	 * 获得排序打分
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Double getScore(String key, String member) {
		return jedisCluster.zscore(key, member);
	}

	@Override
	public boolean set(String key, String value, int second) {
		jedisCluster.set(key, value);
		jedisCluster.expire(key, second);
		return true;
	}

	@Override
	public boolean set(String key, String value) {
		jedisCluster.set(key, value);
		return true;
	}

	@Override
	public String get(String key, String defaultValue) {
		return jedisCluster.get(key) == null ? defaultValue : jedisCluster.get(key);
	}

	@Override
	public boolean del(String key) {
		jedisCluster.del(key);
		return true;
	}

	@Override
	public long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public long decr(String key) {
		return jedisCluster.decr(key);
	}

	// private void returnBrokenResource(ShardedJedis shardedJedis) {
	// try {
	// shardedJedisPool.returnBrokenResource(shardedJedis);
	// } catch (Exception e) {
	// logger.error("returnBrokenResource error.", e);
	// }
	// }
	//
	// private void returnResource(ShardedJedis shardedJedis) {
	// try {
	// shardedJedisPool.returnResource(shardedJedis);
	// } catch (Exception e) {
	// logger.error("returnResource error.", e);
	// }
	// }

	/**
	 * @author Alex 加入域内多值存取
	 */
	@Override
	public boolean hmset(String key, Map<String, String> hash) {
		jedisCluster.hmset(key, hash);
		return true;
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		return jedisCluster.hmget(key, fields);
	}

}
