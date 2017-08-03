package com.framework.core.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppUtil implements ApplicationContextAware {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Map configMap = new HashMap();

    private static ServletContext servletContext = null;

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
    	AppUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String beanId) {
        return applicationContext.getBean(beanId);
    }

    public static String getAppAbsolutePath() {
        /* 162 */return servletContext.getRealPath("/");
    }

    public static void init(ServletContext in_servletContext) {
        servletContext = in_servletContext;
        String filePath = servletContext.getRealPath("/WEB-INF/classes/");
        Properties props = new Properties();
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(
                    filePath + "/application.properties"));
            props.load(is);
            Iterator it = props.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                configMap.put(key, props.get(key));
            }
        } catch (Exception ex) {
//            logger.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static Map getSysConfig() {
        return configMap;
    }

    public static String getPropertity(String key) {
        if (configMap.isEmpty() || configMap == null) {
            reloadUserRole();
        }
        try {
            return (String) configMap.get(key);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void reloadUserRole() {
        String filePath = servletContext.getRealPath("/WEB-INF/classes/");
        Properties props = new Properties();
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(
                    filePath + "/application.properties"));
            props.load(is);
            Iterator it = props.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                // if(key.equals("role.leaderId")||key.equals("role.proxyLeaderId")){
                configMap.put(key, props.get(key));
                // System.out.println("-----------------------读取资源文件"+configMap.get(key)+"-------------------");
                // }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error(ex.getMessage());
        }
    }
}
