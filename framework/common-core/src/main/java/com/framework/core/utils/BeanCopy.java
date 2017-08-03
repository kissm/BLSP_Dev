package com.framework.core.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

public abstract class  BeanCopy extends org.springframework.beans.BeanUtils {

	public static void copyProperties(Object source, Object target)
			throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(
						source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass()
								.getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
						if (value != null) {
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod
									.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
					} catch (Throwable ex) {
						throw new FatalBeanException(
								"Could not copy properties from source to target",
								ex);
					}
				}
			}
		}
	}


	public static <T extends Object, V extends Object> void copyPropertiesForList(List<V> source,
			List<T> target, Class<T> targetClass) throws BeansException{
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		if (source.size() == 0) {
			return;
		}
		PropertyDescriptor[] targetPds = getPropertyDescriptors(targetClass);

		try {
		for (Object src : source) {
			T bean = targetClass.newInstance();

			for (PropertyDescriptor targetPd : targetPds) {
				if (targetPd.getWriteMethod() != null) {
					PropertyDescriptor sourcePd = getPropertyDescriptor(
							src.getClass(), targetPd.getName());
					if (sourcePd != null && sourcePd.getReadMethod() != null) {
							Method readMethod = sourcePd.getReadMethod();
							if (!Modifier.isPublic(readMethod
									.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(src);
							// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
							if (value != null) {
								Method writeMethod = targetPd.getWriteMethod();
								if (!Modifier.isPublic(writeMethod
										.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}
								writeMethod.invoke(bean, value);
							}
						
					}
				}
			}
			target.add(bean);
		}
		} catch (Throwable ex) {
			throw new FatalBeanException(
					"Could not copy properties from source to target",
					ex);
		}
	}
	
	public static void copyPropertiesWithBlank2Null(Object source, Object target)
			throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(
						source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass()
								.getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
						
						if (value != null && ((value instanceof String) && !"".equals(value.toString().trim()) ) || !(value instanceof String)) {
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod
									.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
					} catch (Throwable ex) {
						throw new FatalBeanException(
								"Could not copy properties from source to target",
								ex);
					}
				}
			}
		}
	}

	public static <T extends Object, V extends Object> void copyPropertiesForListWithBlank2Null(List<V> source,
			List<T> target, Class<T> targetClass) throws BeansException{
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		if (source.size() == 0) {
			return;
		}
		PropertyDescriptor[] targetPds = getPropertyDescriptors(targetClass);

		try {
		for (Object src : source) {
			T bean = targetClass.newInstance();

			for (PropertyDescriptor targetPd : targetPds) {
				if (targetPd.getWriteMethod() != null) {
					PropertyDescriptor sourcePd = getPropertyDescriptor(
							src.getClass(), targetPd.getName());
					if (sourcePd != null && sourcePd.getReadMethod() != null) {
							Method readMethod = sourcePd.getReadMethod();
							if (!Modifier.isPublic(readMethod
									.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(src);
							// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
							if (value != null && ((value instanceof String) && !"".equals(value.toString().trim()) ) || !(value instanceof String)) {
								Method writeMethod = targetPd.getWriteMethod();
								if (!Modifier.isPublic(writeMethod
										.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}
								writeMethod.invoke(bean, value);
							}
						
					}
				}
			}
			target.add(bean);
		}
		} catch (Throwable ex) {
			throw new FatalBeanException(
					"Could not copy properties from source to target",
					ex);
		}
	}
		
		
}