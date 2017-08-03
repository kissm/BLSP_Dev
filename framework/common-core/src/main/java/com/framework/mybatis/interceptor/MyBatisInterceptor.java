package com.framework.mybatis.interceptor;

public interface MyBatisInterceptor {
	public Object invoke(MyBatisInvocation handler) throws Throwable;
}
