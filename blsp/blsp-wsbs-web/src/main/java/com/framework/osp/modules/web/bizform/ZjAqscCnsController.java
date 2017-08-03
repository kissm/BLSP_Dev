package com.framework.osp.modules.web.bizform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IZjAqscCnsService;
import com.lpcode.modules.service.project.dto.pinstance.FormZjAqscCnsVo;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 珠海市建设工程项目安全生产文明施工目标管理责任承诺书   控制层
 * @project osp-blsp-web
 * @package com.framework.osp.modules.web.bizform
 * @author maxiaowei
 * @createDate 2016年6月23日 下午1:25:31
 */
@Controller
@RequestMapping(value = "${adminPath}/zjAqscCns")
@Scope("prototype")
public class ZjAqscCnsController extends BaseController {

	@Autowired
	private IZjAqscCnsService zjAqscCnsService;
	@Autowired
	private ProjectServiceInf projectService;
	
	/**
	 * 保存  珠海市建设工程项目安全生产文明施工目标管理责任承诺书   表单  数据
	 * @param formZjGczlZszrcnsVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public String save(FormZjAqscCnsVo formZjAqscCnsVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		zjAqscCnsService.saveOrUpdateForm(formZjAqscCnsVo);
		String id = formZjAqscCnsVo.getPrjId().toString();
		PrjInstanceVo prjInstanceVo = projectService.getPrjInstanceVoById(Long.valueOf(id));
		if(prjInstanceVo.getPrjType().equals("1")){			
			return "redirect:" + adminPath + "/project/accpet/getTaskMap?projectId="+id;
		}else{
			return "redirect:" + adminPath + "/project/bizaccept/getTaskMap?projectId="+id;
		}
	}
	
}
