package com.lpcode.modules.service.impl.report;

import com.framework.core.utils.BeanCopy;
import com.lpcode.modules.blsp.entity.ServiceSbLogWithBLOBs;
import com.lpcode.modules.blsp.mapper.ServiceSbLogMapper;
import com.lpcode.modules.dto.report.ServiceSbLogDto;
import com.lpcode.modules.service.report.ServiceSbLogService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ServiceSbLogServiceImpl implements ServiceSbLogService {
	Log _log = LogFactory.getLog(ServiceSbLogServiceImpl.class);
	@Autowired
	ServiceSbLogMapper serviceSbLogMapper;
	

	@Override
	public void save(ServiceSbLogDto dto) {
		ServiceSbLogWithBLOBs record = new ServiceSbLogWithBLOBs();
		BeanCopy.copyProperties(dto, record);
		if(StringUtils.isNotEmpty(dto.getId())){
			serviceSbLogMapper.updateByPrimaryKeySelective(record);	
		}else{
			record.setCreationTime(new Date());
			serviceSbLogMapper.insert(record);
		}
		
	}

}
