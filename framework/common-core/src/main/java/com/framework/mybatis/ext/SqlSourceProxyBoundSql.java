package com.framework.mybatis.ext;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 扩展兼容分页
 */
public class SqlSourceProxyBoundSql implements SqlSource {

	private final BoundSql boundSql;

	public SqlSourceProxyBoundSql(BoundSql boundSql) {
		this.boundSql = boundSql;
	}

	@Override
	public BoundSql getBoundSql(Object parameterObject) {
		return boundSql;
	}

}
