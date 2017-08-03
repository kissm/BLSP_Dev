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
import com.lpcode.modules.service.form.IZjSgxkSqbService;
import com.lpcode.modules.service.project.dto.pinstance.FormZjSgxkSqbJlVo;
import com.lpcode.modules.service.project.dto.pinstance.FormZjSgxkSqbJsVo;
import com.lpcode.modules.service.project.dto.pinstance.FormZjSgxkSqbSgVo;
import com.lpcode.modules.service.project.dto.pinstance.FormZjSgxkSqbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 珠海市房屋建筑和市政基础设施工程施工许可并联审批申请表   控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年6月24日 下午2:26:45
 */
@Controller
@RequestMapping(value = "${adminPath}/zjSgxkSqb")
@Scope("prototype")
public class ZjSgxkSqbController extends BaseController {

	@Autowired
	private IZjSgxkSqbService zjSgxkSqbService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存   珠海市房屋建筑和市政基础设施工程施工许可并联审批申请表   表单  数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model) {
		String jsonDataObj = request.getParameter("jsonDataObj");
		FormZjSgxkSqbVo formZjSgxkSqbVo = JSONArray.parseObject(jsonDataObj, FormZjSgxkSqbVo.class);
		String jsonDataBuiList = request.getParameter("jsonDataBuiList");
		if(jsonDataBuiList != null && !"".equals(jsonDataBuiList)){
			List<FormZjSgxkSqbJsVo> buiList = JSONArray.parseArray(jsonDataBuiList, FormZjSgxkSqbJsVo.class);
			formZjSgxkSqbVo.setBuiList(buiList);
		}
		String jsonDataConList = request.getParameter("jsonDataConList");
		if(jsonDataConList != null && !"".equals(jsonDataConList)){
			List<FormZjSgxkSqbSgVo> conList = JSONArray.parseArray(jsonDataConList, FormZjSgxkSqbSgVo.class);
			formZjSgxkSqbVo.setConList(conList);
		}
		String jsonDataSupList = request.getParameter("jsonDataSupList");
		if(jsonDataSupList != null && !"".equals(jsonDataSupList)){
			List<FormZjSgxkSqbJlVo> supList = JSONArray.parseArray(jsonDataSupList, FormZjSgxkSqbJlVo.class);
			formZjSgxkSqbVo.setSupList(supList);
		}
		zjSgxkSqbService.saveOrUpdateForm(formZjSgxkSqbVo);
		String id = formZjSgxkSqbVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/forms?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/forms?projectId="+id;
		}
	}
	
}
