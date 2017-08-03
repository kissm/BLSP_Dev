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
import com.lpcode.modules.service.form.IGtJsydSqbService;
import com.lpcode.modules.service.project.dto.pinstance.FormGtJsydSqbJsVo;
import com.lpcode.modules.service.project.dto.pinstance.FormGtJsydSqbVo;
import com.lpcode.modules.service.project.dto.pinstance.FormGtJsydSqbZlVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 珠海市建设用地申请表  控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年6月20日 上午11:37:35
 */
@Controller
@RequestMapping(value = "${adminPath}/gtJsydSqb")
@Scope("prototype")
public class GtJsydSqbController extends BaseController {
	
	@Autowired
	private IGtJsydSqbService gtJsydSqbService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  珠海市建设用地申请表  表单  数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model) {
		String jsonDataObj = request.getParameter("jsonDataObj");
		FormGtJsydSqbVo formGtJsydSqbVo = JSONArray.parseObject(jsonDataObj, FormGtJsydSqbVo.class);
		String jsonDataBuiList = request.getParameter("jsonDataBuiList");
		if(jsonDataBuiList != null && !"".equals(jsonDataBuiList)){
			List<FormGtJsydSqbJsVo> buiList = JSONArray.parseArray(jsonDataBuiList, FormGtJsydSqbJsVo.class);
			formGtJsydSqbVo.setBuiList(buiList);
		}
		String jsonDataMetList = request.getParameter("jsonDataMetList");
		if(jsonDataMetList != null && !"".equals(jsonDataMetList)){
			List<FormGtJsydSqbZlVo> metList = JSONArray.parseArray(jsonDataMetList, FormGtJsydSqbZlVo.class);
			formGtJsydSqbVo.setMetList(metList);
		}
		gtJsydSqbService.saveOrUpdateForm(formGtJsydSqbVo);
		String id = formGtJsydSqbVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/forms?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/forms?projectId="+id;
		}
	}
	
}
