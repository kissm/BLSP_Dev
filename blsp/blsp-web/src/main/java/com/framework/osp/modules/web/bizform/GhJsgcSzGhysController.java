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
import com.lpcode.modules.service.form.IGhJsgcSzGhysService;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcSzGhysClVo;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcSzGhysVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 珠海市建设工程（市政类）规划验收申请表   控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年6月15日 下午3:14:03
 */
@Controller
@RequestMapping(value = "${adminPath}/ghJsgcSzGhys")
@Scope("prototype")
public class GhJsgcSzGhysController extends BaseController {

	@Autowired
	private IGhJsgcSzGhysService ghJsgcSzGhysService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  珠海市建设工程（市政类）规划验收申请表  表单  数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model) {
		String jsonDataObj = request.getParameter("jsonDataObj");
		FormGhJsgcSzGhysVo formGhJsgcSzGhysVo = JSONArray.parseObject(jsonDataObj, FormGhJsgcSzGhysVo.class);
		String jsonDataMetList = request.getParameter("jsonDataMetList");
		if(jsonDataMetList != null && !"".equals(jsonDataMetList)){
			List<FormGhJsgcSzGhysClVo> metList = JSONArray.parseArray(jsonDataMetList, FormGhJsgcSzGhysClVo.class);
			formGhJsgcSzGhysVo.setMetList(metList);
		}
		ghJsgcSzGhysService.saveOrUpdateForm(formGhJsgcSzGhysVo);
		String id = formGhJsgcSzGhysVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/forms?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/forms?projectId="+id;
		}
	}
	
}
