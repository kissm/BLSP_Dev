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
import com.lpcode.modules.service.form.IZjJsgcZljdzcbService;
import com.lpcode.modules.service.project.dto.pinstance.FormZjJsgcZljdzcbJlVo;
import com.lpcode.modules.service.project.dto.pinstance.FormZjJsgcZljdzcbJsVo;
import com.lpcode.modules.service.project.dto.pinstance.FormZjJsgcZljdzcbSgVo;
import com.lpcode.modules.service.project.dto.pinstance.FormZjJsgcZljdzcbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 建设工程质量监督注册表  控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年5月31日 下午6:27:47
 */
@Controller
@RequestMapping(value = "${adminPath}/zjJsgcZljdzcb")
@Scope("prototype")
public class ZjJsgcZljdzcbController extends BaseController {

	@Autowired
	private IZjJsgcZljdzcbService zjJsgcZljdzcbService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  建设工程质量监督注册表  数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model) {
		String jsonDataObj = request.getParameter("jsonDataObj");
		FormZjJsgcZljdzcbVo formZjJsgcZljdzcbVo = JSONArray.parseObject(jsonDataObj, FormZjJsgcZljdzcbVo.class);
		String jsonDataBuiList = request.getParameter("jsonDataBuiList");
		if(jsonDataBuiList != null && !"".equals(jsonDataBuiList)){
			List<FormZjJsgcZljdzcbJsVo> buiList = JSONArray.parseArray(jsonDataBuiList, FormZjJsgcZljdzcbJsVo.class);
			formZjJsgcZljdzcbVo.setBuiList(buiList);
		}
		String jsonDataConList = request.getParameter("jsonDataConList");
		if(jsonDataConList != null && !"".equals(jsonDataConList)){
			List<FormZjJsgcZljdzcbSgVo> conList = JSONArray.parseArray(jsonDataConList, FormZjJsgcZljdzcbSgVo.class);
			formZjJsgcZljdzcbVo.setConList(conList);
		}
		String jsonDataSupList = request.getParameter("jsonDataSupList");
		if(jsonDataSupList != null && !"".equals(jsonDataSupList)){
			List<FormZjJsgcZljdzcbJlVo> supList = JSONArray.parseArray(jsonDataSupList, FormZjJsgcZljdzcbJlVo.class);
			formZjJsgcZljdzcbVo.setSupList(supList);
		}
		zjJsgcZljdzcbService.saveOrUpdateForm(formZjJsgcZljdzcbVo);
		String id = formZjJsgcZljdzcbVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/forms?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/forms?projectId="+id;
		}
	}
	
}
