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
import com.lpcode.modules.service.form.IGhJsgcSzGhxkService;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcSzGhxkGxVo;
import com.lpcode.modules.service.project.dto.pinstance.FormGhJsgcSzGhxkVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 珠海市建设工程（市政类）规划许可申请表  控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年6月13日 下午6:18:38
 */
@Controller
@RequestMapping(value = "${adminPath}/ghJsgcSzGhxk")
@Scope("prototype")
public class GhJsgcSzGhxkController extends BaseController {

	@Autowired
	private IGhJsgcSzGhxkService ghJsgcSzGhxkService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  珠海市建设工程（市政类）规划许可申请表 表单  数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model) {
		String jsonDataObj = request.getParameter("jsonDataObj");
		FormGhJsgcSzGhxkVo formGhJsgcSzGhxkVo = JSONArray.parseObject(jsonDataObj, FormGhJsgcSzGhxkVo.class);
		String jsonDataPipList = request.getParameter("jsonDataPipList");
		if(jsonDataPipList != null && !"".equals(jsonDataPipList)){
			List<FormGhJsgcSzGhxkGxVo> gxList = JSONArray.parseArray(jsonDataPipList, FormGhJsgcSzGhxkGxVo.class);
			formGhJsgcSzGhxkVo.setPipList(gxList);
		}
		ghJsgcSzGhxkService.saveOrUpdateForm(formGhJsgcSzGhxkVo);
		String id = formGhJsgcSzGhxkVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/getTaskMap?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/getTaskMap?projectId="+id;
		}
	}
	
}
