package com.framework.netty.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * spring context工具
 *
 * @author liufl
 * @date 2014.9.19
 * @email hawkdowen@126.com
 */
public class ApplicationContextHolder {

  private static Logger log = LoggerFactory.getLogger("ApplicationContextHolder");

  private static ApplicationContext       rootContext = null;
  private static XmlWebApplicationContext mvcContext  = null;

  // */
  // 开发环境下
  private static final String prePath = "";

    /*
     * / private static final String prePath = "resources/"; //
     */

  public static final void init() {
    if (mvcContext == null) {
      long start = System.currentTimeMillis();
      log.info("init spring mvc context...");
      mvcContext = new XmlWebApplicationContext();
      mvcContext.setConfigLocation("classpath:" + prePath
                                   + "springmvc-servlet.xml");
      mvcContext.setParent(getRootContext());
      long end = System.currentTimeMillis();
      log.info("init spring mvc context complate in " + (end - start) + "ms.");
    }
  }

  public static ApplicationContext getRootContext() {
    if (rootContext == null) {
      long start = System.currentTimeMillis();
      log.info("init spring root context...");
      // rootContext = new FileSystemXmlApplicationContext(
      // "classpath:" + prePath + "applicationContext.xml");
      rootContext = new ClassPathXmlApplicationContext("classpath*:" + prePath + "applicationContext.xml");
      long end = System.currentTimeMillis();
      log.info("init spring root context complate in " + (end - start) + "ms.");
    }
    return rootContext;
  }

  public static WebApplicationContext getMvcContext() {
    return mvcContext;
  }
}
