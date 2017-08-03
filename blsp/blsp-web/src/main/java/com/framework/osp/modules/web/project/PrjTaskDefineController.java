package com.framework.osp.modules.web.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.core.result.ListResult;
import com.framework.core.result.RequestDTOPage;
import com.framework.osp.common.config.Global;
import com.framework.osp.common.mapper.JsonMapper;
import com.framework.osp.common.persistence.Page;
import com.framework.osp.modules.sys.entity.Dict;
import com.framework.osp.modules.sys.entity.Office;
import com.framework.osp.modules.sys.service.SystemService;
import com.framework.osp.modules.sys.utils.DictUtils;
import com.framework.osp.modules.web.util.StageUtils;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.blsp.entity.DimMaterial;
import com.lpcode.modules.dto.project.PrjStageDefineDTO;
import com.lpcode.modules.dto.project.PrjTaskDefineCondDimDTO;
import com.lpcode.modules.dto.project.PrjTaskDefineDTO;
import com.lpcode.modules.dto.project.PrjTaskMaterialDefDTO;
import com.lpcode.modules.dto.project.PrjTaskTimerDefineDTO;
import com.lpcode.modules.service.impl.material.MaterialServiceImpl;
import com.lpcode.modules.service.material.inf.IMaterialService;
import com.lpcode.modules.service.project.inf.PrjStageDefineService;
import com.lpcode.modules.service.project.inf.PrjTaskDefineService;
import com.lpcode.modules.service.project.inf.PrjTaskMaterialDefService;
import com.lpcode.modules.service.project.inf.PrjTaskTimerDefineService;

/**
 * 事项定义Controller
 *
 * @author lpcode
 *
 */

@Controller
@RequestMapping(value = "${adminPath}/prjTaskDefine")
public class PrjTaskDefineController {

	@Autowired
	private PrjTaskDefineService service;

	@Autowired
	private PrjTaskTimerDefineService timerDefineService;

	@Autowired
	private PrjTaskMaterialDefService materialDefService;

	@Autowired
	private PrjStageDefineService stageDefineService;
	
	@Autowired 
	private IMaterialService materialService;
	
	@Autowired
	SystemService systemService;

	@ModelAttribute("prjTaskDefineDTO")
	public PrjTaskDefineDTO get(@RequestParam(required = false) Long id) {
		if (null != id) {
			return service.findByPrimaryKey(id);
		} else {
			PrjTaskDefineDTO dto = new PrjTaskDefineDTO();
			dto.setTaskType("1");
			return dto;
		}
	}

	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, PrjTaskDefineDTO dto, Model model) {

		RequestDTOPage<PrjTaskDefineDTO> requestPage = new RequestDTOPage<PrjTaskDefineDTO>();
		Page<PrjTaskDefineDTO> page = new Page<PrjTaskDefineDTO>(request, response);
		requestPage.setObj(dto);
		requestPage.setPage(page);

		List<Office> offices = systemService.findAllOffice();
		Map<String, String> map = new HashMap<String, String>();
		if (offices != null && offices.size() > 0) {
			for (Office office : offices) {
				map.put(office.getId(), office.getName());
			}
			model.addAttribute("offices", map);
		}
		service.findList(requestPage);
		ListResult<PrjStageDefineDTO> stageList = stageDefineService.findAllList();
		if (stageList.getObj() != null && stageList.getObj().size() > 0) {
			Map<Long, String> smap = new HashMap<Long, String>();
			for (PrjStageDefineDTO sdto : stageList.getObj()) {
				smap.put(sdto.getId(), sdto.getStageName());
			}
			model.addAttribute("stages", smap);
		}

		// page.setList(pageResult.getObj().getList());
		// page.setPageNo(pageResult.getObj().getPageNo());
		// page.setPageSize(pageResult.getObj().getPageSize());
		// page.setCount(pageResult.getObj().getCount());
		model.addAttribute("page", page);
		model.addAttribute("prjTaskDTO", dto);

		return "modules/project/taskDefineList";
	}

	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, PrjTaskDefineDTO dto, Model model) {

		if (dto.getId() != null) {
			List<PrjTaskMaterialDefDTO> materialList = materialDefService.findByTaskId(dto.getId());
			List<Map> result = new ArrayList<Map>();
			if (materialList != null) {
				for (PrjTaskMaterialDefDTO temp : materialList) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", temp.getMaterialId().toString());
					map.put("value", temp.getDescription());
					result.add(map);
				}
				model.addAttribute("jeesiteMap", JsonMapper.toJsonString(result));
			}
			if (DicConstants.TASK_DEFINE_TIMER_CAT_NORMAL.equals(dto.getTimerCat())) {
				List<PrjTaskTimerDefineDTO> timerList = timerDefineService.findByTaskId(dto.getId());
				if (timerList != null && timerList.size() == 1) {
					dto.setDefaultDimType(timerList.get(0).getTimeType());
					dto.setDefaultTimeLimit(timerList.get(0).getTimeLimit());
				}
			}
		}

		return "modules/project/taskDefineEdit";
	}

	@ResponseBody
	@RequestMapping(value = "checkTaskCode")
	public boolean checkTaskCode(PrjTaskDefineDTO dto) {
		if (StringUtils.isNotEmpty(dto.getTaskCode())) {
			return (service.findByTaskCode(dto) == null);
		}
		return false;
	}

	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, PrjTaskDefineDTO dto, Model model) {

		String[] materials = request.getParameterValues("material");
		String[] materialNames = request.getParameterValues("materialName");
		// String[] isOriginals = request.getParameterValues("isOriginal");
		String[] copyNums = request.getParameterValues("copyNum");
		String[] originalNum = request.getParameterValues("originalNum");
		String[] isMandatorys = request.getParameterValues("isMandatory");
		String[] isByConditions = request.getParameterValues("isByCondition");
		String[] conditionTypes = request.getParameterValues("conditionType");
		String[] conditionTypeValues = request.getParameterValues("conditionTypeValue");
		
		String[] isResultMaterials = request.getParameterValues("isResultMaterial");
		String[] resultTaskIds = request.getParameterValues("resultTaskId");

		if (materials != null && materials.length > 0) {
			List<PrjTaskMaterialDefDTO> materialDefs = new ArrayList<PrjTaskMaterialDefDTO>();
			for (int i = 0; i < materials.length; i++) {
				PrjTaskMaterialDefDTO defDto = new PrjTaskMaterialDefDTO();
				defDto.setCopyNum(StringUtils.isNotBlank(copyNums[i]) ? Integer.parseInt(copyNums[i]) : 0);
				defDto.setMaterialId(StringUtils.isNotBlank(materials[i]) ? Long.parseLong(materials[i]) : null);
				defDto.setIsMandatory(isMandatorys[i]);
				defDto.setOriginalNum(StringUtils.isNotBlank(originalNum[i]) ? Integer.parseInt(originalNum[i]) : 0);
				//放开备注字段
				//defDto.setDescription(materialNames[i]);
				if (isResultMaterials != null && isResultMaterials[i] != null && "1".equals(isResultMaterials[i])) {
					defDto.setIsResultMaterial(isResultMaterials[i]);
					defDto.setResultTaskId(Long.parseLong(resultTaskIds[i]));
				}else{
					defDto.setIsResultMaterial("0");
					defDto.setResultTaskId(null);
				}
				if (isByConditions != null && isByConditions[i] != null && "1".equals(isByConditions[i])) {
					defDto.setIsByCondition(isByConditions[i]);
					if(StringUtils.isNotEmpty(conditionTypes[i])){
						defDto.setLandType("");
						defDto.setIsNeedPreAudit("");
						defDto.setIsSpecialProject("");
						defDto.setIsWithBasePart("");
						defDto.setIsItType("");
						defDto.setIsGovType("");
						if(DicConstants.TASK_DEFINE_CONDITION_LAND_TYPE.equals(conditionTypes[i])){
							defDto.setLandType(conditionTypeValues[i]);
						}else if(DicConstants.TASK_DEFINE_CONDITION_NEED_PRE_AUDIT.equals(conditionTypes[i])){
							defDto.setIsNeedPreAudit(conditionTypeValues[i]);
						}else if(DicConstants.TASK_DEFINE_CONDITION_SPECIAL_PROJECT.equals(conditionTypes[i])){
							defDto.setIsSpecialProject(conditionTypeValues[i]);
						}else if(DicConstants.TASK_DEFINE_CONDITION_WITH_BASE_PART.equals(conditionTypes[i])){
							defDto.setIsWithBasePart(conditionTypeValues[i]);
						}else if(DicConstants.TASK_DEFINE_CONDITION_IT_TYPE.equals(conditionTypes[i])){
							defDto.setIsItType(conditionTypeValues[i]);
						}else if(DicConstants.TASK_DEFINE_CONDITION_GOV_TYPE.equals(conditionTypes[i])){
							defDto.setIsGovType(conditionTypeValues[i]);
						}
					}
				} else {
					defDto.setIsByCondition("0");
					defDto.setLandType("");
					defDto.setIsNeedPreAudit("");
					defDto.setIsSpecialProject("");
					defDto.setIsWithBasePart("");
					defDto.setIsItType("");
					defDto.setIsGovType("");
				}
				materialDefs.add(defDto);
			}
			dto.setMaterialDefs(materialDefs);
		}

		List<PrjTaskTimerDefineDTO> timerList = new ArrayList<PrjTaskTimerDefineDTO>();


		if (DicConstants.TASK_DEFINE_TIMER_CAT_CONDITION.equals(dto.getTimerCat()) && StringUtils.isNotEmpty(dto.getCondDimType())) {
			List<Dict> dicts = null;
			if (DicConstants.TASK_DEFINE_TIMER_CAT_NORMAL.equals(dto.getCondDimType())) {
				dicts = DictUtils.getDictList(DicConstants.DIC_TYPE_PROJECT_PRICE);
			} else {
				dicts = DictUtils.getDictList(DicConstants.DIC_TYPE_PROJECT_USEAGE);
			}
			if (dicts != null) {
				for (Dict dict : dicts) {
					PrjTaskTimerDefineDTO timerDto = new PrjTaskTimerDefineDTO();
					timerDto.setTimeType(request.getParameter("timeType" + dict.getValue()));
					String timeLimitStr = request.getParameter("timeLimit" + dict.getValue());
					timerDto.setTimeLimit(Integer.parseInt(timeLimitStr));
					if (DicConstants.TASK_DEFINE_TIMER_CAT_NORMAL.equals(dto.getCondDimType())) {
						timerDto.setPriceType(dict.getValue());
					} else {
						timerDto.setUseageType(dict.getValue());
					}
					timerList.add(timerDto);
				}
				dto.setTimerList(timerList);
			}
		} else {
			PrjTaskTimerDefineDTO timerDto = new PrjTaskTimerDefineDTO();
			timerDto.setTimeType(dto.getDefaultDimType());
			timerDto.setTimeLimit(dto.getDefaultTimeLimit());
			timerList.add(timerDto);
			dto.setTimerList(timerList);
		}

		if (dto.getId() != null) {
			service.update(dto);
		} else {
			service.save(dto);
		}
		return "redirect:" + Global.getAdminPath() + "/prjTaskDefine/list";
	}

	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request, PrjTaskDefineDTO dto, Model model) {

		if (dto.getId() != null) {
			service.delete(dto.getId());
		}
		return "redirect:" + Global.getAdminPath() + "/prjTaskDefine/list";
	}

	@RequestMapping(value = "getCondDim")
	public String getCondDim(HttpServletRequest request, HttpServletResponse response, PrjTaskDefineDTO dto,
			Model model) {

		List<PrjTaskDefineCondDimDTO> list = new ArrayList<PrjTaskDefineCondDimDTO>();
		List<PrjTaskTimerDefineDTO> timerList = null;
		if (dto.getId() != null) {
			timerList = timerDefineService.findByTaskId(dto.getId());
		}

		if (StringUtils.isNotEmpty(dto.getCondDimType())) {
			List<Dict> dicts = null;
			Map<String, PrjTaskTimerDefineDTO> map = new HashMap<String, PrjTaskTimerDefineDTO>();
			if (DicConstants.TASK_DEFINE_COND_DIM_TYPE_PRICE.equals(dto.getCondDimType())) {
				dicts = DictUtils.getDictList(DicConstants.DIC_TYPE_PROJECT_PRICE);
				if (timerList != null && timerList.size() > 0) {
					for (PrjTaskTimerDefineDTO tdto : timerList) {
						map.put(tdto.getPriceType(), tdto);
					}
				}
			} else {
				dicts = DictUtils.getDictList(DicConstants.DIC_TYPE_PROJECT_USEAGE);
				if (timerList != null && timerList.size() > 0) {
					for (PrjTaskTimerDefineDTO tdto : timerList) {
						map.put(tdto.getUseageType(), tdto);
					}
				}
			}
			if (dicts != null) {
				for (Dict dict : dicts) {
					PrjTaskDefineCondDimDTO temp = new PrjTaskDefineCondDimDTO();
					temp.setLabel(dict.getLabel());
					temp.setValue(dict.getValue());
					if (map.get(dict.getValue()) != null) {
						temp.setTimeLimit(map.get(dict.getValue()).getTimeLimit());
						temp.setTimeType(map.get(dict.getValue()).getTimeType());
					}
					list.add(temp);
				}
			}
		}
		model.addAttribute("condDimList", list);
		model.addAttribute("prjTaskDTO", dto);

		return "modules/project/taskDefineCondDim";
	}

	@RequestMapping(value = "getReplyTask")
	public String getReplyTask(HttpServletRequest request, HttpServletResponse response, PrjTaskDefineDTO dto,
			Model model) {
		List<PrjTaskDefineCondDimDTO> list = new ArrayList<PrjTaskDefineCondDimDTO>();
		ListResult<PrjTaskDefineDTO> result = service.findReplyList(dto);
		ListResult<Long> dresult = service.findDependencyList(dto);
		
		for(PrjTaskDefineDTO temp : result.getObj()){
			if(dresult.getObj().contains(temp.getId())){
				temp.setIsValid("1");
			}else{
				temp.setIsValid("0");
			}
		}
		model.addAttribute("replyList", result.getObj());
		//model.addAttribute("dependencyList", dresult.getObj());
		model.addAttribute("prjTaskDTO", dto);
		return "modules/project/taskDefineReply";

	}

	private void setConditionType(PrjTaskMaterialDefDTO dto ){
		//		public static final String TASK_DEFINE_CONDITION_LAND_TYPE = "land_type";//用地类型
		//		public static final String TASK_DEFINE_CONDITION_NEED_PRE_AUDIT = "need_pre_audit";//项目初步设计评审
		//		public static final String TASK_DEFINE_CONDITION_SPECIAL_PROJECT = "special_project";//特殊专业工程
		//		public static final String TASK_DEFINE_CONDITION_WITH_BASE_PART = "with_base_part";//基础部分工程或地基处理工程
		//		public static final String TASK_DEFINE_CONDITION_IT_TYPE = "it_type";//信息化类项目
		//		public static final String TASK_DEFINE_CONDITION_GOV_TYPE = "gov_type";//市场类项目
		if(StringUtils.isNotEmpty(dto.getLandType())){
			dto.setConditionType(DicConstants.TASK_DEFINE_CONDITION_LAND_TYPE);
			dto.setConditionTypeValue(dto.getLandType());
		}else if(StringUtils.isNotEmpty(dto.getIsNeedPreAudit())){
			dto.setConditionType(DicConstants.TASK_DEFINE_CONDITION_NEED_PRE_AUDIT);
			dto.setConditionTypeValue(dto.getIsNeedPreAudit());
		}else if(StringUtils.isNotEmpty(dto.getIsSpecialProject())){
			dto.setConditionType(DicConstants.TASK_DEFINE_CONDITION_SPECIAL_PROJECT);
			dto.setConditionTypeValue(dto.getIsSpecialProject());
		}else if(StringUtils.isNotEmpty(dto.getIsWithBasePart())){
			dto.setConditionType(DicConstants.TASK_DEFINE_CONDITION_WITH_BASE_PART);
			dto.setConditionTypeValue(dto.getIsWithBasePart());
		}else if(StringUtils.isNotEmpty(dto.getIsItType())){
			dto.setConditionType(DicConstants.TASK_DEFINE_CONDITION_IT_TYPE);
			dto.setConditionTypeValue(dto.getIsItType());
		}else if(StringUtils.isNotEmpty(dto.getIsGovType())){
			dto.setConditionType(DicConstants.TASK_DEFINE_CONDITION_GOV_TYPE);
			dto.setConditionTypeValue(dto.getIsGovType());
		}
	}

	@RequestMapping(value = "getMaterialDef")
	public String getMaterialDef(HttpServletRequest request, HttpServletResponse response, PrjTaskDefineDTO dto,
			Model model) {
		List<PrjTaskMaterialDefDTO> materialList = null;
		if (dto.getId() != null) {
			materialList = materialDefService.findByTaskId(dto.getId());
		}
		
		String materials = request.getParameter("materials");
		if (StringUtils.isNotEmpty(materials)) {
			List<Map<String, String>> list = (List) JsonMapper.fromJsonString(materials, List.class);
			materialList = new ArrayList<PrjTaskMaterialDefDTO>();
			for (Map<String, String> map : list) {
				PrjTaskMaterialDefDTO defdto = new PrjTaskMaterialDefDTO();
				defdto.setMaterialId(Long.parseLong(map.get("id")));
				defdto.setDescription(map.get("value"));
				materialList.add(defdto);
			}

		}
		if(materialList != null){
			for (PrjTaskMaterialDefDTO temp:materialList) {
				temp.setMaterialName(materialService.getMaterialById(temp.getMaterialId()).getName());
			}
		}
		ListResult<PrjTaskDefineDTO> result = service.findReplyList(dto);
		model.addAttribute("resultTaskList", result.getObj());
		
		model.addAttribute("materialList", materialList);
		// List<PrjTaskDefineCondDimDTO> list = new
		// ArrayList<PrjTaskDefineCondDimDTO>();
		// ListResult<PrjTaskDefineDTO> result = service.findReplyList(dto);
		// ListResult<Long> dresult = service.findDependencyList(dto);
		// model.addAttribute("replyList",result.getObj());
		// model.addAttribute("dependencyList",dresult.getObj());
		// model.addAttribute("prjTaskDTO", dto);
		return "modules/project/taskDefineMaterialDef";

	}

	@RequestMapping(value = "getTaskStage")
	public String getTaskStage(HttpServletRequest request, HttpServletResponse response, PrjTaskDefineDTO dto,
			Model model) {
		if (StringUtils.isNotEmpty(dto.getTaskType())) {
			model.addAttribute("stageList", StageUtils.findListByType(dto.getTaskType()));
		}
		return "modules/project/taskDefineStage";

	}

	@RequestMapping(value = "getConditionType")
	public String getConditionType(HttpServletRequest request, HttpServletResponse response, PrjTaskDefineDTO dto,
			Model model) {
		String conditionType = request.getParameter("conditionType");
		String conditionTypeValue = request.getParameter("conditionTypeValue");
		model.addAttribute("conditionTypeValue", conditionTypeValue);
		model.addAttribute("conditionType", conditionType);
		return "modules/project/taskConditionType";

	}

	// @ResponseBody
	// @RequestMapping(value = "getCondDim")
	// public Result<List<PrjTaskTimerDefineDTO>> getCondDim(PrjTaskDefineDTO
	// dto) {
	// Result<List<PrjTaskTimerDefineDTO>> result = new
	// Result<List<PrjTaskTimerDefineDTO>>();
	// List<PrjTaskTimerDefineDTO> list = new
	// ArrayList<PrjTaskTimerDefineDTO>();
	// if(StringUtils.isNotBlank(dto.getCondDimType())){
	// List<Dict> dicts =
	// DictUtils.getDictList(DicConstants.DIC_TYPE_PROJECT_PRICE);
	// if(dicts != null){
	// for(Dict dict:dicts){
	// PrjTaskTimerDefineDTO temp = new PrjTaskTimerDefineDTO();
	// if(DicConstants.TASK_DEFINE_COND_DIM_TYPE_1.equals(dto.getCondDimType())){
	// temp.setPriceType(dict.getValue());
	// }else{
	// temp.setUseageType(dict.getLabel());
	// }
	// temp.setTypeLabel(dict.getLabel());
	// list.add(temp);
	// }
	// }
	// }
	// result.setObj(list);
	// result.setResCode("0");
	// return result;
	// }

}
