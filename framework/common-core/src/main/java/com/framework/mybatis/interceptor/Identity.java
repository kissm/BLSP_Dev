package com.framework.mybatis.interceptor;

/**
 * 实体继承表示有主键id,且如果插入时为null, 则自动通过idworker获取id并赋值
 */
public abstract class Identity {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
