package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IQxSjjsPjxxService;
import com.lpcode.modules.service.project.dto.pinstance.FormQxSjjsPjxxVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 防雷装置设计技术评价信息表   控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年6月22日 上午11:05:00
 */
@Controller
@RequestMapping(value = "${adminPath}/qxSjjsPjxx")
@Scope("prototype")
public class QxSjjsPjxxController extends BaseController {

	@Autowired
	private IQxSjjsPjxxService qxSjjsPjxxService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  防雷装置设计技术评价信息表   表单  数据
	 * @param formQxSjjsPjxxVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(FormQxSjjsPjxxVo formQxSjjsPjxxVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		qxSjjsPjxxService.saveOrUpdateForm(formQxSjjsPjxxVo);
		String id = formQxSjjsPjxxVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/forms?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/forms?projectId="+id;
		}
	}
	
}
