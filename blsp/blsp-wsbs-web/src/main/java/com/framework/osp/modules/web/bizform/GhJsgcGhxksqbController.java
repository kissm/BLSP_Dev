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
import com.lpcode.modules.service.form.IGhJsgcGhxksqbService;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcGhxksqbSjVo;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcGhxksqbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 珠海市建设工程（建筑类）规划许可申请表   控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年6月4日 上午10:10:23
 */
@Controller
@RequestMapping(value = "${adminPath}/ghJsgcGhxksqb")
@Scope("prototype")
public class GhJsgcGhxksqbController extends BaseController {
	
	@Autowired
	private IGhJsgcGhxksqbService ghJsgcGhxksqbService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  珠海市建设工程（建筑类）规划许可申请表 表单  数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model) {
		String jsonDataObj = request.getParameter("jsonDataObj");
		FormGhJsgcGhxksqbVo formGhJsgcGhxksqbVo = JSONArray.parseObject(jsonDataObj, FormGhJsgcGhxksqbVo.class);
		String jsonDataDesList = request.getParameter("jsonDataDesList");
		if(jsonDataDesList != null && !"".equals(jsonDataDesList)){
			List<FormGhJsgcGhxksqbSjVo> sjList = JSONArray.parseArray(jsonDataDesList, FormGhJsgcGhxksqbSjVo.class);
			formGhJsgcGhxksqbVo.setDesList(sjList);
		}
		ghJsgcGhxksqbService.saveOrUpdateForm(formGhJsgcGhxksqbVo);
		String id = formGhJsgcGhxksqbVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/getTaskMap?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/getTaskMap?projectId="+id;
		}
	}

}
