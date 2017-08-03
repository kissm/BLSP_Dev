package com.lpcode.modules.service.impl.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.core.utils.BeanCopy;
import com.lpcode.modules.blsp.entity.PrjTaskMaterialDef;
import com.lpcode.modules.blsp.entity.PrjTaskMaterialDefExample;
import com.lpcode.modules.blsp.mapper.PrjTaskMaterialDefMapper;
import com.lpcode.modules.dto.project.PrjTaskMaterialDefDTO;
import com.lpcode.modules.service.project.inf.PrjTaskMaterialDefService;

@Service
@Transactional(readOnly = true)
public class PrjTaskMaterialDefServiceImpl implements PrjTaskMaterialDefService{

	@Autowired
	private PrjTaskMaterialDefMapper defMapper;
	
	@Override
	public List<PrjTaskMaterialDefDTO> findByTaskId(Long taskId) {
		PrjTaskMaterialDefExample example = new PrjTaskMaterialDefExample();
		PrjTaskMaterialDefExample.Criteria criteria = example.createCriteria();
		criteria.andTaskIdEqualTo(taskId);
		List<PrjTaskMaterialDef> list = defMapper.selectByExample(example);
		List<PrjTaskMaterialDefDTO> result = null;
		if(list != null && list.size()>0 ){
			result = new ArrayList<PrjTaskMaterialDefDTO>();
			BeanCopy.copyPropertiesForList(list, result, PrjTaskMaterialDefDTO.class);
		}
		return result;
	}

	@Override
	public PrjTaskMaterialDefDTO findByPrimaryKey(Long id) {
		PrjTaskMaterialDef prjTaskMaterialDef = defMapper.selectByPrimaryKey(id);
		PrjTaskMaterialDefDTO dto = null;
		if(prjTaskMaterialDef != null){
			dto = new PrjTaskMaterialDefDTO();
			BeanCopy.copyProperties(prjTaskMaterialDef, dto);
		}
		return dto ;
	}

}
