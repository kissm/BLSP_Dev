package com.framework.osp.modules.web.sys;

import com.framework.osp.common.persistence.Page;
import com.framework.osp.common.utils.StringUtils;
import com.framework.osp.common.web.BaseController;
import com.framework.osp.modules.sys.entity.Office;
import com.framework.osp.modules.sys.entity.User;
import com.framework.osp.modules.sys.service.SystemService;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.lpcode.modules.service.project.inf.PrjTaskDefineService;
import com.lpcode.modules.service.sys.IUserOfficeViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户查看权限 Controller
 * @author pengs
 * @version 2016-07-19
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/userview")
public class UserViewController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private IUserOfficeViewService service;
	@Autowired
	private PrjTaskDefineService prjTaskDefineService;

	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			User user = systemService.getUser(id);
			user.setOfficeViewList(service.getOfficeIDByUserId(id));
			return user;
		}else{
			return new User();
		}
	}

	/**
	 * 用户可视化看板的查看权限
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"listUserView", ""})
	public String listUserView(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		model.addAttribute("page", page);
		return "modules/sys/userViewList";
	}

	@RequestMapping(value = "viewForm")
	public String viewForm(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		model.addAttribute("user", user);
		model.addAttribute("allOfficeId", prjTaskDefineService.findAllDeptIdOfTask());
		return "modules/sys/userViewForm";
	}

	@RequestMapping(value = "saveUserView")
	public String saveUserView(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		List<String> officeIdList = user.getOfficeViewList();
		List<String> checkOfficeIdList = Lists.newArrayList();
		for (String deptId : prjTaskDefineService.findAllDeptIdOfTask()){
			if (officeIdList.contains(deptId)){
				checkOfficeIdList.add(deptId);
			}
		}
		// 保存用户信息
		service.saveOrUpdate(user.getId(),checkOfficeIdList);
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "用户可视化看版权限更新成功");
		return "redirect:" + adminPath + "/sys/userview/listUserView";
	}
}
