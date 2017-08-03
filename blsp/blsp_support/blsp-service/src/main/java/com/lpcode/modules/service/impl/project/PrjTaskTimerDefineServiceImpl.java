/**
 *
 */
package com.lpcode.modules.service.impl.project;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.core.utils.BeanCopy;
import com.lpcode.modules.blsp.entity.PrjTaskTimerDefine;
import com.lpcode.modules.blsp.entity.PrjTaskTimerDefineExample;
import com.lpcode.modules.blsp.mapper.PrjTaskTimerDefineMapper;
import com.lpcode.modules.dto.project.PrjTaskTimerDefineDTO;
import com.lpcode.modules.service.project.inf.PrjTaskTimerDefineService;

/**
 *
 */
@Service
@Transactional(readOnly = true)
public class PrjTaskTimerDefineServiceImpl implements PrjTaskTimerDefineService {
	
	@Autowired
	private PrjTaskTimerDefineMapper timerMapper;
	
	@Override
	public List<PrjTaskTimerDefineDTO> findByTaskId(Long taskId) {
		PrjTaskTimerDefineExample example = new PrjTaskTimerDefineExample();
		PrjTaskTimerDefineExample.Criteria criteria = example.createCriteria();
		criteria.andTaskIdEqualTo(taskId);
		List<PrjTaskTimerDefine> list = timerMapper.selectByExample(example);
		List<PrjTaskTimerDefineDTO> result = null;
		
		if(list != null && list.size()>0 ){
			result = new ArrayList<PrjTaskTimerDefineDTO>();
			BeanCopy.copyPropertiesForList(list, result, PrjTaskTimerDefineDTO.class);
		}
		return result;
	}

	@Override
	public PrjTaskTimerDefineDTO findByPrimaryKey(Long id) {
		PrjTaskTimerDefine PrjTaskTimerDefine = timerMapper.selectByPrimaryKey(id);
		PrjTaskTimerDefineDTO dto = null;
		if(PrjTaskTimerDefine != null){
			dto = new PrjTaskTimerDefineDTO();
			BeanCopy.copyProperties(PrjTaskTimerDefine, dto);
		}
		return dto ;
	}

	
}