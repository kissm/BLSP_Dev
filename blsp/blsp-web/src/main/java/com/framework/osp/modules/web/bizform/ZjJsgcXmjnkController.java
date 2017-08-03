package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IZjJsgcXmjnkService;
import com.lpcode.modules.service.project.dto.pinstance.FormZjJsgcXmjnkVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 金湾区建设工程项目节能卡  控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年5月31日 下午2:23:27
 */
@Controller
@RequestMapping(value = "${adminPath}/zjJsgcXmjnk")
@Scope("prototype")
public class ZjJsgcXmjnkController extends BaseController {

	@Autowired
	private IZjJsgcXmjnkService zjJsgcXmjnkService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  金湾区建设工程项目节能卡   表单  数据
	 * @param formZjJsgcXmjnkVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(FormZjJsgcXmjnkVo formZjJsgcXmjnkVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		zjJsgcXmjnkService.saveOrUpdateForm(formZjJsgcXmjnkVo);
		String id = formZjJsgcXmjnkVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/forms?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/forms?projectId="+id;
		}
	}
	
}
