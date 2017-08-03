package com.framework.osp.modules.web.project;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpcode.modules.service.project.inf.PrjMaterialSupplementService;
import com.lpcode.modules.service.report.NewSbDataService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.framework.core.result.ListResult;
import com.framework.core.utils.BeanCopy;
import com.framework.fdfs.images.FastDFSFileFactory;
import com.framework.fdfs.images.FastDFSImage;
import com.framework.osp.common.config.Global;
import com.framework.osp.common.persistence.Page;
import com.framework.osp.common.web.BaseController;
import com.framework.osp.modules.sys.utils.DictUtils;
import com.framework.osp.modules.web.util.FormEncodeUtil;
import com.framework.osp.modules.web.util.FreeMakerUtils;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.blsp.entity.PrjBusinessAccept;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.dto.project.change.PrjMaterialVO;
import com.lpcode.modules.dto.project.change.PrjTaskDefVO;
import com.lpcode.modules.service.buildcompany.dto.BuildCompanyDto;
import com.lpcode.modules.service.buildcompany.inf.IBuildCompanyService;
import com.lpcode.modules.service.dimension.holiday.DimHolidayService;
import com.lpcode.modules.service.form.IFactoryFormService;
import com.lpcode.modules.service.impl.project.util.ProjectStepUtil;
import com.lpcode.modules.service.material.dto.MaterialDto;
import com.lpcode.modules.service.project.dto.PrjTaskForOffLineFinishDTO;
import com.lpcode.modules.service.project.dto.Project;
import com.lpcode.modules.service.project.dto.ProjectChangeForm;
import com.lpcode.modules.service.project.dto.pinstance.AcceptVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjBusinessAcceptVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjCodeGeneratorVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjStageMaterialDefineVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjStageMaterialVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjTaskDefineVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjTaskVo;
import com.lpcode.modules.service.project.dto.pinstance.ProjectVo;
import com.lpcode.modules.service.project.dto.pinstance.TaskFormConf;
import com.lpcode.modules.service.project.inf.PrjTaskService;
import com.lpcode.modules.service.project.inf.ProjectAcceptServiceInf;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 企业项目受理过程
 * @author Pengs
 * @package com.framework.osp.modules.web.project
 * @fileName ProjectBizAcceptController
 * @date 16/5/23.
 */
@Controller
@RequestMapping(value = "${adminPath}/project/bizaccept/")
public class ProjectBizAcceptController extends BaseController {
	@Autowired
	private ProjectAcceptServiceInf projectAcceptServiceInf;
	@Autowired
	private ProjectServiceInf projectServiceInf;
	@Autowired
	private IBuildCompanyService buildCompanyService;
	@Autowired
	private IFactoryFormService factoryFormService;
	@Autowired
	private DimHolidayService dimHolidayService;
	@Autowired
	private PrjTaskService prjTaskService;
	@Autowired
	private PrjMaterialSupplementService supplementService;
	@ModelAttribute
	public ProjectChangeForm get(@RequestParam(required = false) String projectId) {
		if (StringUtils.isNotBlank(projectId)) {
			Long pid = Long.parseLong(projectId);
			PrjInstanceVo vo = ProjectStepUtil.getInstance(pid);
			ProjectChangeForm p = new ProjectChangeForm();
			p.setPrjInstanceVo(vo);
			return p;
		} else {
			return new ProjectChangeForm();
		}
	}

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
		return "modules/project/bizaccept/enterPrjList";
	}

	@RequestMapping(value = "form")
	public String form(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String projectId = request.getParameter("projectId");
		String newStageId = request.getParameter("nextStageId");
		if (org.apache.commons.lang3.StringUtils.isNotBlank(projectId) && org.apache.commons.lang3.StringUtils.isBlank(newStageId)) {
			project = projectServiceInf.getProject(Long.parseLong(projectId));
			newStageId = project.getPrjInstanceVo().getStageId().toString();
		} else {
			// 新建项目
			if (StringUtils.isBlank(newStageId)) {
				// 第一个阶段受理
				newStageId = ProjectStepUtil.getFirstStage("2").getId().toString();// 企业首阶段
			} else {
				// 新阶段受理标志，用于页面材料显示
				model.addAttribute("newStageFlag", "1");
			}

			project = projectServiceInf.getEnterProject(projectId, newStageId);// 创建当前阶段新的受理表单

			if (org.apache.commons.lang3.StringUtils.isBlank(projectId)) {
				PrjCodeGeneratorVo pvo = ProjectStepUtil.getProjectCode("2");// 企业类项目编号
				if (pvo.getSeq() != null) {
					pvo.setSeq(pvo.getSeq() + 1);
					project.setPrjCodeGeneratorVo(pvo);
					PrjInstanceVo prjInstanceVo = new PrjInstanceVo();
					prjInstanceVo.setPrjCode(project.getPrjCodeGeneratorVo().toString());
					project.setPrjInstanceVo(prjInstanceVo);
				}
			}
		}
		model.addAttribute("stageId", newStageId);
		model.addAttribute("project", project);
		request.setAttribute("action", "update");
		if ("1".equals(request.getParameter("editFlag")) || !newStageId.equals(ProjectStepUtil.getFirstStage("2").getId().toString())) {
			model.addAttribute("editFlag", true);
		}
		return "modules/project/bizaccept/enterPrjForm";
	}

//	@RequestMapping(value = "index")
//	public String index(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model) {
//		return "modules/project/accept/index";
//	}

	@RequestMapping(value = "basic")
	public String basic(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String view = "modules/project/bizaccept/enterPrjForm";
		model.addAttribute("project", project);
		String newStageId = request.getParameter("stageId");
		String action = request.getAttribute("action") == null ? request.getParameter("action") : (String) request.getAttribute("action");
		String type = request.getAttribute("type") == null ? request.getParameter("type") : (String) request.getAttribute("type");
		String url = request.getAttribute("url") == null ? request.getParameter("url") : (String) request.getAttribute("url");
//		String projectIdstr = request.getAttribute("projectId") == null ? request.getParameter("projectId") : (String) request.getAttribute("projectId");
//		if(!StringUtils.equals(action, "update") && StringUtils.isNotBlank(projectIdstr) ){
//			project = get(projectIdstr);
//		}
		if (StringUtils.equals(action, "save")) {
			String finalPrjCode = projectAcceptServiceInf.lockPrjCode(project.getPrjCodeGeneratorVo(), project.getPrjInstanceVo().getPrjCode());
			project.getPrjInstanceVo().setPrjCode(finalPrjCode);
			// 项目龙贝码
			// 受理龙贝码
			lpcode(project.getPrjInstanceVo());
			project.getPrjInstanceVo().setChannel("0");//供应渠道为0,标识为后台窗口人员录入
			projectAcceptServiceInf.savePrjInstance(project.getPrjInstanceVo());
			if (url != null && url.equals("next")) {
				request.setAttribute("action", "view");
				material(project, request, response, model);
				String projectId = project.getPrjInstanceVo().getId().toString();
				return "redirect:"+ Global.getAdminPath() +"/project/bizaccept/toMaterial?projectId="+projectId;
			}
		} else if (StringUtils.equals(action, "update")) {
			//如果为空时生成 项目龙贝码 受理龙贝码 （历史数据存在的问题）
			if(StringUtils.isBlank(project.getPrjInstanceVo().getLpcodeAddr())){
				lpcode(project.getPrjInstanceVo());
			}
			if(StringUtils.isNumeric(newStageId)){
				project.getPrjInstanceVo().setStageId(Long.parseLong(newStageId));
			}
			projectAcceptServiceInf.updatePrjInstance(project.getPrjInstanceVo());
			if (url != null && url.equals("next")) {
				request.setAttribute("action", "view");
				return material(project, request, response, model);
			}
		} else {
			if (project.getPrjInstanceVo() == null || project.getPrjInstanceVo().getId() == null) {
				PrjInstanceVo in = project.getPrjInstanceVo() == null ? new PrjInstanceVo() : project.getPrjInstanceVo();
				project.setPrjInstanceVo(in);
				PrjCodeGeneratorVo pvo = ProjectStepUtil.getProjectCode(type);
				if (pvo.getSeq() != null) {
					pvo.setSeq(pvo.getSeq() + 1);
					project.setPrjCodeGeneratorVo(pvo);
					in.setPrjCode(project.getPrjCodeGeneratorVo().toString());
				}
				model.addAttribute("action", "save");
			} else {
				model.addAttribute("action", "update");
			}
		}
		return view;
	}

	@RequestMapping(value = "getProject")
	public String getProject(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("project", project);
		String id = request.getParameter("id");
		BuildCompanyDto dto = buildCompanyService.getBuildCompanyById(Long.parseLong(id));
		if (project.getPrjInstanceVo() == null) {
			PrjInstanceVo vo = new PrjInstanceVo();
			project.setPrjInstanceVo(vo);
			BeanCopy.copyProperties(dto, vo);
			vo.setId(null);
		} else if (project.getPrjInstanceVo().getId() == null) {
			BeanCopy.copyProperties(dto, project.getPrjInstanceVo());
			project.getPrjInstanceVo().setId(null);
		} else {
			BeanCopy.copyProperties(dto, project.getPrjInstanceVo());
		}
		return basic(project, request, response, model);
	}

	@RequestMapping(value = "material")
	public String material(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String view = "modules/project/bizaccept/bizMaterial";
		model.addAttribute("project", project);
		String action = request.getAttribute("action") == null ? request.getParameter("action") : (String) request.getAttribute("action");
		String url = request.getAttribute("url") == null ? request.getParameter("url") : (String) request.getAttribute("url");
		if (project.getPrjInstanceVo() == null) {
			return "modules/project/bizaccept/enterPrjForm";
		}
		String josnData = request.getParameter("jsonData");
		List<PrjStageMaterialVo> list = JSONArray.parseArray(josnData, PrjStageMaterialVo.class);

		Map<Long,MaterialDto> mapMaterial = ProjectStepUtil.getAllMaterial();
		//判断是否有补交材料
		boolean supplementMater = supplementService.existsSuplementMaterial(project.getPrjInstanceVo());
		model.addAttribute("supplementMater", supplementMater);
		if (StringUtils.equals(action, "save")) {
			if (list != null && list.size() > 0) {
				for (PrjStageMaterialVo vo : list) {
					vo.setPrjId(project.getPrjInstanceVo().getId());
				}
			}
			projectAcceptServiceInf.savePrjMaterial(list, project.getPrjInstanceVo());
			if (url != null) {
				if (url.equals("pre")) {
					request.setAttribute("action", "view");
					request.setAttribute("url", "view");
					return basic(project, request, response, model);
				} else if (url.equals("next")) {
					request.setAttribute("action", "view");
					request.setAttribute("url", "view");
					return forms(project, request, response, model);
				}
			}
		} else if (StringUtils.equals(action, "update")) {
			if (list != null && list.size() > 0) {
				for (PrjStageMaterialVo vo : list) {
					vo.setPrjId(project.getPrjInstanceVo().getId());
				}
			}
			projectAcceptServiceInf.updatePrjMaterial(list, project.getPrjInstanceVo());
			if (url != null) {
				if (url.equals("pre")) {
					request.setAttribute("action", "view");
					request.setAttribute("url", "view");
					return basic(project, request, response, model);
				} else if (url.equals("next")) {
					request.setAttribute("action", "view");
					request.setAttribute("url", "view");
					return forms(project, request, response, model);
				}
			}
		} else {
			Long stageId = project.getPrjInstanceVo().getStageId() ;
			stageId = (stageId == null ? ProjectStepUtil.getFirstStage(project.getPrjInstanceVo().getPrjType()).getId() : stageId);
			Long projectId = project.getPrjInstanceVo().getId();
			Map<Long, PrjMaterialVO> materialDefMapDefine = ProjectStepUtil.getStageDefineMaterList(projectId, stageId);
			Map<Long, Map<Long, PrjMaterialVO>> taskDefMapDefine = ProjectStepUtil.getStageDefineTaskList(projectId, stageId);
			Map<Long, String> taskMap = ProjectStepUtil.getAllTaskjInstance(projectId);
			Map<Long,String> prjTaskStatusMap = ProjectStepUtil.getPrjTaskStatusMap(projectId);
			if(prjTaskStatusMap != null)
				project.setPrjTaskStatus(prjTaskStatusMap);
			List<PrjStageMaterialVo> maList = ProjectStepUtil.getStageMaterList(projectId, stageId);
			if (maList == null || maList.size() == 0) {
				project.setMaterialDefMap(materialDefMapDefine);
				project.setTaskDefMap(taskDefMapDefine);
				model.addAttribute("action", "save");
			} else {
				model.addAttribute("action", "update");
				Map<Long, PrjStageMaterialVo> map = new HashMap<Long, PrjStageMaterialVo>();
				Map<String, PrjStageMaterialVo> maps = new HashMap<String, PrjStageMaterialVo>();
				for (PrjStageMaterialVo vo : maList) {
					PrjStageMaterialVo newVo = new PrjStageMaterialVo();
					BeanCopy.copyProperties(vo, newVo);
					String key = vo.getMaterialId().toString() + "-" + vo.getTaskId().toString();
					maps.put(key, newVo);
					if (map.get(vo.getMaterialId()) != null) {
						PrjStageMaterialVo m = map.get(vo.getMaterialId());
						if (mapMaterial.get(vo.getMaterialId()).getIsOriginalCumulation().equals("1")) {
							m.setOriginalNum((m.getOriginalNum() == null ? 0 : m.getOriginalNum()) + (vo.getOriginalNum() == null ? 0 : vo.getOriginalNum()));
						}else if(mapMaterial.get(vo.getMaterialId()).getIsOriginalCumulation().equals("0") && vo.getOriginalNum() != 0){
							//取不等于0时最大的原件数
							m.setOriginalNum(vo.getOriginalNum() > m.getOriginalNum() ? vo.getOriginalNum() : m.getOriginalNum());
						}
						m.setCopyNum((m.getCopyNum() == null ? 0 : m.getCopyNum()) + (vo.getCopyNum() == null ? 0 : vo.getCopyNum()));
					} else {
						map.put(vo.getMaterialId(), vo);
					}
				}
				if (materialDefMapDefine != null && materialDefMapDefine.size() > 0) {
					for (Long key : materialDefMapDefine.keySet()) {
						PrjStageMaterialVo vo = map.get(key);
						if (vo != null) {
							PrjMaterialVO v = materialDefMapDefine.get(key);
							if (v != null) {
								v.setCopyNumReal(vo.getCopyNum());
								v.setOriginalNumReal(vo.getOriginalNum());
								v.setMaterialAddr(vo.getMaterialAddr());
								//在task表中查询project的对应task处理状态,未受理的默认为0
								if(v.getTaskList() != null){
									for(PrjTaskDefVO taskDefVO : v.getTaskList()){
										String taskStatus = taskMap.get(taskDefVO.getId());
										if(StringUtils.isNotBlank(taskStatus)){
											taskDefVO.setTaskStatus(taskStatus);
										}else{
											taskDefVO.setTaskStatus("0");
										}
									}
								}
							}
						}
					}
				}
				if (taskDefMapDefine != null && taskDefMapDefine.size() > 0) {
					for (Long key : taskDefMapDefine.keySet()) {
						Map<Long, PrjMaterialVO> mmap = taskDefMapDefine.get(key);
						for (Long k : mmap.keySet()) {
							PrjMaterialVO v = mmap.get(k);
							String keys = v.getMaterialId().toString() + "-" + v.getTaskId().toString();
							PrjStageMaterialVo vo = maps.get(keys);
							if (vo != null) {
								v.setCopyNumReal(vo.getCopyNum());
								v.setOriginalNumReal(vo.getOriginalNum());
							}
						}
					}
				}
			}
			project.setMaterialDefMap(materialDefMapDefine);
			project.setTaskDefMap(taskDefMapDefine);
		}
		return view;
	}

	@RequestMapping(value = "forms")
	public String forms(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String view = "modules/project/bizaccept/bizbusiness";
		model.addAttribute("project", project);
		String action = request.getAttribute("action") == null ? request.getParameter("action") : (String) request.getAttribute("action");
		String url = request.getAttribute("url") == null ? request.getParameter("url") : (String) request.getAttribute("url");
		if (project.getPrjInstanceVo() == null) {
			return "modules/project/bizaccept/enterPrjForm";
		}

		if (url != null) {
			if (url.equals("pre")) {
				request.setAttribute("action", "view");
				return material(project, request, response, model);
			} else if (url.equals("next")) {
				request.setAttribute("action", "view");
				String taskIds = request.getParameter("taskIds");
				request.setAttribute("taskIds",taskIds);
				return tasks(project, request, response, model);
			}
		}

		Long sid = project.getPrjInstanceVo().getStageId() == null ? 0L : project.getPrjInstanceVo().getStageId();
		List<PrjStageMaterialVo> maList = ProjectStepUtil.getStageMaterList(project.getPrjInstanceVo().getId(), sid);
		List<PrjStageMaterialDefineVo> listDefine = ProjectStepUtil.getStageDefineMaterList(sid);
		List<PrjStageMaterialVo> list = new ArrayList<PrjStageMaterialVo>();
		BeanCopy.copyPropertiesForList(listDefine, list, PrjStageMaterialVo.class);
		List<PrjTaskDefineVo> taskList = new ArrayList<PrjTaskDefineVo>();
		Map<Long, List<PrjStageMaterialVo>> must = new HashMap<Long, List<PrjStageMaterialVo>>();
		Map<String, PrjStageMaterialVo> materialList = new HashMap<String, PrjStageMaterialVo>();
		//定义可直接完成事项集合
		List<PrjTaskDefineVo> prjTaskDefList = new ArrayList<PrjTaskDefineVo>();
		if (maList != null && maList.size() > 0) {
			//获取可直接办结的事项集合
			prjTaskDefList = projectServiceInf.mayFinishTaskList(maList);
			for (PrjStageMaterialVo mater : maList) {
				String key = mater.getMaterialId().toString() + "-" + mater.getTaskId().toString();
				materialList.put(key, mater);
			}
			for (PrjStageMaterialVo mater : list) {
				String key = mater.getMaterialId().toString() + "-" + mater.getTaskId().toString();
				if (materialList.get(key) != null) {
					PrjStageMaterialVo v = materialList.get(key);
					mater.setIsComplete(v.getIsComplete());
				} else {
					mater.setIsComplete("0");
				}
				Long taskid = mater.getTaskId();
				if (must.get(taskid) != null) {
					List<PrjStageMaterialVo> l = must.get(taskid);
					l.add(mater);
				} else {
					List<PrjStageMaterialVo> l = new ArrayList<PrjStageMaterialVo>();
					l.add(mater);
					must.put(taskid, l);
				}
			}
			for (Long key : must.keySet()) {
				List<PrjStageMaterialVo> materList = must.get(key);
				boolean canStartTask = true;
				boolean hasComplete = false;
				for (PrjStageMaterialVo v : materList) {
					if (v.getIsComplete().equals("1")) {
						hasComplete = true;
					}
					if (v.getIsMandatory().equals("1") && v.getIsComplete().equals("0")) {
						canStartTask = false;
						break;
					}
				}
				if (canStartTask && hasComplete) {
					PrjTaskDefineVo vo = ProjectStepUtil.getPrjTaskDefineVo(key);
					taskList.add(vo);
				}
			}
		}
		List<Long> taskIds = new ArrayList<Long>();
		for (PrjTaskDefineVo vo : taskList) {
			Map<Long, PrjTaskVo> m = ProjectStepUtil.getHasStartTask(project.getPrjInstanceVo());
			PrjTaskVo p = m.get(vo.getId());
			if (p == null) {
				taskIds.add(vo.getId());
			}
		}
		Map<Long, TaskFormConf> map = ProjectStepUtil.getTaskFormConf(project.getPrjInstanceVo().getId(), taskIds);
		model.addAttribute("tasks", map);
		model.addAttribute("taskList", prjTaskDefList);
		return view;
	}

	@RequestMapping(value = "tasks")
	public String tasks(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("project", project);
		String action = request.getAttribute("action") == null ? request.getParameter("action") : (String) request.getAttribute("action");
		String url = request.getAttribute("url") == null ? request.getParameter("url") : (String) request.getAttribute("url");
		String views = "modules/project/bizaccept/biztask";
		if (project.getPrjInstanceVo() == null) {
			return "modules/project/bizaccept/enterPrjForm";
		}
		List<PrjTaskVo> list = project.getPrjTaskVoList();
		if (StringUtils.equals(action, "save")) {
			if (url != null) {
				if (url.equals("pre")) {
					request.setAttribute("action", "view");
					return forms(project, request, response, model);
				} else if (url.equals("next")) {
					if (list != null && list.size() > 0) {
						for (PrjTaskVo vo : list) {
							vo.setPrjId(project.getPrjInstanceVo().getId());
						}
					}
					projectAcceptServiceInf.savePrjTask(list, project.getPrjInstanceVo());
					request.setAttribute("action", "view");
					return printView(project, request, response, model);
				}
			}
		} else {
			Long sid = project.getPrjInstanceVo().getStageId() == null ? 0L : project.getPrjInstanceVo().getStageId();
			List<PrjStageMaterialVo> maList = ProjectStepUtil.getStageMaterList( project.getPrjInstanceVo().getId(),sid);
			List<PrjStageMaterialDefineVo> listDefine = ProjectStepUtil.getStageDefineMaterList(sid);
			List<PrjStageMaterialVo> plist = new ArrayList<PrjStageMaterialVo>();
			BeanCopy.copyPropertiesForList(listDefine, plist, PrjStageMaterialVo.class);
			List<PrjTaskVo> taskList = new ArrayList<PrjTaskVo>();
			Map<Long, List<PrjStageMaterialVo>> must = new HashMap<Long, List<PrjStageMaterialVo>>();
			Map<String, PrjStageMaterialVo> materialList = new HashMap<String, PrjStageMaterialVo>();
			if (maList != null && maList.size() > 0) {
				for (PrjStageMaterialVo mater : maList) {
					String key = mater.getMaterialId().toString() + "-" + mater.getTaskId().toString();
					materialList.put(key, mater);
				}
				for (PrjStageMaterialVo mater : plist) {
					String key = mater.getMaterialId().toString() + "-" + mater.getTaskId().toString();
					if (materialList.get(key) != null) {
						PrjStageMaterialVo v = materialList.get(key);
						mater.setIsComplete(v.getIsComplete());
					} else {
						mater.setIsComplete("0");
					}
					Long taskid = mater.getTaskId();
					if (must.get(taskid) != null) {
						List<PrjStageMaterialVo> l = must.get(taskid);
						l.add(mater);
					} else {
						List<PrjStageMaterialVo> l = new ArrayList<PrjStageMaterialVo>();
						l.add(mater);
						must.put(taskid, l);
					}
				}
				String taskIds = (String) request.getAttribute("taskIds");
				String[] taskIdsArr = taskIds.split(",");
				for (Long key : must.keySet()) {
					List<PrjStageMaterialVo> materList = must.get(key);
					boolean canStartTask = true;
					boolean hasComplete = false;
					for (PrjStageMaterialVo v : materList) {
						if (v.getIsComplete().equals("1")) {
							hasComplete = true;
						}
						if (v.getIsMandatory().equals("1") && v.getIsComplete().equals("0")) {
							canStartTask = false;
							break;
						}
					}
					if (canStartTask && hasComplete) {
						PrjTaskDefineVo vo = ProjectStepUtil.getPrjTaskDefineVo(key);
						for (String taskId : taskIdsArr) {
							if(taskId.equals(vo.getId().toString())){
								PrjTaskVo target = new PrjTaskVo();
								BeanCopy.copyProperties(vo, target);
								target.setId(null);
								target.setTaskId(vo.getId());
								taskList.add(target);
							}
						}
					}
				}
			}
			Iterator<PrjTaskVo> ite = taskList.iterator();
			while (ite.hasNext()) {
				PrjTaskVo vo = ite.next();
				Map<Long, PrjTaskVo> m = ProjectStepUtil.getHasStartTask(project.getPrjInstanceVo());
				if (m.get(vo.getTaskId()) != null) {
					ite.remove();
				}
			}
			project.setPrjTaskVoList(taskList);
			model.addAttribute("action", "save");
		}
		return views;
	}



	private void lpcode(PrjInstanceVo prjInstanceVo) {
		PrjBusinessAcceptVo accept = new PrjBusinessAcceptVo();
		ProjectVo pvo = new ProjectVo();
		AcceptVo act = new AcceptVo();
		BeanCopy.copyProperties(prjInstanceVo, pvo);
		// 项目lpcode
		byte[] b = null;
		try {
			b = FormEncodeUtil.generatePrjLpocde(pvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = uploadCode(b);
		// 受理 lpcode
		act.setPrjCode(prjInstanceVo.getPrjCode());
		act.setPrjName(prjInstanceVo.getPrjName());
		String code = getAcceptCode(accept);
		act.setSblsh(code);
		byte[] a = null;
		try {
			a = FormEncodeUtil.generateAcceptLpocde(act);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String aurl = uploadCode(a);
		accept.setLpcodeAddr(aurl);
		accept.setAcceptCode(code);
		if ("2".equals(prjInstanceVo.getPrjType()))
			accept.setAcceptTye("2");
		else
			accept.setAcceptTye("1");
		prjInstanceVo.setLpcodeAddr(url);
		prjInstanceVo.setAcceptId(accept.getId());
		projectAcceptServiceInf.updateAccept(accept);
	}

	private String uploadCode(byte[] b) {
		if (b == null || b.length == 0)
			return null;
		InputStream sbs = new ByteArrayInputStream(b);
		FastDFSImage j = new FastDFSImage("bmp") {
			private static final long serialVersionUID = 1L;
		};
		FastDFSFileFactory.getInstance().saveFile(sbs, j);
		return j.getUrl();
	}

	@RequestMapping(value = "printView")
	public String printView(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("project", project);
		PrjInstanceVo vo = ProjectStepUtil.getInstance(project.getPrjInstanceVo().getId());
		project.setPrjInstanceVo(vo);
		PrjBusinessAcceptVo accept = new PrjBusinessAcceptVo();
		accept.setId(project.getPrjInstanceVo().getAcceptId());
		accept = ProjectStepUtil.getPrjBusinessAccept(accept);
		project.setPrjBusinessAcceptVo(accept);
		project.setPrjStageVo(ProjectStepUtil.getStageInstanceByProId(project.getPrjInstanceVo().getId()));
		List<Long> taskIds = new ArrayList<Long>();
		StringBuffer sb = new StringBuffer();
		for (PrjTaskVo taskVo : project.getPrjTaskVoList()) {
			taskIds.add(taskVo.getTaskId());
			sb.append(taskVo.getTaskId()+",");
		}
		project.setPrjStageMaterialVoMap(ProjectStepUtil.getAllTaskMaterByAcceptIdAndTaskIds(project.getPrjInstanceVo().getAcceptId(),taskIds));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String n = DictUtils.getDictValue("LAST_MATRERIAL_DATE", "5");
		Date end = dimHolidayService.findWorkDay(new Date(), Integer.parseInt(n), DicConstants.TASK_DEFINE_DIM_TYPE_WORKDAY);
		model.addAttribute("taskIds", sb);
		model.addAttribute("date", sdf.format(end));
		String views = "modules/project/bizaccept/bizprint";
		return views;
	}


	@RequestMapping(value = "print")
	public void print(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String d = request.getParameter("date");
		//接收前台传入的事项ID串
		String taskIdsStr = request.getParameter("taskIds");
		List<Long> taskIds = new ArrayList<Long>();
		String[] idsStr = taskIdsStr.split(",");
		for (String idStr : idsStr) {
			taskIds.add(Long.valueOf(idStr));
		}
		if (id == null)
			return;
		Long projectId = Long.parseLong(id);
		Map<String, Object> map = new HashMap<String, Object>();
		project.setPrjStageVo(ProjectStepUtil.getStageInstanceByProId(projectId));
		project.setPrjInstanceVo(ProjectStepUtil.getInstance(projectId));
		PrjBusinessAcceptVo accept = new PrjBusinessAcceptVo();
		accept.setId(project.getPrjInstanceVo().getAcceptId() == null ? 0 : project.getPrjInstanceVo().getAcceptId());
		project.setPrjBusinessAcceptVo(ProjectStepUtil.getPrjBusinessAccept(accept));
		//打印的事项材料信息
		Map<Long, List<PrjStageMaterialVo>> m = ProjectStepUtil.getAllTaskMaterByAcceptIdAndTaskIds(project.getPrjInstanceVo().getAcceptId(),taskIds);
		Map<String, List<PrjStageMaterialVo>> values = new HashMap<String, List<PrjStageMaterialVo>>();
		if (m != null && m.size() > 0) {
			for (Object o : m.keySet()) {
				Long e = (Long) o;
				PrjTaskDefineVo taskdefine = ProjectStepUtil.getPrjTaskDefineVo(e);
				values.put(taskdefine.getTaskName(), m.get(o));
			}
		}
		map.put("map", values);
		map.put("project", project);
		Date date = new Date();
		map.put("date", date);
		map.put("d", d);
		FreeMakerUtils.service(request, response, map, name);
	}

	private String getAcceptCode(PrjBusinessAcceptVo vo) {
		StringBuffer sb = new StringBuffer();
		sb.append("JW");
		java.text.DateFormat format2 = new SimpleDateFormat("yyyyMMdd");
		sb.append(format2.format(new Date()));
		PrjBusinessAccept accept = new PrjBusinessAccept();
		BeanCopy.copyProperties(vo, accept);
		projectServiceInf.saveAccept(accept);
		vo.setId(accept.getId());
		if (vo.getId() == null) {
			return null;
		}
		String id = vo.getId().toString();
		if (id.length() < 10) {
			sb.append("00000").append(id);
		} else if (id.length() < 100) {
			sb.append("0000").append(id);
		} else if (id.length() < 1000) {
			sb.append("000").append(id);
		} else if (id.length() < 10000) {
			sb.append("00").append(id);
		} else if (id.length() < 100000) {
			sb.append("0").append(id);
		} else if (id.length() >= 100000) {
			sb.append(id.substring(id.length() - 6, id.length()));
		}
		return sb.toString();
	}

	@RequestMapping(value = "/formDetail")
	public String detail(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String formCode = request.getParameter("formCode").toUpperCase();
		
		String taskId = request.getParameter("taskId");
		PrjFormVO prjFormVO = factoryFormService.initBizForm(project.getPrjInstanceVo(), formCode,taskId);
		model.addAttribute("formObject", prjFormVO.getObject());

		return prjFormVO.getModuleUrl();
	}
	
	@RequestMapping("/toMaterial")
	public String toMaterial(HttpServletRequest request, HttpServletResponse response, Model model){
		String projectId = request.getParameter("projectId");
		ProjectChangeForm project = new ProjectChangeForm();
		if (StringUtils.isNotBlank(projectId)) {
			PrjInstanceVo vo = ProjectStepUtil.getInstance(Long.parseLong(projectId));
			project.setPrjInstanceVo(vo);
		} 
		getProjectChangeForm(project,model);
		model.addAttribute("project", project);
		return "modules/project/bizaccept/bizMaterial";
	}
	
	private ProjectChangeForm getProjectChangeForm(ProjectChangeForm project,Model model){
		Map<Long,MaterialDto> mapMaterial = ProjectStepUtil.getAllMaterial();
		Long stageId = project.getPrjInstanceVo().getStageId() ;
		stageId = (stageId == null ? ProjectStepUtil.getFirstStage(project.getPrjInstanceVo().getPrjType()).getId() : stageId);
		Long projectId = project.getPrjInstanceVo().getId();
		Map<Long, PrjMaterialVO> materialDefMapDefine = ProjectStepUtil.getStageDefineMaterList(projectId, stageId);
		Map<Long, Map<Long, PrjMaterialVO>> taskDefMapDefine = ProjectStepUtil.getStageDefineTaskList(projectId, stageId);
		Map<Long, String> taskMap = ProjectStepUtil.getAllTaskjInstance(projectId);
		Map<Long,String> prjTaskStatusMap = ProjectStepUtil.getPrjTaskStatusMap(projectId);
		if(prjTaskStatusMap != null)
			project.setPrjTaskStatus(prjTaskStatusMap);
		List<PrjStageMaterialVo> maList = ProjectStepUtil.getStageMaterList(projectId,stageId);
		if (maList == null || maList.size() == 0) {
			project.setMaterialDefMap(materialDefMapDefine);
			project.setTaskDefMap(taskDefMapDefine);
			model.addAttribute("action", "save");
		} else {
			model.addAttribute("action", "update");
			Map<Long, PrjStageMaterialVo> map = new HashMap<Long, PrjStageMaterialVo>();
			Map<String, PrjStageMaterialVo> maps = new HashMap<String, PrjStageMaterialVo>();
			for (PrjStageMaterialVo vo : maList) {
				PrjStageMaterialVo newVo = new PrjStageMaterialVo();
				BeanCopy.copyProperties(vo, newVo);
				String key = vo.getMaterialId().toString() + "-" + vo.getTaskId().toString();
				maps.put(key, newVo);
				if (map.get(vo.getMaterialId()) != null) {
					PrjStageMaterialVo m = map.get(vo.getMaterialId());
					if (mapMaterial.get(vo.getMaterialId()).getIsOriginalCumulation().equals("1")) {
						m.setOriginalNum((m.getOriginalNum() == null ? 0 : m.getOriginalNum()) + (vo.getOriginalNum() == null ? 0 : vo.getOriginalNum()));
					}else if(mapMaterial.get(vo.getMaterialId()).getIsOriginalCumulation().equals("0") && vo.getOriginalNum() != 0){
						//取不等于0时最大的原件数
						m.setOriginalNum(vo.getOriginalNum() > m.getOriginalNum() ? vo.getOriginalNum() : m.getOriginalNum());
					}
					m.setCopyNum((m.getCopyNum() == null ? 0 : m.getCopyNum()) + (vo.getCopyNum() == null ? 0 : vo.getCopyNum()));
				} else {
					map.put(vo.getMaterialId(), vo);
				}
			}
			if (materialDefMapDefine != null && materialDefMapDefine.size() > 0) {
				for (Long key : materialDefMapDefine.keySet()) {
					PrjStageMaterialVo vo = map.get(key);
					if (vo != null) {
						PrjMaterialVO v = materialDefMapDefine.get(key);
						if (v != null) {
							v.setCopyNumReal(vo.getCopyNum());
							v.setOriginalNumReal(vo.getOriginalNum());
							v.setMaterialAddr(vo.getMaterialAddr());
							//在task表中查询project的对应task处理状态,未受理的默认为0
							if(v.getTaskList() != null){
								for(PrjTaskDefVO taskDefVO : v.getTaskList()){
									String taskStatus = taskMap.get(taskDefVO.getId());
									if(StringUtils.isNotBlank(taskStatus)){
										taskDefVO.setTaskStatus(taskStatus);
									}else{
										taskDefVO.setTaskStatus("0");
									}
								}
							}
						}
					}
				}
			}
			if (taskDefMapDefine != null && taskDefMapDefine.size() > 0) {
				for (Long key : taskDefMapDefine.keySet()) {
					Map<Long, PrjMaterialVO> mmap = taskDefMapDefine.get(key);
					for (Long k : mmap.keySet()) {
						PrjMaterialVO v = mmap.get(k);
						String keys = v.getMaterialId().toString() + "-" + v.getTaskId().toString();
						PrjStageMaterialVo vo = maps.get(keys);
						if (vo != null) {
							v.setCopyNumReal(vo.getCopyNum());
							v.setOriginalNumReal(vo.getOriginalNum());
						}
					}
				}
			}
			project.setMaterialDefMap(materialDefMapDefine);
			project.setTaskDefMap(taskDefMapDefine);
		}
		return project;
	}


	/**
	 * ajax获取不通过的事项
	 *
	 * @param request
	 * @param prjInsId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("task:offline:finish")
	@RequestMapping(value = "/unpass/form")
	public String findUnpassForm(HttpServletRequest request, Long prjInsId, Long stageInsId,Model model) {
		if (prjInsId != null) {
			ListResult<PrjTaskForOffLineFinishDTO> resultList = prjTaskService.findTaskForUnPass(prjInsId, stageInsId);
			model.addAttribute("list",resultList.getObj());
			model.addAttribute("prjInsId",prjInsId);
		}
		return "modules/project/bizaccept/taskUnpassForm";
	}

	/**
	 * 不通地事项激活
	 */
	@RequestMapping(value = "/unpass/taskRedo")
	public String updateUnpassTaskRedo(HttpServletRequest request, PrjTaskForOffLineFinishDTO dto, Model model) {
		prjTaskService.updateTaskState(dto);
		model.addAttribute("message", "ok");
		model.addAttribute("prjInsId", dto.getPrjInsId());
		return "modules/project/bizaccept/taskUnpassForm";
	}
	
	@RequestMapping(value = "/formDetailView")
	public String formDetailView( HttpServletRequest request, HttpServletResponse response, Model model) {
		String formCode = request.getParameter("formCode").toUpperCase();
		String id = request.getParameter("id");
		String view = request.getParameter("view");
		PrjFormVO prjFormVO = new PrjFormVO();
		PrjInstanceVo prjInstanceVo = new PrjInstanceVo();
		prjInstanceVo.setId(Long.parseLong(id));
		
		String taskId = request.getParameter("taskId");
		prjFormVO = factoryFormService.initBizForm(prjInstanceVo, formCode,taskId);
		
		model.addAttribute("formObject", prjFormVO.getObject());
		model.addAttribute("view", view);
		model.addAttribute("formCode", formCode);
		return prjFormVO.getModuleUrl();
	}

}
