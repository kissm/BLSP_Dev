package com.framework.osp.modules.web.project;

import com.framework.core.result.RequestDTOPage;
import com.framework.osp.common.persistence.Page;
import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.project.dto.WsbsProjInstanceDto;
import com.lpcode.modules.service.project.dto.pinstance.PrjInstanceVo;
import com.lpcode.modules.service.project.inf.WsbsPrjInstanceServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pengs
 * @package com.framework.osp.modules.web.project
 * @fileName ProjectPlusAddController
 * @date 16/8/11.
 */

@Controller
@RequestMapping(value = "${adminPath}/project/plusadd/")
public class ProjectPlusAddController extends BaseController {

    @Autowired
    WsbsPrjInstanceServiceInf service;

    /**
     * 选择并联审批项目信息列表
     *
     * @param request
     * @param response
     * @param blspPrjInstancVo
     * @param model
     * @return
     */
    @RequestMapping(value = "/getBlspList")
    public String getBlspList(HttpServletRequest request, HttpServletResponse response, PrjInstanceVo blspPrjInstancVo, Model model) {
        String prjType = request.getParameter("prjType");
        blspPrjInstancVo.setPrjType(prjType);
        RequestDTOPage<PrjInstanceVo> requestPage = new RequestDTOPage<PrjInstanceVo>();
        Page<PrjInstanceVo> queryPage = new Page<PrjInstanceVo>(request, response);
        requestPage.setObj(blspPrjInstancVo);
        requestPage.setPage(queryPage);
        service.blspPrjList(requestPage, blspPrjInstancVo);
        model.addAttribute("page", queryPage);
        model.addAttribute("blspPrjInstanc", blspPrjInstancVo);
        model.addAttribute("prjType", prjType);
        return "modules/project/accept/blsplist";
    }

    /**
     * 选择一个并联审批的项目
     * @param request
     * @param response
     * @param blspPrjInstancVo
     * @param model
     * @return
     */
    @RequestMapping(value = "/getBlspPrjInstance")
    public String getBlspPrjInstance(HttpServletRequest request, HttpServletResponse response, PrjInstanceVo blspPrjInstancVo, Model model) {
        Long id = blspPrjInstancVo.getId();
        blspPrjInstancVo = service.blspPrjInstanceById(id);
        model.addAttribute("blspPrjInstanc", blspPrjInstancVo);
        return "modules/project/accept/blspPrj";
    }

    /**
     * 选择网厅项目信息列表
     *
     * @param request
     * @param response
     * @param wsbsProjInstanceDto
     * @param model
     * @return
     */
    @RequestMapping(value = "/getWsbsList")
    public String getWsbsList(HttpServletRequest request, HttpServletResponse response, WsbsProjInstanceDto wsbsProjInstanceDto, Model model) {
        RequestDTOPage<WsbsProjInstanceDto> requestPage = new RequestDTOPage<WsbsProjInstanceDto>();
        Page<WsbsProjInstanceDto> queryPage = new Page<WsbsProjInstanceDto>(request, response);
        requestPage.setObj(wsbsProjInstanceDto);
        requestPage.setPage(queryPage);
        service.wsbsPrjListByCompanyCode(requestPage);
        model.addAttribute("page", queryPage);
        model.addAttribute("wsbsProjInstanceDto", wsbsProjInstanceDto);
        return "modules/project/accept/wsbslist";
    }

    /**
     * 选择一个网厅的
     * @param request
     * @param response
     * @param wsbsProjInstanceDto
     * @param model
     * @return
     */
    @RequestMapping(value = "/getWsbsPrjInstance")
    public String getWsbsPrjInstance(HttpServletRequest request, HttpServletResponse response, WsbsProjInstanceDto wsbsProjInstanceDto, Model model) {
        String id = wsbsProjInstanceDto.getId();
        wsbsProjInstanceDto = service.wsbsPrjInstanceById(id);
        model.addAttribute("wsbsProjInstanceDto", wsbsProjInstanceDto);
        return "modules/project/accept/wsbsPrj";
    }


    /**
     * 选择并联审批项目信息列表
     *
     * @param request
     * @param response
     * @param blspPrjInstancVo
     * @param model
     * @return
     */
    @RequestMapping(value = "/getOldBlspList")
    public String getOldBlspList(HttpServletRequest request, HttpServletResponse response, PrjInstanceVo blspPrjInstancVo, Model model) {
        String prjType = request.getParameter("prjType");
        String prjCode = request.getParameter("prjCode");
        blspPrjInstancVo.setPrjType(prjType);
        blspPrjInstancVo.setPrjCode(prjCode);
        RequestDTOPage<PrjInstanceVo> requestPage = new RequestDTOPage<PrjInstanceVo>();
        Page<PrjInstanceVo> queryPage = new Page<PrjInstanceVo>(request, response);
        requestPage.setObj(blspPrjInstancVo);
        requestPage.setPage(queryPage);
        service.blspPrjList(requestPage, blspPrjInstancVo);
        model.addAttribute("page", queryPage);
        model.addAttribute("blspPrjInstanc", blspPrjInstancVo);
        model.addAttribute("prjType", prjType);
        return "modules/project/pretrial/blspOldList";
    }

}
