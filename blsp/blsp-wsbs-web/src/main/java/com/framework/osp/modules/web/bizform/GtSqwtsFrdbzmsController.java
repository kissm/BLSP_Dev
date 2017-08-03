package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IGtSqwtsFrdbzmsService;
import com.lpcode.modules.service.project.dto.pinstance.FormGtSqwtsFrdbzmsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 授权委托书、法人代表证明书   控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年5月27日 下午3:13:52
 */
@Controller
@RequestMapping(value = "${adminPath}/gtSqwtsFrdbzms")
@Scope("prototype")
public class GtSqwtsFrdbzmsController extends BaseController {

	@Autowired
	private IGtSqwtsFrdbzmsService gtSqwtsFrdbzmsService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  授权委托书、法人代表证明书   表单  数据
	 * @param formGtSqwtsFrdbzmsVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(FormGtSqwtsFrdbzmsVo formGtSqwtsFrdbzmsVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		gtSqwtsFrdbzmsService.saveOrUpdateForm(formGtSqwtsFrdbzmsVo);
		String id = formGtSqwtsFrdbzmsVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/getTaskMap?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/getTaskMap?projectId="+id;
		}
	}
	
}
