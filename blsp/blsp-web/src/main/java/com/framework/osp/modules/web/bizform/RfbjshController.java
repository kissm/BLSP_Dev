package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IRfBjshService;
import com.lpcode.modules.service.project.dto.pinstance.FormRfBjshVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

@Controller
@RequestMapping(value = "${adminPath}/rfbjsh")
public class RfbjshController extends BaseController {
  

    @Autowired
    IRfBjshService IRfBjshService;
	@Autowired
	private ProjectServiceInf projectService;
    
    @RequestMapping(value = "/save")
	public String save(FormRfBjshVo formRfBjshVo,HttpServletRequest request, HttpServletResponse response, Model model) {

    	IRfBjshService.saveOrUpdateForm(formRfBjshVo);
		String id = formRfBjshVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/forms?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/forms?projectId="+id;
		}
	}
  
}
