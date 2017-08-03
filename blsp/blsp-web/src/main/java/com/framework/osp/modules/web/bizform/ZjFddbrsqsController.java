package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IZjFddbrsqsService;
import com.lpcode.modules.service.project.dto.pinstance.FormZjFddbrsqsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 法定代表人授权书  控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年5月31日 下午1:20:52
 */
@Controller
@RequestMapping(value = "${adminPath}/zjFddbrsqs")
@Scope("prototype")
public class ZjFddbrsqsController extends BaseController {

	@Autowired
	private IZjFddbrsqsService zjFddbrsqsService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  法定代表人授权书   表单  数据
	 * @param formZjGczlZszrcnsVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(FormZjFddbrsqsVo formZjFddbrsqsVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		zjFddbrsqsService.saveOrUpdateForm(formZjFddbrsqsVo);
		String id = formZjFddbrsqsVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/forms?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/forms?projectId="+id;
		}
	}
	
}
