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
import com.lpcode.modules.service.form.IRfJsxmqkbService;
import com.lpcode.modules.service.project.dto.pinstance.FormRfJsxmqkbQkVo;
import com.lpcode.modules.service.project.dto.pinstance.FormRfJsxmqkbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 建设项目情况表
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @updateAuthor maxiaowei
 * @createDate 2016年6月29日 下午4:54:34
 */
@Controller
@RequestMapping(value = "${adminPath}/rfJsxmqkb")
@Scope("prototype")
public class RfJsxmqkbController extends BaseController {
	
	@Autowired
	private IRfJsxmqkbService rfJsxmqkbService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  建设项目情况表 表单  数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model) {
		String jsonDataObj = request.getParameter("jsonDataObj");
		FormRfJsxmqkbVo formRfJsxmqkbVo = JSONArray.parseObject(jsonDataObj, FormRfJsxmqkbVo.class);
		String jsonDataConList = request.getParameter("jsonDataConList");
		if(jsonDataConList != null && !"".equals(jsonDataConList)){
			List<FormRfJsxmqkbQkVo> conList = JSONArray.parseArray(jsonDataConList, FormRfJsxmqkbQkVo.class);
			formRfJsxmqkbVo.setConList(conList);
		}
		rfJsxmqkbService.saveOrUpdateForm(formRfJsxmqkbVo);
		String id = formRfJsxmqkbVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/getTaskMap?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/getTaskMap?projectId="+id;
		}
	}
	
}
