package com.framework.core.utils.listener;

import javax.servlet.ServletContextEvent;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.framework.core.utils.AppUtil;

public class MyContextLoaderListener extends ContextLoaderListener {
//    private static Log logger = LogFactory
//            .getLog(MyContextLoaderListener.class);

    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        AppUtil.init(event.getServletContext());
    }
}
