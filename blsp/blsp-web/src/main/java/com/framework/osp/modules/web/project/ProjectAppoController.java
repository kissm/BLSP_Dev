package com.framework.osp.modules.web.project;

import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.blsp.entity.PrjTask;
import com.lpcode.modules.blsp.entity.PrjTaskExample;
import com.lpcode.modules.blsp.entity.ServiceSbQueue;
import com.lpcode.modules.blsp.entity.ServiceSbQueueExample;
import com.lpcode.modules.blsp.mapper.PrjTaskMapper;
import com.lpcode.modules.blsp.mapper.ServiceSbQueueMapper;
import com.lpcode.modules.dto.project.ReqPrjAppointment;
import com.lpcode.modules.dto.project.ReqReportMsg;
import com.lpcode.modules.dto.project.RespPrjAppointment;
import com.lpcode.modules.dto.project.RespReportMSg;
import com.lpcode.modules.service.impl.report.NewSbDataServiceImpl;
import com.lpcode.modules.service.project.constant.TaskConstants;
import com.lpcode.modules.service.project.inf.PrjAppointmentService;
import com.lpcode.modules.service.project.inf.PrjTaskService;
import com.lpcode.modules.service.report.ReportPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 公共服务及后台数据补录脚本的入口服务
 * @author Pengs
 * @package com.lpcode.modules.controller.project
 * @fileName ProjectController
 * @date 16/3/8.
 */
@RestController
@RequestMapping(value = "${adminPath}/projectApi")
public class ProjectAppoController {

    @Autowired
    private PrjAppointmentService service;
    @Autowired
    private NewSbDataServiceImpl newSbDataService;
    @Autowired
    private ReportPushService reportPushService;
    @Autowired
    private PrjTaskService prjTaskList;
    @Autowired
    private ServiceSbQueueMapper serviceSbQueueMapper;
    @Autowired
    private PrjTaskMapper prjTaskMapper;
    @RequestMapping(value = "/appo", method = RequestMethod.POST)
    public RespPrjAppointment appointment(@RequestBody ReqPrjAppointment reqPrjAppointment){
        RespPrjAppointment resp = new RespPrjAppointment(reqPrjAppointment);
        Integer s = service.appointment(reqPrjAppointment);
        HashMap<String,Object> result = new HashMap<String,Object>();
        result.put("result",s);
        resp.setData(result);
        resp.getRespHeader().setStatus(s + "");

        if(s==0){
            resp.getRespHeader().setRespmsg("success");
        }else if(s==70){
            resp.getRespHeader().setRespmsg("接口参数错误/缺少必填参数");
        }else{
            resp.getRespHeader().setRespmsg("总线内部处理错误");
        }
        return resp;
    }



    /**
     * 初始化事项推送,将系统内没有上报的事项推送上报（只推送暂停和处理中的事项）
     * @param reportMsg
     * @return
     */
    @RequestMapping(value = "/reportAgain", method = RequestMethod.POST)
    public RespReportMSg reportAgain(@RequestBody ReqReportMsg reportMsg){
        RespReportMSg resp = new RespReportMSg(reportMsg);
        PrjTaskExample example = new PrjTaskExample();
        List<String> listStatus = new ArrayList<>();
        listStatus.add(TaskConstants.TASK_STATUS_AUDIT);
        listStatus.add(TaskConstants.TASK_STATUS_PAUSE);
        example.createCriteria().andTaskStatusIn(listStatus);
        List<PrjTask> prjTaskList = prjTaskMapper.selectByExample(example);
        int s = 0;
        // 查出所有暂停和未提交的事项
        for(PrjTask prjTask : prjTaskList){
            //TODO 过滤上报过的不进行上报,只对没有上报的进行上报
            s = reportPushService.initPushTaskBegin(prjTask.getId());
        }
        resp.getRespHeader().setStatus(s + "");
        return resp;
    }

    /**
     * 通过TaskInstId，补录廉情预警，以事项纬度提交
     * @param reportMsg
     * @return
     */
    @RequestMapping(value = "/reportLqyjAgain", method = RequestMethod.POST)
    public RespReportMSg reportLqyjAgain(@RequestBody ReqReportMsg reportMsg){
        RespReportMSg resp = new RespReportMSg(reportMsg);
        String taskInstIdStr = reportMsg.getData().getPrjTaskInstId();
        Long taskId = Long.parseLong(taskInstIdStr);
        PrjTask prjTask = prjTaskMapper.selectByPrimaryKey(taskId);
        prjTaskList.postLqyjApply(prjTask);
        resp.getRespHeader().setStatus("0");
        return resp;
    }


    /**
     * 通过TaskInstId，事项实例ID 补录上报受理作业
     * @param reportMsg
     * @return
     */
    @RequestMapping(value = "/reportSl", method = RequestMethod.POST)
    public RespReportMSg reportSl(@RequestBody ReqReportMsg reportMsg){
        RespReportMSg resp = new RespReportMSg(reportMsg);
        String taskInstIdStr = reportMsg.getData().getPrjTaskInstId();
        Long taskId = Long.parseLong(taskInstIdStr);
        reportPushService.createSlJob(taskId);
        resp.getRespHeader().setStatus("0");
        return resp;
    }


    /**
     * 通过TaskInstId，事项实例ID 补录上报申办作业
     * @param reportMsg
     * @return
     */
    @RequestMapping(value = "/reportSb", method = RequestMethod.POST)
    public RespReportMSg reportSb(@RequestBody ReqReportMsg reportMsg){
        RespReportMSg resp = new RespReportMSg(reportMsg);
        String taskInstIdStr = reportMsg.getData().getPrjTaskInstId();
        Long taskId = Long.parseLong(taskInstIdStr);
        resp.getRespHeader().setStatus("0");
        reportPushService.createSbJob(taskId);
        return resp;
    }


    /**
     * 初始化受理事项推送,将系统内申办推送过的数据，增加受理推送
     * @param reportMsg
     * @return
     */
    @RequestMapping(value = "/reportAllsl", method = RequestMethod.POST)
    public RespReportMSg reportAllsl(@RequestBody ReqReportMsg reportMsg){
        RespReportMSg resp = new RespReportMSg(reportMsg);
        int s = 0;
        try{
            ServiceSbQueueExample example = new ServiceSbQueueExample();
            example.createCriteria().andSbStatusEqualTo(DicConstants.REPORT_STATUS_SB);
            List<ServiceSbQueue> serviceSbQueueList = serviceSbQueueMapper.selectByExample(example);
            // 查出所有暂停和未提交的事项
            for(ServiceSbQueue sbQueue : serviceSbQueueList){
                newSbDataService.createNextJob(sbQueue);
            }
        }catch (Exception e){
            e.printStackTrace();
            s = 101;
        }finally {
            resp.getRespHeader().setStatus(s + "");
            return resp;
        }
    }



    /**
     * 通过TaskInstId，事项实例ID 补录上报领证登记作业
     * @param reportMsg
     * @return
     */
    @RequestMapping(value = "/reportLzdj", method = RequestMethod.POST)
    public RespReportMSg reportLzdj(@RequestBody ReqReportMsg reportMsg){
        RespReportMSg resp = new RespReportMSg(reportMsg);
        String taskInstIdStr = reportMsg.getData().getPrjTaskInstId();
        Long taskId = Long.parseLong(taskInstIdStr);
        PrjTask prjTask = prjTaskMapper.selectByPrimaryKey(taskId);
        reportPushService.sbLzdjDataMethod(prjTask);
        resp.getRespHeader().setStatus("0");
        return resp;
    }


    /**
     * 初始化受理事项推送,将系统内申办推送过的数据，增加领证登记推送
     * @param reportMsg
     * @return
     */
    @RequestMapping(value = "/reportAlllzdj", method = RequestMethod.POST)
    public RespReportMSg reportAlllzdj(@RequestBody ReqReportMsg reportMsg){
        RespReportMSg resp = new RespReportMSg(reportMsg);
        int s = 0;
        try{
            PrjTaskExample example = new PrjTaskExample();
            example.createCriteria().andIsFetchedEqualTo("1");//1为已经领取
            List<PrjTask> prjTaskList = prjTaskMapper.selectByExample(example);
            for(PrjTask prjTask : prjTaskList){
                reportPushService.sbLzdjDataMethod(prjTask);
            }
        }catch (Exception e){
            e.printStackTrace();
            s = 101;
        }finally {
            resp.getRespHeader().setStatus(s + "");
            return resp;
        }
    }

}
