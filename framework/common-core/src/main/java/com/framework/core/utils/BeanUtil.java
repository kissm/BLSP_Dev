package com.framework.core.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

/**
 * Bean 工具类
 * 
 * @author hzh
 *
 */
public class BeanUtil {

	/**
	 * 对象与对象之间的属性赋值
	 * 
	 * @param <T>
	 * @param clazz
	 * @param convertObj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T convertObject(Class<T> clazz, Object convertObj) {
		T obj = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Class _clazz = convertObj.getClass();
			BeanInfo convertBeanInfo = Introspector.getBeanInfo(_clazz);
			PropertyDescriptor[] convertPropertyDescriptors = convertBeanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : convertPropertyDescriptors) {
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(convertObj, new Object[0]);
					if (result != null) {
						map.put(propertyName, result);
					}
				}
			}
			obj = clazz.newInstance();
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz); // 获取类属性
			// 给 JavaBean 对象的属性赋值
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				String propertyName = descriptor.getName();
				if (map.containsKey(propertyName)) {
					// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
					Object value = map.get(propertyName);
					Object[] args = new Object[1];
					args[0] = value;
					descriptor.getWriteMethod().invoke(obj, args);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}

	/**
	 * 对象与对象之间的属性赋值
	 * 
	 * @param <T>
	 * @param clazz
	 * @param convertObj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T convertObject(Class<T> clazz, Object convertObj, String[] cargs, String[] nargs) {
		T obj = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Class _clazz = convertObj.getClass();
			BeanInfo convertBeanInfo = Introspector.getBeanInfo(_clazz);
			PropertyDescriptor[] convertPropertyDescriptors = convertBeanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : convertPropertyDescriptors) {
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(convertObj, new Object[0]);
					if (result != null) {
						map.put(propertyName, result);
					}
				}
			}
			obj = clazz.newInstance();
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz); // 获取类属性
			// 给 JavaBean 对象的属性赋值
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				String propertyName = descriptor.getName();

				if (map.containsKey(propertyName)) {
					// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
					Object value = map.get(propertyName);
					Object[] args = new Object[1];
					args[0] = value;
					descriptor.getWriteMethod().invoke(obj, args);
				}

				if (cargs != null && nargs != null && cargs.length == nargs.length) {
					for (int j = 0; j < cargs.length; j++) {
						if (nargs[j].equals(propertyName)) {
							Object value = map.get(cargs[j]);
							Object[] args = new Object[1];
							args[0] = value;
							descriptor.getWriteMethod().invoke(obj, args);
							break;
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}

	/**
	 * 对象转换成map，目前暂不考虑复杂类型
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T> Map<String, Object> convertBean(T obj) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			Class clazz = obj.getClass();
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(obj, new Object[0]);
					if (result != null) {
						returnMap.put(propertyName, result);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	/**
	 * 对象转换成map，目前暂不考虑复杂类型
	 * 
	 * @param <T>
	 * @param obj
	 * @param oargs
	 *            原始字段名称
	 * @param nargs
	 *            新字段名称
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T> Map<String, Object> convertBean(T obj, String[] oargs, String[] nargs) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			Class clazz = obj.getClass();
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(obj, new Object[0]);
					if (result != null) {
						returnMap.put(propertyName, result);
					}
				}
			}
			if (oargs != null && nargs != null && oargs.length == nargs.length) {
				for (int i = 0; i < oargs.length; i++) {
					Object _obj = returnMap.get(oargs[i]);
					returnMap.remove(oargs[i]);
					if (_obj != null) {
						returnMap.put(nargs[i], _obj);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 *
	 * @param type
	 *            要转化的类型
	 * @param map
	 *            包含属性值的 map
	 * @return 转化出来的 JavaBean 对象
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T convertMap(Class<T> clazz, Map map) {
		T obj = null;
		try {
			obj = clazz.newInstance(); // 创建 JavaBean 对象
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz); // 获取类属性
			// 给 JavaBean 对象的属性赋值
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor descriptor : propertyDescriptors) {
				String propertyName = descriptor.getName();
				if (map.containsKey(propertyName)) {
					// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
					Object value = map.get(propertyName);
					Object[] args = new Object[1];
					args[0] = value;
					descriptor.getWriteMethod().invoke(obj, args);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static <T> List<Map<String, Object>> convertBeanList(List<T> objs) {
		List<Map<String, Object>> mapList = null;
		if (objs != null && objs.size() > 0) {
			mapList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < objs.size(); i++) {
				T obj = objs.get(i);
				Map<String, Object> map = convertBean(obj);
				mapList.add(map);
			}
		}
		return mapList;
	}

	public static <T> List<Map<String, Object>> convertBeanList(List<T> objs, String[] oargs, String[] nargs) {
		List<Map<String, Object>> mapList = null;
		if (objs != null && objs.size() > 0) {
			mapList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < objs.size(); i++) {
				T obj = objs.get(i);
				Map<String, Object> map = convertBean(obj, oargs, nargs);
				mapList.add(map);
			}
		}
		return mapList;
	}

	public static <T> List<Map<String, Object>> convertBeanArray(T[] array) {
		return convertBeanList(Arrays.asList(array));
	}

	public static <T> List<Map<String, Object>> convertBeanArray(T[] array, String[] oargs, String[] nargs) {
		return convertBeanList(Arrays.asList(array), oargs, nargs);
	}

	public static Map<String, Object> removeNULL(Map<String, Object> map) {
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			Object value = entry.getValue();
			if (value == null) {
				it.remove(); // OK
			}
		}
		return map;
	}

	/**
	 * 实体转换（因为不支持Date复制，重载该converter）
	 *
	 * @param src
	 * @param dest
	 */
	public static void copyBeanForDateToStr(Object src, Object dest) {
		// formbaen中的birthday是String类型的,但是我们需要转到userbean的是Date.这里用到org.apache.commons.beanutils.ConvetUtils类
		// 注册一个日期转换器
		ConvertUtils.register(new Converter() {
			@Override
			public Object convert(Class type, Object value) {
				if (value == null) {
					return null;
				}
				String str = (String) value;
				if ("".equals(str.trim())) {
					return null;
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					return df.parse(str);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		}, Date.class);
		try {
			BeanUtils.copyProperties(dest, src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
