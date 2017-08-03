/**
 * @Probject Name: blgroup-osp-base-core
 * @Path: com.bailiangroup.osp.base.core.service.ctxContextService.java
 * @Create By feelyn
 * @Create In 2014-12-24 上午11:42:58
 */
package com.framework.osp.common.service.ctx;

import java.util.List;

/**
 * @Class Name ContextService
 * @Author feelyn
 * @Create In 2014-12-24
 */
public interface ContextService {

	/***
	 * Get Current User Info { @link CtxBean } From Context
	 * 
	 * @Methods Name getCurrentUser
	 * @Create In 2014-12-24 By feelyn
	 * @return T
	 */
	public <T extends CtxBean> T getCurrentUser();

	/***
	 * Get Current User Info { @link CtxBean } From Context with refresh command
	 * 
	 * @Methods Name getCurrentUser
	 * @Create In 2014-12-24 By feelyn
	 * @param refreshRequired
	 * @return T
	 */
	public <T extends CtxBean> T getCurrentUser(Boolean refreshRequired);

	/***
	 * Get Current User Roles Info { @link CtxBean } List from Context
	 * 
	 * @Methods Name getCurrentUserRoleList
	 * @Create In 2014-12-24 By feelyn
	 * @return List<T>
	 */
	public <T extends CtxBean> List<T> getCurrentUserRoleList();

	/***
	 * Get Current User's MenuInfo { @link CtxBean } List from Context
	 * 
	 * @Methods Name getCurrentUserMenuList
	 * @Create In 2014-12-24 By feelyn
	 * @return List<T>
	 */
	public <T extends CtxBean> List<T> getCurrentUserMenuList();

	/**
	 * Get Current User's Module MenuInfo { @link CtxBean } List from Context
	 * 
	 * @Methods Name getCurrentUserMenuList
	 * @Create In 2014-12-24 By feelyn
	 * @return List<T>
	 */
	public <T extends CtxBean> List<T> getCurrentUserModuleMenuList();

	/**
	 * Get Current User's OrgInfo { @link CtxBean } List from Context
	 * 
	 * @Methods Name getCurrentUserOrgList
	 * @Create In 2014-12-24 By feelyn
	 * @return List<T>
	 */
	public <T extends CtxBean> List<T> getCurrentUserOrgList();

	/***
	 * Get Current User's AreaInfo { @link CtxBean } List from Context
	 * 
	 * @Methods Name getCurrentUserAreaList
	 * @Create In 2014-12-24 By feelyn
	 * @return List<T>
	 */
	public <T extends CtxBean> List<T> getCurrentUserAreaList();

	/**
	 * 获得静态内容的动态URL地址
	 * 
	 * @param linkPath
	 * @param prefix
	 * @param idConnector
	 * @param resourceIds
	 * @return
	 */
	public String getUrlDynamic(String linkPath, String prefix, String idConnector, String... resourceIds);

	/***
	 * 格式化静态内容的地址（移除Context）
	 * 
	 * @param resourcePath
	 * @param includeContext
	 *            Path or not
	 * @return
	 */
	public String resolveResourceUrl(String resourcePath, boolean includeContext);

}
