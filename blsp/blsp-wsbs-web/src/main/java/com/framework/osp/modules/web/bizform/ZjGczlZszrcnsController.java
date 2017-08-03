package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IZjGczlZszrcnsService;
import com.lpcode.modules.service.project.dto.pinstance.FormZjGczlZszrcnsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 工程质量终身责任承诺书  控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年5月31日 上午10:53:03
 */
@Controller
@RequestMapping(value = "${adminPath}/zjGczlZszrcns")
@Scope("prototype")
public class ZjGczlZszrcnsController extends BaseController {

	@Autowired
	private IZjGczlZszrcnsService zjGczlZszrcnsService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  工程质量终身责任承诺书   表单  数据
	 * @param formZjGczlZszrcnsVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(FormZjGczlZszrcnsVo formZjGczlZszrcnsVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		zjGczlZszrcnsService.saveOrUpdateForm(formZjGczlZszrcnsVo);
		String id = formZjGczlZszrcnsVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/getTaskMap?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/getTaskMap?projectId="+id;
		}
	}
	
}
