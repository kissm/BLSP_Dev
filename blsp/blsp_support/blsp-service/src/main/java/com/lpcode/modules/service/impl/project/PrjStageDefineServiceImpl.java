/**
 *
 */
package com.lpcode.modules.service.impl.project;

import java.util.ArrayList;
import java.util.List;

import com.framework.core.constants.BaseCode;
import com.lpcode.modules.blsp.entity.PrjTaskDefine;
import com.lpcode.modules.blsp.entity.PrjTaskDefineExample;
import com.lpcode.modules.blsp.mapper.PrjTaskDefineMapper;
import com.lpcode.modules.dto.project.change.PrjTaskDefVO;
import com.lpcode.modules.service.project.dto.pinstance.PrjTaskDefineVo;
import com.lpcode.modules.service.project.dto.stageTaskMaterialDef.PrjStageConTaskListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.core.result.ListResult;
import com.framework.core.result.Result;
import com.framework.core.utils.BeanCopy;
import com.lpcode.modules.blsp.entity.PrjStageDefine;
import com.lpcode.modules.blsp.entity.PrjStageDefineExample;
import com.lpcode.modules.blsp.entity.PrjStageDefineExample.Criteria;
import com.lpcode.modules.blsp.mapper.PrjStageDefineMapper;
import com.lpcode.modules.dto.project.PrjStageDefineDTO;
import com.lpcode.modules.service.project.inf.PrjStageDefineService;

@Service
@Transactional(readOnly = true)
public class PrjStageDefineServiceImpl implements PrjStageDefineService{

	@Autowired
	private PrjStageDefineMapper mapper;
	@Autowired
	private PrjStageDefineMapper prjStageDefineMapper;
	@Autowired
	private PrjTaskDefineMapper prjTaskDefineMapper;

	public ListResult<PrjStageDefineDTO> findListByType(String stageType) {
		
		PrjStageDefineExample example = new PrjStageDefineExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteEqualTo("0");
		criteria.andStageTypeEqualTo(stageType);
		List<PrjStageDefine> list= mapper.selectByExample(example);
		List<PrjStageDefineDTO> dtoList = new ArrayList<PrjStageDefineDTO>();
		if (list != null && list.size() > 0){
			BeanCopy.copyPropertiesForList(list, dtoList,PrjStageDefineDTO.class);
		}
		ListResult<PrjStageDefineDTO> result = new ListResult<PrjStageDefineDTO>();
		result.setObj(dtoList);
		return result;
	}
	
	
	public ListResult<PrjStageDefineDTO> findAllList(){
		PrjStageDefineExample example = new PrjStageDefineExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteEqualTo("0");
		List<PrjStageDefine> list= mapper.selectByExample(example);
		List<PrjStageDefineDTO> dtoList = new ArrayList<PrjStageDefineDTO>();
		if (list != null && list.size() > 0){
			BeanCopy.copyPropertiesForList(list, dtoList,PrjStageDefineDTO.class);
		}
		ListResult<PrjStageDefineDTO> result = new ListResult<PrjStageDefineDTO>();
		result.setObj(dtoList);
		return result;
	}
	
	@Override
	public Result<Long> getNextStageId(Long stageId) {
		Result<Long> result = new Result<Long>();
		PrjStageDefineExample psdExample = new PrjStageDefineExample();
		psdExample.createCriteria().andPreStageIdEqualTo(stageId);
		List<PrjStageDefine> prjStageDefineList = prjStageDefineMapper.selectByExample(psdExample);
		if (prjStageDefineList != null && prjStageDefineList.size() > 0) {
			result.setObj(prjStageDefineList.get(0).getId());
		}
		return result;
	}

	@Override
	public List<PrjStageConTaskListDTO> getTaskBeforStageId(Long stageId) {
		List<PrjStageConTaskListDTO> listDTOs = new ArrayList<PrjStageConTaskListDTO>();
		PrjStageDefine prjStageDefine = prjStageDefineMapper.selectByPrimaryKey(stageId);
		if(prjStageDefine != null){
			PrjStageDefineExample psdExample = new PrjStageDefineExample();
			psdExample.createCriteria().andStageTypeEqualTo(prjStageDefine.getStageType()).andIdLessThan(prjStageDefine.getId());
			List<PrjStageDefine> prjStageDefineList = prjStageDefineMapper.selectByExample(psdExample);
			for(PrjStageDefine p : prjStageDefineList){
				PrjStageConTaskListDTO pDto = new PrjStageConTaskListDTO();
				BeanCopy.copyProperties(p,pDto,PrjStageConTaskListDTO.class);
				PrjTaskDefineExample taskDefineExample = new PrjTaskDefineExample();
				taskDefineExample.createCriteria().andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL).andStageIdEqualTo(p.getId());
				taskDefineExample.setOrderByClause(" ORDER_NUM");
				List<PrjTaskDefine> prjTaskDefineList =  prjTaskDefineMapper.selectByExample(taskDefineExample);
				List<PrjTaskDefVO> prjTaskDefVOList = new ArrayList();
				BeanCopy.copyPropertiesForList(prjTaskDefineList, prjTaskDefVOList,PrjTaskDefVO.class);
				pDto.setTaskDefList(prjTaskDefVOList);
				listDTOs.add(pDto);
			}
		}
		return listDTOs;
	}


}