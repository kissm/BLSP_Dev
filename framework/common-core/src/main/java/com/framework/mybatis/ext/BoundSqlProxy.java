package com.framework.mybatis.ext;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.core.base.page.Page;

/**
 * BoundSql代理
 */
public class BoundSqlProxy extends BoundSql {

	private static final Logger logger = LoggerFactory.getLogger(BoundSqlProxy.class);

	private final Page<?> page;

	private final BoundSql proxy;

	private final String dialect;

	public BoundSqlProxy(Configuration configuration, Page<?> page, BoundSql proxy) {

		super(configuration, proxy.getSql(), proxy.getParameterMappings(), proxy.getParameterObject());
		dialect = configuration.getVariables().getProperty("dialect");
		if (null == dialect || "".equals(dialect)) {
			logger.debug("Property dialect is not setted,use default 'mysql' ");
		}
		this.page = page;
		this.proxy = proxy;
	}

	@Override
	public String getSql() {

		String baseSql = proxy.getSql();

		StringBuilder sb = new StringBuilder();
		if ("mysql".equalsIgnoreCase(dialect)) {
			sb.append(baseSql);
			sb.append(" LIMIT ").append(page.getStartNo() - 1).append(",").append(page.getPageSize());
		} else if ("oracle".equalsIgnoreCase(dialect)) {
			sb.append("SELECT * FROM ( select temp.*, ROWNUM AS row_id FROM ( ");
			sb.append(baseSql);
			sb.append(" ) temp WHERE ROWNUM <= ").append(page.getStartNo() + page.getPageSize() - 1);
			sb.append(") WHERE row_id > ").append(page.getStartNo() - 1);
		} else {
			sb.append(baseSql);
			logger.error("未配置方言,数据未分页");
		}
		return sb.toString();

	}

	@Override
	public boolean hasAdditionalParameter(String name) {

		return proxy.hasAdditionalParameter(name);

	}

	@Override
	public Object getAdditionalParameter(String name) {

		return proxy.getAdditionalParameter(name);

	}

	@Override
	public void setAdditionalParameter(String name, Object value) {

		proxy.setAdditionalParameter(name, value);

	}
}
