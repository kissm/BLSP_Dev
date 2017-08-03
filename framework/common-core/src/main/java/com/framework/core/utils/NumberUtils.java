/**
 * @Probject Name: common-core
 * @Path: com.bailian.core.utilsNumberUtils.java
 * @Create By fanshunqing
 * @Create In 2014年10月22日 下午6:48:12
 */
package com.framework.core.utils;

import java.util.regex.Pattern;

/**
 * @Class Name NumberUtils
 * @Author fanshunqing
 * @Create In 2014年10月22日
 */
public class NumberUtils {
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
}
