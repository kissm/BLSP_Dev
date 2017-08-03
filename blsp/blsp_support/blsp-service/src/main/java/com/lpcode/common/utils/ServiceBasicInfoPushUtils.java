package com.lpcode.common.utils;

import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.dto.report.ServiceBasicInfoPushDto;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class ServiceBasicInfoPushUtils {
	
//	private static ServiceBasicInfoPushService serviceBasicInfoPushService = SpringContextHolder.getBean(ServiceBasicInfoPushServiceImpl.class);
	
	private static Configuration configuration = null;
	
	private static Map<String, Template> templates = null;
	
	public static enum PushTemplate{
		PUSH_GR(DicConstants.SB_TYPE_GR,"gr"),
		PUSH_QY(DicConstants.SB_TYPE_QY,"qy"),
		PUSH_XM(DicConstants.SB_TYPE_TZJS,"tzjs");
        // 成员变量
	    private String type;
        private String templateName;
        
        // 构造方法
        private PushTemplate(String type, String templateName) {
            this.type = type;
            this.templateName = templateName;
        }

        public String getType() {
			return type;
		}

		public String getTemplateName() {
			return templateName;
		}

		// 普通方法
        public static String getTemplateName(String type) {
            for (PushTemplate c : PushTemplate.values()) {
                if (c.getType().equals(type)) {
                    return c.templateName;
                }
            }
            return null;
        }
	}
	
	static{
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setClassForTemplateLoading(ServiceBasicInfoPushUtils.class,"/freemaker/push");
		templates = new HashMap<>();
		try {
			for (PushTemplate c : PushTemplate.values()) {
				if(!templates.containsKey(c.getTemplateName())){
					templates.put(c.getTemplateName(), configuration.getTemplate(c.getTemplateName()+".ftl","UTF-8"));
				}
            }
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	public static String getRequestBody(ServiceBasicInfoPushDto dto) throws Exception{
		//获取模板  
		if(dto == null || StringUtils.isEmpty(dto.getSbstatus())) return null;
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("servicePushDto",dto);
		StringWriter result = new StringWriter();
		Template template = templates.get(PushTemplate.getTemplateName(dto.getSbstatus()));
	    template.process(dataMap, result);
		return result.toString();
	}
}
