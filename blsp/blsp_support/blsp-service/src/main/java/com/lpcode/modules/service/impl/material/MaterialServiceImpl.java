package com.lpcode.modules.service.impl.material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framework.core.base.page.Page;
import com.framework.core.constants.BaseCode;
import com.framework.core.result.PageResult;
import com.framework.core.result.RequestDTOPage;
import com.framework.core.utils.BeanCopy;
import com.lpcode.modules.blsp.entity.DimMaterial;
import com.lpcode.modules.blsp.entity.DimMaterialExample;
import com.lpcode.modules.blsp.entity.PrjTaskDefine;
import com.lpcode.modules.blsp.entity.PrjTaskDefineExample;
import com.lpcode.modules.blsp.entity.PrjTaskMaterialDef;
import com.lpcode.modules.blsp.entity.PrjTaskMaterialDefExample;
import com.lpcode.modules.blsp.mapper.DimMaterialMapper;
import com.lpcode.modules.blsp.mapper.PrjTaskDefineMapper;
import com.lpcode.modules.blsp.mapper.PrjTaskMaterialDefMapper;
import com.lpcode.modules.service.material.dto.MaterialDto;
import com.lpcode.modules.service.material.inf.IMaterialService;

/**
 * @author Pengs
 * @package com.lpcode.modules.service.impl.material
 * @fileName MaterialServiceImpl
 * @date 16/2/19.
 */
@Service
public class MaterialServiceImpl implements IMaterialService {
	@Autowired
	DimMaterialMapper dimMaterialMapper;
	@Autowired
	PrjTaskDefineMapper prjTaskDefineMapper;
	@Autowired
	PrjTaskMaterialDefMapper prjTaskMaterialDefMapper;

	@Override
	public PageResult<MaterialDto> findList(RequestDTOPage<MaterialDto> requestPage) {
		MaterialDto req = requestPage.getObj();
		PageResult<MaterialDto> pageResult = new PageResult<>();
		Page<DimMaterial> queryPage = new Page<DimMaterial>();
		queryPage.setPageNo(requestPage.getPage().getPageNo());
		queryPage.setPageSize(requestPage.getPage().getPageSize());

		DimMaterialExample example = new DimMaterialExample();
		DimMaterialExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
		if (StringUtils.isNotBlank(req.getName())) {
			criteria.andNameLike("%" + req.getName().trim() + "%");
		}
		if (StringUtils.isNotBlank(req.getIsValid())) {
			criteria.andIsValidEqualTo(req.getIsValid());
		}
		if (req.getId() != null ) {
			criteria.andIdEqualTo(req.getId());
		}
		example.setOrderByClause(" id");
		Page<DimMaterial> dimMaterialList = dimMaterialMapper.pagedSelectByExample(example, queryPage);
		requestPage.getPage().setList(new ArrayList<MaterialDto>());
		requestPage.getPage().setCount(queryPage.getCount());
		BeanCopy.copyPropertiesForList(dimMaterialList.getList(), requestPage.getPage().getList(), MaterialDto.class);
		pageResult.setObj(requestPage.getPage());
		return pageResult;
	}

	@Override
	public MaterialDto getMaterialById(Long materialId) {
		DimMaterial dimMaterial = dimMaterialMapper.selectByPrimaryKey(materialId);
		MaterialDto materialDto = new MaterialDto();
		if (dimMaterial != null) {
			BeanCopy.copyProperties(dimMaterial, materialDto, MaterialDto.class);
		}
		return materialDto;
	}

	@Override
	public void save(MaterialDto materialDto) {
		DimMaterial dimMaterial = new DimMaterial();
		BeanCopy.copyProperties(materialDto, dimMaterial, DimMaterial.class);
		dimMaterialMapper.insert(dimMaterial);
	}

	@Override
	public void edit(MaterialDto materialDto) {
		DimMaterial dimMaterial = new DimMaterial();
		BeanCopy.copyProperties(materialDto, dimMaterial, DimMaterial.class);
		dimMaterialMapper.updateByPrimaryKey(dimMaterial);
	}

	@Override
	public void delete(Long materialId) {
		DimMaterial dimMaterial = dimMaterialMapper.selectByPrimaryKey(materialId);
		dimMaterial.setIsDelete(BaseCode.DEL_FLAG_DELETE);
		dimMaterialMapper.updateByPrimaryKey(dimMaterial);
	}

	@Override
	public boolean enableMaterialName(String name) {
		DimMaterialExample example = new DimMaterialExample();
		example.createCriteria().andNameEqualTo(name).andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
		List<DimMaterial> dimMaterialList = dimMaterialMapper.selectByExample(example);
		if (dimMaterialList != null && dimMaterialList.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
     * 获取全部材料名称Map
     * @return
     */
	@Override
	public Map<Long, String> getAllMaterName(Long stageId) {
		Map<Long, String> allMap = new HashMap<Long, String>();
		PrjTaskDefineExample example = new PrjTaskDefineExample();
		example.createCriteria().andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL).andIsValidEqualTo("1").andStageIdEqualTo(stageId);
		List<PrjTaskDefine> prjTaskDefList = prjTaskDefineMapper.selectByExample(example);
		List<Long> values = new ArrayList<Long>();
		for(PrjTaskDefine taskDef:prjTaskDefList ){
			values.add(taskDef.getId());	
		}
		PrjTaskMaterialDefExample prjTMDexample = new PrjTaskMaterialDefExample();
		prjTMDexample.createCriteria().andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL).andTaskIdIn(values);
		List<PrjTaskMaterialDef> prjTMDList = prjTaskMaterialDefMapper.selectByExample(prjTMDexample);
		List<Long> materIds = new ArrayList<Long>();
		for(PrjTaskMaterialDef prjTaskMater : prjTMDList ){
			materIds.add(prjTaskMater.getMaterialId());	
		}
		
		DimMaterialExample materExample = new DimMaterialExample();
		materExample.createCriteria().andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL).andIdIn(materIds);
		List<DimMaterial> dimMaterialList = dimMaterialMapper.selectByExample(materExample);
		
		for(DimMaterial dimMater : dimMaterialList){
			String materName = allMap.get(dimMater.getId());
			if(StringUtils.isBlank(materName)){
				allMap.put(dimMater.getId(), dimMater.getName());
			}
		}
		return allMap;
	}


}
