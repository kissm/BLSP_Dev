package com.framework.osp.modules.web.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpcode.modules.service.project.inf.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.common.config.Global;
import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.dto.project.PrjTaskMaterialDefDTO;
import com.lpcode.modules.dto.project.change.PrjMaterialVO;
import com.lpcode.modules.service.impl.project.util.ProjectStepUtil;
import com.lpcode.modules.service.impl.project.util.ProjectUtil;
import com.lpcode.modules.service.material.inf.IMaterialService;
import com.lpcode.modules.service.project.dto.ProjectChangeForm;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjMaterialSupplementVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjStageMaterialVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjTaskDefineVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjTaskVo;

/**
 * 对项目补齐材料做处理   控制层
 * @project osp-wsbsblsp-web
 * @package com.framework.osp.modules.web.project
 * @author maxiaowei
 * @createDate 2016年7月28日 下午5:13:11
 */
@Controller
@RequestMapping(value = "${adminPath}/polish")
@Scope("prototype")
public class ProjectPolishMaterialController extends BaseController {
	@Autowired
	private ProjectServiceInf projectService;
	@Autowired
	private PrjTaskMaterialDefService prjTaskMaterialDefService;
	@Autowired
	private PrjTaskService prjTaskService;
	@Autowired
	private IMaterialService materialService;
	@Autowired
	private PrjMaterialSupplementService prjMaterialSupplementService;
	@Autowired
	private ProjectAcceptServiceInf projectAcceptServiceInf;

	/**
	 * 通过项目ID获取该项目下本阶段所有材料信息并跳转到材料补齐页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/polishMaterial")
	public String polishMaterial(HttpServletRequest request, HttpServletResponse response, Model model){
		String id = request.getParameter("id");
		ProjectChangeForm project = new ProjectChangeForm();
		if (StringUtils.isNotBlank(id)) {
			PrjInstanceVo vo = ProjectUtil.getInstance(Long.parseLong(id));
			project.setPrjInstanceVo(vo);
		}
		//得到网厅同类prjCode
		PrjInstanceVo blspSamePrjCode = projectService.getBlspPrjByPrjCode(project.getPrjInstanceVo().getPrjCode());

		//获取对应并联审批项目中事项状态
		List<PrjTaskVo> taskVoList = prjMaterialSupplementService.getPrjTaskVoList(blspSamePrjCode);
		//定义的项目阶段材料清单表Map
		Map<Long,PrjMaterialVO> prjMaterialVoMap = new LinkedHashMap<Long, PrjMaterialVO>();
		project.setMaterialDefMap(prjMaterialVoMap);

		//该阶段，该建筑类别下所有事项
		List<PrjTaskDefineVo> taskList = ProjectStepUtil.getAllTaskByStageGovType(project.getPrjInstanceVo().getIsGovType(),blspSamePrjCode.getStageId().toString());
		if(taskList != null && taskList.size() > 0){
			for (PrjTaskDefineVo prjTaskDefineVo : taskList) {
				//将描述置空，用来存储对应事项的事项处理情况
				prjTaskDefineVo.setDescription(null);
			}
			if(taskVoList != null && taskVoList.size() > 0){
				for (PrjTaskDefineVo prjTaskDefineVo : taskList) {
					for (PrjTaskVo prjTaskVo : taskVoList) {
						if(prjTaskDefineVo.getId().equals(prjTaskVo.getTaskId())){
							prjTaskDefineVo.setDescription(prjTaskVo.getTaskStatus());
							break;
						}
					}
				}
			}
			for (PrjTaskDefineVo prjTaskDefineVo : taskList) {
				//根据事项ID获取所有对应的事项材料关系集合
				List<PrjTaskMaterialDefDTO> taskMaterialDefList = prjTaskMaterialDefService.findByTaskId(prjTaskDefineVo.getId());
				List<PrjMaterialVO> prjMaterialVoList = new ArrayList<PrjMaterialVO>();
				BeanCopy.copyPropertiesForList(taskMaterialDefList, prjMaterialVoList, PrjMaterialVO.class);
				for (PrjMaterialVO prjMaterialVO : prjMaterialVoList) {
					//用来存储事项描述（即：所属并联审批事项的事项状态）
					prjMaterialVO.setDescription(prjTaskDefineVo.getDescription());
					prjMaterialVoMap.put(prjMaterialVO.getMaterialId(), prjMaterialVO);
				}
			}
		}
		//处理项目已保存的材料数据
		prjTaskService.conductMaterial(project);
		//获取某阶段下所有材料名称
		Map<Long, String> allMaterName = materialService.getAllMaterName(blspSamePrjCode.getStageId());
		//遍历项目阶段材料清单表Map，再遍历材料名称map，将材料名称存入到项目阶段材料清单表Map中
		Set<Long> prjMaterialIdSet = prjMaterialVoMap.keySet();
		for (Long prjMaterialId : prjMaterialIdSet) {
			Set<Long> materialIdSet = allMaterName.keySet();
			for (Long materialId : materialIdSet) {
				if(prjMaterialId.equals(materialId)){
					prjMaterialVoMap.get(prjMaterialId).setName(allMaterName.get(materialId));
					break;
				}
			}
		}
		model.addAttribute("project", project);
		return "modules/project/wsbs_polish";
	}
	
	/**
	 * 保存补充的材料信息
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/savePolish")
	public String savePolish(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model){
		//表单提交过来的项目补充的材料
		String josnData = request.getParameter("jsonData");
		//之前已提交的材料数据信息
		String sourceData = request.getParameter("sourceData");
		List<PrjStageMaterialVo> inputList = new ArrayList<PrjStageMaterialVo>();
		if(josnData != null && !"".equals(josnData)){
			inputList = JSONArray.parseArray(josnData, PrjStageMaterialVo.class);
		}
		List<PrjStageMaterialVo> sourceList = new ArrayList<PrjStageMaterialVo>();
		if(sourceData != null && !"".equals(sourceData)){
			sourceList = JSONArray.parseArray(sourceData, PrjStageMaterialVo.class);
		}
		Map<Long, PrjStageMaterialVo> sourceMaterMap = new HashMap();
		for (PrjStageMaterialVo s : sourceList){
			sourceMaterMap.put(s.getMaterialId(),s);
		}

		//补交材料批次,初始化为1
		Long batchNo = prjMaterialSupplementService.getBatchNo(project.getPrjInstanceVo());

//		//通过网上办事项目信息获取到补齐材料表中该项目下的列表
//		List<PrjMaterialSupplementVo> materialSupplementList =  prjMaterialSupplementService.getListByPrjId(project.getPrjInstanceVo());

		//用提交记录里的数据与已有项目数据做比较,没有变化的则跳过,否则进行保存
		Map<Long, PrjStageMaterialVo> changeMaterMap = new HashMap();
		for(PrjStageMaterialVo inputMaterVo : inputList){
			changeMaterMap.put(inputMaterVo.getMaterialId(),inputMaterVo);
			PrjStageMaterialVo svo = sourceMaterMap.get(inputMaterVo.getMaterialId());
			if(svo != null && svo.getMaterialAddr().equals(inputMaterVo.getMaterialAddr())){
				changeMaterMap.remove(svo.getMaterialId());
			}
		}
		//进行数据保存
		List<PrjStageMaterialVo> changeMaterialList = new ArrayList<PrjStageMaterialVo>();
		Iterator iter = changeMaterMap.entrySet().iterator();
		while(iter.hasNext()) {
			Entry entry = (Entry)iter.next();
			PrjStageMaterialVo changeVo = (PrjStageMaterialVo)entry.getValue();
			prjMaterialSupplementService.saveOrUpdateSouceMaterial(changeVo,project.getPrjInstanceVo());//插入或更新网厅提交的项目材料表
			prjMaterialSupplementService.insertNewBatchRecord(changeVo, project.getPrjInstanceVo(), batchNo);//插入新的材料提交批次记录,并查看是否要覆盖以往的提交记录
		}
		return "redirect:"+ Global.getAdminPath() +"/project/succeed?id="+project.getPrjInstanceVo().getId()+"&msg=1";
	}
	
}