package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IQxJgysSqsService;
import com.lpcode.modules.service.project.dto.pinstance.FormQxJgysSqsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 防雷装置竣工验收申请书   控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年6月22日 下午4:34:56
 */
@Controller
@RequestMapping(value = "${adminPath}/qxJgysSqs")
@Scope("prototype")
public class QxJgysSqsController extends BaseController {

	@Autowired
	private IQxJgysSqsService qxJgysSqsService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  防雷装置竣工验收申请书   表单  数据
	 * @param formQxJgysSqsVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(FormQxJgysSqsVo formQxJgysSqsVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		qxJgysSqsService.saveOrUpdateForm(formQxJgysSqsVo);
		String id = formQxJgysSqsVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/forms?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/forms?projectId="+id;
		}
	}
	
}
