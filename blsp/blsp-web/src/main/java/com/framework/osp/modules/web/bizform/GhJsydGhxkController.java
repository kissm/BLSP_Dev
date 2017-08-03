package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IGhJsydGhxkService;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsydGhxkVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 珠海市建设用地规划许可申请表  控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年6月16日 下午6:11:28
 */
@Controller
@RequestMapping(value = "${adminPath}/ghJsydGhxk")
@Scope("prototype")
public class GhJsydGhxkController extends BaseController {

	@Autowired
	private IGhJsydGhxkService ghJsydGhxkService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  珠海市建设用地规划许可申请表   表单  数据
	 * @param formGhJsydGhxkVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(FormGhJsydGhxkVo formGhJsydGhxkVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		ghJsydGhxkService.saveOrUpdateForm(formGhJsydGhxkVo);
		String id = formGhJsydGhxkVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/forms?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/forms?projectId="+id;
		}
	}
	
}
