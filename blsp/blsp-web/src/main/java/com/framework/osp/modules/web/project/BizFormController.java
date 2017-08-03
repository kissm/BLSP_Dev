package com.framework.osp.modules.web.project;

import com.framework.osp.common.web.BaseController;
import com.lpcode.modules.service.form.IFactoryFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pengs
 * @package com.framework.osp.modules.web.project
 * @fileName BizFormController
 * @date 16/4/28.
 */

@Controller
@RequestMapping(value = "${adminPath}/bizform")
public class BizFormController extends BaseController {
    @Autowired
    IFactoryFormService factoryFormService;

    @RequestMapping(value = "/detail")
    public String detail(HttpServletRequest request, HttpServletResponse response,Model model){
        String formCode = request.getParameter("formCode");
//        factoryFormService
        return null;
    }
}
