package com.lpcode.modules.service.impl.report;


import com.framework.core.result.ListResult;
import com.framework.core.utils.BeanCopy;
import com.lpcode.client.push.InvokeResult;
import com.lpcode.client.push.ServieBasicInfoHttpUtil;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.blsp.entity.ServiceBasicinfoPushLogExample;
import com.lpcode.modules.blsp.entity.ServiceBasicinfoPushLogWithBLOBs;
import com.lpcode.modules.blsp.entity.ServiceSbQueue;
import com.lpcode.modules.blsp.mapper.ServiceBasicinfoPushLogMapper;
import com.lpcode.modules.blsp.mapper.ServiceSbQueueMapper;
import com.lpcode.modules.dto.report.ServiceBasicinfoPushLogDto;
import com.lpcode.modules.service.report.ServiceBasicInfoPushService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ServiceBasicInfoPushServiceImpl implements ServiceBasicInfoPushService {

	@Autowired
	private ServiceSbQueueMapper serviceSbQueueMapper;
	
	@Autowired
	private ServiceBasicinfoPushLogMapper serviceBasicinfoPushLogMapper;

	@Override
	@Transactional(readOnly = false)
	public void save(ServiceSbQueue sbQueue) {
		try {
			ServiceBasicinfoPushLogDto dto = new ServiceBasicinfoPushLogDto();
			dto.setSbId(sbQueue.getSbId());
			dto.setSbBasicId(sbQueue.getSbBasicId());
			InvokeResult result = ServieBasicInfoHttpUtil.pushInfo(sbQueue.getSbXml(), sbQueue.getSbStatus());
			dto.setCreationTime(new Date());
			dto.setIsSuccess((byte) DicConstants.FALSE);
			dto.setIsSuccess(String.valueOf(DicConstants.FALSE).equals(result.getRespHeader().getStatus()) ? (byte) DicConstants.TRUE : (byte) DicConstants.FALSE);
			dto.setPushData(dto.getPushData() + "@" + sbQueue.getSbXml());
			dto.setPushResult(dto.getPushResult() + (StringUtils.isBlank(dto.getPushResult()) ? "" : "@") + result.getRespHeader().getRespmsg());

			ServiceBasicinfoPushLogWithBLOBs record = new ServiceBasicinfoPushLogWithBLOBs();
			BeanCopy.copyProperties(dto,record);
			if(StringUtils.isBlank(dto.getId())){
				serviceBasicinfoPushLogMapper.insert(record);
			}else{
				serviceBasicinfoPushLogMapper.updateByPrimaryKeySelective(record);
			}
			sbQueue.setSbIsjh(DicConstants.SB_ISJH_UP_SUCCESS);
			sbQueue.setSbResult(dto.getPushResult());
		}catch (Exception e){
			e.printStackTrace();
			sbQueue.setSbIsjh(DicConstants.SB_ISJH_UP_FAIL);
		}finally {
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

	@Override
	public ListResult<ServiceBasicinfoPushLogDto> findRePushList() {
		ListResult<ServiceBasicinfoPushLogDto> result = new ListResult<ServiceBasicinfoPushLogDto>();
		ServiceBasicinfoPushLogExample example = new ServiceBasicinfoPushLogExample();
		ServiceBasicinfoPushLogExample.Criteria criteria  = example.createCriteria();
		criteria.andIsSuccessEqualTo(new Byte(String.valueOf(DicConstants.FALSE)));
		List<ServiceBasicinfoPushLogWithBLOBs> list = serviceBasicinfoPushLogMapper.selectByExampleWithBLOBs(example);
		List<ServiceBasicinfoPushLogDto> resultList = new ArrayList<ServiceBasicinfoPushLogDto>();
		if(list != null && list.size()>0){
			BeanCopy.copyPropertiesForListWithBlank2Null(list, resultList, ServiceBasicinfoPushLogDto.class);
		}
		result.setObj(resultList);
		return result;
	}
	
}
