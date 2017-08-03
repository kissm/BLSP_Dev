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
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.framework.core.result.ListResult;
import com.framework.core.utils.BeanCopy;
import com.framework.core.utils.StringUtil;
import com.framework.fdfs.images.FastDFSFileFactory;
import com.framework.fdfs.images.FastDFSImage;
import com.framework.osp.common.config.Global;
import com.framework.osp.common.persistence.Page;
import com.framework.osp.common.web.BaseController;
import com.framework.osp.modules.sys.utils.DictUtils;
import com.framework.osp.modules.web.util.FormEncodeUtil;
import com.framework.osp.modules.web.util.FreeMakerUtils;
import com.framework.osp.modules.web.util.RequestUtil;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.blsp.entity.PrjBusinessAccept;
import com.lpcode.modules.blsp.vo.PrjFormVO;
import com.lpcode.modules.dto.project.change.PrjMaterialVO;
import com.lpcode.modules.dto.project.change.PrjTaskDefVO;
import com.lpcode.modules.service.dimension.holiday.DimHolidayService;
import com.lpcode.modules.service.form.IFactoryFormService;
import com.lpcode.modules.service.impl.project.util.ProjectStepUtil;
import com.lpcode.modules.service.impl.project.util.ProjectUtil;
import com.lpcode.modules.service.material.dto.MaterialDto;
import com.lpcode.modules.service.project.constant.StateFormConstants;
import com.lpcode.modules.service.project.dto.PrjTaskDTO;
import com.lpcode.modules.service.project.dto.PrjTaskHandleDTO;
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
import com.lpcode.modules.service.project.dto.stageTaskMaterialDef.TaskMaterialDefForm;
import com.lpcode.modules.service.project.inf.PrjTaskScheduleService;
import com.lpcode.modules.service.project.inf.PrjTaskService;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

@Controller
@RequestMapping(value = "${adminPath}/project")
public class ProjectController extends BaseController {
	@Autowired
	private ProjectServiceInf projectServiceInf;
	@Autowired
	PrjTaskService prjTaskService;
	@Autowired
	DimHolidayService dimHolidayService;
	@Autowired
	private PrjTaskScheduleService prjTaskScheduleService;
	@Autowired
	private IFactoryFormService factoryFormService;
	
	/**
	 * 到网上办事首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		//通过连接获取网上办事用户名
		String userName = request.getParameter("userName");
		model.addAttribute("userName", userName);
		return "modules/project/wsbs_index";
	}

	// @RequiresPermissions("project:edit")
	@RequestMapping(value = "view")
	public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = request.getParameter("id");
		Project project = projectServiceInf.getProjectByPrjId(Long.parseLong(id));
		project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(Long.parseLong(id)));
		// 重新处理  project中PrjStageMaterialVoMap集合 中的数据
		//projectServiceInf.getProjectById(project.getPrjInstanceVo().getStageId(),project);
		project.setPrjStageVoList(ProjectUtil.getStageInsListByProId(id));
		model.addAttribute("project", project);
		return "modules/project/view";
	}

	// @RequiresPermissions("project:edit")
	@RequestMapping(value = "detail")
	public String detial(HttpServletRequest request, HttpServletResponse response, Model model) {
		String table = StateFormConstants.getInstance().getFirstState();
		String id = request.getParameter("id");
		Project project = projectServiceInf.getProject(Long.parseLong(id));
		project.setPrjStageVoList(ProjectUtil.getStageInsListByProId(id));
		project.setPrjStageDefineVoList(ProjectUtil.getAllStageList());
		project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(Long.parseLong(id)));
		model.addAttribute("table", table);
		model.addAttribute("project", project);
		return "modules/project/detail";
	}

	// @RequiresPermissions("project:edit")
	@RequestMapping(value = "approve")
	public String approve(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String table = StateFormConstants.getInstance().getFirstState();
		String id = request.getParameter("id");
		project = projectServiceInf.getProject(Long.parseLong(id));
		if (project.getPrjInstanceVo() == null) {
			PrjInstanceVo in = new PrjInstanceVo();
			project.setPrjInstanceVo(in);
			String priceType = request.getParameter("priceType") == null ? "" : request.getParameter("priceType");
			String landType = request.getParameter("landType") == null ? "" : request.getParameter("landType");
			String useageType = request.getParameter("useageType") == null ? "" : request.getParameter("useageType");
			String isNeedPreAudit = request.getParameter("isNeedPreAudit") == null ? "" : request.getParameter("isNeedPreAudit");
			String isSpecialProject = request.getParameter("isSpecialProject") == null ? "" : request.getParameter("isSpecialProject");
			String isWithBasePart = request.getParameter("isWithBasePart") == null ? "" : request.getParameter("isWithBasePart");
			String isItType = request.getParameter("isItType") == null ? "" : request.getParameter("isItType");
			String isGovType = request.getParameter("isGovType") == null ? "" : request.getParameter("isGovType");
			in.setPriceType(priceType);
			in.setLandType(landType);
			in.setUseageType(useageType);
			in.setIsNeedPreAudit(isNeedPreAudit);
			in.setIsSpecialProject(isSpecialProject);
			in.setIsWithBasePart(isWithBasePart);
			in.setIsItType(isItType);
			in.setIsGovType(isGovType);
			PrjCodeGeneratorVo pvo = ProjectUtil.getProjectCode("1");
			if (pvo.getSeq() != null) {
				pvo.setSeq(pvo.getSeq() + 1);
				project.setPrjCodeGeneratorVo(pvo);
				in.setPrjCode(project.getPrjCodeGeneratorVo().toString());
			}
		}
		model.addAttribute("table", table);
		model.addAttribute("project", project);
		return "modules/project/approve";
	}

	// @RequiresPermissions("project:edit")
	@RequestMapping(value = "form")
	public String form(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = request.getParameter("id");
		project = projectServiceInf.getProject(Long.parseLong(id));
		if (project.getPrjInstanceVo() == null) {
			PrjInstanceVo in = new PrjInstanceVo();
			project.setPrjInstanceVo(in);
			String priceType = request.getParameter("priceType") == null ? "" : request.getParameter("priceType");
			String landType = request.getParameter("landType") == null ? "" : request.getParameter("landType");
			String useageType = request.getParameter("useageType") == null ? "" : request.getParameter("useageType");
			String isNeedPreAudit = request.getParameter("isNeedPreAudit") == null ? "" : request.getParameter("isNeedPreAudit");
			String isSpecialProject = request.getParameter("isSpecialProject") == null ? "" : request.getParameter("isSpecialProject");
			String isWithBasePart = request.getParameter("isWithBasePart") == null ? "" : request.getParameter("isWithBasePart");
			String isItType = request.getParameter("isItType") == null ? "" : request.getParameter("isItType");
			String isGovType = request.getParameter("isGovType") == null ? "" : request.getParameter("isGovType");
			in.setPriceType(priceType);
			in.setLandType(landType);
			in.setUseageType(useageType);
			in.setIsNeedPreAudit(isNeedPreAudit);
			in.setIsSpecialProject(isSpecialProject);
			in.setIsWithBasePart(isWithBasePart);
			in.setIsItType(isItType);
			in.setIsGovType(isGovType);
			PrjCodeGeneratorVo pvo = ProjectUtil.getProjectCode("1");
			if (pvo.getSeq() != null) {
				pvo.setSeq(pvo.getSeq() + 1);
				project.setPrjCodeGeneratorVo(pvo);
				in.setPrjCode(project.getPrjCodeGeneratorVo().toString());
			}
		}
		if (project.getPrjInstanceVo().getId() == null) {
			Map<Long, List<PrjStageMaterialDefineVo>> map = project.getPrjStageMaterialDefineVoMap();
			for (Long key : map.keySet()) {
				List<PrjStageMaterialDefineVo> list = map.get(key);
				List<PrjStageMaterialDefineVo> nlist = new ArrayList<PrjStageMaterialDefineVo>();
				for (PrjStageMaterialDefineVo vo : list) {
					if (vo.getIsByCondition() != null && vo.getIsByCondition().equals("0")) {// 非条件判断
						nlist.add(vo);
					} else if (vo.getLandType() != null && vo.getLandType().equals(project.getPrjInstanceVo().getLandType())) {
						nlist.add(vo);
					} else if (vo.getIsNeedPreAudit() != null && vo.getIsNeedPreAudit().equals(project.getPrjInstanceVo().getIsNeedPreAudit())) {
						nlist.add(vo);
					} else if (vo.getIsSpecialProject() != null && vo.getIsSpecialProject().equals(project.getPrjInstanceVo().getIsSpecialProject())) {
						nlist.add(vo);
					} else if (vo.getIsWithBasePart() != null && vo.getIsWithBasePart().equals(project.getPrjInstanceVo().getIsWithBasePart())) {
						nlist.add(vo);
					} else if (vo.getIsGovType() != null && vo.getIsGovType().equals(project.getPrjInstanceVo().getIsGovType())) {
						nlist.add(vo);
					} else if (vo.getIsItType() != null && vo.getIsItType().equals(project.getPrjInstanceVo().getIsItType())) {
						nlist.add(vo);
					}
				}
				map.put(key, nlist);
			}
		}
		model.addAttribute("project", project);
		return "modules/project/form";
	}

	// @RequiresPermissions("project:edit")
	@RequestMapping(value = "draft")
	public String draft(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("project", project);
		if (project.getPrjInstanceVo() == null)
			return null;
		if (project.getPrjInstanceVo().getId() == null) {
			projectServiceInf.saveCode(project);
			projectServiceInf.saveProject(project);
		} else {
			projectServiceInf.updateProject(project);
		}
		return null;
	}

	// @RequiresPermissions("project:edit")
	/**
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 * @return 草稿受理
	 */
	@RequestMapping(value = "accept")
	public String accept(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		// projectServiceInf.validate(project);// 校验材料是否完成
		model.addAttribute("project", project);
		if (project.getPrjInstanceVo() == null)
			return null;
		List<PrjTaskDefineVo> pdv = project.getPrjTaskDefineVoList();
		if (pdv != null && pdv.size() > 0) {
			Iterator<PrjTaskDefineVo> it = pdv.iterator();
			while (it.hasNext()) {
				PrjTaskDefineVo v = it.next();
				if (v.getId() == null) {
					it.remove();
				}
			}
		}
		if (project.getPrjInstanceVo().getId() == null) {
			List<PrjTaskDefineVo> list = new ArrayList<PrjTaskDefineVo>();
			Long stageId = project.getPrjInstanceVo().getStageId();
			List<PrjTaskDefineVo> l = ProjectUtil.getAllTaskByStage(stageId);
			if (l != null && l.size() > 0) {
				for (PrjTaskDefineVo vo : l) {
					for (PrjTaskDefineVo tv : pdv) {
						if (tv.getId() != null && tv.getId().equals(vo.getId())) {
							list.add(vo);
						}
					}
				}
			}
			project.setPrjTaskDefineVoList(list);
		} else {// 修改操作
			
			if (project.getPrjStageVo() != null && "1".equals(project.getPrjStageVo().getNewStageFlag())) {
				// 新阶段受理
				List<PrjTaskDefineVo> list = new ArrayList<PrjTaskDefineVo>();
				Long stageId = project.getPrjInstanceVo().getStageId();
				if (stageId == null)
					return null;
				
				List<PrjTaskDefineVo> newStageTask = ProjectUtil.getAllTaskByStage(stageId);
				if (newStageTask != null && newStageTask.size() > 0) {
					for (PrjTaskDefineVo vo : newStageTask) {
						for (PrjTaskDefineVo tv : pdv) {
							if (tv.getId() != null && tv.getId().equals(vo.getId())) {
								list.add(vo);
							}
						}
					}
				}
				project.setPrjTaskDefineVoList(list);
			} else {
				// 非新阶段，修改操作
				// project.setPrjInstanceVo(ProjectUtil.getInstance(project.getPrjInstanceVo().getId().toString()));
				List<PrjTaskVo> listT = new ArrayList<PrjTaskVo>();
				List<PrjTaskVo> done = new ArrayList<PrjTaskVo>();
				List<PrjTaskVo> taskList = ProjectUtil.getAllTaskByPrjId(project.getPrjInstanceVo().getId());

				Iterator<PrjTaskVo> ite = taskList.iterator();
				while (ite.hasNext()) {
					PrjTaskVo pv = ite.next();
					if (!"0".equals(pv.getTaskStatus()) && !"6".equals(pv.getTaskStatus())) {
						done.add(pv);
						ite.remove();
					}
				}
				if (taskList != null && taskList.size() > 0 && pdv != null && pdv.size() > 0) {
					for (PrjTaskVo vo : taskList) {
						for (PrjTaskDefineVo tv : pdv) {
							if (tv.getId() != null && tv.getId().equals(vo.getTaskId())) {
								listT.add(vo);
							}
						}
					}
				}
				boolean noNew = true;
				project.setPrjTaskVoList(listT);
				if (listT.size() > 0) {
					noNew = false;
				}
				if (noNew) {
					return success(project, request, response, model);
				}

				project.setPrjTaskDefineVoList(null);
			}
			// project.setPrjTaskVoList(ProjectUtil.getAllTaskByPrjId(project.getPrjInstanceVo().getId().toString()));
		}
		return "modules/project/accept";
	}

	// @RequiresPermissions("project:edit")
	/**
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 * @return 暂时不用
	 */
	@RequestMapping(value = "validate")
	public String validate(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectServiceInf.validate(project);// 校验材料是否完成
		if (!project.isValidate()) {
			return success(project, request, response, model);
		} else {
			return accept(project, request, response, model);
		}
	}

	@RequestMapping(value = "success")
	public String success(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("project", project);
		String url = null;
		PrjBusinessAcceptVo accept = new PrjBusinessAcceptVo();
		if (project.getPrjInstanceVo().getId() == null) {
			projectServiceInf.saveCode(project);
		}
		if (project.getPrjTaskVoList() != null && project.getPrjTaskVoList().size() > 0) {
		ProjectVo pvo = new ProjectVo();
		AcceptVo act = new AcceptVo();
		BeanCopy.copyProperties(project.getPrjInstanceVo(), pvo);
		// 项目lpcode
		byte[] b = null;
		try {
			b = FormEncodeUtil.generatePrjLpocde(pvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		url = uploadCode(b);
		// 受理 lpcode
		act.setPrjCode(project.getPrjInstanceVo().getPrjCode());
		act.setPrjName(project.getPrjInstanceVo().getPrjName());
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
		if ("2".equals(project.getPrjInstanceVo().getPrjType())) 
			accept.setAcceptTye("2");
		else 
			accept.setAcceptTye("1");
		project.setPrjBusinessAcceptVo(accept);
		project.getPrjInstanceVo().setLpcodeAddr(url);
		}
		if (project.getPrjInstanceVo().getId() == null) {
			projectServiceInf.saveProject(project);
			project.setPrjStageMaterialVoMap(ProjectUtil.getAllTaskMaterByAcceptId(project.getPrjInstanceVo().getAcceptId()));
		} else {
			projectServiceInf.updateProject(project);
			PrjInstanceVo vo = ProjectUtil.getInstance(project.getPrjInstanceVo().getId());
			project.setPrjInstanceVo(vo);
			// project.setPrjStageMaterialVoList(ProjectUtil.getStageMaterList(vo.getStageId().toString(),
			// vo.getId().toString()));
			project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(project.getPrjInstanceVo().getId()));
			project.setPrjStageMaterialVoMap(ProjectUtil.getAllTaskMaterByAcceptId(project.getPrjInstanceVo().getAcceptId()));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String n = DictUtils.getDictValue("LAST_MATRERIAL_DATE", "5");
		Date end = dimHolidayService.findWorkDay(new Date(), Integer.parseInt(n), DicConstants.TASK_DEFINE_DIM_TYPE_WORKDAY );
		model.addAttribute("date",sdf.format(end));
		
		// 新阶段受理：先保存再受理
		if ("1".equals(project.getPrjStageVo().getNewStageFlag())) {
			return accept(project, request, response, model);
		}
		return "modules/project/success";
	}

	@RequestMapping(value = "notice")
	public String notice(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("project", project);
		project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(project.getPrjInstanceVo().getId()));
		return "modules/project/success";
	}

	// @RequiresPermissions("project:edit")
	@RequestMapping(value = "save")
	public String save(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("project", project);
		if (project.getPrjInstanceVo() == null)
			return null;
		String url = null;
		if (project.getPrjInstanceVo().getId() == null) {
			projectServiceInf.saveCode(project);
		}
		PrjBusinessAcceptVo accept = new PrjBusinessAcceptVo();
		if (project.getPrjTaskVoList() != null && project.getPrjTaskVoList().size() > 0) {
			ProjectVo pvo = new ProjectVo();
			AcceptVo act = new AcceptVo();
			BeanCopy.copyProperties(project.getPrjInstanceVo(), pvo);
			// 项目lpcode
			byte[] b = null;
			try {
				b = FormEncodeUtil.generatePrjLpocde(pvo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			url = uploadCode(b);
			// 受理 lpcode
			act.setPrjCode(project.getPrjInstanceVo().getPrjCode());
			act.setPrjName(project.getPrjInstanceVo().getPrjName());
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
			if ("2".equals(project.getPrjInstanceVo().getPrjType())) 
				accept.setAcceptTye("2");
			else 
				accept.setAcceptTye("1");
			project.setPrjBusinessAcceptVo(accept);
		}
		project.getPrjInstanceVo().setLpcodeAddr(url);
		if (project.getPrjInstanceVo().getId() == null) {
			projectServiceInf.saveProject(project);
			project.setPrjStageMaterialVoMap(ProjectUtil.getAllTaskMaterByAcceptId(project.getPrjInstanceVo().getAcceptId()));
		} else {
			projectServiceInf.updateProject(project);
			project.setPrjStageMaterialVoMap(ProjectUtil.getAllTaskMaterByAcceptId(project.getPrjInstanceVo().getAcceptId()));
			project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(project.getPrjInstanceVo().getId()));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String n = DictUtils.getDictValue("LAST_MATRERIAL_DATE", "5");
		Date end = dimHolidayService.findWorkDay(new Date(), Integer.parseInt(n), DicConstants.TASK_DEFINE_DIM_TYPE_WORKDAY );
		model.addAttribute("date",sdf.format(end));
		return "modules/project/success";
	}

	// @RequiresPermissions("project:edit")
	@RequestMapping(value = "task")
	public String doTask(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("project", project);
		String url = null;
		String pid = project.getPrjInstanceVo().getId().toString();
		if(StringUtils.isBlank(pid)){
			return null;
		}
		Long projectId = Long.parseLong(pid);
		PrjInstanceVo vo = ProjectUtil.getInstance(projectId);
		PrjBusinessAcceptVo accept = new PrjBusinessAcceptVo();
		project.setPrjInstanceVo(vo);
		if (project.getPrjTaskVoList() != null && project.getPrjTaskVoList().size() > 0) {
			ProjectVo pvo = new ProjectVo();
			AcceptVo act = new AcceptVo();
			BeanCopy.copyProperties(project.getPrjInstanceVo(), pvo);
			// 项目lpcode
			byte[] b = null;
			try {
				b = FormEncodeUtil.generatePrjLpocde(pvo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			url = uploadCode(b);
			// 受理 lpcode
			act.setPrjCode(project.getPrjInstanceVo().getPrjCode());
			act.setPrjName(project.getPrjInstanceVo().getPrjName());
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
			if ("2".equals(project.getPrjInstanceVo().getPrjType())) 
				accept.setAcceptTye("2");
			else 
				accept.setAcceptTye("1");
			project.setPrjBusinessAcceptVo(accept);
		}
		project.getPrjInstanceVo().setLpcodeAddr(url);
		projectServiceInf.doTask(project);
		if (vo.getStageId() == null) {
			project.setPrjStageMaterialVoList(ProjectUtil.getDraftStageMaterList(projectId));
		} else {
			project.setPrjStageMaterialVoList(ProjectUtil.getStageMaterList(vo.getStageId(), projectId));
		}
		project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(project.getPrjInstanceVo().getId()));
		return "modules/project/success";
	}

	// @RequiresPermissions("project:edit")
	@ResponseBody
	@RequestMapping(value = "upload")
	public String uploadFile(HttpServletRequest request, HttpServletResponse response, Model model) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		String fileName = "";
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile fileS = (CommonsMultipartFile) multiRequest.getFile("Filedata");
			fileName = RequestUtil.unloadFile(fileS, request);
		}
		// projectServiceInf.updateMater(request.getParameter("id"), fileName);
		return renderString(response, fileName, "text/html");
	}

	@ResponseBody
	@RequestMapping(value = "down")
	public String downFile(HttpServletRequest request, HttpServletResponse response, Model model) {
		// projectServiceInf.updateMater(request.getParameter("id"), fileName);
		String url = request.getParameter("url");
		String filename = url.substring(url.lastIndexOf("/"), url.length());
		RequestUtil.download(request, response, url, filename);
		return null;
	}

	@RequestMapping(value = "material")
	public String material(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("project", project);
		String pid = project.getPrjInstanceVo().getId().toString();
		if(StringUtils.isBlank(pid))
			return null;
		Long projectId = Long.parseLong(pid);
		PrjInstanceVo vo = ProjectUtil.getInstance(projectId);
		project.setPrjInstanceVo(vo);
		List<PrjStageMaterialVo> list = ProjectUtil.getStageMaterList(vo.getStageId(), projectId);
		project.setPrjStageMaterialVoList(list);
		Map<Long, List<PrjStageMaterialVo>> map = new TreeMap<Long, List<PrjStageMaterialVo>>();
		project.setPrjStageMaterialVoMap(map);
		if (list != null && list.size() > 0) {
			for (PrjStageMaterialVo v : list) {
				List<PrjStageMaterialVo> taskList = map.get(v.getTaskId() == null ? 0l : v.getTaskId());
				if (taskList == null) {
					taskList = new ArrayList<PrjStageMaterialVo>();
					taskList.add(v);
					map.put(v.getTaskId() == null ? 0l : v.getTaskId(), taskList);
				} else {
					taskList.add(v);
				}
			}
		}
		project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(project.getPrjInstanceVo().getId()));
		return "modules/project/material";
	}

	@RequestMapping(value = "doMaterial")
	public String doMaterial(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("project", project);
		projectServiceInf.doMaterial(project);
		return null;
	}

	// @RequiresPermissions("project:edit")
	@RequestMapping(value = "update")
	public String update(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectServiceInf.updateProject(project);
		return "redirect:" + Global.getAdminPath() + "/project/list";
	}

	/**
	 * 获取网上办事列表集合
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String wsbsUserName = request.getParameter("token");
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
		if(project.getPrjInstanceVo() == null || "".equals(project.getPrjInstanceVo())){
			project.setPrjInstanceVo(new PrjInstanceVo());
		}
		if(StringUtil.isEmpty(project.getPrjInstanceVo().getWsbsUserName())){			
			project.getPrjInstanceVo().setWsbsUserName(wsbsUserName);
		}
		if(StringUtil.isEmpty(wsbsUserName) && StringUtil.isEmpty(project.getPrjInstanceVo().getWsbsUserName())){
			return null;
		}
		page.setOrderBy("creat_Time desc");
		projectServiceInf.getWsbsProjectPage(project, page,true);
		model.addAttribute("page", page);
		model.addAttribute("project", project);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
		return "modules/project/wsbs_list";
	}

	// @RequiresPermissions("project:view")
	@RequestMapping(value = "queryList")
	public String quyerList(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
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
		PrjInstanceVo insvo = project.getPrjInstanceVo();
		if (insvo == null) {
			insvo = new PrjInstanceVo();
			insvo.setPrjType("3");// 政府 And 企业
			project.setPrjInstanceVo(insvo);
		}
		
		projectServiceInf.getProjectPage(project, page);
		model.addAttribute("page", page);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("project", project);
		return "modules/project/queryList";
	}

	// @RequiresPermissions("project:view")
	@RequestMapping(value = "delete")
	public String delete(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectServiceInf.delete(project);
		return "redirect:" + Global.getAdminPath() + "/project/list";
	}

	// @RequiresPermissions("project:view")
	@RequestMapping(value = "nextState")
	public String nextState(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {

		return "redirect:" + Global.getAdminPath() + "/project/list";
	}

	// @RequiresPermissions("project:view")
	@RequestMapping(value = "handle")
	public String handle(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String taskid = request.getParameter("taskId");
		if (taskid == null)
			return null;
		ListResult<PrjTaskHandleDTO> handleList = prjTaskService.taskHandleList(Long.parseLong(taskid)); // 事项操作列表
		model.addAttribute("handle", handleList);
		return "modules/project/handle";
	}

	// @RequiresPermissions("project:view")
	@RequestMapping(value = "print")
	public void print(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String d = request.getParameter("date");
		if (id == null)
			return;
		Long projectId = Long.parseLong(id);
		Map<String, Object> map = new HashMap<String, Object>();
		project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(projectId));
		project.setPrjInstanceVo(ProjectUtil.getInstance(projectId));
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
	
	/**
	 * ajax获取阶段from和材料清单
	 */
	@RequestMapping(value = "load/material")
	public String loadMaterial(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String prjInsId = request.getParameter("prjInsId");
		String stageId = request.getParameter("stageId");
		PrjInstanceVo prjInstanceVo = ProjectUtil.getInstance(Long.parseLong(prjInsId));
		if (StringUtils.isBlank(stageId)) {
			stageId = prjInstanceVo.getStageId().toString();
		}
		
		project = ProjectUtil.getPrjStageFormAndMaterials(Long.parseLong(prjInsId),Long.parseLong(stageId));
		
		model.addAttribute("project", project);
		return "modules/project/detailMateriaView";
	}
	
	@InitBinder
	public void initListBinder(WebDataBinder binder) {
		// 设置需要包裹的元素个数，默认为256
		binder.setAutoGrowCollectionLimit(2048);
	}
	
	/**
	 * 通过事项ID获取事项详情
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/itemDetail")
	public String itemDetail(Project project, HttpServletRequest request, HttpServletResponse response, Model model){
		String taskId = request.getParameter("taskId");
		String id = request.getParameter("id");
		if(StringUtils.isBlank(taskId) || StringUtils.isBlank(id)){
			return null;
		}
		//通过事项ID(taskId)和项目ID得到PrjTaskVo对象
		PrjTaskVo prjTaskVo = new PrjTaskVo();
		prjTaskVo.setPrjId(Long.valueOf(id));
		prjTaskVo.setTaskId(Long.valueOf(taskId));
		PrjTaskDTO prjTask = prjTaskScheduleService.getPrjTaskDTO(prjTaskVo);
		
		project = projectServiceInf.getProjectByPrjId(Long.parseLong(id));
		// 重新处理  project中PrjStageMaterialVoMap集合 中的数据
		projectServiceInf.getProjectByTaskId(project.getPrjInstanceVo().getStageId(),Long.parseLong(taskId),project);
		model.addAttribute("prjTask",prjTask);
		model.addAttribute("project", project);
		model.addAttribute("taskId",taskId);
		return "modules/project/itemDetail";
	}
	
	/**
	 * 网上办事中将暂存项目提交
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sumCheckInfo")
	public String sumCheckInfo(HttpServletRequest request, HttpServletResponse response, Model model){
		String id = request.getParameter("projectId");
		PrjInstanceVo vo = projectServiceInf.getPrjInstanceVoById(Long.valueOf(id));
		vo.setApplyState("1");
		try {
			projectServiceInf.updatePrjInstaceVo(vo);
			response.getWriter().print(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 到政府项目介绍
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toZf")
	public String toZf(HttpServletRequest request, HttpServletResponse response, Model model){
		//通过连接获取网上办事用户名
		String userName = request.getParameter("userName");
		model.addAttribute("userName", userName);
		List<TaskMaterialDefForm> list = ProjectStepUtil.getStageTaskMaterialDefByStage(1L);
		model.addAttribute("TaskMaterialDefFormList", list);
		return "modules/project/wsbs_zf";
	}
	
	/**
	 * 到企业项目介绍
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toQy")
	public String toQy(HttpServletRequest request, HttpServletResponse response, Model model){
		//通过连接获取网上办事用户名
		String userName = request.getParameter("userName");
		model.addAttribute("userName", userName);
		List<TaskMaterialDefForm> list1 = ProjectStepUtil.getStageTaskMaterialDefByStage(11L);
		List<TaskMaterialDefForm> list2 = ProjectStepUtil.getStageTaskMaterialDefByStage(12L);
		List<TaskMaterialDefForm> list3 = ProjectStepUtil.getStageTaskMaterialDefByStage(13L);
		List<TaskMaterialDefForm> list4 = ProjectStepUtil.getStageTaskMaterialDefByStage(14L);
		List<TaskMaterialDefForm> list5 = ProjectStepUtil.getStageTaskMaterialDefByStage(15L);
		model.addAttribute("TaskMaterialDefFormList1", list1);
		model.addAttribute("TaskMaterialDefFormList2", list2);
		model.addAttribute("TaskMaterialDefFormList3", list3);
		model.addAttribute("TaskMaterialDefFormList4", list4);
		model.addAttribute("TaskMaterialDefFormList5", list5);
		return "modules/project/wsbs_qy";
	}
	
	/**
	 * 提交成功提示页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/succeed")
	public String succeed(HttpServletRequest request, HttpServletResponse response, Model model){
		String id = request.getParameter("id");
		Project project = projectServiceInf.getProject(Long.valueOf(id));
		String msg = request.getParameter("msg");
		model.addAttribute("project", project);
		model.addAttribute("msg", msg);
		return "modules/project/wsbs_success";
	}
	
	/**
	 * 查看网上办事项目详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "wsbsview")
	public String wsbsview(HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = request.getParameter("id");
		Project project = projectServiceInf.getProjectByPrjId(Long.parseLong(id));
		project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(Long.parseLong(id)));
		project.setPrjStageVoList(ProjectUtil.getStageInsListByProId(id));
		model.addAttribute("project", project);
		ProjectChangeForm pro = new ProjectChangeForm();
		pro.setPrjInstanceVo(project.getPrjInstanceVo());
		wsbsmaterial(pro, request, response, model);
		Map<Long, TaskFormConf> tasks = getTasks(id, request, response, model);
		model.addAttribute("tasks", tasks);
		return "modules/project/wsbs_view";
	}
	
	private void wsbsmaterial(ProjectChangeForm pro, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("pro", pro);
		Map<Long,MaterialDto> mapMaterial = ProjectStepUtil.getAllMaterial();
		Long stageId = pro.getPrjInstanceVo().getStageId() ;
		stageId = (stageId == null ? ProjectStepUtil.getFirstStage(pro.getPrjInstanceVo().getPrjType()).getId() : stageId);
		Long projectId = pro.getPrjInstanceVo().getId();
		Map<Long, PrjMaterialVO> materialDefMapDefine = ProjectStepUtil.getStageDefineMaterList(projectId, stageId);
		Map<Long, Map<Long, PrjMaterialVO>> taskDefMapDefine = ProjectStepUtil.getStageDefineTaskList(projectId, stageId);
		Map<Long, String> taskMap = ProjectStepUtil.getAllTaskjInstance(pro.getPrjInstanceVo().getId());
		Map<Long,String> prjTaskStatusMap = ProjectStepUtil.getPrjTaskStatusMap(projectId);
		if(prjTaskStatusMap != null)
			pro.setPrjTaskStatus(prjTaskStatusMap);
		List<PrjStageMaterialVo> maList = ProjectUtil.getStageMaterList(stageId, projectId);
		if (maList == null || maList.size() == 0) {
			pro.setMaterialDefMap(materialDefMapDefine);
			pro.setTaskDefMap(taskDefMapDefine);
		} else {
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
		pro.setMaterialDefMap(materialDefMapDefine);
		pro.setTaskDefMap(taskDefMapDefine);
	}
	
	private Map<Long, TaskFormConf> getTasks(String projectId, HttpServletRequest request, HttpServletResponse response, Model model){
		ProjectChangeForm project = new ProjectChangeForm();
		if (StringUtils.isNotBlank(projectId)) {
			PrjInstanceVo vo = ProjectUtil.getInstance(Long.parseLong(projectId));
			project.setPrjInstanceVo(vo);
		} 
		//该阶段，该建筑类别下所有事项
		List<PrjTaskDefineVo> taskList = ProjectStepUtil.getAllTaskByStageGovType(project.getPrjInstanceVo().getIsGovType(),project.getPrjInstanceVo().getStageId().toString());
		List<Long> taskIds = new ArrayList<Long>();
		for (PrjTaskDefineVo vo : taskList) {
			Map<Long, PrjTaskVo> m = ProjectStepUtil.getHasStartTask(project.getPrjInstanceVo());
			PrjTaskVo p = m.get(vo.getId());
			if (p == null) {
				taskIds.add(vo.getId());
			}
		}
		Map<Long, TaskFormConf> map = ProjectStepUtil.getTaskFormConf(project.getPrjInstanceVo().getId(), taskIds);
		return map;
	}
	
	/**
	 * 到表单页面
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/formDetail")
	public String formDetail(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model) {
		String formCode = request.getParameter("formCode").toUpperCase();
		String taskId = request.getParameter("taskId");
		String lookForm = request.getParameter("lookForm");
		PrjFormVO prjFormVO = factoryFormService.initBizForm(project.getPrjInstanceVo(), formCode,taskId);
		model.addAttribute("formObject", prjFormVO.getObject());
		model.addAttribute("lookForm", lookForm);
		return prjFormVO.getModuleUrl();
	}
	
	/**
	 * 清除session作用域中存在的打开事项列表集合
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("clearSessionInfo")
	public String clearSessionInfo(HttpServletRequest request, HttpServletResponse response, Model model){
		String openList = (String) request.getSession().getAttribute("openList");
		if(StringUtil.isNotEmpty(openList)){
			request.getSession().removeAttribute("openList");
		}
		return null;
	}
	
	/**
	 * 到打印基本信息页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("printInstance")
	public String printInstance(HttpServletRequest request, HttpServletResponse response, Model model){
		String id = request.getParameter("id");
		PrjInstanceVo prjInstanceVo = projectServiceInf.getPrjInstanceVoById(Long.valueOf(id));
		model.addAttribute("prjInstanceVo", prjInstanceVo);
		return "modules/project/wsbs_print";
	}
	
}
