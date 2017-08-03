package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IRfYdjsBjspService;
import com.lpcode.modules.service.project.dto.pinstance.FormRfYdjsBjspVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

@Controller
@RequestMapping(value = "${adminPath}/rfydjs")
public class RfydjsController extends BaseController {
  

    @Autowired
    IRfYdjsBjspService IRfYdjsBjspService;
	@Autowired
	private ProjectServiceInf projectService;
    
    @RequestMapping(value = "/save")
	public String save(FormRfYdjsBjspVo formRfYdjsBjspVo,HttpServletRequest request, HttpServletResponse response, Model model) {

    	IRfYdjsBjspService.saveOrUpdateForm(formRfYdjsBjspVo);
		String id = formRfYdjsBjspVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/getTaskMap?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/getTaskMap?projectId="+id;
		}
	}
  
}
