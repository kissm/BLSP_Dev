package com.framework.osp.modules.web.project;

import com.alibaba.fastjson.JSONArray;
import com.framework.core.result.ListResult;
import com.framework.core.utils.BeanCopy;
import com.framework.core.utils.StringUtil;
import com.framework.fdfs.images.FastDFSFileFactory;
import com.framework.fdfs.images.FastDFSImage;
import com.framework.osp.common.config.Global;
import com.framework.osp.common.persistence.Page;
import com.framework.osp.common.web.BaseController;
import com.framework.osp.modules.sys.entity.Office;
import com.framework.osp.modules.sys.service.SystemService;
import com.framework.osp.modules.sys.utils.DictUtils;
import com.framework.osp.modules.sys.utils.UserUtils;
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
import com.lpcode.modules.service.project.constant.TaskConstants;
import com.lpcode.modules.service.project.dto.*;
import com.lpcode.modules.service.project.dto.pinstance.*;
import com.lpcode.modules.service.project.dto.stageTaskMaterialDef.PrjStageConTaskListDTO;
import com.lpcode.modules.service.project.inf.*;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

//import com.mongodb.util.JSON;
// /// prjinstance 是 项目数据表 . stage   项目阶段历史表

@Controller
@RequestMapping(value = "${adminPath}/project")
public class ProjectController extends BaseController {
	@Autowired
	private ProjectServiceInf projectServiceInf;
	@Autowired
	private PrjTaskService prjTaskService;
	@Autowired
	private DimHolidayService dimHolidayService;
	@Autowired
	private PrjTaskScheduleService prjTaskScheduleService;
	@Autowired
	private ProjectAcceptServiceInf projectAcceptServiceInf;
	@Autowired
	private IFactoryFormService factoryFormService;
	@Autowired
	private PrjStageDefineService stageDefineService;
	@Autowired
	private SystemService systemService;


	// @RequiresPermissions("project:edit")
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/project/index";
	}

	// @RequiresPermissions("project:edit")
	@RequestMapping(value = "view")
	public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = request.getParameter("id");

		// ///拿到Project对象
		Project project = projectServiceInf.getProjectByPrjId(Long.parseLong(id));
		// ///	获取项目阶段历史实例
		project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(Long.parseLong(id)));
		// 重新处理  project中PrjStageMaterialVoMap集合 中的数据
		//projectServiceInf.getProjectById(project.getPrjInstanceVo().getStageId(),project);
		// ///  获取项目所有阶段实例
		project.setPrjStageVoList(ProjectUtil.getStageInsListByProId(id));
		model.addAttribute("project", project);
		return "modules/project/view";
	}



	// ///业务查询===项目追踪
	// @RequiresPermissions("project:edit")
	@RequestMapping(value = "detail")
	public String detial(HttpServletRequest request, HttpServletResponse response, Model model) {
		String table = StateFormConstants.getInstance().getFirstState();
		String id = request.getParameter("id");

		// /// 拿到项目对象和它所有的阶段集合
		Project project = projectServiceInf.getProject(Long.parseLong(id));
		project.setPrjStageVoList(ProjectUtil.getStageInsListByProId(id));
		project.setPrjStageDefineVoList(ProjectUtil.getAllStageList());
		project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(Long.parseLong(id)));
		model.addAttribute("table", table);
		model.addAttribute("project", project);
		// /// 判断是不是政府类型
		if(project.getPrjInstanceVo().getPrjType().equals(DicConstants.PROJECT_TYPE_ZF)){
			// ///   拿项目基本信息创建时候的日期
			Date prjCreatTime = project.getPrjInstanceVo().getCreatTime();

			// ///拿项目第一个阶段实例id
			String stageIdStr = project.getPrjStageVoList().get(0).getId().toString();==============
			List<PrjTaskVo> prjTaskVoList = ProjectUtil.getAllTaskByInstanceStage(stageIdStr,project.getPrjInstanceVo().getId().toString());
			for(PrjTaskVo prjTaskVo : prjTaskVoList){
				int useZrr = getUseTimeZrr(prjCreatTime,prjTaskVo.getTaskRealEndtime());
				String useZrrStr = (TaskConstants.TASK_STATUS_FINISH.equals(prjTaskVo.getTaskStatus()))? useZrr+"" :"";
				if(prjTaskVo.getTaskId().equals(TaskConstants.TASK_SGTSC_ID)){//下任务至完成施工图审核用时
					model.addAttribute("xrwzwcsgtshys", useZrrStr);
				}
				if(prjTaskVo.getTaskId().equals(TaskConstants.TASK_JZGCSGXKZHF_ID)){//下任务至完成施工许可证用时
					model.addAttribute("xrwzwcsgxkzys", useZrrStr);
				}
				if(prjTaskVo.getTaskId().equals(TaskConstants.TASK_JSGCJGYSBA_ID)){//下任务至完成验收用时
					model.addAttribute("xrwzwcysys", useZrrStr);
				}
			}
		}
		return "modules/project/detail";
	}

	/**
	 * 项目申报时间到事项办结时间，自然日
	 * @param creatTime
	 * @param realEndTime
	 * @return
	 */
	private int getUseTimeZrr(Date creatTime , Date realEndTime){
		return dimHolidayService.calDatePeriod(creatTime, realEndTime, DicConstants.TASK_DEFINE_DIM_TYPE_CALENDARDAY);
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

	项目受理
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
		项目基本信息实体是空
		if (project.getPrjInstanceVo() == null)
			return null;
		事项存在
                事项定义
		List<PrjTaskDefineVo> pdv = project.getPrjTaskDefineVoList();
		if (pdv != null && pdv.size() > 0) {
		    遍历每一个事项阶段
			Iterator<PrjTaskDefineVo> it = pdv.iterator();
			while (it.hasNext()) {
			    返回当前事项材料如果id是空就删除
                PrjTaskDefineVo v = it.next();
				if (v.getId() == null) {
					it.remove();
				}
			}
		}


		如果项目基本信息实体不存在把阶段对应的事项添加到事项集合中
		if (project.getPrjInstanceVo().getId() == null) {
			List<PrjTaskDefineVo> list = new ArrayList<PrjTaskDefineVo>();
			拿到当前项目所处阶段ID
			Long stageId = project.getPrjInstanceVo().getStageId();
			查当前阶段所有的事项
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
			保存事项集合
			project.setPrjTaskDefineVoList(list);
		} else {// 修改操作
			if (project.getPrjStageVo() != null && "1".equals(project.getPrjStageVo().getNewStageFlag())) {
				// 新阶段受理
				List<PrjTaskDefineVo> list = new ArrayList<PrjTaskDefineVo>();
				Long stageId = project.getPrjInstanceVo().getStageId();
				if (stageId == null)
					return null;
				根据阶段ID取所有事项
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
				保存事项集合
				project.setPrjTaskDefineVoList(list);
			} else {
				// 非新阶段，修改操作
				// project.setPrjInstanceVo(ProjectUtil.getInstance(project.getPrjInstanceVo().getId().toString()));
				List<PrjTaskVo> listT = new ArrayList<PrjTaskVo>();
				List<PrjTaskVo> done = new ArrayList<PrjTaskVo>();
				根据项目ID拿所有事项
				List<PrjTaskVo> taskList = ProjectUtil.getAllTaskByPrjId(project.getPrjInstanceVo().getId());

				Iterator<PrjTaskVo> ite = taskList.iterator();
				while (ite.hasNext()) {
					PrjTaskVo pv = ite.next();
					事项不为草稿或者驳回状态添加到done集合中
					if (!"0".equals(pv.getTaskStatus()) && !"6".equals(pv.getTaskStatus())) {
						done.add(pv);
						ite.remove();
					}
				}
                事项集合存在
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

	项目建立后保存龙贝码
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
		model.addAttribute("date", sdf.format(end));
		return "modules/project/success";
	}


	处理事项
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
		工具类获取项目实体
		PrjInstanceVo vo = ProjectUtil.getInstance(projectId);
		PrjBusinessAcceptVo accept = new PrjBusinessAcceptVo();
        赋值给Project对象
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
		获取项目实体
		PrjInstanceVo vo = ProjectUtil.getInstance(projectId);
		project.setPrjInstanceVo(vo);
		获取项目目前阶段上传的所有资料
		List<PrjStageMaterialVo> list = ProjectUtil.getStageMaterList(vo.getStageId(), projectId);
		project.setPrjStageMaterialVoList(list);
		Map<Long, List<PrjStageMaterialVo>> map = new TreeMap<Long, List<PrjStageMaterialVo>>();
		project.setPrjStageMaterialVoMap(map);
		if (list != null && list.size() > 0) {
            遍历项目阶段材料map.get(v.getTaskId())
                    /*
                    * 遍历项目目前阶段上传的所有资料。如果这个集合是空，那么就创建新集合对象，存当前对象。map存当前的事项id和事项集合
                    * 如果集合不为空，据存遍历当前这个对象
                    * */
            for (PrjStageMaterialVo v : list) {
                如果事项ID是空，那么为01，不是空就是拿到的事项ID
				List<PrjStageMaterialVo> taskList = map.get(v.getTaskId() == null ? 0l : v.getTaskId());
				if (taskList == null) {
					taskList = new ArrayList<PrjStageMaterialVo>();
					taskList.add(v);
					map.put(v.getTaskId() == null ? 0l : v.getTaskId(),taskList);
				} else {
				    事项集合添加阶段
                    taskList.add(v);
				}
			}
		}
        根据项目ID 获取该项目阶段实例
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

	政府项目受理列表
	查看所有的项目列表。默认传递分页参数。
	// @RequiresPermissions("project:view")
	@RequestMapping(value = "list")
	public String list(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Project> page = new Page<Project>();
		String pageNo = request.getParameter("pageNo");
        /*判断某字符串是否为空或长度为0或由空白符(whitespace) 构成
                如果pangeNo不是空，则页数为pageNo,否则页数为1*/
		if (!StringUtils.isBlank(pageNo)) {
			page.setPageNo(Integer.parseInt(pageNo));
		} else {
			page.setPageNo(1);
		}
		/*设置默认行数。10条*/
		String pageSize = request.getParameter("pageSize");
		if (!StringUtils.isBlank(pageSize)) {
			page.setPageSize(Integer.parseInt(pageSize));
		} else {
			page.setPageSize(10);
		}
		/*降序拍显示条数*/
		page.setOrderBy("creat_Time desc");
		projectServiceInf.getProjectPage(project, page);
		model.addAttribute("page", page);
		model.addAttribute("project", project);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
		return "modules/project/list";
	}

	项目追踪
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
		project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(projectId));
		project.setPrjInstanceVo(ProjectUtil.getInstance(projectId));
		PrjBusinessAcceptVo accept = new PrjBusinessAcceptVo();
		accept.setId(project.getPrjInstanceVo().getAcceptId() == null ? 0 : project.getPrjInstanceVo().getAcceptId());
		project.setPrjBusinessAcceptVo(ProjectUtil.getPrjBusinessAccept(accept));
		//打印的事项材料信息
		Map<Long, List<PrjStageMaterialVo>> m = ProjectUtil.getAllTaskMaterByAcceptIdAndTaskIds(project.getPrjInstanceVo().getAcceptId(),taskIds);
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
		String view = request.getParameter("view");
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
		projectServiceInf.getProjectByTaskId(project.getPrjInstanceVo().getStageId(), Long.parseLong(taskId), project);
		model.addAttribute("prjTask",prjTask);
		model.addAttribute("project", project);
		model.addAttribute("taskId", taskId);
		//判断是项目列表中过来的还是项目追踪过来的
		model.addAttribute("view",view);
		return "modules/project/itemDetail";
	}
	
	/**
	 * 获取网上办事列表集合
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "wsbslist")
	public String wsbslist(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
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
		projectServiceInf.getWsbsProjectPage(project, page, false);
		model.addAttribute("page", page);
		model.addAttribute("project", project);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
		return "modules/project/pretrial/wsbsList";
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
		String pretrial = request.getParameter("pretrial");
		if(StringUtils.isNotEmpty(pretrial)){
			if(pretrial.equals("1")){
				return "modules/project/pretrial/wsbsPretrial";
			}
		}
		if(project.getPrjInstanceVo().getPrjType().equals("1"))
			return "modules/project/accept/wsbs_view";
		else			
			return "modules/project/bizaccept/wsbs_bizview";
	}
	
	/**
	 * 对网上办事项目进行审核处理
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "pretrialWsbs")
	public String pretrialWsbs(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
		Date now = new Date();
		project.getPrjInstanceVo().setUpdateTime(now);
		project.getPrjInstanceVo().setUpdator(UserUtils.getUser().getId());
		//判断该项目是通过还是驳回
		String applyState = project.getPrjInstanceVo().getApplyState();
		//网上办事提供项目基本信息
		PrjInstanceVo prjInstanceVo = project.getPrjInstanceVo();
		PrjInstanceVo prjInstanceToBlsp = new PrjInstanceVo();
		BeanCopy.copyProperties(prjInstanceVo,prjInstanceToBlsp);
		if(applyState.equals("2")){

			prjInstanceToBlsp.setCreatTime(now);
			prjInstanceToBlsp.setCreator(UserUtils.getUser().getId());
			prjInstanceToBlsp.setUpdateTime(now);
			prjInstanceToBlsp.setUpdator(UserUtils.getUser().getId());
			prjInstanceVo.setUpdateTime(now);
			prjInstanceVo.setUpdator(UserUtils.getUser().getId());
			prjInstanceToBlsp.setChannel("0");//供应渠道为0,标识为后台窗口人员录入

			//TODO 检查 prjcode
			PrjInstanceVo blspPrj = projectAcceptServiceInf.checkPrjByPrjCode(prjInstanceVo.getPrjCode());
			if(blspPrj != null){
				prjInstanceVo.setId(blspPrj.getId());
				projectAcceptServiceInf.insertInstanceSnapshot(blspPrj);
			}else{
				//获取项目编号
				PrjCodeGeneratorVo pvo = ProjectUtil.getProjectCode(prjInstanceToBlsp.getPrjType());
				if (pvo.getSeq() != null && (blspPrj == null ||blspPrj.getPrjCode() == null)) {
					pvo.setSeq(pvo.getSeq() + 1);
					project.setPrjCodeGeneratorVo(pvo);
					projectAcceptServiceInf.lockPrjCode(pvo, pvo.toString());
					prjInstanceVo.setPrjCode(project.getPrjCodeGeneratorVo().toString());
					prjInstanceToBlsp.setPrjCode(project.getPrjCodeGeneratorVo().toString());
				}
				//原项目ID
				Long wsbsId = prjInstanceVo.getId();
				prjInstanceToBlsp.setId(null);
				projectAcceptServiceInf.savePrjInstance(prjInstanceToBlsp);
				//新保存项目ID
				Long id = prjInstanceToBlsp.getId();
				//将网上办事项目中的项目材料关系提供一份给新项目
				prjTaskService.pretrialWsbsMaterial(wsbsId,id);
				//将网上办事项目中的表单信息提供一份给新项目
				pretrialWsbsForms(wsbsId, id);
				// 受理龙贝码
				lpcode(prjInstanceToBlsp);
				prjInstanceVo.setLpcodeAddr(prjInstanceToBlsp.getLpcodeAddr());
				prjInstanceVo.setAcceptId(prjInstanceToBlsp.getAcceptId());
				String taskProcess = prjInstanceToBlsp.getTaskProcess();
				List<StageTaskProcessVO> taskProcessList = new ArrayList<>();
				//企业项目的前置阶段的预处理
				if(StringUtils.isNotBlank(taskProcess) && prjInstanceToBlsp.getPrjType().equals("2")){
					taskProcess = taskProcess.replaceAll("\\&quot\\;","\"");
					taskProcessList = JSONArray.parseArray(taskProcess, StageTaskProcessVO.class);
					//处理线下办结或免办的阶段事项的处理,并存入事项表
					prjTaskService.creatProcessedTask(prjInstanceToBlsp,taskProcessList);
				}
				prjInstanceVo.setId(wsbsId);//对原网厅提交的数据进行状态及修发送
			}
			//对网上办事提供项目的信息进行修改
			projectAcceptServiceInf.updatePrjInstance(prjInstanceVo);
		}
		return "redirect:"+ Global.getAdminPath() +"/project/wsbslist";
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
	
	private void pretrialWsbsForms(Long wsbsId,Long id){
		ProjectChangeForm project = new ProjectChangeForm();
		if (StringUtils.isNotBlank(wsbsId.toString())) {
			PrjInstanceVo vo = ProjectUtil.getInstance(wsbsId);
			project.setPrjInstanceVo(vo);
		} 
		//该阶段，该建筑类别下所有事项
		List<PrjTaskDefineVo> taskList = ProjectStepUtil.getAllTaskByStageGovType(project.getPrjInstanceVo().getIsGovType(), project.getPrjInstanceVo().getStageId().toString());
		List<Long> taskIds = new ArrayList<Long>();
		for (PrjTaskDefineVo vo : taskList) {
			Map<Long, PrjTaskVo> m = ProjectStepUtil.getHasStartTask(project.getPrjInstanceVo());
			PrjTaskVo p = m.get(vo.getId());
			if (p == null) {
				taskIds.add(vo.getId());
			}
		}
		Map<Long, TaskFormConf> map = ProjectStepUtil.getTaskFormConf(project.getPrjInstanceVo().getId(), taskIds);
		for(Map.Entry<Long, TaskFormConf> entry : map.entrySet()){
			List<FormDefineVo> formDefineList = entry.getValue().getFormDefineList();
			if(formDefineList != null && !"".equals(formDefineList)){
				for (FormDefineVo formDefineVo : formDefineList) {
					if(formDefineVo.getPrjectId() != null && !"".equals(formDefineVo.getPrjectId())){
						String formCode = formDefineVo.getFormCode();
						Long taskId = entry.getValue().getTaskDefId();
						PrjFormVO prjFormVO = factoryFormService.initBizForm(project.getPrjInstanceVo(), formCode.toUpperCase(),taskId.toString());
						//为新建项目存入网上办事表单数据
						factoryFormService.saveOrUpdateBizForm(prjFormVO.getObject(), formCode.toUpperCase(), id);
					}
				}
			}
		}
	}
	
	/**
	 * 返回详情页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("backItem")
	public String backItem(HttpServletRequest request, HttpServletResponse response, Model model){
		String prjId = request.getParameter("prjId");
		String taskId = request.getParameter("taskId");
		String view = request.getParameter("view");
		PrjInstanceVo prjInstanceVo = projectServiceInf.getPrjInstanceVoById(Long.valueOf(prjId));
		String prjType = prjInstanceVo.getPrjType();
		//存储事项ID到session
		request.getSession().setAttribute("stageTaskId", taskId);
		if(view.equals("1")){
			//判断政府项目还是企业项目，并重定向到原来的查看项目详情方法
			if(prjType.equals("1")){
				return "redirect:" + Global.getAdminPath() + "/project/view?id="+prjId;
			}else{
				//存储阶段ID到session
				PrjTaskDefineVo prjTaskDefineVo = ProjectUtil.getPrjTaskDefineVo(taskId);
				request.getSession().setAttribute("stageStageId", prjTaskDefineVo.getStageId().toString());
				return "redirect:" + Global.getAdminPath() + "/enterprise/project/view?id="+prjId;
			}
		}else if(view.equals("4")){
			//判断政府项目还是企业项目，并重定向到原来的查看项目详情方法
			if(prjType.equals("2")){
				//企业项目存储阶段ID到session
				PrjTaskDefineVo prjTaskDefineVo = ProjectUtil.getPrjTaskDefineVo(taskId);
				request.getSession().setAttribute("stageStageId", prjTaskDefineVo.getStageId().toString());
			}
			return "redirect:" + Global.getAdminPath() + "/project/wsbsview?id="+prjId;
		}else if(view.equals("5")){
			//判断政府项目还是企业项目，并重定向到原来的查看项目详情方法
			if(prjType.equals("2")){
				//企业项目存储阶段ID到session
				PrjTaskDefineVo prjTaskDefineVo = ProjectUtil.getPrjTaskDefineVo(taskId);
				request.getSession().setAttribute("stageStageId", prjTaskDefineVo.getStageId().toString());
			}
			return "redirect:" + Global.getAdminPath() + "/project/wsbsview?id="+prjId+"&pretrial=1";
		}else{
			return "redirect:" + Global.getAdminPath() + "/project/detail?id="+prjId;
		}
	}
	
	/**
	 * 清除session作用域中存在的事项编号/阶段编号
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("clearSessionInfo")
	public String clearSessionInfo(HttpServletRequest request, HttpServletResponse response, Model model){
		String stageTaskId = (String) request.getSession().getAttribute("stageTaskId");
		if(StringUtil.isNotEmpty(stageTaskId)){
			request.getSession().removeAttribute("stageTaskId");
		}
		String stageStageId = (String) request.getSession().getAttribute("stageStageId");
		if(StringUtil.isNotEmpty(stageStageId)){
			request.getSession().removeAttribute("stageStageId");
		}
		return null;
	}
	
	/**
	 * ajax显示终止项目页面
	 *
	 * @param request
	 * @param prjInsId
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("project:force:stop")
	@RequestMapping(value = "/stop/form")
	public String prjStopForm(HttpServletRequest request, Long prjInsId, Model model) {
		if (prjInsId != null) {
			model.addAttribute("prjInsId", prjInsId);
		}
		return "modules/project/prjStopForm";
	}
	
	/**
	 * 保存项目终止信息
	 */
	@RequestMapping(value = "/stop/save")
	public String prjStopSave(HttpServletRequest request, StopFormDTO dto, Model model) {
		projectServiceInf.stopProject(dto);
		model.addAttribute("message","ok");
		return "modules/project/prjStopForm";
	}
	
	/**
	 * 生产表单龙贝码
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/createFormLpcode")
	public String createFormLpcode(ProjectChangeForm project, HttpServletRequest request, HttpServletResponse response, Model model){
		String formCode = request.getParameter("formCode").toUpperCase();
		String taskId = request.getParameter("taskDefId");
		PrjFormVO prjFormVO = factoryFormService.initBizForm(project.getPrjInstanceVo(), formCode, taskId);
		if(prjFormVO != null && !"".equals(prjFormVO)){
			if(prjFormVO.getObject() != null && !"".equals(prjFormVO.getObject())){
				OutputStream os=null;
				try {
					byte[] result = null;
					Object object = factoryFormService.FormVoForCreateLpcode(prjFormVO.getObject(), formCode);
					result = FormEncodeUtil.generateFormLpocde(object, formCode);
					if(result!=null && result.length>0){
						os = response.getOutputStream();  
				    	response.reset();  
				    	response.setHeader("Content-Disposition","attachment;filename=lpcode.bmp");  
				    	response.setContentType("application/octet-stream; charset=utf-8");  
			    	    os.write(result);  
			    	    os.flush();  
					}else{
						System.out.println("生成码图失败！"+result);
					}
			    		
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					if (os != null) {
			        	try{
			        		os.close();
			        	}catch(Exception e){
			        		e.printStackTrace();
			        	}
			        }  
				}
			}
		}
		return null;
	}
	
	@RequestMapping(value = "wsbsmaterial")
	public void wsbsmaterial(ProjectChangeForm pro, HttpServletRequest request, HttpServletResponse response, Model model) {
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


	@RequestMapping("showStageTask")
	public String showStageTask(HttpServletRequest request, HttpServletResponse response, Model model){
		String stageIdStr = request.getParameter("stageId");
		if(StringUtils.isNotBlank(stageIdStr) && StringUtils.isNumeric(stageIdStr)){
			Long stageId = Long.parseLong(stageIdStr);
			//得到当前的阶段 ID,从配置表里获取之前的阶段
			List<PrjStageConTaskListDTO> stageList = stageDefineService.getTaskBeforStageId(stageId);
			model.addAttribute("stageList", stageList);
		}
		List<Office> offices = systemService.findAllOffice();
		Map<String, String> map = new HashMap<String, String>();
		if (offices != null && offices.size() > 0) {
			for (Office office : offices) {
				map.put(office.getId(), office.getName());
			}
			model.addAttribute("offices", map);
		}
		return "modules/project/stageTask";
	}
}
