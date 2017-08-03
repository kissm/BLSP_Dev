package com.framework.osp.modules.web.bizform;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IQxSjshSqsService;
import com.lpcode.modules.service.project.dto.pinstance.FormQxSjshSqsDzVo;
import com.lpcode.modules.service.project.dto.pinstance.FormQxSjshSqsVo;
import com.lpcode.modules.service.project.dto.pinstance.FormQxSjshSqsYrVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 防雷装置设计审核申请书    控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年6月21日 上午9:59:44
 */
@Controller
@RequestMapping(value = "${adminPath}/qxSjshSqs")
@Scope("prototype")
public class QxSjshSqsController extends BaseController {

	@Autowired
	private IQxSjshSqsService qxSjshSqsService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存   防雷装置设计审核申请书    表单  数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model) {
		String jsonDataObj = request.getParameter("jsonDataObj");
		FormQxSjshSqsVo formQxSjshSqsVo = JSONArray.parseObject(jsonDataObj, FormQxSjshSqsVo.class);
		String jsonDataFlaList = request.getParameter("jsonDataFlaList");
		if(jsonDataFlaList != null && !"".equals(jsonDataFlaList)){
			List<FormQxSjshSqsYrVo> flaList = JSONArray.parseArray(jsonDataFlaList, FormQxSjshSqsYrVo.class);
			formQxSjshSqsVo.setFlaList(flaList);
		}
		String jsonDataEleList = request.getParameter("jsonDataEleList");
		if(jsonDataEleList != null && !"".equals(jsonDataEleList)){
			List<FormQxSjshSqsDzVo> eleList = JSONArray.parseArray(jsonDataEleList, FormQxSjshSqsDzVo.class);
			formQxSjshSqsVo.setEleList(eleList);
		}
		qxSjshSqsService.saveOrUpdateForm(formQxSjshSqsVo);
		String id = formQxSjshSqsVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/forms?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/forms?projectId="+id;
		}
	}
	
}
