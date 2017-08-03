package com.lpcode.common.utils;

import com.lpcode.modules.dto.report.ServiceReportDto;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class NewServiceReportUtils {

	private static Configuration configuration = null;
	
	private static Map<String, Template> templates = null;
	
	public static enum ReportTemplate{
		
		REPORT_SB("申办","sb","EX_GDBS_SB"),
		REPORT_WSYSL("网上预受理","wsysl","EX_GDBS_WSYSL"),
		REPORT_YSLTH("不予受理","yslth","EX_GDBS_SL"),
		REPORT_SL("受理","sl","EX_GDBS_SL"),
		REPORT_BJGZ("补交告知","bjgz","EX_GDBS_BJGZ"), 
		REPORT_BJSL("补交受理","bjsl","EX_GDBS_BJSL"),
		REPORT_TBCXSQ("特别程序申请","tbcxsq","EX_GDBS_TBCXSQ"),
		REPORT_TBCXSQJG("特别程序申请结果","tbcxsqjg","EX_GDBS_TBCXJG"),
		REPORT_BJ("办结","bj","EX_GDBS_BJ"),
		REPORT_LZDJ("领证登记","lzdj","EX_GDBS_LQDJ"),
		REPORT_CB("承办","cbshpz","EX_GDBS_SPCL"),
		REPORT_SH("审核","cbshpz","EX_GDBS_SPCL"),
		REPORT_PZ("批准","cbshpz","EX_GDBS_SPCL"),
		REPORT_CBTH("承办退回","cbshpzth","EX_GDBS_SPCL"),
		REPORT_SHTH("审核退回","cbshpzth","EX_GDBS_SPCL"),
		REPORT_PZTH("批准退回","cbshpzth","EX_GDBS_SPCL");
        // 成员变量
        private String status;
        private String templateName;
        private String reportTemplateName;
        
        // 构造方法
        private ReportTemplate(String status, String templateName,String reportTemplateName) {
            this.status = status;
            this.templateName = templateName;
            this.reportTemplateName = reportTemplateName;
        }

        public String getStatus() {
			return status;
		}

		public String getTemplateName() {
			return templateName;
		}

		public String getReportTemplateName() {
			return reportTemplateName;
		}

		// 普通方法
        public static String getTemplateName(String status) {
            for (ReportTemplate c : ReportTemplate.values()) {
                if (c.getStatus().equals(status)) {
                    return c.templateName;
                }
            }
            return null;
        }
        
     // 普通方法
        public static String getReportTemplateName(String status) {
            for (ReportTemplate c : ReportTemplate.values()) {
                if (c.getStatus().equals(status)) {
                    return c.reportTemplateName;
                }
            }
            return null;
        }

	}
	
	static{
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setClassForTemplateLoading(NewServiceReportUtils.class,"/freemaker/newReport");
		templates = new HashMap<>();
		try {
			for (ReportTemplate c : ReportTemplate.values()) {
				if(!templates.containsKey(c.getTemplateName())){
					templates.put(c.getTemplateName(), configuration.getTemplate(c.getTemplateName()+".ftl","UTF-8"));
				}
            }
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static String getRequestBody(ServiceReportDto dto, String sbStatus) throws Exception{
		//获取模板  
		if(StringUtils.isEmpty(sbStatus)) return null;
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("serviceReport",dto);
		
		StringWriter result = new StringWriter();
		Template template = templates.get(ReportTemplate.getTemplateName(sbStatus));
	    template.process(dataMap, result);
	    
		return result.toString();
	}
}
