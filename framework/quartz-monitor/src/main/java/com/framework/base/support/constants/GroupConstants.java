/**
 * @Probject Name: quartz-monitor
 * @Path: com.framework.base.support.constantsGroupConstants.java
 * @Create By mogu
 * @Create In 2014年12月30日 上午10:40:46
 * TODO
 */
package com.framework.base.support.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * job组名常量信息
 * @Class Name GroupConstants
 * @Author mogu
 * @Create In 2014年12月30日
 */
public class GroupConstants {
    
    /**
     * 会员
     */
    public static final String MEMBER="member";
    
    public static final String MEMBER_CN="会员支撑";
    
    /**
     * 商品
     */
    public static final String PRODUCT="product";
    
    public static final String PRODUCT_CN="商品支撑";
    
    /**
     * 销售订单
     */
    public static final String OMS="oms";
    public static final String OMS_CN="销售支撑";
    
    /**
     * 销售订单
     */
    public static final String CAMPAIGN="campaign";
    public static final String CAMPAIGN_CN="营销支撑";
    
    public static final Map<String, String> GROUP_INFO_MAP = new HashMap<String, String>();
    
    static {
        GROUP_INFO_MAP.put(MEMBER, MEMBER_CN);
        GROUP_INFO_MAP.put(PRODUCT, PRODUCT_CN);
        GROUP_INFO_MAP.put(OMS, OMS_CN);
        GROUP_INFO_MAP.put(CAMPAIGN, CAMPAIGN_CN);
    }
}
