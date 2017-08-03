package com.lpcode.modules.controller.dimension.holiday;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lpcode.modules.blsp.bean.LpcodeHolidayBean;
import com.lpcode.modules.blsp.bean.LpcodeHolidaySideBean;
import com.lpcode.modules.controller.message.AbstractController;
import com.lpcode.modules.service.dimension.holiday.DimHolidayService;

@RestController
@RequestMapping(value = "/holidays")
public class DimHolidayController extends AbstractController {
  
  @Autowired
  DimHolidayService dimHolidayService;

  private DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
  
  @RequestMapping(value = "/getworkday", method = RequestMethod.POST)
  public Map<String,String> getworkday(@RequestBody LpcodeHolidayBean para, HttpServletRequest request) {
	Date date =  dimHolidayService.findWorkDay(para.getDate(),para.getAmount());
	System.out.println("==========================");
	Map<String,String> map = new HashMap<String,String>();
	map.put("workday",sdf.format(date));
    return map;
  }
  
  @RequestMapping(value = "/calDatePeriod", method = RequestMethod.POST)
  public Map<String,Integer> calDatePeriod(@RequestBody LpcodeHolidaySideBean para, HttpServletRequest request) {
	int date =  dimHolidayService.calDatePeriod(para.getStartDate(),para.getEndDate(),para.getType());
	System.out.println("==========================");
	Map<String,Integer> map = new HashMap<String,Integer>();
	map.put("workday",date);
    return map;
  }
  
}
