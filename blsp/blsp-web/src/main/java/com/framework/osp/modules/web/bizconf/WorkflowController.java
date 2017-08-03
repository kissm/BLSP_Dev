package com.framework.osp.modules.web.bizconf;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.lpcode.modules.service.bizconf.dto.LogSmsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.core.constants.BaseCode;
import com.framework.core.result.PageResult;
import com.framework.core.result.RequestDTOPage;
import com.framework.core.result.Result;
import com.framework.osp.common.persistence.Page;
import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.bizconf.dto.WorkflowTimeLimitDto;
import com.lpcode.modules.service.bizconf.inf.IWorkflowService;

/**
 * @author Pengs
 * @package com.framework.osp.modules.web.bizconf
 * @fileName WorkflowController
 * @date 16/2/19.
 */

@Controller
@RequestMapping("${adminPath}/workflow")
public class WorkflowController extends BaseController {

	@Autowired
	IWorkflowService workflowService;

	@ModelAttribute("workFlowDto")
	public WorkflowTimeLimitDto get(@RequestParam(value = "id", required = false) Long id) {
		if (null != id) {
			return workflowService.getWorkflowById(id);
		} else {
			return new WorkflowTimeLimitDto();
		}
	}

	@RequestMapping(value = "/list")
	public String findWorkflowList(WorkflowTimeLimitDto dto, HttpServletRequest request, HttpServletResponse resp,
			Model model) {
		RequestDTOPage<WorkflowTimeLimitDto> requestPage = new RequestDTOPage();
		Page<WorkflowTimeLimitDto> page = new Page(request, resp);
		requestPage.setObj(dto);
		requestPage.setPage(page);
		PageResult<WorkflowTimeLimitDto> pageResult = workflowService.findList(requestPage);
		model.addAttribute("page", page);
		model.addAttribute("workFlowDto", dto);
		return "modules/bizconf/workflowList";
	}

	@RequestMapping(value = "/add")
	public String workflowAdd() {

		return "modules/bizconf/workflowForm";
	}

	@RequestMapping(value = "/modify")
	public String workflowModify(WorkflowTimeLimitDto dto) {
		if (dto.getId() == null) {
			Date now = new Date();
			dto.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
			dto.setCreatTime(now);
			dto.setUpdateTime(now);
			workflowService.save(dto);
		} else {
			dto.setUpdateTime(new Date());
			workflowService.edit(dto);
		}
		return "redirect:" + adminPath + "/workflow/list";
	}

	@RequestMapping(value = "/findById")
	public String findById() {
		return "modules/bizconf/workflowForm";
	}

	@RequestMapping(value = "/deleteById")
	@ResponseBody
	public Result<Boolean> deleteById(@Valid @RequestBody WorkflowTimeLimitDto param, HttpServletRequest request) {
		workflowService.delete(param.getId());
		// return "redirect:" + adminPath + "/workflow/list";
		return new Result<>(true);
	}

	/**
	 * 验证材料名称是否可用
	 * 
	 * @param oldWorkflowName
	 * @param name
	 * @return
	 */
	@ResponseBody
	// @RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkWorkflowName")
	public String checkWorkflowName(String oldWorkflowName, String name) {
		if (name != null && name.equals(oldWorkflowName)) {
			return "true";
		} else if (name != null && workflowService.enableWorkflowName(name)) {
			return "true";
		}
		return "false";
	}

	@RequestMapping(value = "/smslist")
	public String findSmsList(LogSmsDto dto, HttpServletRequest request, HttpServletResponse resp,Model model) {
		RequestDTOPage<LogSmsDto> requestPage = new RequestDTOPage();
		Page<LogSmsDto> page = new Page(request, resp);
		requestPage.setObj(dto);
		requestPage.setPage(page);
		PageResult<LogSmsDto> pageResult = workflowService.findSmsList(requestPage);
		model.addAttribute("page", page);
		model.addAttribute("logSmsDto", dto);
		return "modules/bizconf/logSmsList";
	}
}
