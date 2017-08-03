package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IRfYdjsJgsbService;
import com.lpcode.modules.service.project.dto.pinstance.FormRfYdjsJgsbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

@Controller
@RequestMapping(value = "${adminPath}/rfydjsjgsb")
public class RfYdjsJgsbController extends BaseController {
	
	@Autowired
	IRfYdjsJgsbService iRfYdjsJgsbService;
	@Autowired
	private ProjectServiceInf projectService;
	
	@RequestMapping(value = "/save")
	public String save(FormRfYdjsJgsbVo formRfYdjsJgsbVo,HttpServletRequest request, HttpServletResponse response, Model model) {

		iRfYdjsJgsbService.saveOrUpdateForm(formRfYdjsJgsbVo);
		String id = formRfYdjsJgsbVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/forms?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/forms?projectId="+id;
		}
	}

}
