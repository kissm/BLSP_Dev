package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IZjSgqyNmggzService;
import com.lpcode.modules.service.project.dto.pinstance.FormZjSgqyNmggzVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 珠海市金湾区施工企业农民工工资支付情况报表  控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年5月30日 下午6:39:57
 */
@Controller
@RequestMapping(value = "${adminPath}/zjSgqyNmggz")
@Scope("prototype")
public class ZjSgqyNmggzController extends BaseController {

	@Autowired
	private IZjSgqyNmggzService zjSgqyNmggzService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  珠海市金湾区施工企业农民工工资支付情况报表  表单  数据
	 * @param formZjSgqyNmggzVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(FormZjSgqyNmggzVo formZjSgqyNmggzVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		zjSgqyNmggzService.saveOrUpdateForm(formZjSgqyNmggzVo);
		String id = formZjSgqyNmggzVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/getTaskMap?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/getTaskMap?projectId="+id;
		}
	}
	
}
