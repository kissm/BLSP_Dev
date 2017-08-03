package com.lpcode.modules.service.impl.dimension.holiday;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.core.constants.BaseCode;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.utils.BeanCopy;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.blsp.entity.DimHoliday;
import com.lpcode.modules.blsp.entity.DimHolidayExample;
import com.lpcode.modules.blsp.mapper.DimHolidayMapper;
import com.lpcode.modules.dto.dimension.holiday.DimHolidayDto;
import com.lpcode.modules.service.dimension.holiday.DimHolidayService;

@Service
public class DimHolidayServiceImpl implements DimHolidayService {

	@Autowired
	DimHolidayMapper dimHolidayMapper;

	private static SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMdd");

	private static final List<String> holidays = new ArrayList<String>();

	private static long timer = -1;

	private static long interval = 10 * 60 * 1000; // 10分钟

	/**
	 * 初始化最新的节假日信息
	 */
	private void initLatelyHolidays() {
		long currentTimer = System.currentTimeMillis();
		// if(timer == -1){
		// timer = currentTimer;
		// }
		if (currentTimer - timer > interval) {
			holidays.clear();
			List<DimHolidayDto> list = findAllHolidays();
			if (list != null && list.size() > 0) {
				for (DimHolidayDto dto : list) {
					holidays.addAll(Arrays.asList(dto.getHolidays().split(",")));
				}
			}
			timer = currentTimer;
		}

	}

	/***
	 * 根据主键（年份，例如2016）查询日历对象
	 */
	@Override
	public DimHolidayDto findByPrimaryKey(Integer year) {
		DimHolidayDto dto = null;
		DimHolidayExample example = new DimHolidayExample();
		DimHolidayExample.Criteria cti =  example.createCriteria();
		cti.andYearEqualTo(year);
		List<DimHoliday> list = dimHolidayMapper.selectByExampleWithBLOBs(example);
		if(list!=null && list.size()==1){
			dto = new DimHolidayDto();
			BeanCopy.copyProperties(list.get(0), dto);
		}
		return dto;
	}

	@Override
	public void save(DimHolidayDto dto) {
		DimHoliday record = new DimHoliday();
		BeanCopy.copyProperties(dto, record);
		DimHolidayExample example = new DimHolidayExample();
		example.createCriteria().andYearEqualTo(dto.getYear());
		dimHolidayMapper.deleteByExample(example);
		dimHolidayMapper.insert(record);
	}

	/**
	 * 计算从开始时间（startDate）到结束时间（endDate）共有多少天（dayType为计算的时间类型，1：自然日，2：工作日）
	 *
	 * 假定 2016/01/15 2016/01/16 为节假日 例如 startDate=2016/01/14，endDate=2016/01/24
	 * dayType=1, 返回结果10 例如 startDate=2016/01/14，endDate=2016/01/24 dayType=2,
	 * 返回结果8
	 *
	 *
	 * * TODO 计算时包含startDate当天
	 */
	@Override
	public int calDatePeriod(Date startDate, Date endDate, String dayType) {

		if (startDate == null || endDate == null || dayType == null || Integer.parseInt(dayType) < 1
				|| Integer.parseInt(dayType) > 2) {
			return 0;
		}

		/** 判断开始时间是否大于结束时间，如果开始时间大于结束时间，将时间反转 start **/
		Integer sd = Integer.parseInt(sdf.format(startDate));
		Integer ed = Integer.parseInt(sdf.format(endDate));
		int i = 1;
		if (sd > ed) {
			Date temp = startDate;
			startDate = endDate;
			endDate = temp;
			i = -1;
		}

		/** 判断开始时间是否大于结束时间，如果开始时间大于结束时间，将时间反转 end **/

		/** 初始化所有工作日历 start **/
		initLatelyHolidays();
		/** 初始化所有工作日历 end **/

		DateTime dt1 = null;
		DateTime dt2 = null;
		try {
			dt1 = new DateTime(sdf.parse(sdf.format(startDate)));
			dt2 = new DateTime(sdf.parse(sdf.format(endDate)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int retVal = 0;
		// 判断当前年是否有2月29日，如果有在加1
		int natureDays = Days.daysBetween(dt1, dt2).getDays();
		// 判断当前年是否有2月29日，如果有在加1
		if (DicConstants.TASK_DEFINE_DIM_TYPE_CALENDARDAY.equals(dayType)) {
			retVal = natureDays;
		} else {
			int holidayCounter = 0;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			while (!sdf.format(endDate).equals(sdf.format(calendar.getTime()))) {
				String tmpStr = sdf.format(calendar.getTime());
				if (holidays.contains(tmpStr)) {
					holidayCounter++;
				}
				calendar.add(Calendar.DATE, 1);
			}
			retVal = natureDays - holidayCounter;
		}
		return i * (retVal + 1);
	}

	/**
	 * 查找工作日方法，从传入时间（date）开始，计算间隔amount天的工作日是哪天） 例如 date=2016/01/24 amount=3, 假定
	 * 2016/01/25 2016/01/26 为节假日 最终返回结果为 2016/01/29
	 *
	 * TODO 从传入日期之后的一天开始算，例如上例 从 1月25开始算，因为 25，26为节假日，所以最终结果为从27开始加三天，最终为29
	 */
	@Override
	public Date findWorkDay(Date date, int amount) {

		System.out.println(sdf.format(date));
		/** 初始化所有工作日历 start **/
		initLatelyHolidays();
		/** 初始化所有工作日历 end **/

		if (date == null) {
			return null;
		}
		int i=1;
		if (amount <= 0) {
			i=-1;
		}
		Date resultDate = null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int loop = 0;
		do {
			resultDate = c.getTime();
			String tmpStr = sdf.format(resultDate);
			if (!holidays.contains(tmpStr)) {// 不是节假日
				loop++;
			}
			c.add(Calendar.DAY_OF_YEAR, i*1);
		} while (loop < i*amount);
		return resultDate;
	}
	
	
	@Override
	public Date findNextWorkDay() {
		Date resultDate = null;
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int loop = 0;
		do {
			c.add(Calendar.DAY_OF_YEAR,1);
			resultDate = c.getTime();
			String tmpStr = sdf.format(resultDate);
			if (!holidays.contains(tmpStr)) {// 不是节假日
				loop++;
			}
			
		} while (loop < 1);
		return resultDate;
	}

	private List<DimHolidayDto> findHolidays(Integer... yearPeriods) {
		DimHolidayExample example = new DimHolidayExample();
		example.createCriteria().andYearIn(Arrays.asList(yearPeriods));
		List<DimHolidayDto> dtos = null;
		List<DimHoliday> holidays = dimHolidayMapper.selectByExample(example);
		if (holidays != null) {
			dtos = new ArrayList<DimHolidayDto>();
			BeanCopy.copyPropertiesForList(holidays, dtos, DimHolidayDto.class);
		}
		return dtos;
	}

	private List<DimHolidayDto> findAllHolidays() {
		DimHolidayExample example = new DimHolidayExample();
		List<DimHolidayDto> dtos = null;
		List<DimHoliday> holidays = dimHolidayMapper.selectByExampleWithBLOBs(example);
		if (holidays != null) {
			dtos = new ArrayList<DimHolidayDto>();
			BeanCopy.copyPropertiesForList(holidays, dtos, DimHolidayDto.class);
		}
		return dtos;
	}

	private Map<Integer, String> findHolidaysMap(Integer... yearPeriods) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		List<DimHolidayDto> list = findHolidays(yearPeriods);
		if (map != null) {
			for (DimHolidayDto o : list) {
				map.put(o.getYear(), o.getHolidays());
			}
		}
		return map;
	}

	private String findHolidaysString(Integer... yearPeriods) {
		StringBuilder result = new StringBuilder();
		List<DimHolidayDto> list = findHolidays(yearPeriods);
		int i = 0;
		for (DimHolidayDto vo : list) {
			result.append(vo.getYear());
			if (++i < list.size() - 1) {
				result.append(",");
			}
		}
		return result.toString();
	}

	@Override
	/**
	 * 计算暂停日期，暂停当天和恢复当天都不计算在暂停时间内。若计算类型为工作日，需考虑暂停日和恢复日不为工作日的情况。
	 */
	public int calculatePausePeriod(Date startDate, Date endDate, String dayType) {
		if (sdf.format(startDate).equals(sdf.format(endDate))) {
			return 0;
		}
		int ret = calDatePeriod(startDate, endDate, dayType);
		if (dayType.equals(DicConstants.TASK_DEFINE_DIM_TYPE_CALENDARDAY)) {
			return ret - 2;
		}
		if (isWorkday(startDate)) {
			ret--;
		}
		if (isWorkday(endDate)) {
			ret--;
		}
		return ret;
	}

	@Override
	public boolean isWorkday(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int currentYear = c.get(Calendar.YEAR);
		String dbHolidays = findHolidaysString(new Integer[] { currentYear });
		if (dbHolidays.indexOf(sdf.format(date)) != -1) {
			return false;
		}
		return true;
	}

	@Override
	public Date findPauseEndDate(Date startDate, int amount, String dayType) {
		if (amount <= 0) {
			return null;
		}
		if (dayType.equals(DicConstants.TASK_DEFINE_DIM_TYPE_CALENDARDAY)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.DAY_OF_YEAR, amount + 1);
			return calendar.getTime();
		}
		if(isWorkday(startDate)){
			return findWorkDay(startDate, amount + 2);
		}else{
			return findWorkDay(startDate, amount + 1);
		}
		
	}

	@Override
	public Date findEndDate(Date startDate, int amount, String dayType) {
		if (amount <= 0) {
			return null;
		}
		if (dayType.equals(DicConstants.TASK_DEFINE_DIM_TYPE_CALENDARDAY)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.DAY_OF_YEAR, amount - 1);
			return calendar.getTime();
		}
		return findWorkDay(startDate, amount);
	}

	@Override
	public Date findWorkDay(Date date, int amount, String dayType) {
		if (StringUtils.isEmpty(dayType) || DicConstants.TASK_DEFINE_DIM_TYPE_WORKDAY.equals(dayType)) {
			return this.findWorkDay(date, amount);
		} else {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_YEAR, amount-1);
			return c.getTime();
		}
	}

}
