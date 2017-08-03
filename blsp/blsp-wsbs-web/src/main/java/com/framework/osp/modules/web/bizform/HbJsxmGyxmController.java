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
import com.lpcode.modules.service.form.IHbJsxmGyxmService;
import com.lpcode.modules.service.project.dto.pinstance.FormHbJsxmGyxmClVo;
import com.lpcode.modules.service.project.dto.pinstance.FormHbJsxmGyxmCpVo;
import com.lpcode.modules.service.project.dto.pinstance.FormHbJsxmGyxmSbVo;
import com.lpcode.modules.service.project.dto.pinstance.FormHbJsxmGyxmVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 环保：建设项目环境影响登记表（工业项目）  控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年7月12日 下午5:28:28
 */
@Controller
@RequestMapping(value = "${adminPath}/hbJsxmGyxm")
@Scope("prototype")
public class HbJsxmGyxmController extends BaseController {

	@Autowired
	private IHbJsxmGyxmService hbJsxmGyxmService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存   建设项目环境影响登记表（工业项目）   表单  数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model) {
		String jsonDataObj = request.getParameter("jsonDataObj");
		FormHbJsxmGyxmVo formHbJsxmGyxmVo = JSONArray.parseObject(jsonDataObj, FormHbJsxmGyxmVo.class);
		String jsonDataProList = request.getParameter("jsonDataProList");
		if(jsonDataProList != null && !"".equals(jsonDataProList)){
			List<FormHbJsxmGyxmCpVo> proList = JSONArray.parseArray(jsonDataProList, FormHbJsxmGyxmCpVo.class);
			formHbJsxmGyxmVo.setProList(proList);
		}
		String jsonDataMetList = request.getParameter("jsonDataMetList");
		if(jsonDataMetList != null && !"".equals(jsonDataMetList)){
			List<FormHbJsxmGyxmClVo> metList = JSONArray.parseArray(jsonDataMetList, FormHbJsxmGyxmClVo.class);
			formHbJsxmGyxmVo.setMetList(metList);
		}
		String jsonDataEquList = request.getParameter("jsonDataEquList");
		if(jsonDataEquList != null && !"".equals(jsonDataEquList)){
			List<FormHbJsxmGyxmSbVo> equList = JSONArray.parseArray(jsonDataEquList, FormHbJsxmGyxmSbVo.class);
			formHbJsxmGyxmVo.setEquList(equList);
		}
		hbJsxmGyxmService.saveOrUpdateForm(formHbJsxmGyxmVo);
		String id = formHbJsxmGyxmVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/getTaskMap?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/getTaskMap?projectId="+id;
		}
	}
	
}
