package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IQxPjjcWtsService;
import com.lpcode.modules.service.project.dto.pinstance.FormQxPjjcWtsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 防雷装置设计技术评价和检测服务委托书   控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年6月22日 下午1:59:19
 */
@Controller
@RequestMapping(value = "${adminPath}/qxPjjcWts")
@Scope("prototype")
public class QxPjjcWtsController extends BaseController {

	@Autowired
	private IQxPjjcWtsService qxPjjcWtsService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  防雷装置设计技术评价和检测服务委托书   表单  数据
	 * @param formQxPjjcWtsVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(FormQxPjjcWtsVo formQxPjjcWtsVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		qxPjjcWtsService.saveOrUpdateForm(formQxPjjcWtsVo);
		String id = formQxPjjcWtsVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/getTaskMap?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/getTaskMap?projectId="+id;
		}
	}
	
}
