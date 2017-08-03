package com.framework.osp.modules.web.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.framework.core.result.Result;
import com.framework.core.utils.BeanCopy;
import com.framework.core.utils.StringUtil;
import com.framework.osp.common.config.Global;
import com.framework.osp.common.utils.StringUtils;
import com.framework.osp.modules.web.bean.blsp.BlspProjectInfoReqBean;
import com.framework.osp.modules.web.bean.blsp.BlspProjectSaveReqBean;
import com.framework.osp.modules.web.bean.blsp.PrjMateriaRespBean;
import com.framework.osp.modules.web.bean.blsp.PrjStageMaterialBean;
import com.framework.osp.modules.web.bean.prjschedule.ProjectScheduleReqBean;
import com.framework.osp.modules.web.bean.prjschedule.ProjectScheduleRespBean;
import com.lpcode.modules.blsp.entity.PrjStageMaterial;
import com.lpcode.modules.dto.project.PrjTaskMaterialDefDTO;
import com.lpcode.modules.dto.project.change.PrjMaterialVO;
import com.lpcode.modules.service.impl.project.ProjectAcceptService;
import com.lpcode.modules.service.impl.project.util.ProjectStepUtil;
import com.lpcode.modules.service.impl.project.util.ProjectUtil;
import com.lpcode.modules.service.material.inf.IMaterialService;
import com.lpcode.modules.service.project.dto.ProjectChangeForm;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjStageMaterialVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjTaskDefineVo;
import com.lpcode.modules.service.project.dto.stageTaskMaterialDef.TaskMaterialDefForm;
import com.lpcode.modules.service.project.inf.PrjTaskMaterialDefService;
import com.lpcode.modules.service.project.inf.PrjTaskService;
import com.lpcode.modules.service.project.inf.ProjectAcceptServiceInf;

/**
 * 并联审批调用接口-政府
 * 
 * @author weinan
 *
 */
@RestController
@RequestMapping(value = "/blspZf")
public class BlspQueryZfController {
	
	@Autowired
	private ProjectAcceptServiceInf projectAcceptServiceInf;
	@Autowired
	private PrjTaskMaterialDefService prjTaskMaterialDefService;
	@Autowired
	private PrjTaskService prjTaskService;
	@Autowired
	private ProjectAcceptService projectAcceptService;
	@Autowired
	private IMaterialService materialService;

	@RequestMapping(value = "/toZf", method = RequestMethod.GET)
	public Result<List<TaskMaterialDefForm>> toZf(HttpServletRequest request, HttpServletResponse response) {
		Result<List<TaskMaterialDefForm>> result = new Result<List<TaskMaterialDefForm>>();
		List<TaskMaterialDefForm> list = ProjectStepUtil.getStageTaskMaterialDefByStage(1L);
		if(list.size()>0){
			result.setObj(list);
		}
		return result;
	}
	/**
	 * 基本信息创建信息
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/project")
	public Result<ProjectChangeForm> basic(@RequestBody BlspProjectInfoReqBean psRequest, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Result<ProjectChangeForm> result=new Result<ProjectChangeForm> ();
		ProjectChangeForm project=new ProjectChangeForm();
		PrjInstanceVo in = new PrjInstanceVo() ;
		project.setPrjInstanceVo(in);
		if (StringUtils.isNotBlank(psRequest.getPriceType())) {
			in.setPriceType(psRequest.getPriceType());
		}
		if (StringUtils.isNotBlank(psRequest.getUseageType())) {
			in.setUseageType(psRequest.getUseageType());
		}
		if (StringUtils.isNotBlank(psRequest.getIsGovType())) {
			in.setIsGovType(psRequest.getIsGovType());
		}
		if (StringUtils.isNotBlank(psRequest.getPrjType())) {
			in.setPrjType(psRequest.getPrjType());
		}
		if (StringUtils.isNotBlank(psRequest.getUserName())) {
			in.setWsbsUserName(psRequest.getUserName());
		}
		
		projectAcceptServiceInf.savePrjInstanceTemp(in);
		
		if (StringUtils.isNotBlank(in.getId().toString())) {
			PrjInstanceVo vo = ProjectUtil.getInstance(Long.parseLong(in.getId().toString()));
			project.setPrjInstanceVo(vo);
		}
		/*//定义的项目阶段材料清单表Map
		Map<Long,PrjMaterialVO> prjMaterialVoMap = new LinkedHashMap<Long, PrjMaterialVO>();
		project.setMaterialDefMap(prjMaterialVoMap);
		//该阶段，该建筑类别下所有事项
		List<PrjTaskDefineVo> taskList = ProjectStepUtil.getAllTaskByStageGovType(project.getPrjInstanceVo().getIsGovType(),project.getPrjInstanceVo().getStageId().toString());
		for (PrjTaskDefineVo prjTaskDefineVo : taskList) {
			//根据事项ID获取所有对应的事项材料关系集合
			List<PrjTaskMaterialDefDTO> taskMaterialDefList = prjTaskMaterialDefService.findByTaskId(prjTaskDefineVo.getId());
			List<PrjMaterialVO> prjMaterialVoList = new ArrayList<PrjMaterialVO>();
			BeanCopy.copyPropertiesForList(taskMaterialDefList, prjMaterialVoList, PrjMaterialVO.class);
			for (PrjMaterialVO prjMaterialVO : prjMaterialVoList) {
				prjMaterialVoMap.put(prjMaterialVO.getMaterialId(), prjMaterialVO);
			}
		}
		//处理项目已保存的材料数据
		prjTaskService.conductMaterial(project);
		//获取某阶段下所有材料名称
		Map<Long, String> allMaterName = materialService.getAllMaterName(project.getPrjInstanceVo().getStageId());
		//遍历项目阶段材料清单表Map，再遍历材料名称map，将材料名称存入到项目阶段材料清单表Map中
		Set<Long> prjMaterialIdSet = prjMaterialVoMap.keySet();
		for (Long prjMaterialId : prjMaterialIdSet) {
			Set<Long> materialIdSet = allMaterName.keySet();
			for (Long materialId : materialIdSet) {
				if(prjMaterialId.equals(materialId)){
					prjMaterialVoMap.get(prjMaterialId).setName(allMaterName.get(materialId));
				}
			}
		}*/
		result.setObj(project);
		return result;
	}
	
	/**
	 * 基本信息-暂存
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/projectSave")
	public Result<ProjectChangeForm> projectSave(@RequestBody BlspProjectSaveReqBean psRequest, HttpServletRequest request, HttpServletResponse response, Model model) {
		Result<ProjectChangeForm> result=new Result<ProjectChangeForm> ();
		ProjectChangeForm project=new ProjectChangeForm();
		PrjInstanceVo prjvo=new PrjInstanceVo();
		project.setPrjInstanceVo(prjvo);
		BeanCopy.copyProperties(psRequest, project.getPrjInstanceVo());
		
		project.getPrjInstanceVo().setIsDelete("0");
	
		projectAcceptServiceInf.updatePrjInstance(project.getPrjInstanceVo());
		/*//接收材料集合
		String josnData = request.getParameter("jsonData");
		if(josnData != null && !"".equals(josnData)){
			List<PrjStageMaterialVo> list = JSONArray.parseArray(josnData, PrjStageMaterialVo.class);
			if (list != null && list.size() > 0) {
				for (PrjStageMaterialVo vo : list) {
					vo.setPrjId(project.getPrjInstanceVo().getId());
				}
				projectAcceptServiceInf.updatePrjMaterial(list, project.getPrjInstanceVo());
			}
		}*/
		String projectId = project.getPrjInstanceVo().getId().toString();
		result.setObj(project);
		return result;
	}
	
	/**
	 * 基本信息-查询材料
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/projectMaterial")
	public Result<List<PrjMaterialVO>> projectMaterial(@RequestBody BlspProjectSaveReqBean psRequest, HttpServletRequest request, HttpServletResponse response, Model model) {
		Result<List<PrjMaterialVO>> result=new Result<List<PrjMaterialVO>> ();
		ProjectChangeForm project=new ProjectChangeForm();
		
		//定义的项目阶段材料清单表Map
		Map<Long,PrjMaterialVO> prjMaterialVoMap = new LinkedHashMap<Long, PrjMaterialVO>();
		project.setMaterialDefMap(prjMaterialVoMap);
		//该阶段，该建筑类别下所有事项
		List<PrjTaskDefineVo> taskList = ProjectStepUtil.getAllTaskByStageGovType(psRequest.getIsGovType(),psRequest.getStageId().toString());
		for (PrjTaskDefineVo prjTaskDefineVo : taskList) {
			//根据事项ID获取所有对应的事项材料关系集合
			List<PrjTaskMaterialDefDTO> taskMaterialDefList = prjTaskMaterialDefService.findByTaskId(prjTaskDefineVo.getId());
			List<PrjMaterialVO> prjMaterialVoList = new ArrayList<PrjMaterialVO>();
			BeanCopy.copyPropertiesForList(taskMaterialDefList, prjMaterialVoList, PrjMaterialVO.class);
			for (PrjMaterialVO prjMaterialVO : prjMaterialVoList) {
				prjMaterialVoMap.put(prjMaterialVO.getMaterialId(), prjMaterialVO);
			}
		}
		//处理项目已保存的材料数据
		//prjTaskService.conductMaterial(project);
		//获取某阶段下所有材料名称
		Map<Long, String> allMaterName = materialService.getAllMaterName(psRequest.getStageId());
		//遍历项目阶段材料清单表Map，再遍历材料名称map，将材料名称存入到项目阶段材料清单表Map中
		Set<Long> prjMaterialIdSet = prjMaterialVoMap.keySet();
		for (Long prjMaterialId : prjMaterialIdSet) {
			Set<Long> materialIdSet = allMaterName.keySet();
			for (Long materialId : materialIdSet) {
				if(prjMaterialId.equals(materialId)){
					prjMaterialVoMap.get(prjMaterialId).setName(allMaterName.get(materialId));
				}
			}
		}
		List<PrjMaterialVO> prjVoList=new ArrayList<PrjMaterialVO>();
		for(Map.Entry<Long,PrjMaterialVO> entry:prjMaterialVoMap.entrySet()){  
			PrjMaterialVO prj = entry.getValue();
			prjVoList.add(prj);
		}

		result.setObj(prjVoList);
		return result;
	}
	
	/**
	 * 基本信息-查询事项
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/projectMaterialItem")
	public Result<List<PrjTaskDefineVo>> projectMaterialItem(@RequestBody BlspProjectSaveReqBean psRequest, HttpServletRequest request, HttpServletResponse response, Model model) {
		Result<List<PrjTaskDefineVo>> result=new Result<List<PrjTaskDefineVo>> ();
		ProjectChangeForm project=new ProjectChangeForm();
		
		//定义的项目阶段材料清单表Map
		Map<Long,PrjMaterialVO> prjMaterialVoMap = new LinkedHashMap<Long, PrjMaterialVO>();
		project.setMaterialDefMap(prjMaterialVoMap);
		//该阶段，该建筑类别下所有事项
		List<PrjTaskDefineVo> taskList = ProjectStepUtil.getAllTaskByStageGovType(psRequest.getIsGovType(),psRequest.getStageId().toString());
		result.setObj(taskList);
		return result;
	}
	
	/**
	 * 基本信息-查询 事项材料
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/projectMaterialItemById")
	public Result<List<PrjMateriaRespBean>> projectMaterialItemById(@RequestBody PrjMateriaRespBean psRequest, HttpServletRequest request, HttpServletResponse response, Model model) {
		Result<List<PrjMateriaRespBean>> result=new Result<List<PrjMateriaRespBean>> ();
		ProjectChangeForm project=new ProjectChangeForm();
		
		//定义的项目阶段材料清单表Map
		Map<Long,PrjMaterialVO> prjMaterialVoMap = new LinkedHashMap<Long, PrjMaterialVO>();
		project.setMaterialDefMap(prjMaterialVoMap);
		//该阶段，该建筑类别下所有事项

			//根据事项ID获取所有对应的事项材料关系集合
			List<PrjTaskMaterialDefDTO> taskMaterialDefList = prjTaskMaterialDefService.findByTaskId(psRequest.getTaskId());
			List<PrjMaterialVO> prjMaterialVoList = new ArrayList<PrjMaterialVO>();
			BeanCopy.copyPropertiesForList(taskMaterialDefList, prjMaterialVoList, PrjMaterialVO.class);
			for (PrjMaterialVO prjMaterialVO : prjMaterialVoList) {
				prjMaterialVoMap.put(prjMaterialVO.getMaterialId(), prjMaterialVO);
			}
		//处理项目已保存的材料数据
		//prjTaskService.conductMaterial(project);
		//获取某阶段下所有材料名称
		Map<Long, String> allMaterName = materialService.getAllMaterName(psRequest.getStageId());
		//遍历项目阶段材料清单表Map，再遍历材料名称map，将材料名称存入到项目阶段材料清单表Map中
		Set<Long> prjMaterialIdSet = prjMaterialVoMap.keySet();
		for (Long prjMaterialId : prjMaterialIdSet) {
			Set<Long> materialIdSet = allMaterName.keySet();
			for (Long materialId : materialIdSet) {
				if(prjMaterialId.equals(materialId)){
					prjMaterialVoMap.get(prjMaterialId).setName(allMaterName.get(materialId));
				}
			}
		}
		List<String> plist = new ArrayList<String>();
		plist.add("null");
		List<PrjMateriaRespBean> prjVoList=new ArrayList<PrjMateriaRespBean>();
		for(Map.Entry<Long,PrjMaterialVO> entry:prjMaterialVoMap.entrySet()){  
			PrjMaterialVO prj = entry.getValue();
			PrjMateriaRespBean preMateriaV=new PrjMateriaRespBean();
			BeanCopy.copyProperties(prj, preMateriaV);
			if(preMateriaV.getFileImageUrls()==null){
				preMateriaV.setFileImageUrls(plist);
			}
			
			prjVoList.add(preMateriaV);
		}

		result.setObj(prjVoList);
		return result;
	}
	
	/**
	 * 基本信息-事项材料保存
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/projectMaterialItemSave")
	public Result<String> projectMaterialItemSave(@RequestBody PrjStageMaterialBean psRequest, HttpServletRequest request, HttpServletResponse response, Model model) {
		Result<String> result=new Result<String> ();
		PrjInstanceVo vo=new PrjInstanceVo();
		vo.setStageId(psRequest.getStageId());
		vo.setId(psRequest.getPrjId());
		PrjStageMaterialVo prjStageMaterialVo=new PrjStageMaterialVo();
		BeanCopy.copyProperties(psRequest, prjStageMaterialVo);
		List<PrjStageMaterialVo> list =new ArrayList<PrjStageMaterialVo>();
		list.add(prjStageMaterialVo);
		//保存的项目阶段材料清单表Map
		projectAcceptService.savePrjMaterial(list, vo);
		result.setObj("success");
		return result;
	}
	

}
