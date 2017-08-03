package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IHbJsxmJjxmService;
import com.lpcode.modules.service.project.dto.pinstance.FormHbJsxmJjxmVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 环保：建设项目环境影响登记表（基建项目）   控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年7月13日 下午2:52:38
 */
@Controller
@RequestMapping(value = "${adminPath}/hbJsxmJjxm")
@Scope("prototype")
public class HbJsxmJjxmController extends BaseController {

	@Autowired
	private IHbJsxmJjxmService hbJsxmJjxmService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  建设项目环境影响登记表（基建项目）  表单  数据
	 * @param formHbJsxmJjxmVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(FormHbJsxmJjxmVo formHbJsxmJjxmVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		hbJsxmJjxmService.saveOrUpdateForm(formHbJsxmJjxmVo);
		String id = formHbJsxmJjxmVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/forms?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/forms?projectId="+id;
		}
	}
	
}
