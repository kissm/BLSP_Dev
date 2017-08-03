package com.framework.core.base.persistence;

import java.util.List;
import java.util.Map;

/**
 * 基本数据操作接口
 * 
 * @Class Name BaseMapper
 * @Author wangfei
 * @Create In 2014年10月23日
 * @param <T>
 */
public interface BaseMapper<T> {

	Integer insert(T entity);

	Integer update(T entity);
	
	Integer updateByParam(T entity);

	T get(Long sid);
	
	T getByParam(Map<String, Object> paramMap);

	List<T> selectListByParam(Map<String, Object> paramMap);

	List<T> selectPageListByParam(Map<String, Object> paramMap);

	Integer getCountByParam(Map<String, Object> paramMap);

	Integer delete(Long sid);

	Integer deleteByParam(Map<String, Object> paramMap);
	
	Long getSequense();
}
