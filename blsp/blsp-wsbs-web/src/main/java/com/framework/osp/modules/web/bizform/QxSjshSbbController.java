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
import com.lpcode.modules.service.form.IQxSjshSbbService;
import com.lpcode.modules.service.project.dto.pinstance.FormQxSjshSbbJzVo;
import com.lpcode.modules.service.project.dto.pinstance.FormQxSjshSbbVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 防雷装置设计审核申报表   控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年6月21日 下午2:34:47
 */
@Controller
@RequestMapping(value = "${adminPath}/qxSjshSbb")
@Scope("prototype")
public class QxSjshSbbController extends BaseController {

	@Autowired
	private IQxSjshSbbService qxSjshSbbService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存   防雷装置设计审核申报表    表单  数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, HttpServletResponse response, Model model) {
		String jsonDataObj = request.getParameter("jsonDataObj");
		FormQxSjshSbbVo formQxSjshSbbVo = JSONArray.parseObject(jsonDataObj, FormQxSjshSbbVo.class);
		String jsonDataBuiList = request.getParameter("jsonDataBuiList");
		if(jsonDataBuiList != null && !"".equals(jsonDataBuiList)){
			List<FormQxSjshSbbJzVo> buiList = JSONArray.parseArray(jsonDataBuiList, FormQxSjshSbbJzVo.class);
			formQxSjshSbbVo.setBuiList(buiList);
		}
		qxSjshSbbService.saveOrUpdateForm(formQxSjshSbbVo);
		String id = formQxSjshSbbVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/getTaskMap?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/getTaskMap?projectId="+id;
		}
	}
	
}
