package com.framework.osp.modules.web.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.core.result.ListResult;
import com.framework.core.result.Result;
import com.framework.core.utils.BeanCopy;
import com.framework.osp.common.persistence.Page;
import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.impl.project.util.ProjectUtil;
import com.lpcode.modules.service.project.dto.PrjTaskDTO;
import com.lpcode.modules.service.project.dto.PrjTaskForOffLineFinishDTO;
import com.lpcode.modules.service.project.dto.Project;
import com.lpcode.modules.service.project.dto.pinstance.FormRfBjshVo;
import com.lpcode.modules.service.project.dto.pinstance.FormRfYdjsBjspVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjCodeGeneratorVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjTaskDefineVo;
import com.lpcode.modules.service.project.inf.PrjTaskService;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

@Controller
@RequestMapping(value = "${adminPath}/enterprise/project")
public class EnterpriseProjectController extends BaseController {
	@Autowired
	private ProjectServiceInf projectServiceInf;
	@Autowired
	PrjTaskService prjTaskService;

	@RequestMapping(value = "list")
	public String list(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Project> page = new Page<Project>();
		String pageNo = request.getParameter("pageNo");
		if (!StringUtils.isBlank(pageNo)) {
			page.setPageNo(Integer.parseInt(pageNo));
		} else {
			page.setPageNo(1);
		}
		String pageSize = request.getParameter("pageSize");
		if (!StringUtils.isBlank(pageSize)) {
			page.setPageSize(Integer.parseInt(pageSize));
		} else {
			page.setPageSize(10);
		}
		page.setOrderBy("creat_Time desc");
		PrjInstanceVo vo = project.getPrjInstanceVo();
		if (vo == null) {
			vo = new PrjInstanceVo();
			vo.setPrjType("2"); // 企业项目
			project.setPrjInstanceVo(vo);
		}
		projectServiceInf.getProjectPage(project, page);
		model.addAttribute("page", page);
		model.addAttribute("project", project);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
		return "modules/project/enterPrjList";
	}
	
	@RequestMapping(value = "form")
	public String form(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = request.getParameter("id");
		String newStageId = request.getParameter("nextStageId");
		if (StringUtils.isNotBlank(id) && StringUtils.isBlank(newStageId)) {
			project = projectServiceInf.getProject(Long.parseLong(id));
			newStageId = project.getPrjInstanceVo().getStageId().toString();
		} else {
			// 新建项目
			if (StringUtils.isBlank(newStageId)) {
				// 第一个阶段受理
				newStageId = ProjectUtil.getFirstStage("2").getId().toString();// 企业首阶段
			} else {
				// 新阶段受理标志，用于页面材料显示
				model.addAttribute("newStageFlag", "1");
			}
			
			project = projectServiceInf.getEnterProject(id, newStageId);// 创建当前阶段新的受理表单
			
			if (StringUtils.isBlank(id)) {
				PrjCodeGeneratorVo pvo = ProjectUtil.getProjectCode("2");// 企业类项目编号
				if (pvo.getSeq() != null) {
					pvo.setSeq(pvo.getSeq() + 1);
					project.setPrjCodeGeneratorVo(pvo);
					PrjInstanceVo prjInstanceVo = new PrjInstanceVo();
					prjInstanceVo.setPrjCode(project.getPrjCodeGeneratorVo().toString());
					project.setPrjInstanceVo(prjInstanceVo);
				}	
			}
		}
		
		// 判断有无人防表单
		for (PrjTaskDefineVo defvo : ProjectUtil.getAllTaskByStage(Long.parseLong(newStageId))) {
			if ("10007200174172973914440404".equals(defvo.getTaskCode()) || "10007200374172973914440404".equals(defvo.getTaskCode())){
				model.addAttribute("taskCodeFlag", "1");
				
				if (StringUtils.isNotBlank(id)) {
					Result<PrjTaskDTO> prjTask = prjTaskService.findPrjTaskByTaskId(defvo.getId(), Long.parseLong(id));
					if (prjTask.getObj() != null && !"0".equals(prjTask.getObj().getTaskStatus())) {
						model.addAttribute("taskCodeEidt", "1");
					}
				}
				
				// 初始化两张业务表单
				if (StringUtils.isNotBlank(id)) {
					PrjInstanceVo prjInstanceVo = project.getPrjInstanceVo();
					FormRfBjshVo formRfBjshVo = ProjectUtil.getFormRfBjshVo(id);
					if (formRfBjshVo == null) {
						formRfBjshVo = new FormRfBjshVo();
						BeanCopy.copyProperties(prjInstanceVo, formRfBjshVo);
						formRfBjshVo.setId(null);
						formRfBjshVo.setLinkman(prjInstanceVo.getAgentName());
						formRfBjshVo.setLinkmanPhone(prjInstanceVo.getAgentMphone());
						formRfBjshVo.setPrjAddress(prjInstanceVo.getPrjAddr());
					}
					project.setFormRfBjshVo(formRfBjshVo);
					FormRfYdjsBjspVo formRfYdjsBjspVo = ProjectUtil.getFormRfYdjsBjsp(id);
					if (formRfYdjsBjspVo == null) {
						formRfYdjsBjspVo = new FormRfYdjsBjspVo();
						BeanCopy.copyProperties(prjInstanceVo, formRfYdjsBjspVo);
						formRfYdjsBjspVo.setId(null);
						formRfYdjsBjspVo.setLinkman(prjInstanceVo.getAgentName());
						formRfYdjsBjspVo.setLinkmanPhone(prjInstanceVo.getAgentMphone());
						formRfYdjsBjspVo.setPrjAddress(prjInstanceVo.getPrjAddr());
					}
					project.setFormRfYdjsBjspVo(formRfYdjsBjspVo);
				}
				break;
			}
		}
		
		model.addAttribute("stageId", newStageId);
		model.addAttribute("project", project);
		
		if ("1".equals(request.getParameter("editFlag")) || !newStageId.equals(ProjectUtil.getFirstStage("2").getId().toString())) {
			model.addAttribute("editFlag", true);
		}
		return "modules/project/enterPrjForm";
	}

	
	@RequestMapping(value = "view")
	public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = request.getParameter("id");
		if(StringUtils.isBlank(id))
			return null;
		Project project = projectServiceInf.getProjectByPrjId(Long.parseLong(id));
		project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(Long.parseLong(id)));
		project.setPrjStageVoList(ProjectUtil.getStageInsListByProId(id));
		model.addAttribute("project", project);
		return "modules/project/enterPrjView";
	}

	/**
	 * ajax获取可线下办结事项
	 *
	 * @param request
	 * @param prjInsId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("task:offline:finish")
	@RequestMapping(value = "/offLine/finish/form")
	public String offLineFinishForm(HttpServletRequest request, Long prjInsId, Long stageInsId,Model model) {
		if (prjInsId != null) {
			ListResult<PrjTaskForOffLineFinishDTO> resultList = prjTaskService.getTaskForOffLineFinish(prjInsId, stageInsId);
			model.addAttribute("list",resultList.getObj());
			model.addAttribute("prjInsId",prjInsId);
		} 
		return "modules/project/taskOffLineFinishForm";
	}
	
	/**
	 * 保存事项线下办结
	 */
	@RequestMapping(value = "/offLine/finish/save")
	public String offLineFinishSave(HttpServletRequest request, PrjTaskForOffLineFinishDTO dto, Model model) {
		
		prjTaskService.saveTaskFinishOffLine(dto);
		model.addAttribute("message","ok");
		return "modules/project/taskOffLineFinishForm";
	}
	
	@InitBinder
	public void initListBinder(WebDataBinder binder) {
		// 设置需要包裹的元素个数，默认为256
		binder.setAutoGrowCollectionLimit(2048);
	}
}
