/**
 * @Probject Name: quartz-monitor
 * @Path: com.framework.base.task.controllerMonitorController.java
 * @Create By mogu
 * @Create In 2015年3月10日 下午5:53:54
 * TODO
 */
package com.framework.base.task.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.base.task.domain.ScheduleJob;
import com.framework.base.task.service.JobTaskService;

/**
 * @Class Name MonitorController
 * @Author mogu
 * @Create In 2015年3月10日
 */
@Controller
@RequestMapping("/monitor")
public class MonitorController {
    @Autowired
    private JobTaskService taskService;  
    
    @RequestMapping("getAllTask")
    public String getAllTask(  ModelMap modelMap, HttpServletRequest request){
        try {
            List<ScheduleJob> list=taskService.getAllJob();
            modelMap.put("list", list);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "base/monitor";
    }
}
