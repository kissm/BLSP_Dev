package com.lpcode.modules.service.impl.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.core.result.CommonResult;
import com.framework.core.result.ListResult;
import com.framework.core.result.Result;
import com.framework.core.utils.BeanCopy;
import com.lpcode.modules.blsp.constdefine.ConstDefine;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.blsp.entity.PrjTask;
import com.lpcode.modules.blsp.entity.PrjTaskPauseDetail;
import com.lpcode.modules.blsp.mapper.PrjTaskAdapterMapper;
import com.lpcode.modules.blsp.mapper.PrjTaskMapper;
import com.lpcode.modules.blsp.mapper.PrjTaskPauseDetailMapper;
import com.lpcode.modules.blsp.vo.PrjTaskOvertimeVO;
import com.lpcode.modules.blsp.vo.PrjTaskPauseDueVO;
import com.lpcode.modules.dto.message.RequestDTO;
import com.lpcode.modules.dto.message.RequestDataDTO;
import com.lpcode.modules.service.dimension.holiday.DimHolidayService;
import com.lpcode.modules.service.message.IServiceMessage;
import com.lpcode.modules.service.project.dto.PrjTaskDTO;
import com.lpcode.modules.service.project.dto.pinstance.PrjTaskVo;
import com.lpcode.modules.service.project.inf.PrjTaskScheduleService;
import com.lpcode.modules.service.project.inf.PrjTaskService;

@Service
@Transactional(readOnly = false)
public class PrjTaskScheduleServiceImpl implements PrjTaskScheduleService {
	
	
	@Autowired
	private PrjTaskAdapterMapper taskAdapterMapper;
	@Autowired
	private PrjTaskPauseDetailMapper pauseMapper;
	@Autowired
	private PrjTaskMapper taskMapper;
	@Autowired
	private DimHolidayService holidayService;
	
	@Autowired
	IServiceMessage messageService;
	@Autowired
	private PrjTaskService prjTaskService;

	
	/**
	 * 讲暂停到期的记录，填入暂停到期日期，与实际暂停时间
	 * 将task置为审批中，暂停时长加上本次暂停超时时长，task的endtime重新做计算
	 * 发送暂停超时自动变为审批中提醒短信。
	 */
	@Transactional(readOnly = false)
	public CommonResult setTaskPauseToEnd(PrjTaskPauseDueVO vo){
		CommonResult result = new CommonResult();
		PrjTaskPauseDetail pause = new PrjTaskPauseDetail();
		pause.setId(vo.getPauseInstId());
		pause.setPauseEndTime(new Date());
		pause.setPauseDuration(vo.getPauseDuration());
		pauseMapper.updateByPrimaryKeySelective(pause);
		
		PrjTask task = new PrjTask();
		task.setId(vo.getTaskInstId());
		Integer pauseDays = vo.getTaskPauseDuration();
		if(pauseDays == null){
			pauseDays = 0;
		}
		pauseDays += vo.getPauseDuration();
		Date endDate = holidayService.findEndDate(vo.getTaskStartTime(), vo.getTaskDueDuration()+pauseDays, vo.getTimeType());
		task.setTaskPauseDuration(pauseDays);
		if (vo.getTaskEndTime() != null && vo.getTaskEndTime().getTime() > new Date().getTime()) 
			task.setTaskEndTime(endDate);
		task.setTaskStatus(DicConstants.TASK_STATUS_IN_PROCESS);
		taskMapper.updateByPrimaryKeySelective(task);
		
		RequestDTO<String> requestDTO = new RequestDTO<String>();
	    List<String> receivers = new ArrayList<>();
	    receivers.add(vo.getMobile());
	    requestDTO.setTplid(DicConstants.SMS_TEMPLATE_TASK_PAUSE_END);
	    requestDTO.setSendby(ConstDefine.SEND_BY_MOBILE);//通过短信ConstDefine.SEND_BY_MOBILE
	    requestDTO.setReceivers(receivers);
	    RequestDataDTO reqData = new RequestDataDTO();
	    requestDTO.setData(reqData.getReqData());
	    messageService.queue(requestDTO);
		
		return result;
		
	}


	@Override
	@Transactional(readOnly = true)
	public ListResult<PrjTaskPauseDueVO> findTaskPauseDueList() {
		ListResult<PrjTaskPauseDueVO> result = new ListResult<PrjTaskPauseDueVO>();
		result.setObj(taskAdapterMapper.findPauseDueList());
		return result;
	}


	@Override
	@Transactional(readOnly = false)
	public CommonResult sendTaskOvertimeSms(PrjTaskOvertimeVO vo) {
		CommonResult result = new CommonResult();
		RequestDTO<String> requestDTO = new RequestDTO<String>();
	    List<String> receivers = new ArrayList<>();
	    receivers.add(vo.getMobile());
	    requestDTO.setTplid(DicConstants.SMS_TEMPLATE_TASK_OVER_DUE);
	    requestDTO.setSendby(ConstDefine.SEND_BY_MOBILE);//通过短信ConstDefine.SEND_BY_MOBILE
	    requestDTO.setReceivers(receivers);
	    RequestDataDTO reqData = new RequestDataDTO();
	    requestDTO.setData(reqData.getReqData());
	    messageService.queue(requestDTO);
		return result;
	}


	/**
	 * 查询得到prjTaskVo实例对象
	 * @param prjTaskVo
	 * @return
	 */
	@Override
	public PrjTaskDTO getPrjTaskDTO(PrjTaskVo prjTaskVo) {
		if(prjTaskVo != null){
			if(prjTaskVo.getPrjId() != null && prjTaskVo.getTaskId() != null){
				PrjTask record = new PrjTask();
				record.setPrjId(prjTaskVo.getPrjId());
				record.setTaskId(prjTaskVo.getTaskId());
				record = taskMapper.selectOneByEntitySelective(record);
				if(record != null){
					BeanCopy.copyProperties(record, prjTaskVo);
				}
			}
		}
		if(prjTaskVo.getId() != null){
			Result<PrjTaskDTO> prjTaskResult = prjTaskService.findTaskById(prjTaskVo.getId()); // 事项实例
			PrjTaskDTO prjTask = prjTaskResult.getObj();
			return prjTask;
		}else{
			return null;
		}
	}

	

}
