package com.framework.core.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtil {

	/**
	 * 字符串转java.sql.Date
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static java.sql.Date formatToDate(String date, String format) {
		java.sql.Date sqlDate = null;
		try {
			if (date != null && date.length() > 0) {
				java.util.Date _date = new SimpleDateFormat(format).parse(date);
				sqlDate = new java.sql.Date(_date.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sqlDate;
	}

	/**
	 * TimeStamp转字符串(ymd)
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatTimeStampToString(Timestamp date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		String str = df.format(date);
		return str;
	}

	/**
	 * 获取当前timestamp(仅精确到日期)
	 *
	 * @param date
	 * @return
	 */
	public static Timestamp getCurrentTimeStampYMD() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String time = df.format(new Date());
		Timestamp ts = Timestamp.valueOf(time);
		return ts;
	}

	/**
	 * 字符串转java.sql.Timestamp
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static java.sql.Timestamp formatToTimestamp(String date, String format) {
		java.sql.Timestamp timestamp = null;
		try {
			if (date != null && date.length() > 0) {
				java.util.Date _date = new SimpleDateFormat(format).parse(date);
				timestamp = new java.sql.Timestamp(_date.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	public static String formatToStr(java.sql.Date date, String format) {
		String dateStr = null;
		if (date != null) {
			dateStr = new SimpleDateFormat(format).format(date);
		}
		return dateStr;
	}

	public static String formatToStr(Date date, String format) {
		String dateStr = null;
		if (date != null) {
			dateStr = new SimpleDateFormat(format).format(date);
		}
		return dateStr;
	}

	public static String formatToStr(java.sql.Timestamp date, String format) {
		String dateStr = null;
		if (date != null) {
			dateStr = new SimpleDateFormat(format).format(date);
		}
		return dateStr;
	}

	// 日期格式字符串格式化
	public static String formatDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		if (StringUtils.isNotEmpty(time) && !"null".equalsIgnoreCase(time)) {
			try {
				date = sdf.parse(time);
				time = sdf.format(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return time;
	}

	// 日期格式字符串格式化
	public static String formatDateTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		if (StringUtils.isNotEmpty(time) && !"null".equalsIgnoreCase(time)) {
			try {
				date = sdf.parse(time);
				time = sdf.format(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return time;
	}

	// 计算日期相隔天数
	public static long dateDiff(String startTime, String endTime, String format) throws ParseException {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数long diff;try {
		// 获得两个时间的毫秒时间差异
		long diff = sd.parse(startTime).getTime() - sd.parse(endTime).getTime();
		long day = diff / nd;// 计算差多少天
		long hour = diff % nd / nh;// 计算差多少小时
		long min = diff % nd % nh / nm;// 计算差多少分钟
		long sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果
		System.out.println("时间相差：" + day + "天" + hour + "小时" + min + "分钟" + sec + "秒。");
		return day;
	}

	// 日期转化为字符串
	public static String getStrFromDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(date);
		return str;
	}

	/**
	 * 获得指定日期的前后日期 t为正表示后 负表示前
	 *
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static Date getSpecifiedDay(Date date, int t) {
		if (date == null) {
			return null;
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, t);
		return c.getTime();
	}

	/**
	 * 获取指定时间的那天 23:59:59.999 的时间
	 *
	 * @param date
	 * @return
	 */
	public static Date dayEnd(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

}
