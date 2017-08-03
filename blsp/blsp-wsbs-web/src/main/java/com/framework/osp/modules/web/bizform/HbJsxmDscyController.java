package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IHbJsxmDscyService;
import com.lpcode.modules.service.project.dto.pinstance.FormHbJsxmDscyVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 环保：建设项目环境影响登记表（第三产业）   控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年7月12日 上午11:06:45
 */
@Controller
@RequestMapping(value = "${adminPath}/hbJsxmDscy")
@Scope("prototype")
public class HbJsxmDscyController extends BaseController {
	
	@Autowired
	private IHbJsxmDscyService hbJsxmDscyService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  建设项目环境影响登记表（第三产业）  表单  数据
	 * @param formHbJsxmDscyVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(FormHbJsxmDscyVo formHbJsxmDscyVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		hbJsxmDscyService.saveOrUpdateForm(formHbJsxmDscyVo);
		String id = formHbJsxmDscyVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/getTaskMap?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/getTaskMap?projectId="+id;
		}
	}
	
}
