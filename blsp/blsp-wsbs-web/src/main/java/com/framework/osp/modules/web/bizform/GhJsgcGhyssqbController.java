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
import com.lpcode.modules.service.form.IGhJsgcGhyssqbService;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcGhyssqbClVo;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcGhyssqbJgVo;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcGhyssqbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 珠海市建设工程（建筑类）规划验收申请表  控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年6月6日 下午4:00:00
 */
@Controller
@RequestMapping(value = "${adminPath}/ghJsgcGhyssqb")
@Scope("prototype")
public class GhJsgcGhyssqbController extends BaseController {
	
	@Autowired
	private IGhJsgcGhyssqbService ghJsgcGhyssqbService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  珠海市建设工程（建筑类）规划验收申请表 表单  数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model) {
		String jsonDataObj = request.getParameter("jsonDataObj");
		FormGhJsgcGhyssqbVo formGhJsgcGhyssqbVo = JSONArray.parseObject(jsonDataObj, FormGhJsgcGhyssqbVo.class);
		String jsonDataComList = request.getParameter("jsonDataComList");
		if(jsonDataComList != null && !"".equals(jsonDataComList)){
			List<FormGhJsgcGhyssqbJgVo> comList = JSONArray.parseArray(jsonDataComList, FormGhJsgcGhyssqbJgVo.class);
			formGhJsgcGhyssqbVo.setComList(comList);
		}
		String jsonDataMetList = request.getParameter("jsonDataMetList");
		if(jsonDataMetList != null && !"".equals(jsonDataMetList)){
			List<FormGhJsgcGhyssqbClVo> metList = JSONArray.parseArray(jsonDataMetList, FormGhJsgcGhyssqbClVo.class);
			formGhJsgcGhyssqbVo.setMetList(metList);
		}
		ghJsgcGhyssqbService.saveOrUpdateForm(formGhJsgcGhyssqbVo);
		String id = formGhJsgcGhyssqbVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/getTaskMap?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/getTaskMap?projectId="+id;
		}
	}

}
