/**
 *
 */
package com.lpcode.modules.service.impl.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.core.base.page.Page;
import com.framework.core.constants.BaseCode;
import com.framework.core.result.ListResult;
import com.framework.core.result.PageResult;
import com.framework.core.result.RequestDTOPage;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.entity.DimMaterial;
import com.lpcode.modules.blsp.entity.PrjStageMaterialDefine;
import com.lpcode.modules.blsp.entity.PrjStageMaterialDefineExample;
import com.lpcode.modules.blsp.entity.PrjTaskDefine;
import com.lpcode.modules.blsp.entity.PrjTaskDefineExample;
import com.lpcode.modules.blsp.entity.PrjTaskDefineExample.Criteria;
import com.lpcode.modules.blsp.entity.PrjTaskDependency;
import com.lpcode.modules.blsp.entity.PrjTaskDependencyExample;
import com.lpcode.modules.blsp.entity.PrjTaskMaterialDef;
import com.lpcode.modules.blsp.entity.PrjTaskMaterialDefExample;
import com.lpcode.modules.blsp.entity.PrjTaskTimerDefine;
import com.lpcode.modules.blsp.entity.PrjTaskTimerDefineExample;
import com.lpcode.modules.blsp.mapper.DimMaterialMapper;
import com.lpcode.modules.blsp.mapper.PrjStageMaterialDefineMapper;
import com.lpcode.modules.blsp.mapper.PrjTaskDefineMapper;
import com.lpcode.modules.blsp.mapper.PrjTaskDependencyMapper;
import com.lpcode.modules.blsp.mapper.PrjTaskMaterialDefMapper;
import com.lpcode.modules.blsp.mapper.PrjTaskTimerDefineMapper;
import com.lpcode.modules.dto.project.PrjTaskDefineDTO;
import com.lpcode.modules.dto.project.PrjTaskMaterialDefDTO;
import com.lpcode.modules.dto.project.PrjTaskTimerDefineDTO;
import com.lpcode.modules.service.project.inf.PrjTaskDefineService;

/**
 * 项目事项定义表表ServiceImpl
 *
 * @author wangyazhou
 *
 */
@Service
@Transactional(readOnly = true)
public class PrjTaskDefineServiceImpl implements PrjTaskDefineService {

	@Autowired
	private PrjTaskDefineMapper mapper;

	@Autowired
	private PrjTaskMaterialDefMapper defMapper;

	@Autowired
	private PrjStageMaterialDefineMapper materialDefineMapper;

	@Autowired
	private PrjTaskDependencyMapper dependencyMapper;

	@Autowired
	private PrjTaskTimerDefineMapper timerMapper;
	
	@Autowired
	DimMaterialMapper dimMaterialMapper;

	/**
	 * 根据部门ID查询项目事项名称
	 *
	 * @param deptIds
	 *            事项所属部门ID
	 * @return
	 */
	@Override
	public ListResult<PrjTaskDefineDTO> findTaskNameByDepyIds(List<String> deptIds) {
		ListResult<PrjTaskDefineDTO> resultList = new ListResult<PrjTaskDefineDTO>();
		List<PrjTaskDefineDTO> dtoList = new ArrayList<PrjTaskDefineDTO>();
		PrjTaskDefineDTO dto = null;

		PrjTaskDefineExample example = new PrjTaskDefineExample();
		PrjTaskDefineExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeleteEqualTo("0");
		criteria.andDeptIdIn(deptIds);
		List<PrjTaskDefine> list = mapper.selectByExample(example);

		if (list != null && list.size() > 0) {
			for (PrjTaskDefine t : list) {
				dto = new PrjTaskDefineDTO();
				dto.setId(t.getId());
				dto.setTaskName(t.getTaskName());
				dtoList.add(dto);
			}
		}
		resultList.setObj(dtoList);
		return resultList;
	}

	@Override
	public PageResult<PrjTaskDefineDTO> findList(RequestDTOPage<PrjTaskDefineDTO> para) {

		PageResult<PrjTaskDefineDTO> pageResult = new PageResult<PrjTaskDefineDTO>();

		Page<PrjTaskDefine> queryPage = new Page<PrjTaskDefine>();
		PrjTaskDefineDTO vo = new PrjTaskDefineDTO();
		queryPage.setPageNo(para.getPage().getPageNo());
		queryPage.setPageSize(para.getPage().getPageSize());
		if (para.getObj() != null) {
			BeanCopy.copyProperties(para.getObj(), vo);
		}

		PrjTaskDefineExample example = new PrjTaskDefineExample();

		Criteria Criteria = example.createCriteria();
		if (StringUtils.isNotBlank(para.getObj().getTaskCode())) {
			Criteria.andTaskCodeLike("%" + para.getObj().getTaskCode() + "%");
		}

		if (StringUtils.isNotBlank(para.getObj().getTaskName())) {
			Criteria.andTaskNameLike("%" + para.getObj().getTaskName() + "%");
		}

		 if(para.getObj().getStageId() != null){
			 Criteria.andStageIdEqualTo(para.getObj().getStageId());
		 }

		if (StringUtils.isNotBlank(para.getObj().getDeptId())) {
			Criteria.andDeptIdEqualTo(para.getObj().getDeptId());
		}

		if (StringUtils.isNotBlank(para.getObj().getIsValid())) {
			Criteria.andIsValidEqualTo(para.getObj().getIsValid());
		}

		Criteria.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
		example.setOrderByClause(" ORDER_NUM ");

		Page<PrjTaskDefine> pageList = mapper.pagedSelectByExample(example, queryPage);
		List<PrjTaskDefine> list = pageList.getList();
		List<PrjTaskDefineDTO> dtoList = new ArrayList<PrjTaskDefineDTO>();
		if (list != null && list.size() > 0) {
			BeanCopy.copyPropertiesForList(list, dtoList, PrjTaskDefineDTO.class);
		}
		para.getPage().setList(dtoList);
		para.getPage().setCount(mapper.countByExample(example));
		pageResult.setObj(para.getPage());
		return pageResult;
	}

	@Override
	public int countBy(RequestDTOPage<PrjTaskDefineDTO> para) {
		PrjTaskDefineExample example = new PrjTaskDefineExample();
		return mapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(PrjTaskDefineDTO prjTaskDefineDTO) {
		PrjTaskDefine target = new PrjTaskDefine();

		if (prjTaskDefineDTO != null) {
			BeanCopy.copyProperties(prjTaskDefineDTO, target);
			target.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
			target.setCreatTime(new Date());
			target.setUpdateTime(new Date());
			mapper.insert(target);
			List<PrjTaskMaterialDefDTO> materialDefs = prjTaskDefineDTO.getMaterialDefs();
			if (materialDefs != null && materialDefs.size() > 0) {
				PrjTaskMaterialDefExample example = new PrjTaskMaterialDefExample();
				example.createCriteria().andTaskIdEqualTo(target.getId());
				defMapper.deleteByExample(example);
				for (PrjTaskMaterialDefDTO defDto : materialDefs) {
					defDto.setTaskId(target.getId());
					defDto.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
					defDto.setCreatTime(new Date());
					defDto.setUpdateTime(new Date());
					PrjTaskMaterialDef defTarget = new PrjTaskMaterialDef();
					BeanCopy.copyProperties(defDto, defTarget);
					defMapper.insert(defTarget);
				}
			}
			List<PrjTaskTimerDefineDTO> timerList = prjTaskDefineDTO.getTimerList();
			if (timerList != null && timerList.size() > 0) {
				PrjTaskTimerDefineExample timerExample = new PrjTaskTimerDefineExample();
				timerExample.createCriteria().andTaskIdEqualTo(target.getId());
				timerMapper.deleteByExample(timerExample);
				for (PrjTaskTimerDefineDTO timerDto : timerList) {
					timerDto.setTaskId(target.getId());
					timerDto.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
					timerDto.setCreatTime(new Date());
					timerDto.setUpdateTime(new Date());
					PrjTaskTimerDefine timerTarget = new PrjTaskTimerDefine();
					BeanCopy.copyProperties(timerDto, timerTarget);
					timerMapper.insert(timerTarget);
				}
			}

			if (StringUtils.isNotEmpty(prjTaskDefineDTO.getDepTaskId())) {
				// prjTaskDefineDTO
//				PrjTaskDependency record = new PrjTaskDependency();
//				record.setDepTaskId(Long.parseLong(prjTaskDefineDTO.getDepTaskId()));
//				record.setTaskId(target.getId());
//				record.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
//				record.setCreatTime(new Date());
//				record.setUpdateTime(new Date());
//				dependencyMapper.insert(record);
				String[] depTaskIds = prjTaskDefineDTO.getDepTaskId().split(",");
				for(int i=0;i<depTaskIds.length;i++){
					PrjTaskDependency record = new PrjTaskDependency();
					record.setDepTaskId(Long.parseLong(depTaskIds[i]));
					record.setTaskId(target.getId());
					record.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
					record.setCreatTime(new Date());
					record.setUpdateTime(new Date());
					dependencyMapper.insert(record);
				}
			}
		}
		// sumStageMaterial(target.getStageId());

		// 保存阶段材料表
		saveStageMaterial(target.getStageId());

	}

	@Override
	@Transactional(readOnly = false)
	public void update(PrjTaskDefineDTO prjTaskDefineDTO) {
		PrjTaskDefine target = new PrjTaskDefine();
		if (prjTaskDefineDTO != null && prjTaskDefineDTO.getId() != null) {
			BeanCopy.copyProperties(prjTaskDefineDTO, target);
			mapper.updateByPrimaryKey(target);

			List<PrjTaskMaterialDefDTO> materialDefs = prjTaskDefineDTO.getMaterialDefs();
			if (materialDefs != null && materialDefs.size() > 0) {
				PrjTaskMaterialDefExample example = new PrjTaskMaterialDefExample();
				example.createCriteria().andTaskIdEqualTo(target.getId());
				defMapper.deleteByExample(example);
				for (PrjTaskMaterialDefDTO defDto : materialDefs) {
					defDto.setTaskId(target.getId());
					defDto.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
					defDto.setCreatTime(new Date());
					defDto.setUpdateTime(new Date());
					PrjTaskMaterialDef defTarget = new PrjTaskMaterialDef();
					BeanCopy.copyProperties(defDto, defTarget);
					defMapper.insert(defTarget);
				}
			}

			List<PrjTaskTimerDefineDTO> timerList = prjTaskDefineDTO.getTimerList();
			if (timerList != null && timerList.size() > 0) {
				PrjTaskTimerDefineExample timerExample = new PrjTaskTimerDefineExample();
				timerExample.createCriteria().andTaskIdEqualTo(target.getId());
				timerMapper.deleteByExample(timerExample);
				for (PrjTaskTimerDefineDTO timerDto : timerList) {
					timerDto.setTaskId(target.getId());
					timerDto.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
					timerDto.setCreatTime(new Date());
					timerDto.setUpdateTime(new Date());
					PrjTaskTimerDefine timerTarget = new PrjTaskTimerDefine();
					BeanCopy.copyProperties(timerDto, timerTarget);
					timerMapper.insert(timerTarget);
				}
			}
			
			PrjTaskDependencyExample example = new PrjTaskDependencyExample();
			example.createCriteria().andTaskIdEqualTo(prjTaskDefineDTO.getId());
			dependencyMapper.deleteByExample(example);
			
			if (StringUtils.isNotEmpty(prjTaskDefineDTO.getDepTaskId())) {
				String[] depTaskIds = prjTaskDefineDTO.getDepTaskId().split(",");
				for(int i=0;i<depTaskIds.length;i++){
					PrjTaskDependency record = new PrjTaskDependency();
					record.setDepTaskId(Long.parseLong(depTaskIds[i]));
					record.setTaskId(target.getId());
					record.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
					record.setCreatTime(new Date());
					record.setUpdateTime(new Date());
					dependencyMapper.insert(record);
				}
			}
		}

		// sumStageMaterial(target.getStageId());

		// 保存阶段材料表
		saveStageMaterial(target.getStageId());

	}

	private void saveStageMaterial(Long stageId) {
		PrjStageMaterialDefineExample smexample = new PrjStageMaterialDefineExample();
		smexample.createCriteria().andStageIdEqualTo(stageId);
		materialDefineMapper.deleteByExample(smexample);
		
		List<DimMaterial> dimMaterialList = dimMaterialMapper.selectByExample(null);
		Map<Long,String> dimMaterialMap = new HashMap<Long,String>();
		if(dimMaterialList!=null && dimMaterialList.size()>0){
			for(DimMaterial dim:dimMaterialList ){
				dimMaterialMap.put(dim.getId(),dim.getIsOriginalCumulation());
			}
		}

		PrjTaskDefineExample tdExample = new PrjTaskDefineExample();
		PrjTaskDefineExample.Criteria tdcriteria = tdExample.createCriteria();
		tdcriteria.andStageIdEqualTo(stageId);
		tdcriteria.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
		List<PrjTaskDefine> tdList = mapper.selectByExample(tdExample);
		
		Map<Long,PrjStageMaterialDefine> materialsMap = new HashMap<>();
		
		PrjStageMaterialDefine stageMaterial =  null;
		if (tdList != null && tdList.size() > 0) {
			for (PrjTaskDefine td : tdList) {
				PrjTaskMaterialDefExample tmexample = new PrjTaskMaterialDefExample();
				tmexample.createCriteria().andTaskIdEqualTo(td.getId());
				List<PrjTaskMaterialDef> tmList = defMapper.selectByExample(tmexample);
				if (tmList != null && tmList.size() > 0) {
					for (PrjTaskMaterialDef tm : tmList) {
						Long tmid = tm.getMaterialId();
						if(materialsMap.containsKey(tmid)){
							stageMaterial = materialsMap.get(tmid);
							stageMaterial.setCopyNum(stageMaterial.getCopyNum() + tm.getCopyNum());
							if("1".equals(dimMaterialMap.get(tmid))){
								stageMaterial.setOriginalNum(stageMaterial.getOriginalNum() + tm.getOriginalNum());
							}else{
								stageMaterial.setOriginalNum(stageMaterial.getOriginalNum() | tm.getOriginalNum());
							}
						}else{
							stageMaterial = new PrjStageMaterialDefine();
							stageMaterial.setStageId(stageId);
							stageMaterial.setMaterialId(tmid);
							stageMaterial.setCopyNum(tm.getCopyNum());
							stageMaterial.setOriginalNum(tm.getOriginalNum());
							stageMaterial.setIsDelete("0");
							stageMaterial.setCreatTime(new Date());
							stageMaterial.setUpdateTime(new Date());
							stageMaterial.setCreator(UserUtils.getUser().getId());
							stageMaterial.setUpdator(UserUtils.getUser().getId());
							materialsMap.put(tmid,stageMaterial);
						}
					}
				}
			}
		}
		if(materialsMap !=null && materialsMap.size()>0){
			Iterator<Long> it =  materialsMap.keySet().iterator();
			while(it.hasNext()){
				materialDefineMapper.insertSelective(materialsMap.get(it.next()));
			}
			
		}
	}

	/**
	private void sumStageMaterial(Long stageId) {
		List<PrjTaskDefineSumMaterialVO> list = sumMaterialMapper.sumMaterialByStageId(stageId);
		List<PrjStageMaterialDefine> mList = new ArrayList<PrjStageMaterialDefine>();
		PrjStageMaterialDefineExample example = new PrjStageMaterialDefineExample();
		example.createCriteria().andStageIdEqualTo(stageId);
		materialDefineMapper.deleteByExample(example);
		if (list != null && list.size() > 0) {
			BeanCopy.copyPropertiesForList(list, mList, PrjStageMaterialDefine.class);
			for (PrjStageMaterialDefine define : mList) {
				define.setCreatTime(new Date());
				define.setUpdateTime(new Date());
				define.setCreator(UserUtils.getUser().getId());
				define.setIsDelete("0");
				define.setUpdator(UserUtils.getUser().getId());
				materialDefineMapper.insertSelective(define);
			}
		}

	}
	 */

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		if (id != null) {
			PrjTaskDefine target = mapper.selectByPrimaryKey(id);
			target.setIsDelete(BaseCode.DEL_FLAG_DELETE);
			mapper.updateByPrimaryKey(target);
			// sumStageMaterial(target.getStageId());

			// 保存阶段材料表
			saveStageMaterial(target.getStageId());

		}
	}

	@Override
	public void enable(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disable(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> findAllDeptIdOfTask() {
		PrjTaskDefineExample example = new PrjTaskDefineExample();
		example.createCriteria();
		List<PrjTaskDefine> prjTaskDefineList = mapper.selectByExample(example);
		Map<String,PrjTaskDefine> prjTaskDefineMap = new HashMap<>();
		for(PrjTaskDefine prjTaskDefine : prjTaskDefineList){
			prjTaskDefineMap.put(prjTaskDefine.getDeptId(),prjTaskDefine);
		}
		List<String> deptIdList = new ArrayList<String>();
		Iterator it = prjTaskDefineMap.keySet().iterator();
		while(it.hasNext()){
			String key = (String) it.next();
			deptIdList.add(key);
		}
		return deptIdList;
	}

	@Override
	public PrjTaskDefineDTO findByPrimaryKey(Long id) {
		PrjTaskDefine target = mapper.selectByPrimaryKey(id);
		PrjTaskDefineDTO dto = null;
		if (target != null) {
			dto = new PrjTaskDefineDTO();
			BeanCopy.copyProperties(target, dto);
		}
		return dto;
	}

	@Override
	public ListResult<PrjTaskDefineDTO> findReplyList(PrjTaskDefineDTO para) {
		ListResult<PrjTaskDefineDTO> result = new ListResult<PrjTaskDefineDTO>();
		PrjTaskDefineExample example = new PrjTaskDefineExample();
		PrjTaskDefineExample.Criteria criteria = example.createCriteria();
		if (para.getId() != null) {
			criteria.andIdNotEqualTo(para.getId());
		}
		criteria.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
		criteria.andIsValidEqualTo("1");
		List<PrjTaskDefine> list = mapper.selectByExample(example);
		List<PrjTaskDefineDTO> dtoList = null;
		if (list != null && list.size() > 0) {
			dtoList = new ArrayList<PrjTaskDefineDTO>();
			BeanCopy.copyPropertiesForList(list, dtoList, PrjTaskDefineDTO.class);
		}
		result.setObj(dtoList);
		return result;
	}

	@Override
	public ListResult<Long> findDependencyList(PrjTaskDefineDTO para) {
		ListResult<Long> result = new ListResult<Long>();
		if (para.getId() != null) {
			PrjTaskDependencyExample dependencyexample = new PrjTaskDependencyExample();
			PrjTaskDependencyExample.Criteria dc = dependencyexample.createCriteria();
			dc.andTaskIdEqualTo(para.getId());
			dc.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
			List<PrjTaskDependency> dlist = dependencyMapper.selectByExample(dependencyexample);
			List<Long> dResultList = new ArrayList<Long>();
			if (dlist != null && dlist.size() > 0) {
				for (PrjTaskDependency d : dlist) {
					dResultList.add(d.getDepTaskId());
				}
			}
			result.setObj(dResultList);
		}
		return result;
	}

	@Override
	public PrjTaskDefineDTO findByTaskCode(PrjTaskDefineDTO task) {
		PrjTaskDefineExample example = new PrjTaskDefineExample();
		PrjTaskDefineExample.Criteria criteria = example.createCriteria();
		criteria.andTaskCodeEqualTo(task.getTaskCode());
		criteria.andTaskTypeEqualTo(task.getTaskType());
		if(task.getId() != null){
			criteria.andIdNotEqualTo(task.getId());
		}
		List<PrjTaskDefine> list = mapper.selectByExample(example);
		if (list != null  && list.size()==1) {
			PrjTaskDefineDTO dto = new PrjTaskDefineDTO();
			BeanCopy.copyProperties(list.get(0), dto);
			return dto;
		}
		return null;
	}
}