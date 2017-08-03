package com.framework.osp.modules.web.buildcompany;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.framework.core.result.PageResult;
import com.framework.core.result.RequestDTOPage;
import com.framework.osp.common.persistence.Page;
import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.buildcompany.dto.BuildCompanyDto;
import com.lpcode.modules.service.buildcompany.inf.IBuildCompanyService;
import com.lpcode.modules.service.project.dto.PrjTaskPauseTimesDTO;
import com.lpcode.modules.service.project.dto.Project;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.PrjTaskService;
import com.lpcode.modules.service.project.inf.ProjectServiceInf;

/**
 * @author Pengs
 * @package com.framework.osp.modules.web.buildcompany
 * @fileName BuildCompanyContraller
 * @date 16/4/22.
 */
@Controller
@RequestMapping(value = "${adminPath}/build")
public class BuildCompanyContraller extends BaseController {
    @Autowired
    IBuildCompanyService buildCompanyService;
    @Autowired
    private ProjectServiceInf projectServiceInf;
    @Autowired
    private PrjTaskService prjTaskService;

    @ModelAttribute("buildCompanyDto")
    public BuildCompanyDto get(@RequestParam(value="id",required=false)Long id) {
        if (null!=id){
            return buildCompanyService.getBuildCompanyById(id);
        }else {
            return new BuildCompanyDto();
        }
    }

    @RequestMapping(value = "/list")
    public String findList(BuildCompanyDto buildCompanyDto,HttpServletRequest request, HttpServletResponse resp,Model model) {
        RequestDTOPage<BuildCompanyDto> requestPage = new RequestDTOPage<BuildCompanyDto>();
        Page<BuildCompanyDto> page = new Page<BuildCompanyDto>(request,resp);
        requestPage.setObj(buildCompanyDto);
        requestPage.setPage(page);
        PageResult<BuildCompanyDto> pageResult  = buildCompanyService.findList(requestPage);
        model.addAttribute("page", page);
        return "modules/build/buildList";
    }

    @RequestMapping(value = "buildPrjqueryList")
    public String quyerList(Project project, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Project> page = new Page<Project>();
        String pageNo = request.getParameter("pageNo");
        if (!StringUtils.isBlank(pageNo)) {
            page.setPageNo(Integer.parseInt(pageNo));
        } else {
            page.setPageNo(1);
        }
        String pageSize = request.getParameter("pageSize");
        if (!StringUtils.isBlank(pageSize)) {
            page.setPageSize(Integer.parseInt(pageSize));
        } else {
            page.setPageSize(10);
        }
        page.setOrderBy("creat_Time desc");
        PrjInstanceVo insvo = project.getPrjInstanceVo();
        if (insvo == null) {
            insvo = new PrjInstanceVo();
            insvo.setPrjType("3");// 政府 And 企业
            project.setPrjInstanceVo(insvo);
        }
        insvo.setPauseTimesForMater(99);;// 建设单位监控查询标记
        projectServiceInf.getProjectPage(project, page);
        model.addAttribute("page", page);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("project", project);
        return "modules/build/buildPrjqueryList";
    }
    
	@RequestMapping(value = "/pauseTask/form")
	public String getPauseTask(HttpServletRequest request, Long prjInsId, Model model) {
		if (prjInsId != null) {
			List<PrjTaskPauseTimesDTO> dtos = prjTaskService.findPauseForMater(prjInsId).getObj();
			model.addAttribute("list", dtos);
		} 
		return "modules/build/pauseTask";
	}
}
