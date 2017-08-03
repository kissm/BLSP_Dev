package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IRfJgyszhbService;
import com.lpcode.modules.service.project.dto.pinstance.FormRfJgyszhbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

@Controller
@RequestMapping(value = "${adminPath}/rfjgyszhb")
public class RfJgyszhbController extends BaseController {
	
	@Autowired
	IRfJgyszhbService iRfJgyszhbService;
	@Autowired
	private ProjectServiceInf projectService;
	
	@RequestMapping(value = "/save")
	public String save(FormRfJgyszhbVo formRfJgyszhbVo,HttpServletRequest request, HttpServletResponse response, Model model) {

		iRfJgyszhbService.saveOrUpdateForm(formRfJgyszhbVo);
		String id = formRfJgyszhbVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/forms?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/forms?projectId="+id;
		}
	}

}
