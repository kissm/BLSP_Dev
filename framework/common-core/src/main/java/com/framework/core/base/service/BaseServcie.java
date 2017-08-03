/**
 * @Probject Name: monTest
 * @Path: org.spring.mongodb.exampleBaseServcie.java
 * @Create By wangfei
 * @Create In 2014年11月27日 下午2:27:06
 */
package com.framework.core.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @Class Name BaseServcie
 * @Author wangfei
 * @Create In 2014年11月27日
 */
public abstract class BaseServcie {

	/**
	 * spring mongodb 集成操作类
	 */
	@Autowired(required = false)
	protected MongoTemplate mongoTemplate;
}
