package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IQxSqsService;
import com.lpcode.modules.service.project.dto.pinstance.FormQxSqsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 防雷装置设计审核（授权书）  控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年6月21日 下午6:09:34
 */
@Controller
@RequestMapping(value = "${adminPath}/qxSqs")
@Scope("prototype")
public class QxSqsController extends BaseController {

	@Autowired
	private IQxSqsService qxSqsService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  防雷装置设计审核（授权书）   表单  数据
	 * @param formQxSqsVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(FormQxSqsVo formQxSqsVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		qxSqsService.saveOrUpdateForm(formQxSqsVo);
		String id = formQxSqsVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/getTaskMap?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/getTaskMap?projectId="+id;
		}
	}
	
}
