package com.framework.osp.modules.web.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.core.result.PageResult;
import com.framework.core.result.RequestDTOPage;
import com.framework.core.result.Result;
import com.framework.osp.common.persistence.Page;
import com.lpcode.modules.service.project.dto.PrjAppointmentListDTO;
import com.lpcode.modules.service.project.dto.PrjAppointmentQueryDTO;
import com.lpcode.modules.service.project.inf.PrjAppointmentService;

/**
 * 我的待办Controller
 * 
 * @author lpcode
 *
 */

@Controller
@RequestMapping(value = "${adminPath}/prj/appointment")
public class PrjAppointmentController {
	@Autowired
	private PrjAppointmentService service;

	@RequestMapping(value = "/list")
	public String findPage(PrjAppointmentQueryDTO dto, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		RequestDTOPage<PrjAppointmentQueryDTO> requestPage = new RequestDTOPage<>();
		Page<PrjAppointmentQueryDTO> queryPage = new Page<PrjAppointmentQueryDTO>(request, response);
		requestPage.setObj(dto);
		requestPage.setPage(queryPage);
		PageResult<PrjAppointmentListDTO> resultPage = service.findPage(requestPage);

		Page<PrjAppointmentListDTO> page = new Page<PrjAppointmentListDTO>();
		page.setPageNo(resultPage.getObj().getPageNo());
		page.setPageSize(resultPage.getObj().getPageSize());
		page.setCount(resultPage.getObj().getCount());
		page.setList(resultPage.getObj().getList());
		model.addAttribute("page", page);
		return "modules/project/prjAppointmentList";
	}

	@RequestMapping(value = "/detail")
	public String findDetail(String certCode, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		PrjAppointmentListDTO dto = new PrjAppointmentListDTO();
		if (StringUtils.isNotBlank(certCode)) {
			Result<PrjAppointmentListDTO> result = service.findByCertCode(certCode);
			dto = result.getObj();
		}
		
		if (StringUtils.isNotBlank(certCode) && StringUtils.isBlank(dto.getAppintSeq())){
			model.addAttribute("message2", "没有查到该证件号码对应的预约信息。");
		}
		model.addAttribute("dto", dto);
		return "modules/project/prjAppointmentDetail";
	}
	
	@RequestMapping(value = "/form")
	public String findForm(String certCode, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		PrjAppointmentListDTO dto = new PrjAppointmentListDTO();
		if (StringUtils.isNotBlank(certCode)) {
			Result<PrjAppointmentListDTO> result = service.findByCertCode(certCode);
			dto = result.getObj();
		} 
		if (StringUtils.isBlank(dto.getAppintSeq())) {
			model.addAttribute("message2", "没有查到该证件号码对应的预约信息。");
		}
		model.addAttribute("dto", dto);
		return "modules/project/prjAppointmentForm";
	}
}
