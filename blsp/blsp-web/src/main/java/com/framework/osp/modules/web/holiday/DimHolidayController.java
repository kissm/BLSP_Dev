package com.framework.osp.modules.web.holiday;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.osp.common.mapper.JsonMapper;
import com.framework.osp.common.utils.StringUtils;
import com.lpcode.modules.controller.message.AbstractController;
import com.lpcode.modules.dto.dimension.holiday.DimHolidayDto;
import com.lpcode.modules.service.dimension.holiday.DimHolidayService;

@Controller
@RequestMapping(value = "${adminPath}/holidays")
public class DimHolidayController extends AbstractController {
  
  @Autowired
  DimHolidayService dimHolidayService;

  private DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
  
    @RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, Model model) {

//		model.addAttribute("page", page);
	  	String yearNow = request.getParameter("yearNow");
	  	DimHolidayDto holidayDto = null;
	  	if(StringUtils.isNotEmpty(yearNow)){
	  		holidayDto = dimHolidayService.findByPrimaryKey(Integer.parseInt(yearNow));
	  		model.addAttribute("yearNow",yearNow);
	  	}else{
	  		Calendar calendar = Calendar.getInstance();
	  		calendar.setTime(new Date());
	  		model.addAttribute("yearNow",calendar.get(Calendar.YEAR));
	  		holidayDto = dimHolidayService.findByPrimaryKey(calendar.get(Calendar.YEAR));
	  	}
	  	//获得当前实际年份
	  	model.addAttribute("year",new SimpleDateFormat("yyyy").format(new Date()));
	  	//获得当年实际日期
	  	model.addAttribute("day",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	  	if(holidayDto != null){
	  		model.addAttribute("yearHolidays",holidayDto.getHolidays());
	  	}
		return "modules/holiday/holidayEdit";
	}
    
    @ResponseBody
    @RequestMapping(value = "/addCalendar")
   	public String addCalendar(HttpServletRequest request, HttpServletResponse response, Model model) {
		String flag = "";
		try{
			String yearHolidays = request.getParameter("yearHolidays");
			//日期按照年份分批保存
			String[] yearHolidaysArr = yearHolidays.split(",");
			HashMap<String,String> map = new HashMap<String,String>();
			for(int i=0;i<yearHolidaysArr.length;i++){
				String yearHoliday = yearHolidaysArr[i];
				String yearPeriod = yearHoliday.substring(0, 4);
				if(StringUtils.isEmpty(map.get(yearPeriod))){
					map.put(yearPeriod, yearHoliday);
				}else{
					map.put(yearPeriod, map.get(yearPeriod)+","+yearHoliday);
				}
			}
			for (String key : map.keySet()) {
				DimHolidayDto d = new DimHolidayDto();
				d.setYear(Integer.valueOf(key));
				d.setHolidays(map.get(key));
				dimHolidayService.save(d);
			}
			flag =  "success";
		}catch(Exception e){
			flag = "error";
			e.printStackTrace();
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("info", flag);
		return JsonMapper.toJsonString(map);
	}
    @ResponseBody
    @RequestMapping(value = "/getWeekends")
	public String getWeekends(HttpServletRequest request, HttpServletResponse response, Model model) {
		String yearPeriod = request.getParameter("yearNow");
		List<String> list =new ArrayList<String>();
		int year = Integer.valueOf(yearPeriod);
		Calendar calendar = new GregorianCalendar(year, 0, 1);
		int i = 1;
		while (calendar.get(Calendar.YEAR) < year + 1) {
			calendar.set(Calendar.WEEK_OF_YEAR, i++);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			if (calendar.get(Calendar.YEAR) == year) {
				String val = calendar.get(Calendar.YEAR)+"";//+"-";
				if(calendar.get(Calendar.MONTH)+1<10){
					val += "0"+((calendar.get(Calendar.MONTH))+1) ;//+"-";
				}else{
					val += ((calendar.get(Calendar.MONTH))+1);//+"-";
				}
				if(calendar.get(Calendar.DAY_OF_MONTH)<10){
					val += "0"+calendar.get(Calendar.DAY_OF_MONTH);
				}else{
					val += calendar.get(Calendar.DAY_OF_MONTH);
				}
				list.add(val);
			}
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			if (calendar.get(Calendar.YEAR) == year) {
				String val = calendar.get(Calendar.YEAR)+"";//"-";
				if(calendar.get(Calendar.MONTH)+1<10){
					val += "0"+((calendar.get(Calendar.MONTH))+1);//+"-";
				}else{
					val += ((calendar.get(Calendar.MONTH))+1);//+"-";
				}
				if(calendar.get(Calendar.DAY_OF_MONTH)<10){
					val += "0"+calendar.get(Calendar.DAY_OF_MONTH);
				}else{
					val += calendar.get(Calendar.DAY_OF_MONTH);
				}
				list.add(val);
			}
		}
		Object[] yearHolidaysArr = list.toArray();
		return JsonMapper.toJsonString(yearHolidaysArr);
	}
  
}
