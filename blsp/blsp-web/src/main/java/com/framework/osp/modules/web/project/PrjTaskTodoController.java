package com.framework.osp.modules.web.project;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.framework.core.result.ListResult;
import com.framework.core.result.PageResult;
import com.framework.core.result.RequestDTOPage;
import com.framework.core.result.Result;
import com.framework.osp.common.config.Global;
import com.framework.osp.common.persistence.Page;
import com.framework.osp.modules.sys.dao.OfficeDao;
import com.framework.osp.modules.sys.entity.Office;
import com.framework.osp.modules.sys.entity.User;
import com.framework.osp.modules.sys.utils.DictUtils;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.framework.osp.modules.web.util.FormEncodeUtil;
import com.lpcode.modules.blsp.constdefine.DicConstants;
import com.lpcode.modules.service.dimension.holiday.DimHolidayService;
import com.lpcode.modules.service.impl.project.util.ProjectUtil;
import com.lpcode.modules.service.project.dto.PrjTaskAuditDTO;
import com.lpcode.modules.service.project.dto.PrjTaskDTO;
import com.lpcode.modules.service.project.dto.PrjTaskDefineDTO;
import com.lpcode.modules.service.project.dto.PrjTaskDependencyListDTO;
import com.lpcode.modules.service.project.dto.PrjTaskHandleDTO;
import com.lpcode.modules.service.project.dto.PrjTaskMaterialDTO;
import com.lpcode.modules.service.project.dto.PrjTaskPauseDTO;
import com.lpcode.modules.service.project.dto.PrjTaskTodoListDTO;
import com.lpcode.modules.service.project.dto.Project;
import com.lpcode.modules.service.project.dto.pinstance.PrjStageDefineVo;
import com.lpcode.modules.service.project.inf.PrjTaskDefineService;
import com.lpcode.modules.service.project.inf.PrjTaskService;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 我的代办Controller
 * 
 * @author lpcode
 *
 */

@Controller
@RequestMapping(value = "${adminPath}/prjTaskTodo")
public class PrjTaskTodoController {
	@Autowired
	private PrjTaskService prjTaskService;
	@Autowired
	private PrjTaskDefineService prjTaskDefineService;
	@Autowired
	private OfficeDao officeDao;
	@Autowired
	DimHolidayService dimHolidayService;
	@Autowired
	private ProjectServiceInf projectServiceInf;

	/**
	 * 待办列表页面
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, PrjTaskTodoListDTO dto, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())) {
			dto.setUserId(user.getId());
			RequestDTOPage<PrjTaskTodoListDTO> requestPage = new RequestDTOPage<PrjTaskTodoListDTO>();
			Page<PrjTaskTodoListDTO> page = new Page<PrjTaskTodoListDTO>(request, response);
			// 页面时间控件不能直接转换时间格式，手工转换
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (StringUtils.isNotBlank(dto.getBeginCreateDateStr())
					&& StringUtils.isNotBlank(dto.getEndCreateDateStr())) {
				try {
					dto.setBeginCreateDate(sdf.parse(dto.getBeginCreateDateStr() + " 00:00:00"));
					dto.setEndCreateDate(sdf.parse(dto.getEndCreateDateStr() + " 23:59:59"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			requestPage.setObj(dto);
			requestPage.setPage(page);
			PageResult<PrjTaskTodoListDTO> pageResult = prjTaskService.findPage(requestPage);
			
			// 从缓存中取到的用户officeList有时为空值，保险起见，再查询一次
			List<Office> oList = user.getOfficeList();
			if (oList == null || oList.size() <= 0) {
				user.setOfficeList(officeDao.findByUserList(user.getId()));
			}
			
			// 根据登陆用户所属部门ID查询其部门下的事项名称，供页面查询列表使用
			if (oList != null && oList.size() > 0) {
				List<String> officeIds = new ArrayList<>();
				for (Office off : oList) {
					officeIds.add(off.getId());
				}
				ListResult<com.lpcode.modules.dto.project.PrjTaskDefineDTO> taskNames = prjTaskDefineService.findTaskNameByDepyIds(officeIds);
				model.addAttribute("taskName", taskNames.getObj());
			}
			
			model.addAttribute("page", pageResult.getObj());
			model.addAttribute("prjTaskDTO", dto);
		}
		return "modules/project/taskTodoList";

	}

	/**
	 * 事项详情（待办、已办、办结通用）
	 * 
	 * @param request
	 * @param prjTaskInstId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public String taskTodoDetail(HttpServletRequest request, Long prjTaskInstId, Model model) {
		String taskStatus = request.getParameter("psta");
		if (prjTaskInstId != null && prjTaskInstId != 0 && StringUtils.isNotBlank(taskStatus)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			User user = UserUtils.getUser();
			Result<PrjTaskDTO> prjTaskDto = prjTaskService.findTaskById(prjTaskInstId);
			if (user != null && prjTaskDto.getObj() != null) {

				ListResult<PrjTaskHandleDTO> handleList = prjTaskService.taskHandleList(prjTaskInstId); // 事项操作列表
				Result<PrjTaskDefineDTO> taskDefine = prjTaskService.findTaskDefine(prjTaskInstId); // 事项定义
				Result<PrjTaskDTO> prjTaskResult = prjTaskService.findTaskById(prjTaskInstId); // 事项实例
				PrjTaskDTO prjTask = prjTaskResult.getObj();

				ListResult<PrjTaskMaterialDTO> materialList = prjTaskService.findTaskMaterialList(prjTaskInstId); // 事项材料

				model.addAttribute("taskDefine", taskDefine.getObj());
				model.addAttribute("handleList", handleList.getObj());
				// taskStatus:1、待办
				if (!("1".equals(taskStatus) && user.getId().equals(prjTaskDto.getObj().getCurrUser()))) {
					// 控制页面操作按钮显示，只有待办并且事项当前用户为登陆用户时才显示操作按钮
					prjTask.setTaskStatus("-1");
				} else {
					Date newDate = new Date();
					Date taskStartDate = prjTask.getTaskStartTime();
					String minPauseDate = null;
					if (taskStartDate.getTime() > newDate.getTime()){
						minPauseDate = sdf.format(DateUtils.addDays(taskStartDate, 1));
					} else {
						minPauseDate = sdf.format(DateUtils.addDays(newDate, 1));
					}
					model.addAttribute("minPauseDate", minPauseDate);
				}
				model.addAttribute("taskStatus", taskStatus);
				model.addAttribute("prjTask", prjTask);
				model.addAttribute("prjTaskInstId", prjTaskInstId);

				String taskCode = taskDefine.getObj().getTaskCode();
				// 只有人防的两张表单显示业务信息
				if ("10007200174172973914440404".equals(taskCode) || "10007200374172973914440404".equals(taskCode)){
					model.addAttribute("taskCodeFlag", "1");
				}
				
				Project project = ProjectUtil.getProject(prjTaskDto.getObj().getPrjId()); // 项目详情
				// 重新处理  project中PrjStageMaterialVoMap集合 中的数据
				projectServiceInf.getProjectByTaskId(project.getPrjInstanceVo().getStageId(),prjTask.getTaskId(),project);
				PrjStageDefineVo prjStageDefineVo = ProjectUtil.getStageByProId(prjTask.getPrjId().toString());
				model.addAttribute("project", project);
				// 项目类型
				if (prjStageDefineVo != null) {
					String stageType =  prjStageDefineVo.getStageType();
					model.addAttribute("projectType", stageType);
					// 企业项目，查询依赖事项操作信息
					if ("2".equals(stageType)) {
						ListResult<PrjTaskDependencyListDTO> depListResult = prjTaskService.findPrjTaskDependency(prjTaskDto.getObj().getTaskId(), prjTaskDto.getObj().getPrjId());
						if (depListResult.getObj() != null && depListResult.getObj().size() > 0)
							model.addAttribute("depList", depListResult.getObj());
					}
				}
				

				model.addAttribute("material", materialList.getObj());
				
				// 提交材料截至日期默认值
				String materEndDate = DictUtils.getDictValue("LAST_MATRERIAL_DATE", "5");
				Date end = dimHolidayService.findWorkDay(new Date(), Integer.parseInt(materEndDate), DicConstants.TASK_DEFINE_DIM_TYPE_WORKDAY );
				model.addAttribute("materEndDate",sdf.format(end));

				return "modules/project/taskTodoDetail";
			}
		}
		return "redirect:" + Global.getAdminPath() + "/prjTaskTodo/list";
	}

	/**
	 * 通过并继续
	 * 
	 * @param request
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pass")
	public String taskTodoPass(HttpServletRequest request, PrjTaskAuditDTO dto, Model model) {
		String userId = UserUtils.getUser().getId();
		if (dto.getPrjTaskInstId() != null && StringUtils.isNotBlank(dto.getCurrUser())
				&& StringUtils.isNotBlank(dto.getAuditDesc()) && StringUtils.isNotBlank(userId)) {
			dto.setUserId(userId);
			prjTaskService.taskPass(dto);
		}

		return "redirect:" + Global.getAdminPath() + "/prjTaskTodo/list";
	}

	/**
	 * 暂停
	 * 
	 * @param request
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pause")
	public String taskTodoPause(HttpServletRequest request, PrjTaskPauseDTO dto, Model model) {
		String userId = UserUtils.getUser().getId();
		if (dto.getPrjTaskInstId() != null && StringUtils.isNotBlank(dto.getPauseDateStr())
				&& StringUtils.isNotBlank(dto.getPauseDesc()) && StringUtils.isNotBlank(userId)) {
			dto.setCreator(userId);
			prjTaskService.taskPause(dto);
			return "redirect:" + Global.getAdminPath() + "/prjTaskTodo/detail?psta=1&prjTaskInstId="+dto.getPrjTaskInstId();
		}
		return "redirect:" + Global.getAdminPath() + "/prjTaskTodo/list";
	}

	/**
	 * 恢复
	 * 
	 * @param request
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/resume")
	public String taskTodoResume(HttpServletRequest request, PrjTaskPauseDTO dto, Model model) {
		String userId = UserUtils.getUser().getId();
		if (dto.getPrjTaskInstId() != null && StringUtils.isNotBlank(userId)) {
			dto.setCreator(userId);
			prjTaskService.taskResume(dto);
			return "redirect:" + Global.getAdminPath() + "/prjTaskTodo/detail?psta=1&prjTaskInstId="+dto.getPrjTaskInstId();
		}

		return "redirect:" + Global.getAdminPath() + "/prjTaskTodo/list";
	}

	/**
	 * 办结
	 * 
	 * @param request
	 * @param dto
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/finish")
	public String taskTodoFinish(HttpServletRequest request, PrjTaskAuditDTO dto, Model model) {
		String userId = UserUtils.getUser().getId();
		if (dto.getPrjTaskInstId() != null && StringUtils.isNotBlank(userId)) {
			dto.setUserId(userId);
			prjTaskService.taskFinish(dto);
		}

		return "redirect:" + Global.getAdminPath() + "/prjTaskTodo/list";
	}
	
	/**
	 *  生成码图功能
	 *  
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/createLpcode")
	public void createLpcode(HttpServletRequest request,HttpServletResponse response,  String prjId, String type, Model model) {
		String userId = UserUtils.getUser().getId();
		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(prjId)) {
			Project project = ProjectUtil.getProject(Long.parseLong(prjId)); // 项目详情
			//String jsonStr = JsonMapper.toJsonString(project.getFormRfYdjsBjspVo());
			OutputStream os=null;
			try {
				byte[] result = null;
				// 第一张表单生成码图
				if ("1".equals(type)) {
					result = FormEncodeUtil.generateFormLpocde(project.getFormRfYdjsBjspVo(),DicConstants.BLSP_RF_FORM1);
				} else {
					// 第二张表单生成码图
					result = FormEncodeUtil.generateFormLpocde(project.getFormRfBjshVo(),DicConstants.BLSP_RF_FORM2);
				}
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
	
	/**
	 * 待办列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/backlog")
	public String backlog(HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())) {
			List<PrjTaskTodoListDTO> list = prjTaskService.backlogList(user.getId());
			try {
				response.getWriter().print(JSONArray.toJSON(list).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
