package com.lpcode.modules.service.impl.project.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.framework.core.constants.BaseCode;
import com.lpcode.modules.blsp.entity.*;
import com.lpcode.modules.blsp.vo.PrjTaskMaterialDefVO;
import com.lpcode.modules.service.project.dto.stageTaskMaterialDef.TaskMaterialDefForm;
import org.apache.commons.lang.StringUtils;

import com.framework.core.utils.BeanCopy;
import com.framework.osp.common.utils.SpringContextHolder;
import com.framework.osp.modules.sys.dao.OfficeDao;
import com.framework.osp.modules.sys.entity.Office;
import com.framework.osp.modules.sys.entity.User;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.lpcode.modules.blsp.mapper.DimMaterialMapper;
import com.lpcode.modules.blsp.mapper.FormDefineMapper;
import com.lpcode.modules.blsp.mapper.FormRfBjshMapper;
import com.lpcode.modules.blsp.mapper.FormRfJsxmqkbMapper;
import com.lpcode.modules.blsp.mapper.FormRfYdjsBjspMapper;
import com.lpcode.modules.blsp.mapper.FormRfYdjsJgsbMapper;
import com.lpcode.modules.blsp.mapper.PrjBusinessAcceptMapper;
import com.lpcode.modules.blsp.mapper.PrjCodeGeneratorMapper;
import com.lpcode.modules.blsp.mapper.PrjFormTaskRealMapper;
import com.lpcode.modules.blsp.mapper.PrjInstanceMapper;
import com.lpcode.modules.blsp.mapper.PrjStageDefineMapper;
import com.lpcode.modules.blsp.mapper.PrjStageMapper;
import com.lpcode.modules.blsp.mapper.PrjStageMaterialDefineMapper;
import com.lpcode.modules.blsp.mapper.PrjStageMaterialMapper;
import com.lpcode.modules.blsp.mapper.PrjTaskDefineMapper;
import com.lpcode.modules.blsp.mapper.PrjTaskMapper;
import com.lpcode.modules.blsp.mapper.PrjTaskMaterialDefMapper;
import com.lpcode.modules.blsp.mapper.PrjTaskMaterialDefMapperSelf;
import com.lpcode.modules.blsp.mapper.PrjTaskTimerDefineMapper;
import com.lpcode.modules.blsp.mapper.PrjTaskTransferDetailMapper;
import com.lpcode.modules.blsp.mapper.ProjectMapper;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.dto.project.change.PrjMaterialVO;
import com.lpcode.modules.dto.project.change.PrjTaskDefVO;
import com.lpcode.modules.service.form.IFactoryFormService;
import com.lpcode.modules.service.material.dto.MaterialDto;
import com.lpcode.modules.service.project.dto.ProjectChangeForm;
import com.lpcode.modules.service.project.dto.pinstance.FormDefineVo;
import com.lpcode.modules.service.project.dto.pinstance.FormRfBjshVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjBusinessAcceptVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjCodeGeneratorVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjStageDefineVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjStageMaterialDefineVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjStageMaterialVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjStageVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjTaskDefineVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjTaskVo;
import com.lpcode.modules.service.project.dto.pinstance.TaskFormConf;
import com.lpcode.modules.service.project.inf.PrjStageDefineService;
import com.lpcode.modules.service.project.inf.PrjTaskService;

public class ProjectStepUtil {
	private static PrjTaskDefineMapper taskDefineDao = SpringContextHolder.getBean(PrjTaskDefineMapper.class);
	private static PrjTaskMapper taskInstanceDao = SpringContextHolder.getBean(PrjTaskMapper.class);
	private static PrjStageDefineMapper StateDao = SpringContextHolder.getBean(PrjStageDefineMapper.class);
	private static PrjStageMapper stateInstanceDao = SpringContextHolder.getBean(PrjStageMapper.class);
	private static PrjInstanceMapper projectDao = SpringContextHolder.getBean(PrjInstanceMapper.class);
	private static PrjStageMaterialDefineMapper stagedefineMaterialDao = SpringContextHolder.getBean(PrjStageMaterialDefineMapper.class);
	private static PrjStageMaterialMapper stageMaterialDao = SpringContextHolder.getBean(PrjStageMaterialMapper.class);
	private static DimMaterialMapper materialDao = SpringContextHolder.getBean(DimMaterialMapper.class);
	private static FormRfBjshMapper FormRfBjshDao = SpringContextHolder.getBean(FormRfBjshMapper.class);
	private static FormRfJsxmqkbMapper FormRfJsxmqkbDao = SpringContextHolder.getBean(FormRfJsxmqkbMapper.class);
	private static FormRfYdjsBjspMapper FormRfYdjsBjspDao = SpringContextHolder.getBean(FormRfYdjsBjspMapper.class);
	private static FormRfYdjsJgsbMapper FormRfYdjsJgsbDao = SpringContextHolder.getBean(FormRfYdjsJgsbMapper.class);
	private static PrjTaskService prjTaskService = SpringContextHolder.getBean(PrjTaskService.class);
	private static PrjTaskTimerDefineMapper prjTaskTimerDefineDao = SpringContextHolder.getBean(PrjTaskTimerDefineMapper.class);
	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
	private static PrjCodeGeneratorMapper PrjCodeGeneratorDao = SpringContextHolder.getBean(PrjCodeGeneratorMapper.class);
	private static PrjBusinessAcceptMapper PrjBusinessAcceptDao = SpringContextHolder.getBean(PrjBusinessAcceptMapper.class);
	private static PrjTaskTransferDetailMapper PrjTaskTransferDetailDao = SpringContextHolder.getBean(PrjTaskTransferDetailMapper.class);
	private static ProjectMapper prjDao = SpringContextHolder.getBean(ProjectMapper.class);
	private static PrjStageDefineService prjStageDefineService = SpringContextHolder.getBean(PrjStageDefineService.class);
	private static PrjTaskMaterialDefMapper prjTaskMaterialDefDao = SpringContextHolder.getBean(PrjTaskMaterialDefMapper.class);
	private static PrjTaskMaterialDefMapperSelf prjTaskMaterialDefDaoSelf = SpringContextHolder.getBean(PrjTaskMaterialDefMapperSelf.class);
	private static FormDefineMapper formDefineMapper = SpringContextHolder.getBean(FormDefineMapper.class);
	private static PrjFormTaskRealMapper prjFormTaskRealMapper = SpringContextHolder.getBean(PrjFormTaskRealMapper.class);
	private static IFactoryFormService factoryFormService = SpringContextHolder.getBean(IFactoryFormService.class);
	private static PrjTaskMapper prjTaskMapper = SpringContextHolder.getBean(PrjTaskMapper.class);
	static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 获取项目实体
	 *
	 * @param pid
	 * @return
	 */
	public static ProjectChangeForm getProject(Long pid) {
		ProjectChangeForm project = new ProjectChangeForm();
		if (pid == null ) {
			Long stageId = ProjectUtil.getFirstStage("1").getId();
			Map<Long, PrjMaterialVO> materialDefMap = getStageDefineMaterList(pid, stageId);
			Map<Long, Map<Long, PrjMaterialVO>> taskDefMap = getStageDefineTaskList(pid, stageId);
			project.setMaterialDefMap(materialDefMap);
			project.setTaskDefMap(taskDefMap);
			Map<Long,String> prjTaskStatusMap = getPrjTaskStatusMap(pid);
			if(prjTaskStatusMap != null)
				project.setPrjTaskStatus(prjTaskStatusMap);
		}
		return project;
	}

	/**
	 * 获取项目的第一个阶段
	 *
	 * @param stageType
	 *            阶段类型：1、政府；2、企业
	 * @return
	 */
	public static PrjStageDefineVo getFirstStage(String stageType) {
		PrjStageDefineVo vo = new PrjStageDefineVo();
		PrjStageDefine record = new PrjStageDefine();
		record.setPreStageId(0L);
		record.setStageType(stageType);
		record = StateDao.selectOneByEntitySelective(record);
		if (record != null)
			BeanCopy.copyProperties(record, vo);
		return vo;
	}

	/**
	 * 获取当前阶段配置需要的所有材料
	 *
	 * @return
	 */
	public static Map<Long, Map<Long, PrjMaterialVO>> getStageDefineTaskList(Long projectId,Long stageId) {
		if (stageId == null)
			return null;
		List<PrjTaskDefineVo> taskDefList = new ArrayList<>();
		List<PrjTaskMaterialDef> materialDefList = new ArrayList<PrjTaskMaterialDef>();
		Map<Long, Map<Long, PrjMaterialVO>> taskDefMap = new LinkedHashMap<>();
		if(projectId == null){
			taskDefList = getAllTaskByStage(stageId);
		}else{
			taskDefList = getAllTaskDefineByPrjStage(projectId,stageId);
		}
		if (taskDefList != null && taskDefList.size() > 0) {
			for (PrjTaskDefineVo vo : taskDefList) {
				Long taskDefId = vo.getId();
				PrjTaskMaterialDefExample example = new PrjTaskMaterialDefExample();
				example.createCriteria().andTaskIdEqualTo(taskDefId);
				//example.setOrderByClause(" ORDER_NUM ");
				materialDefList = prjTaskMaterialDefDao.selectByExample(example);
				List<PrjMaterialVO> materialDefVOs = new ArrayList<PrjMaterialVO>();
				BeanCopy.copyPropertiesForList(materialDefList, materialDefVOs, PrjMaterialVO.class);
				Map<Long, PrjMaterialVO> materialDefVOMap = new HashMap<>();
				for (PrjMaterialVO materialDefVO : materialDefVOs) {
					materialDefVOMap.put(materialDefVO.getMaterialId(), materialDefVO);
				}
				taskDefMap.put(taskDefId, materialDefVOMap);
			}
		}
		return taskDefMap;
	}

	/**
	 * 2016-07-18 pengs
	 * 通过项目ID得到事项的所有状态
	 * @param projectId
	 * @return
	 */
	public static Map<Long,String> getPrjTaskStatusMap (Long projectId){
		Map<Long,String> prjTaskMap = null;
		if(projectId != null){
			prjTaskMap = new  LinkedHashMap<>();
			PrjTaskExample prjTaskExample = new PrjTaskExample();
			prjTaskExample.createCriteria().andPrjIdEqualTo(projectId).andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
			List<PrjTask> prjTaskList = prjTaskMapper.selectByExample(prjTaskExample);
			for(PrjTask prjTask : prjTaskList){
				prjTaskMap.put(prjTask.getTaskId(),prjTask.getTaskStatus());
			}
		}
		return prjTaskMap;
	}


	/**
	 * 获取当前阶段配置需要的所有材料
	 *
	 * @return
	 */
	public static List<PrjStageMaterialDefineVo> getStageDefineMaterList(Long stageId) {
		if (stageId == null)
			return null;
		List<PrjStageMaterialDefineVo> list = new ArrayList<PrjStageMaterialDefineVo>();
		List<PrjStageMaterialDefineVo> nlist = new ArrayList<PrjStageMaterialDefineVo>();
		List<PrjTaskDefineVo> taskList = getAllTaskByStage(stageId);
		if (taskList != null && taskList.size() > 0) {
			List<Long> taskIds = new ArrayList<Long>();
			for (PrjTaskDefineVo vo : taskList) {
				taskIds.add(vo.getId());
			}
			List<PrjTaskMaterialDef> materList = prjTaskMaterialDefDaoSelf.selectByTaskIds(taskIds);
			if (materList != null)
				BeanCopy.copyPropertiesForList(materList, list, PrjStageMaterialDefineVo.class);

			for (PrjStageMaterialDefineVo de : list) {
				Long id = de.getMaterialId();
				DimMaterial mater = new DimMaterial();
				mater.setId(id);
				mater = materialDao.selectOneByEntitySelective(mater);
				if (mater.getIsValid().equals("1")) {
					nlist.add(de);
				} else {
					continue;
				}
				de.setMaterName(mater.getName());
				de.setStageId(stageId);
			}
		}

		return nlist;
	}

	/**
	 * 获取当前阶段配置需要的所有材料
	 *
	 * @return
	 */
	public static Map<Long, PrjMaterialVO> getStageDefineMaterList(Long projectId,Long stageId) {
		if (stageId == null)
			return null;
		List<PrjTaskDefineVo> taskDefineList = new ArrayList<PrjTaskDefineVo>();
		if(projectId != null){
			taskDefineList = getAllTaskDefineByPrjStage(projectId,stageId);
		}else{
			taskDefineList = getAllTaskByStage(stageId);
		}
		PrjTaskDefineVo p = null;
		boolean exchange = false;
		for (int i = 0; i < taskDefineList.size(); i++) {
            exchange = false;
            for (int j = taskDefineList.size() - 2; j >= i; j--) {
                if (((PrjTaskDefineVo) taskDefineList.get(j + 1)).getOrderNum().compareTo(
                        ((PrjTaskDefineVo) taskDefineList.get(j)).getOrderNum()) < 0) {
                    p = (PrjTaskDefineVo) taskDefineList.get(j + 1);
                    taskDefineList.set(j + 1, (PrjTaskDefineVo) taskDefineList.get(j));
                    taskDefineList.set(j, p);
                    exchange = true;
                }
            }
            if (!exchange)
                break;
        }
		List<PrjTaskMaterialDef> materialDefList = new ArrayList<PrjTaskMaterialDef>();
		Map<Long, PrjMaterialVO> materialDefMap = new LinkedHashMap<Long, PrjMaterialVO>();
		Map<Long,MaterialDto> mapMaterial = getAllMaterial();
		if (taskDefineList != null && taskDefineList.size() > 0) {
			for (PrjTaskDefineVo vo : taskDefineList) {
				PrjTaskMaterialDefExample example = new PrjTaskMaterialDefExample();
				example.createCriteria().andTaskIdEqualTo(vo.getId());
				example.setOrderByClause(" id ");
				materialDefList = prjTaskMaterialDefDao.selectByExample(example);
				for (PrjTaskMaterialDef materialDef : materialDefList) {
					MaterialDto materialDto = mapMaterial.get(materialDef.getMaterialId());
					PrjMaterialVO materialDefVO = materialDefMap.get(materialDto.getId());
					List<PrjTaskDefVO> taskDefineVos = null;
					if (materialDefVO == null) {
						materialDefVO = new PrjMaterialVO();
						taskDefineVos = new ArrayList<>();
						BeanCopy.copyProperties(materialDto, materialDefVO, PrjMaterialVO.class);
						BeanCopy.copyProperties(materialDef, materialDefVO, PrjMaterialVO.class);
						PrjTaskDefVO prjTaskDefVO = new PrjTaskDefVO();
						BeanCopy.copyProperties(vo, prjTaskDefVO, PrjTaskDefVO.class);
						taskDefineVos.add(prjTaskDefVO);
						materialDefVO.setTaskList(taskDefineVos);
						materialDefMap.put(materialDto.getId(), materialDefVO);
					} else {
						Integer yetCopyNum = materialDefVO.getCopyNum() + materialDef.getCopyNum();
						Integer yetOriginalNum = materialDef.getOriginalNum();
						if (StringUtils.isNotBlank(materialDto.getIsOriginalCumulation()) && "1".equals(materialDto.getIsOriginalCumulation())) {
							yetOriginalNum = materialDefVO.getOriginalNum() + materialDef.getOriginalNum();
						}else if(StringUtils.isNotBlank(materialDto.getIsOriginalCumulation()) && "0".equals(materialDto.getIsOriginalCumulation())){
							//唯一材料,当前原件已选为0时,取原累计结果,否则取最新数
							if(yetOriginalNum == 0 || yetOriginalNum < materialDefVO.getOriginalNum()){
								yetOriginalNum = materialDefVO.getOriginalNum();
							}
						}
						materialDefVO.setCopyNum(yetCopyNum);
						materialDefVO.setOriginalNum(yetOriginalNum);
						PrjTaskDefVO prjTaskDefVO = new PrjTaskDefVO();
						taskDefineVos = materialDefVO.getTaskList();
						BeanCopy.copyProperties(vo, prjTaskDefVO, PrjTaskDefVO.class);
						taskDefineVos.add(prjTaskDefVO);
						materialDefVO.setTaskList(taskDefineVos);
						materialDefMap.put(materialDto.getId(), materialDefVO);
					}
				}
			}
		}
		return materialDefMap;
	}


	/**
	 * 获取此项目目前阶段所有上传的材料
	 *
	 * @return
	 */
	public static List<PrjStageMaterialVo> getStageMaterList(Long prjId, Long stageId) {
		if (stageId == null)
			return null;
		List<PrjStageMaterialVo> list = new ArrayList<PrjStageMaterialVo>();
		PrjStageMaterial record = new PrjStageMaterial();
		record.setPrjId(prjId);
		record.setStageId(stageId);
		通过项目ID和阶段ID查到阶段材料集合，如果查到集合不为空，就放入prjStageMaterialVo集合
		List<PrjStageMaterial> materList = stageMaterialDao.selectByEntitySelective(record);

		if (materList != null)
			BeanCopy.copyPropertiesForList(materList, list, PrjStageMaterialVo.class);
		根据阶段ID获取所有的事项
		List<PrjTaskDefineVo> taskList = getAllTaskByStage(stageId);
		Map<Long, List<PrjStageMaterialVo>> orderMap = new LinkedHashMap<>();
		有事项就遍历事项，放入ordermap集合
		if (taskList != null && taskList.size() > 0) {
			for (PrjTaskDefineVo vo : taskList) {
				orderMap.put(vo.getId(), new ArrayList<PrjStageMaterialVo>());
			}
		}
		在遍历赋值后的list集合，
		for (PrjStageMaterialVo de : list) {
			orderMap.get(de.getTaskId()).add(de);
		}
		List<PrjStageMaterialVo> orderList = new ArrayList<PrjStageMaterialVo>();
		for (List<PrjStageMaterialVo> l : orderMap.values()) {
			orderList.addAll(l);
		}
		for (PrjStageMaterialVo de : orderList) {
			Long id = de.getMaterialId();
			DimMaterial mater = new DimMaterial();
			mater.setId(id);
			mater = materialDao.selectOneByEntitySelective(mater);
			de.setMaterName(mater.getName());
		}
		return orderList;
	}

	/**
	 * 得到所有材料的map,通过材料ID存入Map
	 * @return
	 */
	public static Map<Long,MaterialDto> getAllMaterial (){
		Map<Long,MaterialDto> mapMaterial = new HashMap<>();
		DimMaterialExample example = new DimMaterialExample();
		example.createCriteria().andIsDeleteEqualTo("0").andIsValidEqualTo("1");
		List<DimMaterial> dimMaterialList = materialDao.selectByExample(example);
		List<MaterialDto> materialDtoList = new ArrayList<>();
		BeanCopy.copyPropertiesForList(dimMaterialList, materialDtoList, MaterialDto.class);
		for(MaterialDto materialDto : materialDtoList){
			mapMaterial.put(materialDto.getId(),materialDto);
		}
		return mapMaterial;
	}
	/**
	 * @param prjId
	 * @return 根据项目id获取某阶段所有审批事项
	 */
	public static List<PrjTaskVo> getAllTaskByPrjId(Long prjId) {
		if (prjId == null)
			return null;
		PrjStageVo vo = getStageInstanceByProId(prjId);
		return getAllTaskByInstanceStage(vo.getId(), prjId);
	}

	/**
	 * @param stateInstanceId
	 * @param prjId
	 * @return 获取某阶段所有审批事项
	 */
	public static List<PrjTaskVo> getAllTaskByInstanceStage(Long stateInstanceId, Long prjId) {
		if (stateInstanceId == null || prjId == null)
			return null;
		List<PrjTaskVo> task = new ArrayList<PrjTaskVo>();
		PrjTask define = new PrjTask();
		define.setPrjStageInstId(stateInstanceId);
		define.setPrjId(prjId);
		List<PrjTask> list = taskInstanceDao.selectByEntitySelective(define);
		if (list != null)
			BeanCopy.copyPropertiesForList(list, task, PrjTaskVo.class);
		for (PrjTaskVo de : task) {
			Long id = de.getTaskId();
			PrjTaskDefine mater = new PrjTaskDefine();
			mater.setId(id);
			if (id == null)
				continue;
			mater = taskDefineDao.selectOneByEntitySelective(mater);// 缓存处理,待优化
			if (mater == null)
				continue;
			de.setTaskName(mater.getTaskName());
			de.setDeptId(mater.getDeptId());

			User curr = UserUtils.get(de.getCurrUser());// 待优化
			if (curr != null)
				de.setCurrUserName(curr.getName());
			User init = UserUtils.get(de.getInitUser());
			if (init != null)
				de.setInitUserName(init.getName());
			Office office = officeDao.get(mater.getDeptId());
			if (office != null) {
				de.setDeptName(office.getName());
			}
			de.setTaskCode(mater.getTaskCode());
		}
		return task;
	}

	/**
	 * @param stateId
	 * @return 获取某阶段所有审批事项
	 */
	public static List<PrjTaskDefineVo> getAllTaskByStage(Long stateId) {
		List<PrjTaskDefineVo> task = new ArrayList<PrjTaskDefineVo>();
		if (stateId == null) {
			PrjStageDefineVo vo = getFirstStage("1");
			stateId = vo.getId();
		}
		PrjTaskDefineExample example = new PrjTaskDefineExample();
		PrjTaskDefineExample.Criteria criteria = example.createCriteria();
		criteria.andIsValidEqualTo("1");
		criteria.andIsDeleteEqualTo("0");
		criteria.andStageIdEqualTo(stateId);
		example.setOrderByClause(" ORDER_NUM ");
		List<PrjTaskDefine> list = taskDefineDao.selectByExample(example);
		if (list != null)
			BeanCopy.copyPropertiesForList(list, task, PrjTaskDefineVo.class);
		return task;
	}

	/**
	 * 根据类型获取某阶段所有审批事项
	 * @param isGovType
	 * @param stateIdStr
	 * @return
	 */
	public static List<PrjTaskDefineVo> getAllTaskByStageGovType(String isGovType,String stateIdStr) {
		List<PrjTaskDefineVo> task = new ArrayList<PrjTaskDefineVo>();
		Long stateId;
		if (StringUtils.isBlank(stateIdStr)) {
			PrjStageDefineVo vo = getFirstStage("1");
			stateId = vo.getId();
		} else {
			stateId = Long.parseLong(stateIdStr);
		}
		PrjTaskDefineExample example = new PrjTaskDefineExample();
		PrjTaskDefineExample.Criteria criteria = example.createCriteria();
		criteria.andIsValidEqualTo("1");
		criteria.andIsDeleteEqualTo("0");
		criteria.andStageIdEqualTo(stateId);
		List<String> govTypeList = new ArrayList();
		govTypeList.add("0");//默认的task
		govTypeList.add(isGovType);
		criteria.andIsGovTypeIn(govTypeList);
		example.setOrderByClause(" ORDER_NUM ");
		List<PrjTaskDefine> list = taskDefineDao.selectByExample(example);
		if (list != null)
			BeanCopy.copyPropertiesForList(list, task, PrjTaskDefineVo.class);
		return task;
	}

	/**
	 * @param projectId
	 * @return 获取某阶段所有审批事项
	 */
	public static Map<Long,String> getAllTaskjInstance(Long projectId) {
		Map<Long,String> map = new HashMap<Long,String>();
		if (projectId != null && !"".equals(projectId)) {
			PrjTask prjTask = new PrjTask();
			prjTask.setPrjId(projectId);
			prjTask.setIsDelete("0");
			List<PrjTask> list = taskInstanceDao.selectByEntitySelective(prjTask);
			for(PrjTask prjTask1 : list){
				map.put(prjTask1.getTaskId(),prjTask1.getTaskStatus());
			}
		}
		return map;
	}

	/**
	 * 获取项目实体
	 *
	 * @return
	 */
	public static PrjInstanceVo getInstance(Long projectId) {
		if (projectId == null)
			return null;
		PrjInstanceVo vo = new PrjInstanceVo();
		PrjInstance prjInstance = new PrjInstance();
		prjInstance.setId(projectId);
		prjInstance = projectDao.selectOneByEntitySelective(prjInstance);
		if (prjInstance != null)
			BeanCopy.copyProperties(prjInstance, vo);
		return vo;
	}


	/**
	 * 获取此项目表单FormRfBjshVo 的结果
	 *
	 * @param projectId
	 * @return
	 */
	public static FormRfBjshVo getFormRfBjshVo(Long projectId) {
		if (projectId == null)
			return null;
		FormRfBjsh record = new FormRfBjsh();
		FormRfBjshVo FormRfBjshVo = new FormRfBjshVo();
		record.setPrjId(projectId);
		record = FormRfBjshDao.selectOneByEntitySelective(record);
		if (record == null)
			return null;
		BeanCopy.copyProperties(record, FormRfBjshVo);
		return FormRfBjshVo;
	}

	/**
	 * 根据项目ID 获取该项目阶段实例
	 *
	 * @param projectId
	 * @return
	 */
	public static PrjStageVo getStageInstanceByProId(Long projectId) {
		if (projectId == null || projectId.equals("")) {
			return null;
		} else {
			PrjInstanceVo prjInstance = getInstance(projectId);
			PrjStageVo vo = new PrjStageVo();
			PrjStage record = new PrjStage();
			if (prjInstance.getStageId() != null)
				record.setStageId(prjInstance.getStageId());
			record.setPrjId(projectId);
			record = stateInstanceDao.selectOneByEntitySelective(record);
			if (record != null)
				BeanCopy.copyProperties(record, vo);
			return vo;
		}
	}
//
//	/**
//	 * 获取此项目表单FormRfBjshVo 的结果
//	 *
//	 * @param pid
//	 * @return
//	 */
//	public static FormRfYdjsBjspVo getFormRfYdjsBjsp(Long pid) {
//		if (pid == null)
//			return null;
//		FormRfYdjsBjsp record = new FormRfYdjsBjsp();
//		FormRfYdjsBjspVo vo = new FormRfYdjsBjspVo();
//		record.setPrjId(pid);
//		record = FormRfYdjsBjspDao.selectOneByEntitySelective(record);
//		if (record == null)
//			return null;
//		BeanCopy.copyProperties(record, vo);
//		return vo;
//	}
//
//	/**
//	 * 获取此项目表单FormRfBjshVo 的结果
//	 *
//	 * @param prjId
//	 * @return
//	 */
//	public static FormRfYdjsJgsbVo getFormRfYdjsJgsb(Long prjId) {
//		if (prjId == null)
//			return null;
//		FormRfYdjsJgsbVo vo = new FormRfYdjsJgsbVo();
//		FormRfYdjsJgsb record = new FormRfYdjsJgsb();
//		record.setPrjId(prjId);
//		record = FormRfYdjsJgsbDao.selectOneByEntitySelective(record);
//		if (record == null)
//			return null;
//		BeanCopy.copyProperties(record, vo);
//		return vo;
//	}
//
//	/**
//	 * 获取此项目表单FormRfJsxmqkbVo 的结果
//	 *
//	 * @param jgsbId
//	 * @return
//	 */
//	public static FormRfJsxmqkbVo getFormRfJsxmqkb(Long jgsbId) {
//		if (jgsbId == null)
//			return null;
//		FormRfJsxmqkbVo vo = new FormRfJsxmqkbVo();
//		FormRfJsxmqkb record = new FormRfJsxmqkb();
//		record.setJgsbId(jgsbId);
//		record = FormRfJsxmqkbDao.selectOneByEntitySelective(record);
//		if (record == null)
//			return null;
//		BeanCopy.copyProperties(record, vo);
//		return vo;
//	}

	public static Map<Long, PrjTaskVo> getHasStartTask(PrjInstanceVo vo) {
		Map<Long, PrjTaskVo> map = new HashMap<Long, PrjTaskVo>();
		if (vo != null) {
			Long stageId = vo.getStageId();
			Long prjId = vo.getId();
			PrjStage state = new PrjStage();
			state.setPrjId(prjId);
			state.setStageId(stageId);
			state = stateInstanceDao.selectOneByEntitySelective(state);
			List<PrjTaskVo> list = new ArrayList<PrjTaskVo>();
			if (state != null) {
				PrjTask record = new PrjTask();
				record.setPrjId(prjId);
				record.setPrjStageInstId(state.getId());
				com.lpcode.modules.blsp.entity.PrjTaskExample exaple = new com.lpcode.modules.blsp.entity.PrjTaskExample();
				com.lpcode.modules.blsp.entity.PrjTaskExample.Criteria c = exaple.createCriteria();
				c.andTaskStatusNotEqualTo("0");
				c.andPrjIdEqualTo(prjId);
				c.andPrjStageInstIdEqualTo(state.getId());
				List<PrjTask> d = taskInstanceDao.selectByExample(exaple);
				BeanCopy.copyPropertiesForList(d, list, PrjTaskVo.class);
			}
			if (list != null && list.size() > 0)
				for (PrjTaskVo v : list) {
					map.put(v.getTaskId(), v);
				}
		}
		return map;
	}

	/**
	 * 得到事项与表单的配置关系
	 *
	 * @param projectId
	 * @param taskIds
	 * @return
	 */
	public static Map<Long, TaskFormConf> getTaskFormConf(Long projectId, List<Long> taskIds) {
		Map<Long, TaskFormConf> taskFormConfs = new HashMap<>();
		PrjFormTaskRealExample example = new PrjFormTaskRealExample();
		PrjFormTaskRealExample.Criteria criteria = example.createCriteria();
		if(taskIds == null || taskIds.size() == 0){
			return null;
		}
		if (taskIds != null && taskIds.size() > 0) {
			criteria.andTaskDefIdIn(taskIds);
		}
		List<PrjFormTaskReal> prjFormTaskRealList = prjFormTaskRealMapper.selectByExample(example);
		for (PrjFormTaskReal prjFormTaskReal : prjFormTaskRealList) {
			TaskFormConf taskFormConf = taskFormConfs.get(prjFormTaskReal.getTaskDefId());
			if (taskFormConf == null) {
				taskFormConf = new TaskFormConf();
				List<FormDefineVo> formDefines = new ArrayList<FormDefineVo>();
				FormDefine formDefine = formDefineMapper.selectByPrimaryKey(prjFormTaskReal.getFormId());
				PrjTaskDefine prjTaskDefine = taskDefineDao.selectByPrimaryKey(prjFormTaskReal.getTaskDefId());
				FormDefineVo formDefineVo = new FormDefineVo();
				BeanCopy.copyProperties(formDefine, formDefineVo, FormDefineVo.class);
				if (projectId != null) {
					PrjFormVO prjFormVO = factoryFormService.checkBizForm(formDefineVo.getFormCode().toUpperCase(), projectId,prjFormTaskReal.getTaskDefId().toString());
					if (prjFormVO != null && prjFormVO.getObject() != null) {
						formDefineVo.setPrjectId(projectId);
					}
				}
				formDefines.add(formDefineVo);
				taskFormConf.setFormDefineList(formDefines);
				taskFormConf.setTaskDefId(prjTaskDefine.getId());
				taskFormConf.setTaskDefName(prjTaskDefine.getTaskName());
				taskFormConf.setOrderNum(prjTaskDefine.getOrderNum());

			} else {
				FormDefine formDefine = formDefineMapper.selectByPrimaryKey(prjFormTaskReal.getFormId());
				FormDefineVo formDefineVo = new FormDefineVo();
				BeanCopy.copyProperties(formDefine, formDefineVo, FormDefineVo.class);
				List<FormDefineVo> formDefines = taskFormConf.getFormDefineList();
				if (projectId != null) {
					PrjFormVO prjFormVO = factoryFormService.checkBizForm(formDefineVo.getFormCode().toUpperCase(), projectId,prjFormTaskReal.getTaskDefId().toString());
					if (prjFormVO != null && prjFormVO.getObject() != null) {
						formDefineVo.setPrjectId(projectId);
					}
				}
				formDefines.add(formDefineVo);
				taskFormConf.setFormDefineList(formDefines);
			}
			taskFormConfs.put(prjFormTaskReal.getTaskDefId(), taskFormConf);
		}


		return initTaskDef(taskFormConfs,taskIds);
	}

	/**
	 * 补上没有表单并可以受理的事项
	 * @param taskFormConfs
	 * @param taskIds
	 * @return
	 */
	public static Map<Long, TaskFormConf> initTaskDef(Map<Long, TaskFormConf> taskFormConfs,List<Long> taskIds){
		PrjTaskDefineExample example = new PrjTaskDefineExample();
		example.createCriteria().andIdIn(taskIds);
		List<PrjTaskDefine>  prjTaskDefines = taskDefineDao.selectByExample(example);
		for(PrjTaskDefine prjTaskDefine : prjTaskDefines){
			TaskFormConf taskFormConf = taskFormConfs.get(prjTaskDefine.getId());
			if(taskFormConf == null){
				taskFormConf = new TaskFormConf();
				taskFormConf.setTaskDefId(prjTaskDefine.getId());
				taskFormConf.setTaskDefName(prjTaskDefine.getTaskName());
				taskFormConf.setOrderNum(prjTaskDefine.getOrderNum());
				taskFormConfs.put(prjTaskDefine.getId(), taskFormConf);
			}
		}
		return taskFormConfs;
	}

	/**
	 * 通过项目ID与阶段ID 得到所有prjTaskDefine对象
	 * @param projectId
	 * @param stageId
	 * @return
	 */
	public static List<PrjTaskDefineVo> getAllTaskDefineByPrjStage(Long projectId, Long stageId) {
		// 通过项目ID与阶段ID 得到所有prjTask对象
		PrjStage prjStage = new PrjStage();
		prjStage.setPrjId(projectId);
		prjStage.setStageId(stageId);
		prjStage = stateInstanceDao.selectOneByEntitySelective(prjStage);
		PrjTask prjTask = new PrjTask();
		prjTask.setPrjStageInstId(prjStage.getId());
		prjTask.setPrjId(projectId);
		List<PrjTask> prjTaskList = taskInstanceDao.selectByEntitySelective(prjTask);
		List<PrjTaskDefineVo> taskDefineList = new ArrayList<PrjTaskDefineVo>();
		if(prjTaskList != null && prjTaskList.size() > 0){
			List<Long> idList = new ArrayList<Long>();
			for (PrjTask prjTaskVo : prjTaskList) {
				idList.add(prjTaskVo.getTaskId());
			}
			List<PrjTaskDefine> selectByPrimaryKeyList = taskDefineDao.SelectByPrimaryKeyList(idList);
			BeanCopy.copyPropertiesForList(selectByPrimaryKeyList, taskDefineList, PrjTaskDefineVo.class);
		}
		return taskDefineList;
	}

	/**
	 * @param seqType
	 * @return 获取项目编号
	 */
	public static PrjCodeGeneratorVo getProjectCode(String seqType) {
		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		PrjCodeGenerator record = new PrjCodeGenerator();
		PrjCodeGeneratorVo vo = new PrjCodeGeneratorVo();
		record.setMonth(month);
		record.setYear(year);
		record.setSeqType(seqType);// 1：政府，2：企业
		record = PrjCodeGeneratorDao.selectOneByEntitySelective(record);
		if (record != null)
			BeanCopy.copyProperties(record, vo);
		return vo;
	}

	/**
	 * @param defineId
	 * @return 获取某阶段所有审批事项
	 */
	public static PrjTaskDefineVo getPrjTaskDefineVo(Long defineId) {
		PrjTaskDefineVo vo = new PrjTaskDefineVo();
		PrjTaskDefine d = taskDefineDao.selectByPrimaryKey(defineId);
		if (d != null)
			BeanCopy.copyProperties(d, vo);
		return vo;
	}

	/**
	 * @param vo
	 * @return 获取受理实体
	 */
	public static PrjBusinessAcceptVo getPrjBusinessAccept(PrjBusinessAcceptVo vo) {
		if (vo == null)
			return null;
		PrjBusinessAccept record = new PrjBusinessAccept();
		BeanCopy.copyProperties(vo, record);
		PrjBusinessAccept a = PrjBusinessAcceptDao.selectOneByEntitySelective(record);
		if (a != null)
			BeanCopy.copyProperties(a, vo);
		return vo;
	}


	/**
	 * @param acceptId
	 * @return
	 */
	public static Map<Long, List<PrjStageMaterialVo>> getAllTaskMaterByAcceptId(Long acceptId) {
		Map<Long, List<PrjStageMaterialVo>> map = new HashMap<Long, List<PrjStageMaterialVo>>();
		if (acceptId == null)
			return map;
		PrjTask define = new PrjTask();
		define.setAcceptId(acceptId);
		define.setTaskStatus("1");
		List<PrjTask> t = taskInstanceDao.selectByEntitySelective(define);
		if (t != null && t.size() > 0)
			for (PrjTask task : t) {
				com.lpcode.modules.blsp.entity.PrjStageMaterialExample example = new com.lpcode.modules.blsp.entity.PrjStageMaterialExample();
				com.lpcode.modules.blsp.entity.PrjStageMaterialExample.Criteria c = example.createCriteria();
				c.andTaskIdEqualTo(task.getTaskId());
				c.andPrjStageInstIdEqualTo(task.getPrjStageInstId());
				List<PrjStageMaterial> listMatr = stageMaterialDao.selectByExample(example);
				List<PrjStageMaterialVo> list = new ArrayList<PrjStageMaterialVo>();
				if (listMatr != null && listMatr.size() > 0)
					BeanCopy.copyPropertiesForList(listMatr, list, PrjStageMaterialVo.class);
				//得到事项中所有材料并替换
				PrjTaskMaterialDef prjTaskMaterialDef = new PrjTaskMaterialDef();
				prjTaskMaterialDef.setTaskId(task.getTaskId());
				List<PrjTaskMaterialDef> prjTaskMaterialDefList = prjTaskMaterialDefDao.selectByEntitySelective(prjTaskMaterialDef);
				List<PrjStageMaterialVo> prjStageMaterialVoList = new ArrayList<PrjStageMaterialVo>();
				if(prjTaskMaterialDefList != null && prjTaskMaterialDefList.size() > 0){
					BeanCopy.copyPropertiesForList(prjTaskMaterialDefList, prjStageMaterialVoList, PrjStageMaterialVo.class);
				}
				map.put(task.getTaskId(), prjStageMaterialVoList);
				if (prjStageMaterialVoList != null && prjStageMaterialVoList.size() > 0){
					for ( int i = 0 ; i < prjStageMaterialVoList.size() ; i++ ) {
						prjStageMaterialVoList.get(i).setIsComplete("0");
						if (list != null && list.size() > 0){
							for (PrjStageMaterialVo l : list) {
								if(prjStageMaterialVoList.get(i).getMaterialId().equals(l.getMaterialId())){
									prjStageMaterialVoList.set(i, l);
								}
								DimMaterial mater = new DimMaterial();
								mater.setId(prjStageMaterialVoList.get(i).getMaterialId());
								mater = materialDao.selectOneByEntitySelective(mater);
								prjStageMaterialVoList.get(i).setMaterName(mater.getName());
							}
						}
					}
				}
			}
		return map;
	}
	
	/**
     * 根据项目中的事项集合以及acceptId获取打印所需事项材料信息
     * @param acceptId
     * @param taskIds
     * @return
     */
    public static Map<Long, List<PrjStageMaterialVo>> getAllTaskMaterByAcceptIdAndTaskIds(Long acceptId,List<Long> taskIds) {
        Map<Long, List<PrjStageMaterialVo>> map = new HashMap<Long, List<PrjStageMaterialVo>>();
        if (acceptId == null)
            return map;
        PrjTask define = new PrjTask();
        define.setAcceptId(acceptId);
        define.setTaskStatus("1");
        List<PrjTask> t = taskInstanceDao.selectByEntitySelective(define);
        List<PrjTask> taskList = new ArrayList<PrjTask>();
        if(taskIds != null && taskIds.size() > 0){
        	for (PrjTask task : t) {
				for(Long taskId : taskIds){
					if(task.getTaskId().equals(taskId)){
						taskList.add(task);
						break;
					}
				}
			}
        }
        if (taskList != null && taskList.size() > 0)
            for (PrjTask task : taskList) {
                com.lpcode.modules.blsp.entity.PrjStageMaterialExample example = new com.lpcode.modules.blsp.entity.PrjStageMaterialExample();
                com.lpcode.modules.blsp.entity.PrjStageMaterialExample.Criteria c = example.createCriteria();
                c.andTaskIdEqualTo(task.getTaskId());
                c.andPrjStageInstIdEqualTo(task.getPrjStageInstId());
                List<PrjStageMaterial> listMatr = stageMaterialDao.selectByExample(example);
                List<PrjStageMaterialVo> list = new ArrayList<PrjStageMaterialVo>();
                if (listMatr != null && listMatr.size() > 0)
                    BeanCopy.copyPropertiesForList(listMatr, list, PrjStageMaterialVo.class);
                //得到事项中所有材料并替换
                PrjTaskMaterialDef prjTaskMaterialDef = new PrjTaskMaterialDef();
                prjTaskMaterialDef.setTaskId(task.getTaskId());
                List<PrjTaskMaterialDef> prjTaskMaterialDefList = prjTaskMaterialDefDao.selectByEntitySelective(prjTaskMaterialDef);
                List<PrjStageMaterialVo> prjStageMaterialVoList = new ArrayList<PrjStageMaterialVo>();
                if(prjTaskMaterialDefList != null && prjTaskMaterialDefList.size() > 0){
                	BeanCopy.copyPropertiesForList(prjTaskMaterialDefList, prjStageMaterialVoList, PrjStageMaterialVo.class);
                }
                map.put(task.getTaskId(), prjStageMaterialVoList);
                if (prjStageMaterialVoList != null && prjStageMaterialVoList.size() > 0){
                    for ( int i = 0 ; i < prjStageMaterialVoList.size() ; i++ ) {
                    	prjStageMaterialVoList.get(i).setIsComplete("0");
                    	if (list != null && list.size() > 0){
                    		for (PrjStageMaterialVo l : list) {
                    			if(prjStageMaterialVoList.get(i).getMaterialId().equals(l.getMaterialId())){
                    				prjStageMaterialVoList.set(i, l);
                    			}
                    			DimMaterial mater = new DimMaterial();
                                mater.setId(prjStageMaterialVoList.get(i).getMaterialId());
                                mater = materialDao.selectOneByEntitySelective(mater);
                                prjStageMaterialVoList.get(i).setMaterName(mater.getName());
                    		}
                    	}
                    }
                }
            }
        return map;
    }


	/**
	 * 通过阶段ID得到阶段下的事项及材料
	 * @param stageId
	 * @return
	 */
	public static List<TaskMaterialDefForm> getStageTaskMaterialDefByStage(Long stageId){
		List<TaskMaterialDefForm> taskMaterialDefFormList = new ArrayList<TaskMaterialDefForm>();
		PrjTaskDefineExample example = new PrjTaskDefineExample();
		example.createCriteria().andStageIdEqualTo(stageId).andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
		List<PrjTaskDefine> taskDefineList = taskDefineDao.selectByExample(example);
		for(PrjTaskDefine prjTaskDefine : taskDefineList){
			TaskMaterialDefForm  taskMaterialDefForm = new TaskMaterialDefForm();
			BeanCopy.copyProperties(prjTaskDefine, taskMaterialDefForm, TaskMaterialDefForm.class);
			List<PrjTaskMaterialDefVO> materialDefVOList = prjTaskMaterialDefDaoSelf.selectMaterByTaskId(prjTaskDefine.getId());
			taskMaterialDefForm.setMaterialList(materialDefVOList);
			taskMaterialDefFormList.add(taskMaterialDefForm);
		}
		return taskMaterialDefFormList;
	}
    
}
