package com.framework.osp.modules.web.bizform;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.ICsGcxmGsysjsService;
import com.lpcode.modules.service.project.dto.pinstance.FormCsGcxmGsysjsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 财审概算、预算、结算表格表单   控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2017年3月21日 下午3:00:41
 */
@Controller
@RequestMapping(value = "${adminPath}/csGcxmGsysjs")
@Scope("prototype")
public class CsGcxmGsysjsController extends BaseController {

	@Autowired
	private ICsGcxmGsysjsService csGcxmGsysjsService;
	
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  财审概算、预算、结算表格表单  数据
	 * @param formCsGcxmGsysjsVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(FormCsGcxmGsysjsVo formCsGcxmGsysjsVo, HttpServletRequest request, HttpServletResponse response, Model model) {
		csGcxmGsysjsService.saveOrUpdateForm(formCsGcxmGsysjsVo);
		String id = formCsGcxmGsysjsVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/forms?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/forms?projectId="+id;
		}
	}
	
}
