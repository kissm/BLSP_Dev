package com.lpcode.modules.service.impl.project.util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SempleDateUtil {
	static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 本年第一天
	 */
	public static Date getCurrYearFirst(Date time) {
		Calendar currCal = Calendar.getInstance();
		currCal.setTime(time);
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}
	/**
	 * 本年最后一天
	 */
	public static Date getCurrYearEnd(Date time) {
		Calendar currCal = Calendar.getInstance();
		currCal.setTime(time);
		int lastDay = currCal.getActualMaximum(Calendar.DAY_OF_YEAR);
		//设置日历一年中的最大天数
		currCal.set(Calendar.DAY_OF_YEAR, lastDay);
		Date end = new Date();
		if(end.after(currCal.getTime())){
			end = currCal.getTime();
		}
		return end;
	}
	/**
	 * 本月第一天
	 */
	public static Date getCurrMonthFirst(Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH);
		return getMonthFirst(currentYear,currentMonth);
	}
	/**
	 * 本月最后一天
	 */
	public static Date getCurrMonthEnd(Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		//设置日历中月份的最大天数
		calendar.set(Calendar.DAY_OF_MONTH, lastDay);
		return calendar.getTime();
	}

	/**
	 * 本季度第一天
	 */
	public static Date getCurrQuarterFirst(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3)
				c.set(Calendar.MONTH, 0);
			else if (currentMonth >= 4 && currentMonth <= 6)
				c.set(Calendar.MONTH, 3);
			else if (currentMonth >= 7 && currentMonth <= 9)
				c.set(Calendar.MONTH, 6);
			else if (currentMonth >= 10 && currentMonth <= 12)
				c.set(Calendar.MONTH, 9);
			c.set(Calendar.DATE, 1);
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}
	/**
	 * 本季度第一天
	 */
	public static Date getCurrQuarterEnd(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int currentMonth = c.get(Calendar.MONTH) + 1;
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3)
				c.set(Calendar.MONTH, 2);
			else if (currentMonth >= 4 && currentMonth <= 6)
				c.set(Calendar.MONTH, 5);
			else if (currentMonth >= 7 && currentMonth <= 9)
				c.set(Calendar.MONTH, 8);
			else if (currentMonth >= 10 && currentMonth <= 12)
				c.set(Calendar.MONTH, 11);
			int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			//设置日历中月份的最大天数
			c.set(Calendar.DAY_OF_MONTH, lastDay);
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}
	/**
	 * 本周第一天
	 */
	public static Date getCurrWeekFirst(Date time) {
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
//		System.out.println("要计算日期为:"+sdf.format(cal.getTime())); //输出要计算日期
		//判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
		if(1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
//		System.out.println("所在周星期一的日期："+sdf.format(cal.getTime()));
		return cal.getTime();

//		Calendar c = Calendar.getInstance();
//		try {
//			int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
//			c.add(Calendar.DATE, -weekday);
//			c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return c.getTime();
	}
	/**
	 * 本周最后一天
	 */
	public static Date getCurrWeekEnd(Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
		if(1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day+6);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		return cal.getTime();
	}
	/**
	 * 本天开始时间
	 */
	public static Date getCurrDayFirst(Date now) {
		try {
			now = shortSdf.parse(shortSdf.format(now));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}
	/**
	 * 本天开始时间
	 */
	public static Date getCurrDayFirstBegin(Date now) {
		try {
			now = longSdf.parse(shortSdf.format(now) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}
	/**
	 * 本天结束时间
	 */
	public static Date getCurrDayFirstEnd(Date now) {
		try {
			now = longSdf.parse(shortSdf.format(now) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	public static Date getMonthFirst(int year,int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	public static void main(String[] str) {
//		System.out.println(longSdf.format(DateUtil.getCurrMonthFirst()));
		//System.out.println(longSdf.format(DateUtil.getCurrQuarterFirst()));
	}
}
