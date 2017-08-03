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

import com.lpcode.modules.service.impl.report.NewSbDataServiceImpl;
import com.lpcode.modules.service.project.dto.pinstance.*;
import com.lpcode.modules.service.project.inf.PrjMaterialSupplementService;
import com.lpcode.modules.service.report.NewSbDataService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.framework.core.result.PageResult;
import com.framework.core.result.RequestDTOPage;
import com.framework.core.utils.BeanCopy;
import com.framework.fdfs.images.FastDFSFileFactory;
import com.framework.fdfs.images.FastDFSImage;
import com.framework.osp.common.config.Global;
import com.framework.osp.common.mapper.JsonMapper;
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
import com.lpcode.modules.service.impl.project.util.ProjectUtil;
import com.lpcode.modules.service.material.dto.MaterialDto;
import com.lpcode.modules.service.project.dto.ProjectChangeForm;
import com.lpcode.modules.service.project.inf.ProjectAcceptServiceInf;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

@Controller
@RequestMapping(value = "${adminPath}/project/accpet/")
public class ProjectAcceptController extends BaseController {
	@Autowired
	private ProjectAcceptServiceInf projectAcceptServiceInf;
	@Autowired
	private ProjectServiceInf projectServiceInf;
	@Autowired
	IBuildCompanyService IBuildCompanyService;
	@Autowired
	IFactoryFormService factoryFormService;
	@Autowired
	DimHolidayService dimHolidayService;
	@Autowired
	private PrjMaterialSupplementService supplementService;
	@ModelAttribute
	public ProjectChangeForm get(@RequestParam(required = false) String projectId) {
		if (StringUtils.isNotBlank(projectId)) {
			PrjInstanceVo vo = ProjectUtil.getInstance(Long.parseLong(projectId));
			ProjectChangeForm p = new ProjectChangeForm();
			p.setPrjInstanceVo(vo);
			return p;
		} else {
			return new ProjectChangeForm();
		}
	}

	@RequestMapping(value = "index")
	public String index(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/project/accept/index";
	}

	@RequestMapping(value = "basic")
	public String basic(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String view = "modules/project/accept/basic";
		model.addAttribute("project", project);
		String action = request.getAttribute("action") == null ? request.getParameter("action") : (String) request.getAttribute("action");
		String type = request.getAttribute("type") == null ? request.getParameter("type") : (String) request.getAttribute("type");
		String url = request.getAttribute("url") == null ? request.getParameter("url") : (String) request.getAttribute("url");
		if (StringUtils.equals(action, "save")) {
			String finalPrjCode = "";
			//网厅的项目导入时,不用加锁
			if(project.getPrjInstanceVo().getPrjCode().contains("JWWT")){
				finalPrjCode = project.getPrjInstanceVo().getPrjCode();
			}else{
				finalPrjCode = projectAcceptServiceInf.lockPrjCode(project.getPrjCodeGeneratorVo(), project.getPrjInstanceVo().getPrjCode());
			}
			project.getPrjInstanceVo().setPrjCode(finalPrjCode);
			// 项目龙贝码
			// 受理龙贝码
			lpcode(project.getPrjInstanceVo());
			project.getPrjInstanceVo().setChannel("0");//供应渠道为0,标识为后台窗口人员录入
			projectAcceptServiceInf.savePrjInstance(project.getPrjInstanceVo());
			if (url != null) {
				if (url.equals("pre")) {
					request.setAttribute("action", "view");
					return index(project, request, response, model);
				} else if (url.equals("next")) {
					request.setAttribute("action", "view");
					material(project, request, response, model);
					String projectId = project.getPrjInstanceVo().getId().toString();
					return "redirect:"+ Global.getAdminPath() +"/project/accpet/toMaterial?projectId="+projectId;
				}
			}
		} else if (StringUtils.equals(action, "update")) {
			//如果为空时生成 项目龙贝码 受理龙贝码 （历史数据存在的问题）
			if(StringUtils.isBlank(project.getPrjInstanceVo().getLpcodeAddr())){
				lpcode(project.getPrjInstanceVo());
			}
			projectAcceptServiceInf.updatePrjInstance(project.getPrjInstanceVo());
			if (url != null) {
				if (url.equals("pre")) {
					request.setAttribute("action", "view");
					return index(project, request, response, model);
				} else if (url.equals("next")) {
					request.setAttribute("action", "view");
					return material(project, request, response, model);
				}
			}
		} else {
			if (project.getPrjInstanceVo() == null || project.getPrjInstanceVo().getId() == null) {
				PrjInstanceVo in = project.getPrjInstanceVo() == null ? new PrjInstanceVo() : project.getPrjInstanceVo();
				project.setPrjInstanceVo(in);
				if (in.getPriceType() == null) {
					String priceType = request.getParameter("priceType") == null ? "" : request.getParameter("priceType");
					in.setPriceType(priceType);
				}
				if (in.getUseageType() == null) {
					String useageType = request.getParameter("useageType") == null ? "" : request.getParameter("useageType");
					in.setUseageType(useageType);
				}
				if (in.getIsGovType() == null) {
					String isGovType = request.getParameter("isGovType") == null ? "" : request.getParameter("isGovType");
					in.setIsGovType(isGovType);
				}
				PrjCodeGeneratorVo pvo = ProjectUtil.getProjectCode(type);
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

	@RequestMapping(value = "project")
	public String company(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("project", project);
		String company = request.getParameter("company");
		String companyCode = request.getParameter("companyCode");
		String prjCompanyCode = request.getParameter("prjCompanyCode");
		String priceType = request.getParameter("priceType") == null ? "" : request.getParameter("priceType");
		String useageType = request.getParameter("useageType") == null ? "" : request.getParameter("useageType");
		model.addAttribute("priceType", priceType);
		model.addAttribute("useageType", useageType);
		model.addAttribute("company", company);
		model.addAttribute("companyCode", companyCode);
		model.addAttribute("prjCompanyCode", prjCompanyCode);
		model.addAttribute("companyCodeJson", JsonMapper.toJsonString(companyCode));
		Page<BuildCompanyDto> page = new Page<BuildCompanyDto>();
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
		RequestDTOPage<BuildCompanyDto> requestPage = new RequestDTOPage<BuildCompanyDto>();
		BuildCompanyDto dto = new BuildCompanyDto();
		dto.setCompany(company);
		dto.setCompanyCode(companyCode);
		requestPage.setObj(dto);
		requestPage.setPage(page);
		PageResult<BuildCompanyDto> pageResult = IBuildCompanyService.findList(requestPage);
		model.addAttribute("page", pageResult.getObj());
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
		return "modules/project/accept/project";
	}

	@RequestMapping(value = "getProject")
	public String getProject(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("project", project);
		String id = request.getParameter("id");
		BuildCompanyDto dto = IBuildCompanyService.getBuildCompanyById(Long.parseLong(id));
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
		String view = "modules/project/accept/material";
		model.addAttribute("project", project);
		String action = request.getAttribute("action") == null ? request.getParameter("action") : (String) request.getAttribute("action");
		String url = request.getAttribute("url") == null ? request.getParameter("url") : (String) request.getAttribute("url");
		if (project.getPrjInstanceVo() == null) {
			return index(project, request, response, model);
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
			Map<Long, String> taskMap = ProjectStepUtil.getAllTaskjInstance(project.getPrjInstanceVo().getId());
			Map<Long,String> prjTaskStatusMap = ProjectStepUtil.getPrjTaskStatusMap(projectId);
			if(prjTaskStatusMap != null)
				project.setPrjTaskStatus(prjTaskStatusMap);
			List<PrjStageMaterialVo> maList = ProjectUtil.getStageMaterList(stageId, projectId);
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
		String view = "modules/project/accept/business";
		model.addAttribute("project", project);
		String action = request.getAttribute("action") == null ? request.getParameter("action") : (String) request.getAttribute("action");
		String url = request.getAttribute("url") == null ? request.getParameter("url") : (String) request.getAttribute("url");
		if (project.getPrjInstanceVo() == null) {
			return index(project, request, response, model);
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
		List<PrjStageMaterialVo> maList = ProjectUtil.getStageMaterList(sid, project.getPrjInstanceVo().getId());
		List<PrjStageMaterialDefineVo> listDefine = ProjectUtil.getStageDefineMaterList(sid);
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
					PrjTaskDefineVo vo = ProjectUtil.getPrjTaskDefineVo(key.toString());
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
		String views = "modules/project/accept/task";
		if (project.getPrjInstanceVo() == null) {
			return index(project, request, response, model);
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
		} else if (StringUtils.equals(action, "update")) {
			projectAcceptServiceInf.updatePrjTask(list);
			if (url != null) {
				if (url.equals("pre")) {
					request.setAttribute("action", "view");
					return forms(project, request, response, model);
				} else if (url.equals("next")) {
					request.setAttribute("action", "view");
					return printView(project, request, response, model);
				}
			}
		} else {
			Long sid = project.getPrjInstanceVo().getStageId() == null ? 0L : project.getPrjInstanceVo().getStageId();
			List<PrjStageMaterialVo> maList = ProjectUtil.getStageMaterList(sid, project.getPrjInstanceVo().getId());
			List<PrjStageMaterialDefineVo> listDefine = ProjectUtil.getStageDefineMaterList(sid);
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
				//增加选择事项推送的逻辑
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
						PrjTaskDefineVo vo = ProjectUtil.getPrjTaskDefineVo(key.toString());
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
		PrjInstanceVo vo = ProjectUtil.getInstance(project.getPrjInstanceVo().getId());
		project.setPrjInstanceVo(vo);
		PrjBusinessAcceptVo accept = new PrjBusinessAcceptVo();
		accept.setId(project.getPrjInstanceVo().getAcceptId());
		accept = ProjectUtil.getPrjBusinessAccept(accept);
		project.setPrjBusinessAcceptVo(accept);
		project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(project.getPrjInstanceVo().getId()));
		List<Long> taskIds = new ArrayList<Long>();
		StringBuffer sb = new StringBuffer();
		for (PrjTaskVo taskVo : project.getPrjTaskVoList()) {
			taskIds.add(taskVo.getTaskId());
			sb.append(taskVo.getTaskId()+",");
		}
		project.setPrjStageMaterialVoMap(ProjectUtil.getAllTaskMaterByAcceptIdAndTaskIds(project.getPrjInstanceVo().getAcceptId(), taskIds));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String n = DictUtils.getDictValue("LAST_MATRERIAL_DATE", "5");
		Date end = dimHolidayService.findWorkDay(new Date(), Integer.parseInt(n), DicConstants.TASK_DEFINE_DIM_TYPE_WORKDAY);
		model.addAttribute("taskIds", sb);
		model.addAttribute("date", sdf.format(end));
		String views = "modules/project/accept/print";
		return views;
	}


	@RequestMapping(value = "print")
	public void print(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String d = request.getParameter("date");
		if (id == null)
			return;
		Map<String, Object> map = new HashMap<String, Object>();
		project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(Long.parseLong(id)));
		project.setPrjInstanceVo(ProjectUtil.getInstance(Long.parseLong(id)));
		PrjBusinessAcceptVo accept = new PrjBusinessAcceptVo();
		accept.setId(project.getPrjInstanceVo().getAcceptId() == null ? 0 : project.getPrjInstanceVo().getAcceptId());
		project.setPrjBusinessAcceptVo(ProjectUtil.getPrjBusinessAccept(accept));
		Map<Long, List<PrjStageMaterialVo>> m = ProjectUtil.getAllTaskMaterByAcceptId(project.getPrjInstanceVo().getAcceptId());
		Map<String, List<PrjStageMaterialVo>> values = new HashMap<String, List<PrjStageMaterialVo>>();
		if (m != null && m.size() > 0) {
			for (Object o : m.keySet()) {
				Long e = (Long) o;
				PrjTaskDefineVo taskdefine = ProjectUtil.getPrjTaskDefineVo(e.toString());
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
		java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyyMMdd");
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
		
		PrjFormVO prjFormVO = factoryFormService.initBizForm(project.getPrjInstanceVo(), formCode, taskId);

		model.addAttribute("formObject", prjFormVO.getObject());
		
		return prjFormVO.getModuleUrl();
	}
	
	@RequestMapping("/toMaterial")
	public String toMaterial(HttpServletRequest request, HttpServletResponse response, Model model){
		String projectId = request.getParameter("projectId");
		ProjectChangeForm project = new ProjectChangeForm();
		if (StringUtils.isNotBlank(projectId)) {
			PrjInstanceVo vo = ProjectUtil.getInstance(Long.parseLong(projectId));
			project.setPrjInstanceVo(vo);
		} 
		getProjectChangeForm(project,model);
		model.addAttribute("project", project);
		return "modules/project/accept/material";
	}
	
	private ProjectChangeForm getProjectChangeForm(ProjectChangeForm project,Model model){
		Map<Long,MaterialDto> mapMaterial = ProjectStepUtil.getAllMaterial();
		Long stageId = project.getPrjInstanceVo().getStageId() ;
		stageId = (stageId == null ? ProjectStepUtil.getFirstStage(project.getPrjInstanceVo().getPrjType()).getId() : stageId);
		Long projectId = project.getPrjInstanceVo().getId();
		Map<Long, PrjMaterialVO> materialDefMapDefine = ProjectStepUtil.getStageDefineMaterList(projectId, stageId);
		Map<Long, Map<Long, PrjMaterialVO>> taskDefMapDefine = ProjectStepUtil.getStageDefineTaskList(projectId, stageId);
		Map<Long, String> taskMap = ProjectStepUtil.getAllTaskjInstance(project.getPrjInstanceVo().getId());
		List<PrjStageMaterialVo> maList = ProjectUtil.getStageMaterList(stageId, project.getPrjInstanceVo().getId());
		if (maList == null || maList.size() == 0) {
			project.setMaterialDefMap(materialDefMapDefine);
			project.setTaskDefMap(taskDefMapDefine);
			model.addAttribute("action", "save");
		} else {
			model.addAttribute("action", "update");
			if (stageId == null) {
				stageId = ProjectUtil.getFirstStage(project.getPrjInstanceVo().getPrjType()).getId();
			}
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




	/**
	 * 验证项目名称是否可用
	 * @param oldPrjName
	 * @param prjName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkProjectName")
	public String checkProjectName(String oldPrjName , String prjName) {
		if (prjName !=null && prjName.equals(oldPrjName)) {
			return "true";
		} else if (prjName !=null && projectAcceptServiceInf.enableProjectName(prjName)) {
			return "true";
		}
		return "false";
	}


	/**
	 * 验证项目编号是否可用
	 * @param prjCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkProjectCode")
	public String checkProjectCode(String oldPrjCode ,String prjCode) {
		if (oldPrjCode !=null && oldPrjCode.equals(prjCode)) {
			return "true";
		} else if (prjCode !=null && projectAcceptServiceInf.enablePrjCode(prjCode)) {
			return "true";
		}
		return "false";
	}

	/**
	 * 得出补材料列表
	 * @param project
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "suplementMaterial")
	public String suplementMaterial(ProjectChangeForm project, HttpServletRequest request,  Model model) {
		model.addAttribute("project", project);
		List<PrjMaterialSupplementVo> list = supplementService.getListByPrjCode(project.getPrjInstanceVo());
		model.addAttribute("suplementMaterList", list);
		return "modules/project/accept/supplementMaterial";
	}

	@ResponseBody
	@RequestMapping(value = "subMaterial")
	public boolean subMaterial(Long id) {
		if(id != null && id != 0){
			return supplementService.subMaterial(id);
		}
		return false;
	}
	@ResponseBody
	@RequestMapping(value = "cancelMaterial")
	public boolean cancelMaterial(Long id) {
		if(id != null && id != 0){
			return supplementService.cancelMaterial(id);
		}
		return false;
	}

}
