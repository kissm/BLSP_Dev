/**
 * @Probject Name: quartz-monitor
 * @Path: com.framework.base.support.constantsScheduleConstants.java
 * @Create By mogu
 * @Create In 2014年12月18日 下午4:50:04
 * TODO
 */
package com.framework.base.support.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 调度常量类
 * @Class Name ScheduleConstants
 * @Author mogu
 * @Create In 2014年12月18日
 */
public class ScheduleConstants {
    public static final String STATUS_RUNNING = "1";
    public static final String STATUS_NOT_RUNNING = "0";
    public static final String CONCURRENT_IS = "1";
    public static final String CONCURRENT_NOT = "0";
    
    public static final String STATUS_SUCCESS_CODE = "0";
    
    /**
     * 1.平台已发起 2.响应方已接受  3.响应方执行失败  4.响应方执行成功
     */
    public static final String EXCUTE_SEND="1";
    
    public static final String EXCUTE_SEND_CN="平台已发起";
    
    public static final String EXCUTE_RECEIVE="2";
    
    public static final String EXCUTE_RECEIVE_CN="响应方已接受";
    
    public static final String EXCUTE_FAIL="3";
    public static final String EXCUTE_FAIL_CN="响应方执行失败";
    
    public static final String EXCUTE_SUCCESS="4";
    public static final String EXCUTE_SUCCESS_CN="响应方执行成功";
    
    public static final Map<String, String> EXCUTE_DETAIL_MAP = new HashMap<String, String>();
    
    static {
        EXCUTE_DETAIL_MAP.put(EXCUTE_SEND, EXCUTE_SEND_CN);
        EXCUTE_DETAIL_MAP.put(EXCUTE_RECEIVE, EXCUTE_RECEIVE_CN);
        EXCUTE_DETAIL_MAP.put(EXCUTE_FAIL, EXCUTE_FAIL_CN);
        EXCUTE_DETAIL_MAP.put(EXCUTE_SUCCESS, EXCUTE_SUCCESS_CN);
    }
}
