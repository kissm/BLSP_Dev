package com.framework.osp.modules.web.project;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.impl.project.util.ProjectUtil;
import com.lpcode.modules.service.project.dto.Project;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * 项目进度查询
 * @project osp-wsbsblsp-web
 * @package com.framework.osp.modules.web.project
 * @author maxiaowei
 * @createDate 2016年7月27日 下午1:49:57
 */
@Controller
@RequestMapping(value = "${adminPath}/schedule")
public class ProjectScheduleController extends BaseController {
	
	@Autowired
	private ProjectServiceInf projectServiceInf;

	/**
	 * 项目基本信息是否存在
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/prjInfo")
	public void prjInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		String prjCode = request.getParameter("prjCode");
		PrjInstanceVo prjInstanceVo = new PrjInstanceVo();
		prjInstanceVo.setPrjCode(prjCode);
		//通过项目编号查询项目基本信息
		Boolean bo = projectServiceInf.checkIsHasPrjInf(prjInstanceVo);
		try {
			response.getWriter().print(bo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 到项目进度查询页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/progress")
	public String progress(HttpServletRequest request, HttpServletResponse response, Model model) {
		String prjCode = request.getParameter("prjCode");
		PrjInstanceVo prjInstanceVo = new PrjInstanceVo();
		prjInstanceVo.setPrjCode(prjCode);
		model.addAttribute("prjIn", prjInstanceVo);
		return "modules/project/progress";
	}
	
	/**
	 * 获取项目进度
	 * @param prjInstanceVo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/progressInfo")
	public void progressInfo(PrjInstanceVo prjInstanceVo,HttpServletRequest request, HttpServletResponse response, Model model) {
		//通过项目编号（建设单位）查询项目基本信息
		prjInstanceVo = projectServiceInf.getPrjInstanceVoByPrj(prjInstanceVo);
		if(prjInstanceVo.getId() == null || "".equals(prjInstanceVo.getId())){
			try {
				response.getWriter().print("0");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				response.getWriter().print(prjInstanceVo.getId());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 到事项进度页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/progressTask")
	public String progressTask(HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = request.getParameter("id");
		PrjInstanceVo prjInstanceVo = new PrjInstanceVo();
		if(id == null || "".equals(id)){
			String prjCode = request.getParameter("prjCode");
			prjInstanceVo.setPrjCode(prjCode);
			model.addAttribute("prjCode", prjCode);
			prjInstanceVo = projectServiceInf.getPrjInstanceVoByPrj(prjInstanceVo);
		}else{
			prjInstanceVo.setId(Long.valueOf(id));
		}
		Project project = projectServiceInf.getProjectByPrjId(prjInstanceVo.getId());
		project.setPrjStageVo(ProjectUtil.getStageInstanceByProId(prjInstanceVo.getId()));
		project.setPrjStageVoList(ProjectUtil.getStageInsListByProId(prjInstanceVo.getId().toString()));
		model.addAttribute("project", project);
		return "modules/project/progressTasks";
	}
	
}
