package com.lpcode.modules.service.impl.job;

import com.lpcode.modules.blsp.constdefine.ConstDefine;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.blsp.entity.ServiceSbQueue;
import com.lpcode.modules.blsp.entity.ServiceSbQueueExample;
import com.lpcode.modules.blsp.mapper.ServiceSbQueueMapper;
import com.lpcode.modules.service.impl.project.util.HttpClientPostRequestUtil;
import com.lpcode.modules.service.report.NewSbDataService;
import com.lpcode.modules.service.report.ServiceBasicInfoPushService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * @author Pengs
 * @package com.lpcode.modules.service.impl.job
 * @fileName ExecuteSbJob
 * @date 2017/05/18.
 */
@Service
@Transactional(readOnly = false)
public class ExecuteSbJob {
    @Autowired
    private ServiceSbQueueMapper serviceSbQueueMapper;
    @Autowired
    private ServiceBasicInfoPushService serviceBasicInfoPushService;
    @Autowired
    private NewSbDataService newSbDataService;
    @Value("${wsbs.overTask.lqyjbl.url}")
    private String lqyjblUrl ;//廉情预警调用地址

    protected Logger logger = LoggerFactory.getLogger(ExecuteSbJob.class);

    public void execute() {
        ServiceSbQueueExample example = new ServiceSbQueueExample();
        example.createCriteria().andIsDeleteEqualTo("0").andSbIsjhNotEqualTo(DicConstants.SB_ISJH_UP_SUCCESS).andSbTimesLessThan(ConstDefine.MAX_SEND_TIMES);
        example.setOrderByClause(" CREAT_TIME ");
        List<ServiceSbQueue> serviceSbQueues = serviceSbQueueMapper.selectByExample(example);
        //从队列表中获取10条数据，进行上报操作
        for(ServiceSbQueue sbQueue : serviceSbQueues){
            if(sbQueue.getSbTimes() > ConstDefine.MAX_SEND_TIMES){
                sbQueue.setSbIsjh(DicConstants.SB_ISJH_UP_FAIL);
                updateSbQueue(sbQueue);
            }else if(DicConstants.SB_TYPE_TYPT.equals(sbQueue.getSbType())){ //统一审批上报
                pushSpToTypt(sbQueue);
            }else if(DicConstants.SB_TYPE_GXPT.equals(sbQueue.getSbType())){//共享数据上报
                pushGxToGxpt(sbQueue);
            }else if(DicConstants.SB_TYPE_LQYJ.equals(sbQueue.getSbType())){//廉情预警
                sbLqyj(sbQueue);
            }
        }
    }

    /**
     * 数据上报
     * @param sbQueue
     */
    private void pushSpToTypt(ServiceSbQueue sbQueue){
        newSbDataService.dataReport(sbQueue);
    }

    /**
     * 向共享平台推送数据
     * @param sbQueue
     */
    private void pushGxToGxpt(ServiceSbQueue sbQueue){
        serviceBasicInfoPushService.save(sbQueue);
    }

    /**
     * 廉情预警的上报
     * @param sbQueue
     */
    private void sbLqyj (ServiceSbQueue sbQueue){
        String result ="";
        sbQueue.setSbIsjh(DicConstants.SB_ISJH_UP_FAIL);
        try {
            result = HttpClientPostRequestUtil.CallingRequestService(sbQueue.getSbXml(), lqyjblUrl);
            if(StringUtils.isNotBlank(result) && (result.contains("200") || result.contains("201"))){
                result += ":调用网厅的廉情预警信息补录信息接成功";
                sbQueue.setSbIsjh(DicConstants.SB_ISJH_UP_SUCCESS);
            }else{
                result += ":调用网厅的廉情预警信息补录信息接失败";
            }
        } catch (HttpException | IOException e) {
            result += "调用网厅的廉情预警信息补录信息接口出现问题,请追溯!!!";
            e.printStackTrace();
        }finally {
            sbQueue.setSbResult(result);
            updateSbQueue(sbQueue);
        }
    }

    /**
     * 更新上报队列数据
     * @param sbQueue
     */
    private void updateSbQueue (ServiceSbQueue sbQueue){
        //times +1; 更新状态
        sbQueue.setSbTimes(sbQueue.getSbTimes() + 1);
        serviceSbQueueMapper.updateByPrimaryKey(sbQueue);
    }


}
