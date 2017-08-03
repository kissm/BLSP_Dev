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

public class ServiceBasicInfoReuseUtils {
	
	private static Configuration configuration = null;
	
	private static Map<String, Template> templates = null;
	
	public static enum ReuseTemplate{
		REUSE_GR(DicConstants.SB_TYPE_GR,"gr"),
		REUSE_QY(DicConstants.SB_TYPE_QY,"qy"),
		REUSE_TZJS(DicConstants.SB_TYPE_TZJS,"tzjs");
        // 成员变量
	    private String type;
        private String templateName;
        
        // 构造方法
        private ReuseTemplate(String type, String templateName) {
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
            for (ReuseTemplate c : ReuseTemplate.values()) {
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
		configuration.setClassForTemplateLoading(ServiceBasicInfoReuseUtils.class,"/freemaker/reuse");
		templates = new HashMap<>();
		try {
			for (ReuseTemplate c : ReuseTemplate.values()) {
				if(!templates.containsKey(c.getTemplateName())){
					templates.put(c.getTemplateName(), configuration.getTemplate(c.getTemplateName()+".ftl","UTF-8"));
				}
            }
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static String getSharedInfoDto(ServiceBasicInfoPushDto dto, String sbType) throws Exception{
		//获取模板  
		if(dto == null || StringUtils.isEmpty(sbType)) return null;
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("serviceReuseDto",dto);
		StringWriter result = new StringWriter();
		Template template = templates.get(ReuseTemplate.getTemplateName(sbType));
	    template.process(dataMap, result);
		return result.toString();
	}
}
