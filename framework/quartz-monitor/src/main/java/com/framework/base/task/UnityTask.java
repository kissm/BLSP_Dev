/**
 * @Probject Name: quartz-spring_demo
 * @Path: com.framework.base.taskUnityTask.java
 * @Create By mogu
 * @Create In 2014年12月18日 下午5:12:30
 * TODO
 */
package com.framework.base.task;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.framework.base.support.HttpUtil;
import com.framework.base.support.constants.ScheduleConstants;
import com.framework.base.task.dao.TaskExcuteDetailMapper;
import com.framework.base.task.domain.TaskExcuteDetail;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @Class Name UnityTask
 * @Author mogu
 * @Create In 2014年12月18日
 */
@Component
public class UnityTask {
    @Autowired
    private TaskExcuteDetailMapper taskExcuteDetailMapper;
    // 日志记录器
    private static final Logger LOG = LoggerFactory.getLogger(UnityTask.class);
    public boolean run(Long jobId,String url,String paras){
        LOG.info("调用远程服务 = [jobId:" + jobId + "url:"+url+"]");
        TaskExcuteDetail detail=new TaskExcuteDetail();
        detail.setJobId(jobId);
        detail.setStatus(ScheduleConstants.EXCUTE_SEND);
        detail.setCreateDate(new Date());
        detail.setUpdateDate(new Date());
        taskExcuteDetailMapper.insert(detail);
        String response=null;
        if(StringUtils.isBlank(paras)){
            paras="{}";
        }
        JsonObject sourcePara=new JsonParser().parse(paras).getAsJsonObject();
        sourcePara.addProperty("id", detail.getId());
        String targetParas=sourcePara.toString();
        try {
            response = HttpUtil.doPost(url, targetParas);
            LOG.info("远程请求服务响应结果--url:{},json:{},response:{}",new Object[]{url,targetParas,response});
        } catch (Exception e) {
        	LOG.error("远程请求服务异常--url:{},json:{},errorMsg:{}",new Object[]{url,targetParas,e.getMessage()});
        }
        return false;
    }
    public static void main(String[] args){
        String paras="{}";
        JsonObject sourcePara=new JsonParser().parse(paras).getAsJsonObject();
        sourcePara.addProperty("id", 100);
        String targetParas=sourcePara.toString();
        //String response = HttpUtil.doPost("www.sohu.com", "");
        System.out.println(targetParas);
    }
}
