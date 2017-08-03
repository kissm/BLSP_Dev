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
import com.lpcode.modules.service.form.IXfJsgcSjshsbbService;
import com.lpcode.modules.service.project.dto.pinstance.FormXfJsgcSjshsbbDwVo;
import com.lpcode.modules.service.project.dto.pinstance.FormXfJsgcSjshsbbJzVo;
import com.lpcode.modules.service.project.dto.pinstance.FormXfJsgcSjshsbbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 建设工程消防设计审核申报表  控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年5月25日 上午11:11:41
 */
@Controller
@RequestMapping(value = "${adminPath}/xfJsgcSjshsbb")
@Scope("prototype")
public class XfJsgcSjshsbbController extends BaseController {

	@Autowired
	private IXfJsgcSjshsbbService xfJsgcSjshsbbService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  建设工程消防设计审核申报表 表单  数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model) {
		String jsonDataObj = request.getParameter("jsonDataObj");
		FormXfJsgcSjshsbbVo formXfJsgcSjshsbbVo = JSONArray.parseObject(jsonDataObj, FormXfJsgcSjshsbbVo.class);
		String jsonDataUnitList = request.getParameter("jsonDataUnitList");
		if(jsonDataUnitList != null && !"".equals(jsonDataUnitList)){
			List<FormXfJsgcSjshsbbDwVo> dwList = JSONArray.parseArray(jsonDataUnitList, FormXfJsgcSjshsbbDwVo.class);
			formXfJsgcSjshsbbVo.setDwList(dwList);
		}
		String jsonDataBuildList = request.getParameter("jsonDataBuildList");
		if(jsonDataBuildList != null && !"".equals(jsonDataBuildList)){
			List<FormXfJsgcSjshsbbJzVo> jzList = JSONArray.parseArray(jsonDataBuildList, FormXfJsgcSjshsbbJzVo.class);
			formXfJsgcSjshsbbVo.setJzList(jzList);
		}
		xfJsgcSjshsbbService.saveOrUpdateForm(formXfJsgcSjshsbbVo);
		String id = formXfJsgcSjshsbbVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/forms?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/forms?projectId="+id;
		}
	}
	
}
