package com.framework.mybatis.ext;

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 扩展功能,mapper文件自动加载,空resultMap自动配置,mapper拦截
 */
public class SqlSessionFactoryBeanExt extends SqlSessionFactoryBean implements ApplicationContextAware {
	private static final Logger logger = LoggerFactory.getLogger(SqlSessionFactoryBeanExt.class);
	private ApplicationContext applicationContext;

	private final Class<?> superClass;

	private SqlSessionFactory sqlSessionFactory;

	public SqlSessionFactoryBeanExt() {
		superClass = SqlSessionFactoryBean.class;
	}

	public void setValue(String name, Object value) {
		try {
			Field field = superClass.getDeclaredField(name);
			field.setAccessible(true);
			field.set(this, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
		SqlSessionFactory factory = super.buildSqlSessionFactory();
		try {
			Configuration config = factory.getConfiguration();
			Class<?> classConfig = Configuration.class;
			// 拦截
			Field field = classConfig.getDeclaredField("mapperRegistry");
			field.setAccessible(true);
			field.set(config, new MapperRegistryExt(config, applicationContext));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return factory;
	}

	@Override
	public SqlSessionFactory getObject() throws Exception {
		if (sqlSessionFactory == null) {
			sqlSessionFactory = buildSqlSessionFactory();
			setValue("sqlSessionFactory", sqlSessionFactory);
		}
		return sqlSessionFactory;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
