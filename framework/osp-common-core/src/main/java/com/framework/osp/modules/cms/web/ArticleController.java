/**
 *
 */
package com.framework.osp.modules.cms.web;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.framework.core.utils.BeanCopy;
import com.framework.osp.common.mapper.JsonMapper;
import com.framework.osp.common.utils.StringUtils;
import com.framework.osp.common.web.BaseController;
import com.framework.osp.modules.cms.entity.Article;
import com.framework.osp.modules.cms.entity.Category;
import com.framework.osp.modules.cms.entity.Site;
import com.framework.osp.modules.cms.service.ArticleDataService;
import com.framework.osp.modules.cms.service.ArticleService;
import com.framework.osp.modules.cms.service.CategoryService;
import com.framework.osp.modules.cms.service.FileTplService;
import com.framework.osp.modules.cms.service.SiteService;
import com.framework.osp.modules.cms.utils.CmsUtils;
import com.framework.osp.modules.cms.utils.TplUtils;
import com.framework.osp.modules.sys.utils.UserUtils;

/**
 * 文章Controller
 *
 * @author osp
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/article")
public class ArticleController extends BaseController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleDataService articleDataService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private FileTplService fileTplService;
	@Autowired
	private SiteService siteService;

	@ModelAttribute
	public Article get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return articleService.get(id);
		} else {
			return new Article();
		}
	}

	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = "form")
	public String form(Article article, Model model) {
		Article articleNow = new Article();
		BeanCopy.copyProperties(article, articleNow, Article.class);
		// 如果当前传参有子节点，则选择取消传参选择
		if (article.getCategory() != null && StringUtils.isNotBlank(article.getCategory().getId())) {
			List<Category> list = categoryService.findByParentId(article.getCategory().getId(),
					Site.getCurrentSiteId());
			if (list.size() > 0) {
				article.setCategory(null);
			} else {
				article.setCategory(categoryService.get(article.getCategory().getId()));
			}
		}
		article.setArticleData(articleDataService.get(article.getId()));
		// if (article.getCategory()=null &&
		// StringUtils.isNotBlank(article.getCategory().getId())){
		// Category category =
		// categoryService.get(article.getCategory().getId());
		// }
		if (article.getCategory() == null || StringUtils.isBlank(article.getCategory().getId())) {
			model.addAttribute(MESSAGE, "目录无添加文章权限");
			return "modules/cms/articleForm";
		} else {
			model.addAttribute("contentViewList", getTplContent());
			model.addAttribute("article_DEFAULT_TEMPLATE", Article.DEFAULT_TEMPLATE);
			model.addAttribute("article", article);
			CmsUtils.addViewConfigAttribute(model, article.getCategory());
			return "modules/cms/articleForm";
		}
	}

	private List subImgStr(String imgStr, String contextPath) {
		if (StringUtils.isNotBlank(contextPath)) {
			contextPath = contextPath.replace("/", "");
		}
		String split = "src=&quot;";
		List<String> a = new ArrayList();
		String[] strArr = imgStr.split(split);
		for (String str : strArr) {
			if (str.split("&quot;")[0].contains(contextPath + "/userfiles")) {
				a.add(str.split("&quot;")[0]);
			}
		}
		return a;
	}

	public static List getImgStr(String htmlStr) {
		String img = "";
		Pattern p_image;
		Matcher m_image;
		List pics = new ArrayList();
		String regEx_img = "]*?>";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			img = img + "," + m_image.group();
			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
			while (m.find()) {
				pics.add(m.group(1));
			}
		}
		return pics;
	}

	@RequiresPermissions("cms:article:edit")
	@RequestMapping(value = "delete")
	public String delete(Article article, String categoryId, @RequestParam(required = false) Boolean isRe,
			RedirectAttributes redirectAttributes) {
		// 如果没有审核权限，则不允许删除或发布。
		if (!UserUtils.getSubject().isPermitted("cms:article:audit")) {
			addMessage(redirectAttributes, "你没有删除或发布权限");
		}
		articleService.delete(article, isRe);
		addMessage(redirectAttributes, (isRe != null && isRe ? "发布" : "删除") + "文章成功");
		return "redirect:" + adminPath + "/cms/article/?repage&category.id=" + (categoryId != null ? categoryId : "");
	}

	@RequiresPermissions("cms:article:edit")
	@RequestMapping(value = "deletePosid")
	public String deletePosid(Article article, String categoryId, @RequestParam(required = false) Boolean isRe,
			RedirectAttributes redirectAttributes) {
		// 如果没有审核权限，则不允许删除或发布。
		if (!UserUtils.getSubject().isPermitted("cms:article:audit")) {
			addMessage(redirectAttributes, "你没有删除或发布权限");
		}
		articleService.deletePosid(article, isRe, categoryId);
		addMessage(redirectAttributes, (isRe != null && isRe ? "发布" : "删除") + "成功");
		return "redirect:" + adminPath + "/cms/article/?repage&category.id=" + (categoryId != null ? categoryId : "");
	}

	/**
	 * 通过编号获取文章标题
	 */
	@RequiresPermissions("cms:article:view")
	@ResponseBody
	@RequestMapping(value = "findByIds")
	public String findByIds(String ids) {
		List<Object[]> list = articleService.findByIds(ids);
		return JsonMapper.nonDefaultMapper().toJson(list);
	}

	private List<String> getTplContent() {
		List<String> tplList = fileTplService
				.getNameListByPrefix(siteService.get(Site.getCurrentSiteId()).getSolutionPath());
		tplList = TplUtils.tplTrim(tplList, Article.DEFAULT_TEMPLATE, "");
		return tplList;
	}
}
