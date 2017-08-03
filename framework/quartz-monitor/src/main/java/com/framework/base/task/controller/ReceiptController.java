/**
 * @Probject Name: quartz-monitor
 * @Path: com.framework.base.task.controllerReceiptController.java
 * @Create By mogu
 * @Create In 2014年12月26日 下午2:41:44
 * TODO
 */
package com.framework.base.task.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.base.support.constants.ScheduleConstants;
import com.framework.base.task.domain.TaskExcuteDetail;
import com.framework.base.task.service.JobTaskService;

/**
 * @Class Name ReceiptController
 * @Author mogu
 * @Create In 2014年12月26日
 */
@Controller
@RequestMapping("/receipt")
public class ReceiptController {
	
	private static String TASK_ID ="id";
    
    @Autowired
    private JobTaskService jobTaskService;
    
    /**
     * 定时任务统一回调入口
     * @Methods Name notify
     * @Create In 2014年12月26日 By mogu
     * @param request
     * @param paras void
     */
    @RequestMapping("notify")
    @ResponseBody
    public void notify(@RequestBody Map paras,HttpServletRequest request) {
        TaskExcuteDetail detail=new TaskExcuteDetail();
        Integer id=(Integer) paras.get("taskId");
        String descCode=(String)paras.get("resCode");
        detail.setId(new Long(id));
        detail.setDescCode(descCode);
        detail.setReceiptIp(request.getLocalAddr());
        //判断是否执行成功
        detail.setStatus(ScheduleConstants.STATUS_SUCCESS_CODE.equals(descCode)?ScheduleConstants.EXCUTE_SUCCESS:ScheduleConstants.EXCUTE_FAIL);
        if(paras.get("msg")!=null){
            String descMsg=(String)paras.get("msg");
            detail.setDescMsg(descMsg); 
        }
        detail.setReceiptParas(paras.toString());
        jobTaskService.updateTaskDetail(detail);
    }
}
