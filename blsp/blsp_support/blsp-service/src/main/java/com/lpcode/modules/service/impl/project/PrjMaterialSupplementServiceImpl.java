package com.lpcode.modules.service.impl.project;

import java.util.*;

import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.entity.*;
import com.lpcode.modules.blsp.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.core.utils.BeanCopy;
import com.lpcode.modules.blsp.entity.PrjMaterialSupplementExample.Criteria;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjMaterialSupplementVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjStageMaterialVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjTaskVo;
import com.lpcode.modules.service.project.inf.PrjMaterialSupplementService;

/**
 * 网上办事补充材料    业务层实例
 * @project blsp-service
 * @package com.lpcode.modules.service.impl.project
 * @author maxiaowei
 * @createDate 2016年7月29日 上午9:23:23
 */
@Service
@Transactional(readOnly = false)
public class PrjMaterialSupplementServiceImpl implements PrjMaterialSupplementService {

	@Autowired
	private PrjMaterialSupplementMapper prjMaterialSupplementMapper;
	@Autowired
	private PrjInstanceMapper prjInstanceMapper;
	@Autowired
	private PrjStageMapper prjStageMapper;
	@Autowired
	private PrjTaskMapper prjTaskMapper;
	@Autowired
	private PrjTaskDefineMapper prjTaskDefineMapper;
	@Autowired
	private PrjStageMaterialMapper prjStageMaterialMapper;
	@Autowired
	private PrjTaskMaterialDefMapper prjTaskMaterialDefMapper;
	@Autowired
	private DimMaterialMapper dimMaterialMapper;


	/**
	 * 通过网上办事项目信息获取到补齐材料表中该项目下的列表
	 */
	@Override
	public List<PrjMaterialSupplementVo> getListByPrjId(PrjInstanceVo prjInstanceVo) {
		List<PrjMaterialSupplementVo> list = new ArrayList<PrjMaterialSupplementVo>();
		PrjMaterialSupplementExample example = new PrjMaterialSupplementExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteEqualTo("0");
		//保证获取的是未处理的材料对象（列表中材料ID不会重复）
		criteria.andProcessStatusEqualTo("0");
		criteria.andPrjIdEqualTo(prjInstanceVo.getId());
		criteria.andStageIdEqualTo(prjInstanceVo.getStageId());
		//获取网上办事项目下的补齐材料的列表信息
		List<PrjMaterialSupplement> supplementList = prjMaterialSupplementMapper.selectByExample(example);
		if(supplementList != null && !"".equals(supplementList)){
			//通过网上办事项目中的项目编号和ID确定出并联审批项目中的项目信息
			com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
			com.lpcode.modules.blsp.entity.PrjInstanceExample.Criteria prjCriteria = prjExample.createCriteria();
			prjCriteria.andIsDeleteEqualTo("0");
			prjCriteria.andPrjCodeEqualTo(prjInstanceVo.getPrjCode());
			prjCriteria.andIdNotEqualTo(prjInstanceVo.getId());
			List<PrjInstance> prjList = prjInstanceMapper.selectByExample(prjExample);
			String blspId = null;
			if(prjList != null && prjList.size() > 0){
				blspId = prjList.get(0).getId().toString();
			}
			BeanCopy.copyPropertiesForList(supplementList, list, PrjMaterialSupplementVo.class);
			for (PrjMaterialSupplementVo prjMaterialSupplementVo : list) {
				//将并联审批项目ID存入以便使用
				prjMaterialSupplementVo.setBlspPrjId(blspId);
			}
		}
		return list;
	}

	@Override
	public Long getBatchNo(PrjInstanceVo prjInstanceVo) {
		Long batchNo = 1L;
		List<PrjMaterialSupplementVo> list = new ArrayList<PrjMaterialSupplementVo>();
		PrjMaterialSupplementExample example = new PrjMaterialSupplementExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteEqualTo("0");
		criteria.andPrjIdEqualTo(prjInstanceVo.getId());
		criteria.andStageIdEqualTo(prjInstanceVo.getStageId());
		example.setOrderByClause(" BATCH_NO DESC ");
		//获取网上办事项目下的补齐材料的列表信息
		List<PrjMaterialSupplement> supplementList = prjMaterialSupplementMapper.selectByExample(example);
		if(supplementList != null && supplementList.size() > 0){
			batchNo = supplementList.get(0).getBatchNo() + 1;
		}
		return batchNo;
	}

	/**
	 * 修改单条数据
	 */
	@Override
	public void updatePrjMaterialSupplement(PrjMaterialSupplementVo prjMaterialSupplementVo) {
		PrjMaterialSupplement record = new PrjMaterialSupplement();
		BeanCopy.copyProperties(prjMaterialSupplementVo, record, PrjMaterialSupplement.class);
		prjMaterialSupplementMapper.updateByPrimaryKey(record);
	}

	/**
	 * 通过项目材料列表以及项目基本信息添加信息
	 */
	@Override
	public void saveByPrjStageMaterialVoList(Map<Long,PrjStageMaterialVo> map, int batchNo, PrjInstanceVo vo, List<Long> materialIds) {
		List<PrjMaterialSupplement> records = new ArrayList<PrjMaterialSupplement>();
		List<PrjStageMaterialVo> list = null;
		if (list != null && list.size() > 0) {
			PrjStage state = new PrjStage();
			state.setStageId(vo.getStageId());
			state.setPrjId(vo.getId());
			state = prjStageMapper.selectOneByEntitySelective(state);
			BeanCopy.copyPropertiesForList(list, records, PrjMaterialSupplement.class);
			if(materialIds != null && materialIds.size() > 0){
				for (int i=0;i<records.size();i++) {
					for (Long mId : materialIds) {
						if(records.get(i).getMaterialId().equals(mId)){
							records.remove(i);
						}
					}
				}
			}
			for (PrjMaterialSupplement m : records) {
				m.setPrjId(vo.getId());
				m.setStageId(vo.getStageId());
				m.setCreatTime(new Date());
				m.setPrjStageInstId(state.getId());
				m.setStageId(vo.getStageId());
				m.setUpdateTime(new Date());
				m.setBatchNo(Long.valueOf(batchNo));
				m.setProcessStatus("0");
				m.setIsDelete("0");
				if (m.getIsComplete() == null) {
					m.setIsComplete("1");
				}
				prjMaterialSupplementMapper.insert(m);
			}
		}
	}

	/**
	 * 获取对应并联审批项目中事项状态
	 */
	@Override
	public List<PrjTaskVo> getPrjTaskVoList(PrjInstanceVo prjInstanceVo) {
		//通过网上办事项目中的项目编号和ID确定出并联审批项目中的项目信息
		com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
		com.lpcode.modules.blsp.entity.PrjInstanceExample.Criteria prjCriteria = prjExample.createCriteria();
		prjCriteria.andIsDeleteEqualTo("0");
		prjCriteria.andPrjCodeEqualTo(prjInstanceVo.getPrjCode());
		prjCriteria.andIdNotEqualTo(prjInstanceVo.getId());
		List<PrjInstance> prjList = prjInstanceMapper.selectByExample(prjExample);
		List<PrjTaskVo> list = null;
		if(prjList != null && prjList.size() > 0){
			PrjTask define = new PrjTask();
	        define.setPrjId(prjList.get(0).getId());
	        //获取并联审批项目事项列表信息
	        List<PrjTask> taskList = prjTaskMapper.selectByEntitySelective(define);
	        if (taskList != null){
	        	list = new ArrayList<PrjTaskVo>();
	            BeanCopy.copyPropertiesForList(taskList, list, PrjTaskVo.class);
	        }
		}
		return list;
	}

	@Override
	public void savePrjMaterialSupplement(PrjMaterialSupplementVo prjMaterialSupplementVo, String materialAddr, int batchNo) {
		PrjMaterialSupplement prjMaterialSupplement = new PrjMaterialSupplement();
		BeanCopy.copyProperties(prjMaterialSupplementVo, prjMaterialSupplement);
		prjMaterialSupplement.setId(null);
		prjMaterialSupplement.setCreatTime(new Date());
		prjMaterialSupplement.setUpdateTime(new Date());
		prjMaterialSupplement.setMaterialAddr(materialAddr);
		prjMaterialSupplement.setBatchNo(Long.valueOf(batchNo));
		prjMaterialSupplementMapper.insert(prjMaterialSupplement);
	}

	/**
	 * 获取并联审批对应材料列表
	 */
	@Override
	public List<PrjStageMaterialVo> getBlspMaterialList(PrjInstanceVo prjInstanceVo) {
		List<PrjStageMaterialVo> list = null;
		com.lpcode.modules.blsp.entity.PrjInstanceExample prjExample = new com.lpcode.modules.blsp.entity.PrjInstanceExample();
		com.lpcode.modules.blsp.entity.PrjInstanceExample.Criteria prjCriteria = prjExample.createCriteria();
		prjCriteria.andIsDeleteEqualTo("0");
		prjCriteria.andPrjCodeEqualTo(prjInstanceVo.getPrjCode());
		prjCriteria.andIdNotEqualTo(prjInstanceVo.getId());
		List<PrjInstance> prjList = prjInstanceMapper.selectByExample(prjExample);
		if(prjList != null && prjList.size() > 0){
			list = new ArrayList<PrjStageMaterialVo>();
			com.lpcode.modules.blsp.entity.PrjStageMaterialExample example = new com.lpcode.modules.blsp.entity.PrjStageMaterialExample();
			com.lpcode.modules.blsp.entity.PrjStageMaterialExample.Criteria criteria = example.createCriteria();
			criteria.andPrjIdEqualTo(prjList.get(0).getId());
			List<PrjStageMaterial> mList = prjStageMaterialMapper.selectByExample(example);
			BeanCopy.copyPropertiesForList(mList, list, PrjStageMaterialVo.class);
		}
		return list;
	}

	@Override
	public void saveOrUpdateSouceMaterial(PrjStageMaterialVo changeVo, PrjInstanceVo prjInstanceVo) {
		PrjStage state = new PrjStage();
		state.setStageId(prjInstanceVo.getStageId());
		state.setPrjId(prjInstanceVo.getId());
		state = prjStageMapper.selectOneByEntitySelective(state);
		PrjStageMaterialVo souceVo = new PrjStageMaterialVo();
		souceVo.setId(null);
		souceVo.setOriginalNum(changeVo.getOriginalNum());
		souceVo.setCopyNum(changeVo.getCopyNum());
		souceVo.setIsMandatory(changeVo.getIsMandatory());
		souceVo.setTaskId(changeVo.getTaskId());
		souceVo.setPrjId(prjInstanceVo.getId());
		souceVo.setPrjStageInstId(state.getId());
		souceVo.setStageId(prjInstanceVo.getStageId());
		souceVo.setMaterialId(changeVo.getMaterialId());
		PrjStageMaterial prjStageMaterial = prjStageMaterialMapper.selectOneByEntitySelective(souceVo);

		if(prjStageMaterial == null){
			souceVo.setMaterialAddr(changeVo.getMaterialAddr());
			souceVo.setCreatTime(new Date());
			souceVo.setUpdateTime(new Date());
			souceVo.setIsComplete("1");
			souceVo.setIsDelete("0");
			prjStageMaterialMapper.insert(souceVo);
		}else{
			prjStageMaterial.setMaterialAddr(changeVo.getMaterialAddr());
			prjStageMaterial.setIsComplete("1");
			prjStageMaterial.setUpdateTime(new Date());
			prjStageMaterialMapper.updateByPrimaryKey(prjStageMaterial);
		}
	}

	@Override
	public void insertNewBatchRecord(PrjStageMaterialVo changeVo, PrjInstanceVo prjInstanceVo ,Long batchNo) {
		PrjStage state = new PrjStage();
		state.setStageId(prjInstanceVo.getStageId());
		state.setPrjId(prjInstanceVo.getId());
		state = prjStageMapper.selectOneByEntitySelective(state);
		PrjMaterialSupplement prjMaterialSupplement = new PrjMaterialSupplement();
		BeanCopy.copyProperties(changeVo,prjMaterialSupplement,PrjMaterialSupplement.class);
		prjMaterialSupplement.setId(null);
		prjMaterialSupplement.setMaterialAddr(null);
		prjMaterialSupplement.setMaterialId(changeVo.getMaterialId());
		prjMaterialSupplement.setPrjId(prjInstanceVo.getId());
		prjMaterialSupplement.setPrjStageInstId(state.getId());
		prjMaterialSupplement.setStageId(prjInstanceVo.getStageId());
		prjMaterialSupplement.setIsComplete("1");
		PrjMaterialSupplement beforeSupple = prjMaterialSupplementMapper.selectOneByEntitySelective(prjMaterialSupplement);
		if(beforeSupple != null && beforeSupple.getProcessStatus().equals("0")){
			beforeSupple.setProcessStatus("3");
			prjMaterialSupplementMapper.updateByPrimaryKey(beforeSupple);
		}
		prjMaterialSupplement.setProcessStatus("0");
		prjMaterialSupplement.setMaterialAddr(changeVo.getMaterialAddr());
		prjMaterialSupplement.setCreatTime(new Date());
		prjMaterialSupplement.setUpdateTime(new Date());
		prjMaterialSupplement.setBatchNo(batchNo);
		prjMaterialSupplement.setIsDelete("0");
		prjMaterialSupplementMapper.insert(prjMaterialSupplement);
	}

	@Override
	public List<PrjMaterialSupplementVo> getListByPrjCode(PrjInstanceVo prjInstanceVo) {
		String blspId = prjInstanceVo.getId().toString();
		PrjInstance selectRecord = new PrjInstance();
		selectRecord.setPrjCode(prjInstanceVo.getPrjCode());
		selectRecord.setChannel("1");
		selectRecord = prjInstanceMapper.selectOneByEntitySelective(selectRecord) ;

		List<PrjMaterialSupplementVo> list = new ArrayList<PrjMaterialSupplementVo>();
		PrjMaterialSupplementExample example = new PrjMaterialSupplementExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteEqualTo("0");
		//保证获取的是未处理的材料对象（列表中材料ID不会重复）
		criteria.andProcessStatusEqualTo("0");

		criteria.andPrjIdEqualTo(selectRecord.getId());
		criteria.andStageIdEqualTo(selectRecord.getStageId());
		//获取网上办事项目下的补齐材料的列表信息
		List<PrjMaterialSupplement> supplementList = prjMaterialSupplementMapper.selectByExample(example);
		List<Long> materialIds = new ArrayList<Long>();
		if(supplementList != null && !"".equals(supplementList)){
			BeanCopy.copyPropertiesForList(supplementList, list, PrjMaterialSupplementVo.class);
			for (PrjMaterialSupplementVo prjMaterialSupplementVo : list) {
				materialIds.add(prjMaterialSupplementVo.getMaterialId());
				//将并联审批项目ID存入以便使用
				prjMaterialSupplementVo.setBlspPrjId(blspId);
			}
		}

		//给材料加上名称
		DimMaterialExample dimMaterialExample = new DimMaterialExample();
		dimMaterialExample.createCriteria().andIdIn(materialIds);
		List<DimMaterial> dimMaterialList = dimMaterialMapper.selectByExample(dimMaterialExample);
		Map<Long,String> materialNames = new HashMap<Long,String>();
		for(DimMaterial dimMaterial : dimMaterialList){
			materialNames.put(dimMaterial.getId(),dimMaterial.getName());
		}
		for (PrjMaterialSupplementVo prjMaterialSupplementVo : list) {
			prjMaterialSupplementVo.setMaterialName(materialNames.get(prjMaterialSupplementVo.getMaterialId()));
		}

		return list;
	}

	@Override
	public boolean existsSuplementMaterial(PrjInstanceVo prjInstanceVo) {
		PrjInstance selectRecord = new PrjInstance();
		selectRecord.setPrjCode(prjInstanceVo.getPrjCode());
		selectRecord.setChannel("1");
		selectRecord = prjInstanceMapper.selectOneByEntitySelective(selectRecord) ;

		if(selectRecord != null){
			List<PrjMaterialSupplementVo> list = new ArrayList<PrjMaterialSupplementVo>();
			PrjMaterialSupplementExample example = new PrjMaterialSupplementExample();
			Criteria criteria = example.createCriteria();
			criteria.andIsDeleteEqualTo("0");
			//保证获取的是未处理的材料对象（列表中材料ID不会重复）
			criteria.andProcessStatusEqualTo("0");

			criteria.andPrjIdEqualTo(selectRecord.getId());
			criteria.andStageIdEqualTo(selectRecord.getStageId());
			//获取网上办事项目下的补齐材料的列表信息
			List<PrjMaterialSupplement> supplementList = prjMaterialSupplementMapper.selectByExample(example);
			if(supplementList != null && supplementList.size() > 0){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean subMaterial(Long supplementMaterId) {
		PrjMaterialSupplement materialSupplement = prjMaterialSupplementMapper.selectByPrimaryKey(supplementMaterId);
		if(materialSupplement == null){
			return false;
		}
		//通过补交材料得到对应的网厅提交项目
		PrjInstance wsbsInstance = prjInstanceMapper.selectByPrimaryKey(materialSupplement.getPrjId());
		//得到并联审批受理中的项目数据
		PrjInstance blspInstance = new PrjInstance();
		blspInstance.setPrjCode(wsbsInstance.getPrjCode());
		blspInstance.setChannel("0");
		blspInstance = prjInstanceMapper.selectOneByEntitySelective(blspInstance) ;
		//选出此材料此阶段的材料数据配置,然后循环list 去找出
		//得出ID下的所有材料事项配置,从而得到Task的配置列表
		PrjTaskMaterialDefExample taskMaterDefExample = new PrjTaskMaterialDefExample();
		taskMaterDefExample.createCriteria().andMaterialIdEqualTo(materialSupplement.getMaterialId());
		List<PrjTaskMaterialDef> taskMaterialDefList = prjTaskMaterialDefMapper.selectByExample(taskMaterDefExample);

		//得到当前阶段的事项
		PrjTaskExample example = new PrjTaskExample();
		example.createCriteria().andPrjIdEqualTo(blspInstance.getId());
		List<PrjTask> taskList = prjTaskMapper.selectByExample(example);
		Map<Long,PrjTask> taskMap = new HashMap<>();
		for(PrjTask task : taskList){
			taskMap.put(task.getTaskId(),task);
		}
		int temp = 0;//计数统计被修改了几个材料
		for(PrjTaskMaterialDef def : taskMaterialDefList){
			//如果此材料配置不属于当前阶段的事项,则不进行比较存储
			if(taskMap.get(def.getTaskId()) != null){
				PrjStageMaterial materialBlsp = new PrjStageMaterial();
				materialBlsp.setTaskId(def.getTaskId());
				materialBlsp.setPrjId(blspInstance.getId());
				materialBlsp.setStageId(blspInstance.getStageId());
				materialBlsp.setMaterialId(def.getMaterialId());
				materialBlsp = prjStageMaterialMapper.selectOneByEntitySelective(materialBlsp);
				if(materialBlsp == null){
					materialBlsp = new PrjStageMaterial();
					materialBlsp.setTaskId(def.getTaskId());
					materialBlsp.setPrjId(blspInstance.getId());
					materialBlsp.setStageId(blspInstance.getStageId());
					materialBlsp.setMaterialId(def.getMaterialId());
				}
				materialBlsp.setUpdateTime(new Date());
				materialBlsp.setUpdator(UserUtils.getUser().getId());
				materialBlsp.setMaterialAddr(materialSupplement.getMaterialAddr());
				if(materialBlsp.getId() != null){
					prjStageMaterialMapper.updateByPrimaryKey(materialBlsp);
				}else{
					materialBlsp.setId(null);
					PrjStage state = new PrjStage();
					state.setStageId(blspInstance.getStageId());
					state.setPrjId(blspInstance.getId());
					state = prjStageMapper.selectOneByEntitySelective(state);
					materialBlsp.setPrjStageInstId(state.getId());
					materialBlsp.setOriginalNum(def.getOriginalNum());
					materialBlsp.setCopyNum(def.getCopyNum());
					materialBlsp.setIsMandatory(def.getIsMandatory());
					materialBlsp.setIsDelete("0");
					materialBlsp.setIsComplete("1");
					materialBlsp.setCreatTime(new Date());
					materialBlsp.setCreator(UserUtils.getUser().getId());
					prjStageMaterialMapper.insert(materialBlsp);
				}
				temp++;
			}
		}
		if(temp > 0 ){
			//更新接受此材料
			materialSupplement.setProcessStatus("1");
			prjMaterialSupplementMapper.updateByPrimaryKey(materialSupplement);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean cancelMaterial(Long supplementMaterId) {
		PrjMaterialSupplement materialSupplement = prjMaterialSupplementMapper.selectByPrimaryKey(supplementMaterId);
		if(materialSupplement == null){
			return false;
		}
		materialSupplement.setProcessStatus("2");
		materialSupplement.setUpdateTime(new Date());
		materialSupplement.setUpdator(UserUtils.getUser().getId());
		prjMaterialSupplementMapper.updateByPrimaryKey(materialSupplement);
		return true;
	}

}
