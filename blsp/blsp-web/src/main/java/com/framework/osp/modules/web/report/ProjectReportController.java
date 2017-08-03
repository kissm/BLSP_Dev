package com.framework.osp.modules.web.report;

import com.framework.core.result.ListResult;
import com.framework.osp.common.persistence.Page;
import com.framework.osp.common.web.BaseController;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.framework.osp.modules.web.util.DateUtil;
import com.lpcode.modules.blsp.vo.ProjectReportVo;
import com.lpcode.modules.blsp.vo.ProjectVo;
import com.lpcode.modules.blsp.vo.TaskVo;
import com.lpcode.modules.service.impl.project.util.ProjectUtil;
import com.lpcode.modules.service.project.dto.PrjTaskHandleDTO;
import com.lpcode.modules.service.project.inf.PrjTaskService;
import com.lpcode.modules.service.sys.IUserOfficeViewService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "${adminPath}/project/report")
public class ProjectReportController extends BaseController {
	
	@Autowired
	private PrjTaskService prjTaskService;
	@Autowired
	private IUserOfficeViewService userOfficeViewService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 得到当天的日期串
	 * @return
     */
	private String getTodayStr(){
		return sdf.format(new Date());
	}

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/report")
	public String progress(HttpServletRequest request, HttpServletResponse response, Model model) {
		String type = request.getParameter("type");
		String dType = request.getParameter("dType");
		String reportDateStr = request.getParameter("reportDate");
		if (StringUtils.isBlank(type) || StringUtils.isBlank(dType))
			return null;

		if (StringUtils.isBlank(reportDateStr)){
			reportDateStr = getTodayStr();
		}

		Date from = new Date();
		try {
			from = sdf.parse(reportDateStr);
		}catch (Exception e){
			e.printStackTrace();
			from = new Date();
		}
		from = getDate(dType, from);
		Date to = getEndDate(dType, from);
		ProjectReportVo vo = ProjectUtil.getProjectReportVo(type);
		vo.setAcceptNum(ProjectUtil.getPrjAcceptStatus(from, to, type));
		vo.setOverNum(ProjectUtil.getPrjOverStatus(from, to, type));
		vo.setStopNum(ProjectUtil.getPrjStopStatus(from, to, type));
		vo.setProcessNum(ProjectUtil.getPrjProcessStatus(from,to,type));
		if (type.equals("2")) {
			vo.setPendingNum(ProjectUtil.getPrjPendingStatus(type, from, to));
		}
		//通过用户查看可视化看版的权限进行授权
		Map<String,Boolean> officeViewMap = new HashMap<String ,Boolean>();
		List<String> officeIds = userOfficeViewService.getOfficeIDByUserId(UserUtils.getUser().getId());
		for(String officeId : officeIds){
			officeViewMap.put(officeId,true);
		}

		model.addAttribute("officeViewMap", officeViewMap);
		model.addAttribute("report", vo);
		model.addAttribute("dType", dType);
		model.addAttribute("type", type);
		model.addAttribute("reportDate", reportDateStr);
		if (type.equals("1")) {
			return "modules/report/report";
		} else {
			return "modules/report/reportCompany";
		}
	}

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/project")
	public String project(HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectVo> page = new Page<ProjectVo>();
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
		String type = request.getParameter("type");
		String dType = request.getParameter("dType");
		String status = request.getParameter("status");
		String reportDateStr = request.getParameter("reportDate");
		if (StringUtils.isBlank(reportDateStr)){
			reportDateStr = getTodayStr();
		}
		model.addAttribute("type", type);
		model.addAttribute("dType", dType);
		model.addAttribute("status", status);
		model.addAttribute("reportDate", reportDateStr);
		if (StringUtils.isBlank(type) || StringUtils.isBlank(dType))
			return null;
		Date from ;
		try {
			from = sdf.parse(reportDateStr);
		}catch (Exception e){
			e.printStackTrace();
			from = new Date();
		}
		from = getDate(dType, from);
		Date to = getEndDate(dType, from);
		if (StringUtils.isNotBlank(status) && status.equals("1")) {// 受理
			ProjectUtil.getPrjAcceptList(page, from, to, type);
			model.addAttribute("page", page);
			return "modules/report/acceptPrjList";
		} else if (StringUtils.isNotBlank(status) && status.equals("2")) {// 办结
			ProjectUtil.getPrjOverList(page, from, to, type);
			model.addAttribute("page", page);
			return "modules/report/overPrjList";
		}else if(StringUtils.isNotBlank(status) && status.equals("3")){// 阶段类受理
			ProjectUtil.getPrjPendingList(page, from, to, type);
			model.addAttribute("page", page);
			return "modules/report/acceptPrjList";
		} else if (StringUtils.isNotBlank(status) && status.equals("4")) {// 终止
			ProjectUtil.getPrjStopList(page, from, to, type);
			model.addAttribute("page", page);
			return "modules/report/overPrjList";
		}else if (StringUtils.isNotBlank(status) && status.equals("5")) {// 下任务数
			ProjectUtil.getAllPrjList(page, from, to, type);
			model.addAttribute("page", page);
			return "modules/report/acceptPrjList";
		}else if (StringUtils.isNotBlank(status) && status.equals("6")) {// 审批中项目数

			ProjectUtil.getPrjProcessList(page, from, to, type);
			model.addAttribute("page", page);
			return "modules/report/acceptPrjList";
		}
//		else if (StringUtils.isNotBlank(status) && status.equals("7")) {// 办理项目数
////			ProjectUtil.getPrjAcceptList(page, from, to, type);
//			model.addAttribute("page", page);
//			return "modules/report/acceptPrjList";
//		}
		return null;
	}

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/task")
	public String task(HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TaskVo> page = new Page<TaskVo>();
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
		String type = request.getParameter("type");
		String dType = request.getParameter("dType");
		String taskid = request.getParameter("taskid");
		String taskType = request.getParameter("taskType");
		String reportDateStr = request.getParameter("reportDate");
		if (StringUtils.isBlank(reportDateStr)){
			reportDateStr = getTodayStr();
		}
		model.addAttribute("type", type);
		model.addAttribute("dType", dType);
		model.addAttribute("taskid", taskid);
		model.addAttribute("taskType", taskType);
		model.addAttribute("reportDate", reportDateStr);
		if (StringUtils.isBlank(type) || StringUtils.isBlank(dType))
			return null;
		Date from ;
		try {
			from = sdf.parse(reportDateStr);
		}catch (Exception e){
			e.printStackTrace();
			from = new Date();
		}
		from = getDate(dType, from);
		Date to = getEndDate(dType, from);
		if (taskType.equals("1")) {// 待审批
			ProjectUtil.getTaskPendingList(page, taskid);
			model.addAttribute("page", page);
			return "modules/report/taskList";
		} else if (taskType.equals("2")) {// 审批中
			ProjectUtil.getTaskCheckingList(page, taskid);
			model.addAttribute("page", page);
			return "modules/report/approvaling";
		} else if (taskType.equals("3")) {// 已办结
			ProjectUtil.getTaskOverList(page, from, to, taskid);
			model.addAttribute("page", page);
			return "modules/report/achieve";
		} else if (taskType.equals("4")) {// 暂停数
			ProjectUtil.getTaskStopingList(page, from, to, taskid);
			model.addAttribute("page", page);
			return "modules/report/pause";
		} else if (taskType.equals("5")) {// 超时
			ProjectUtil.getTaskOverTimeList(page, taskid);
			model.addAttribute("page", page);
			return "modules/report/taskOverTimeList";
		}
		return null;
	}

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deptid")
	public String deptid(HttpServletRequest request, HttpServletResponse response, Model model) {
		String type = request.getParameter("type");
		String dType = request.getParameter("dType");
		String depid = request.getParameter("depid");
		String stageid = request.getParameter("stageid");
		String reportDateStr = request.getParameter("reportDate");
		if (StringUtils.isBlank(reportDateStr)){
			reportDateStr = getTodayStr();
		}
		model.addAttribute("type", type);
		model.addAttribute("dType", dType);
		model.addAttribute("depid", depid);
		model.addAttribute("stageid", stageid);
		model.addAttribute("reportDate", reportDateStr);
		if (StringUtils.isBlank(type) || StringUtils.isBlank(dType))
			return null;
		Date from ;
		try {
			from = sdf.parse(reportDateStr);
		}catch (Exception e){
			e.printStackTrace();
			from = new Date();
		}
		from = getDate(dType, from);
		Date to = getEndDate(dType, from);
		List<Long> taskid = ProjectUtil.getPrjTaskDefineList(depid, stageid);
		List<DepartMentStatus> list = new ArrayList<DepartMentStatus>();
		DepartMentStatus dep = new DepartMentStatus();
		dep.setName("待审批数");
		dep.setNum(ProjectUtil.getTaskPendingStatus(taskid).toString());
		// list.add(dep);
		DepartMentStatus dep1 = new DepartMentStatus();
		dep1.setName("审批中数量");
		dep1.setNum(ProjectUtil.getTaskCheckingStatus(taskid).toString());
		list.add(dep1);
		DepartMentStatus dep2 = new DepartMentStatus();
		dep2.setName("暂停数");
		dep2.setNum(ProjectUtil.getTaskStopingStatus(from, to, taskid).toString());
		list.add(dep2);
		DepartMentStatus dep3 = new DepartMentStatus();
		dep3.setName("已办结数量");
		dep3.setNum(ProjectUtil.getTaskOverStatus(from, to, taskid).toString());
		list.add(dep3);
		DepartMentStatus dep4 = new DepartMentStatus();
		dep4.setName("超时未办结数量");
		dep4.setNum(ProjectUtil.getTaskOverTimeStatus(taskid).toString());
		list.add(dep4);
		model.addAttribute("list", list);
		return "modules/report/status";
	}

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/taskid")
	public String taskId(HttpServletRequest request, HttpServletResponse response, Model model) {
		String type = request.getParameter("type");
		String dType = request.getParameter("dType");
		String taskid = request.getParameter("taskid");
		String reportDateStr = request.getParameter("reportDate");
		if (StringUtils.isBlank(reportDateStr)){
			reportDateStr = getTodayStr();
		}
		model.addAttribute("type", type);
		model.addAttribute("dType", dType);
		model.addAttribute("taskid", taskid);
		model.addAttribute("reportDate", reportDateStr);
		if (StringUtils.isBlank(type) || StringUtils.isBlank(dType))
			return null;
		Date from ;
		try {
			from = sdf.parse(reportDateStr);
		}catch (Exception e){
			e.printStackTrace();
			from = new Date();
		}
		from = getDate(dType, from);
		Date to = getEndDate(dType, from);
		from = getDate(dType, from);
		List<DepartMentStatus> list = new ArrayList<DepartMentStatus>();
		DepartMentStatus dep = new DepartMentStatus();
		dep.setName("待审批数");
		dep.setNum(ProjectUtil.getTaskPendingStatus(taskid).toString());
		dep.setTaskType("1");
		dep.setTaskid(taskid);
		// list.add(dep);
		DepartMentStatus dep1 = new DepartMentStatus();
		dep1.setName("审批中数量");
		dep1.setNum(ProjectUtil.getTaskCheckingStatus(taskid).toString());
		dep1.setTaskType("2");
		dep1.setTaskid(taskid);
		list.add(dep1);
		DepartMentStatus dep2 = new DepartMentStatus();
		dep2.setName("暂停数");
		dep2.setNum(ProjectUtil.getTaskStopingStatus(from, to, taskid).toString());
		dep2.setTaskType("4");
		dep2.setTaskid(taskid);
		list.add(dep2);
		DepartMentStatus dep3 = new DepartMentStatus();
		dep3.setName("已办结数量");
		dep3.setNum(ProjectUtil.getTaskOverStatus(from, to, taskid).toString());
		dep3.setTaskType("3");
		dep3.setTaskid(taskid);
		list.add(dep3);
		DepartMentStatus dep4 = new DepartMentStatus();
		dep4.setName("超时未办结数量");
		dep4.setNum(ProjectUtil.getTaskOverTimeStatus(taskid).toString());
		dep4.setTaskType("5");
		dep4.setTaskid(taskid);
		list.add(dep4);
		model.addAttribute("list", list);
		return "modules/report/link";
	}

	private Date getDate(String dType, Date from) {
		if (dType.equals("1")) {// 本日
			from = DateUtil.getCurrDayFirstBegin(from);
		} else if (dType.equals("2")) {// 本周
			from = DateUtil.getCurrWeekFirst(from);
		} else if (dType.equals("3")) {// 本月
			from = DateUtil.getCurrMonthFirst(from);
		} else if (dType.equals("4")) {// 本季
			from = DateUtil.getCurrQuarterFirst(from);
		} else if (dType.equals("5")) {// 本年
			from = DateUtil.getCurrYearFirst(from);
		}
		return from;
	}

	private Date getEndDate(String dType, Date from) {
		Date to = new Date();
		if (dType.equals("1")) {// 本日
			to = DateUtil.getCurrDayFirstEnd(from);
		} else if (dType.equals("2")) {// 本周
			to = DateUtil.getCurrWeekEnd(from);
		} else if (dType.equals("3")) {// 本月
			to = DateUtil.getCurrMonthEnd(from);
		} else if (dType.equals("4")) {// 本季
			to = DateUtil.getCurrQuarterEnd(from);
		} else if (dType.equals("5")) {// 本年
			to = DateUtil.getCurrYearEnd(from);
		}
		return to;
	}
	/**
	 * 获得某个事项操作的详情信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/record")
	public String record(HttpServletRequest request, HttpServletResponse response, Model model){
		String instId = request.getParameter("instId");
		ListResult<PrjTaskHandleDTO> HandleList = prjTaskService.taskHandleList(Long.valueOf(instId));
		model.addAttribute("list", HandleList.getObj());
		return "modules/report/taskDetail";
	}
	
}
