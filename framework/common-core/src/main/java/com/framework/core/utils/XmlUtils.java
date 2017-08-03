package com.framework.core.utils;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.BigDecimalConverter;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.converters.basic.DoubleConverter;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.LongConverter;
import com.thoughtworks.xstream.converters.basic.StringConverter;

/**
 * 
 * @author wangchaochao
 * 
 */
public class XmlUtils {

	/**
	 * 
	 * @param claz
	 * @param xmlMessage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertXml2JavaBean(Class<T> claz, String xmlMessage) {
		XStream xStream = new XStream();
		xStream.processAnnotations(claz);
		xStream.registerConverter(new DateConverter("yyyy-MM-dd HH:mm:ss",
				null, TimeZone.getTimeZone("GMT+8")));
		xStream.registerConverter(new DateConverter("yyyy-MM-ddTHH:mm:ssZ",
						null, TimeZone.getTimeZone("GMT+8")));
		xStream.registerConverter(new StringConverter() {

			@Override
			public String fromString(String str) {
				if (StringUtils.isEmpty(str)) {
					return null;
				}
				return super.fromString(str).toString().trim();
			}
		});
		// 下面是转换常用的数字类型
		xStream.registerConverter(new DoubleConverter() {

			@Override
			public Object fromString(String str) {
				if (StringUtils.isEmpty(str)) {
					return null;
				}
				return super.fromString(str);
			}
		});
		xStream.registerConverter(new LongConverter() {

			@Override
			public Object fromString(String str) {
				if (StringUtils.isEmpty(str)) {
					return null;
				}
				return super.fromString(str);
			}
		});
		xStream.registerConverter(new IntConverter() {

			@Override
			public Object fromString(String str) {
				if (StringUtils.isEmpty(str)) {
					return null;
				}
				if("FALSE".equals(str)){
					return 0;
				}
				if("TRUE".equals(str)){
					return 1;
				}
				return super.fromString(str);
			}
		});
		xStream.registerConverter(new BigDecimalConverter() {

			@Override
			public Object fromString(String str) {
				if (StringUtils.isEmpty(str)) {
					return null;
				}
				return super.fromString(str);
			}
		});
		T t = (T) xStream.fromXML(xmlMessage);
		return t;
	}

	/**
	 * 
	 * @param claz
	 * @param xmlMessage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertXml2JavaBean(Class<T> claz, String xmlMessage,
			String dataFormat) {
		XStream xStream = new XStream();
		xStream.processAnnotations(claz);
		xStream.registerConverter(new DateConverter(dataFormat, null));
		T t = (T) xStream.fromXML(xmlMessage);
		return t;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String bean2xml(Map<String, Class> clazzMap, Object bean) {
		XStream xStream = new XStream();
		for (Iterator it = clazzMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Class> m = (Map.Entry<String, Class>) it.next();
			xStream.alias(m.getKey(), m.getValue());
		}
		String xml = xStream.toXML(bean);
		return xml;
	}

	public static String bean2xml(Object bean) {
		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);
		String xml = xStream.toXML(bean);
		return xml;
	}

	public static void main(String[] args) throws Exception {
		String time = "2015-01-12T15:17:02+08:00";
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		System.out.println(date.parse(time));
	}
}
