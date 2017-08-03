package com.framework.core.cache;

import java.util.List;

/**
 * 缓存操作对象 <BR>
 * 1.List,Set集合,如果集合中内容发生变更(包括item新增,修改,删除),需整体删除,然后从新整体添加,禁止单条记录的SADD,否则会引起线程安全问题.
 * 
 * @Class Name CacheVo
 * @Author guowenqin
 * @Create In 2014年11月11日
 */
public class RedisVo implements CacheVo {

	private int type;

	private String key;

	private String field;

	private String value;

	/**
	 * 存放集合item
	 */
	private List<String> values;

	public RedisVo() {
	}

	public RedisVo(int type, String key) {
		this.type = type;
		this.key = key;
	}

	public RedisVo(int type, String key, String field) {
		this.type = type;
		this.key = key;
		this.field = field;
	}

	public RedisVo(int type, String key, String field, String value) {
		this.type = type;
		this.key = key;
		this.field = field;
		this.value = value;
	}

	/**
	 * 用于构建集合操作bean
	 * 
	 * @param type
	 * @param field
	 * @param key
	 * @param values
	 */
	public RedisVo(int type, String key, List<String> values) {
		this.type = type;
		this.key = key;
		this.values = values;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

}
