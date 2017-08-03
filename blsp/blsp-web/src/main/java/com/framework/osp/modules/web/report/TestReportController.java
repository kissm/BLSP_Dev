package com.framework.osp.modules.web.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping(value = "/test/report")
public class TestReportController {
	
	@RequestMapping(value="/accept")
	public ModelAndView jasperAccpte(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		ArrayList<TestBean> list = new ArrayList<TestBean>();
		TestBean bean;
        int index = 1;
        for(int i=0;i<30;i++){
            bean = new TestBean();
            bean.setType(1);
            bean.setRowNum(index++);
            bean.setItem("条目item"+i);
            list.add(bean);
        }
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
        mav.getModel().put("lpcodeParam", "http://c.hiphotos.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=43d7b2776a81800a7ae8815cd05c589f/8601a18b87d6277f0ca079c02e381f30e824fc9c.jpg");
        mav.getModel().put("projectName", "项目名称");
        mav.getModel().put("projectCode", "PRJ001");
        mav.getModel().put("date", new Date());
        mav.getModel().put("recordNum", new Integer(list.size()));
        mav.getModel().put("dataSource", ds);
		mav.getModel().put("format", "pdf");
		mav.setViewName("rpt_template");
		return mav;
	}
	
	@RequestMapping(value="/reject")
	public ModelAndView jasperReject(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		ArrayList<TestBean> list = new ArrayList<TestBean>();
		TestBean bean;
        int index = 1;
        for(int i=0;i<30;i++){
            bean = new TestBean();
            if(i<20){
            	bean.setType(1);
            }else{
            	bean.setType(2);
            }
            if(i==20) index =1;
            bean.setRowNum(index++);
            bean.setItem("条目item"+i);
            list.add(bean);
        }
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
        mav.getModel().put("lpcodeParam", "http://c.hiphotos.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=43d7b2776a81800a7ae8815cd05c589f/8601a18b87d6277f0ca079c02e381f30e824fc9c.jpg");
        mav.getModel().put("projectName", "项目名称");
        mav.getModel().put("projectCode", "PRJ001");
        mav.getModel().put("date", new Date());
        mav.getModel().put("recordNum", new Integer(list.size()));
        mav.getModel().put("dataSource", ds);
		mav.getModel().put("format", "pdf");
		mav.setViewName("rpt_template_lackMaterial");
		return mav;
	}
	
	
	
	

}
