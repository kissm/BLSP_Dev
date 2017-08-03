package com.lpcode.modules.service.impl.report;

import com.alibaba.fastjson.JSONObject;
import com.framework.osp.common.utils.StringUtils;
import com.framework.osp.modules.sys.utils.DictUtils;
import com.lpcode.client.newreport.NewInvokResult;
import com.lpcode.client.newreport.NewServieReportHttpUtil;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.blsp.entity.ServiceSbQueue;
import com.lpcode.modules.blsp.mapper.ServiceSbQueueMapper;
import com.lpcode.modules.dto.report.ServiceReportDto;
import com.lpcode.modules.dto.report.ServiceSbLogDto;
import com.lpcode.modules.service.message.IMessageSMS;
import com.lpcode.modules.service.report.NewSbDataService;
import com.lpcode.modules.service.report.ReportPushService;
import com.lpcode.modules.service.report.ServiceSbLogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
@Transactional
public class NewSbDataServiceImpl implements NewSbDataService {
	
	Log _log = LogFactory.getLog(NewSbDataServiceImpl.class);

	@Autowired
	private ServiceSbLogService serviceSbLogService;
	@Autowired
	private IMessageSMS iMessageSMS;
	@Autowired
	private ReportPushService reportPushService;
	@Autowired
	private ServiceSbQueueMapper serviceSbQueueMapper;

	@Override
	public void dataReport(ServiceSbQueue sbQueue) {
		String resultJson = sbQueue.getSbXml();
		sbQueue.setSbIsjh(DicConstants.SB_ISJH_UP_FAIL);
		Byte tbIsjh = new Byte(String.valueOf(DicConstants.FALSE));
		String msg = "更新成功";
		ServiceSbLogDto serviceSbLogDto = new ServiceSbLogDto();
		String resultCode =null;
		ServiceReportDto serviceReportDto = new ServiceReportDto();
		try {
			JSONObject object = JSONObject.parseObject(resultJson);
			serviceReportDto = JSONObject.toJavaObject(object,ServiceReportDto.class);
			serviceSbLogDto.setSbStatus(sbQueue.getSbStatus());
			serviceSbLogDto.setSbDate(new Date());
			serviceSbLogDto.setTbXml(resultJson);
			NewInvokResult reportResult = NewServieReportHttpUtil.execute(resultJson,sbQueue.getSbStatus());
			if(reportResult != null && reportResult.getRespHeader() != null){
				resultCode = reportResult.getRespHeader().getStatus();
				String resultDesc = reportResult.getRespHeader().getRespmsg();
				//如果上报成功，更新上报记录表的状态，同步状态为  是 ，是否交换 为 1，并且记录上报报文
				if ("0".equals(resultCode)) {
					tbIsjh = new Byte(String.valueOf(DicConstants.TRUE));
					if(sbQueue.getSbStatus().equals(DicConstants.REPORT_STATUS_SB)){//如果是申办环节，上报成功后创建受理环节
						createNextJob(sbQueue);
					}
					msg = resultDesc;
					sbQueue.setSbIsjh(DicConstants.SB_ISJH_UP_SUCCESS);
				} else if ("1".equals(resultCode)) {
					msg = "申报上报数据格式错误；错误描述：" + resultDesc;
				} else if ("2".equals(resultCode)) {
					msg =  "申报上报文件类错误；错误描述：" + resultDesc;
				} else if ("3".equals(resultCode)) {
					msg = "申报上报其他错误；错误描述：" + resultDesc;
				} else if ("4".equals(resultCode)) {
					msg = "申报上报接入验证错误；错误描述：" + resultDesc;
				} else {
					msg = "未知错误，错误编码是:" + resultCode + "，错误描述：" + resultDesc;
				}
			}else{
				msg = "调用数据上报接口，返回数据为空";
			}
		} catch (Exception e) {
			//如果上报失败，更新上报记录表的状态，同步状态为  是 ，是否交换 为 0，并且记录上报报文
			msg = "申报上报异常；异常描述：" + e.getMessage();
			e.printStackTrace();
		}
		serviceSbLogDto.setTbIsjh(tbIsjh);
		serviceSbLogDto.setTbResult(msg);
		serviceSbLogDto.setTbStatus(DicConstants.YES);
		serviceSbLogService.save(serviceSbLogDto);
//		上报出错发短信
		if(StringUtils.isNotBlank(resultCode) && !"0".equals(resultCode)){
			//电话号码在数据字典中可配置，且有默认值
			String mobile = DictUtils.getDictValue("DATA_REPORT_WARNING_TEL", "DATA_REPORT_WARNING_TEL", "15818989136");
			Map<String,String> map = new HashMap();
			map.put("sbstatus",sbQueue.getSbStatus());
			map.put("sblsh",serviceReportDto.getSblsh());
			iMessageSMS.sendSmsByTplIdAndMap(mobile, DicConstants.SMS_TEMPLATE_PUSH_WARNING,map);
		}
		sbQueue.setSbResult(msg);
		updateSbQueue(sbQueue);
	}

	/**
	 * 通过service_sb_queue对象生成受理任务
	 * @param sbQueue
	 */
	@Override
	public void createNextJob(ServiceSbQueue sbQueue){
		String xml = sbQueue.getSbXml();
		Map sbXmlMap = JSONObject.parseObject(xml);
		String taskIdStr = (String) sbXmlMap.get("sblsh");//受理流水号，就是事项实例ID
		Long taskId = Long.parseLong(taskIdStr);
		if(sbQueue.getSbStatus().equals(DicConstants.REPORT_STATUS_SB)){//如果是申办环节，上报成功后创建受理环节
			reportPushService.createSlJob(taskId);
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
