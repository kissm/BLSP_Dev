package com.framework.mybatis.interceptor;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;






//import com.frame.mybatis.ext.MapperProxyExt;
//import com.frame.mybatis.interceptor.MyBatisInvocation;
import com.framework.mybatis.annotation.Batch;
import com.framework.mybatis.ext.MapperProxyExt;

/**
 * 批量处理，如果遇见Batch注解 批量查询,批量更新 参数是list或数组
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class BatchInterceptor implements Interceptor {

	
	public Object intercept(Invocation inv) throws Throwable {
		MyBatisInvocation invocation = MapperProxyExt.getMyBatisInvocation();
		if(invocation == null) return inv.proceed();
		Batch batch = invocation.getMethod().getAnnotation(Batch.class);
		
		if (batch != null) {
			Object[] args = invocation.getArgs();
			Iterator<Object> iterator = getIterator(args);
			if (iterator == null) {
				return inv.proceed();
			}
			// 对list进行批量处理
			if ("update".equals(inv.getMethod().getName())) {
				return update(inv, iterator);
			} else {
				// 查询
				return query(inv, iterator);
			}
		}
		return inv.proceed();

	}

	private Iterator<Object> getIterator(Object[] args) {
		Object parameter = null;
		if (args != null && args.length == 1) {
			parameter = args[0];
		}
		if (parameter != null && parameter instanceof List) {
			return new IteratorImpl<Object>((List<?>) parameter);
		} else if (parameter != null && parameter.getClass().isArray()) {
			return new IteratorImpl<Object>(parameter);
		}
		return null;
	}

	private int update(Invocation invocation, Iterator<Object> iter) throws Throwable {
		Object statment = invocation.getArgs()[0];
		Executor target = (Executor) invocation.getTarget();
		Object[] param = new Object[] { statment, null };
		Method method = invocation.getMethod();
		int count = 0;
		while (iter.hasNext()) {
			count++;
			//inv.batchIndexIncrease();
			param[1] = iter.next();
			method.invoke(target, param);
			if (count % 100 == 0) {
				target.flushStatements();
			}

		}
		target.flushStatements();
		return count;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Object> query(Invocation invocation, Iterator<Object> iter) throws Throwable {
		Object statment = invocation.getArgs()[0];
		Object rowBounds = invocation.getArgs()[2];
		Object resultHandler = invocation.getArgs()[3];
		Object target = invocation.getTarget();
		Object[] param = new Object[] { statment, null, rowBounds, resultHandler };
		Method method = invocation.getMethod();
		List<Object> resultList = new ArrayList<Object>();
		while (iter.hasNext()) {
			//inv.batchIndexIncrease();
			param[1] = iter.next();
			Object result = method.invoke(target, param);
			if (result instanceof Collection) {
				resultList.addAll((Collection) result);
			} else {
				resultList.add(result);

			}
		}
		return resultList;
	}
	

	 

	
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	
	public void setProperties(Properties properties) {
	}

	private class IteratorImpl<E> implements Iterator<Object> {
		private boolean isArray = false;
		private Object data;
		private int index = 0;
		private int size = 0;

		public IteratorImpl(List<?> list) {
			data = list;
			size = list.size();
		}

		public IteratorImpl(Object array) {
			isArray = true;
			data = array;
			size = Array.getLength(data);
		}

		
		public boolean hasNext() {
			return index < size;

		}

		
		public Object next() {
			if (isArray) {
				Object obj = Array.get(data, index);
				index++;
				return obj;
			} else {
				List<?> list = (List<?>) data;
				Object obj = list.get(index);
				index++;
				return obj;
			}
		}

		
		public void remove() {
			throw new RuntimeException("not support");
		}

	}
}