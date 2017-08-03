/**
 * Copyright &copy; 2014-2020 <a href="https://www.bailiangroup.com/osp">Bailian Group OSP</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.framework.osp.common.security;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

//import com.framework.osp.common.chche.CacheManager;
import com.framework.osp.common.utils.SpringContextHolder;
import com.framework.osp.common.web.Servlets;
//
//import com.bailiangroup.osp.base.core.service.ctx.ContextService;
//import com.bailiangroup.osp.base.core.util.SpringContextHolder;
//import com.bailiangroup.osp.common.service.SystemService;
import com.framework.osp.modules.sys.entity.Menu;
import com.framework.osp.modules.sys.entity.Role;
import com.framework.osp.modules.sys.entity.User;
import com.framework.osp.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.framework.osp.modules.sys.service.SystemService;
import com.framework.osp.modules.sys.utils.LogUtils;
import com.framework.osp.modules.sys.utils.UserUtils;

/**
 * 系统安全认证实现类
 *
 * @author IBM Consultant Team
 * @version 2013-5-29
 */
public class CasAuthorizingRealm extends CasRealm implements RealmEventListener {

	// private ContextService contextService;

	private SystemService systemService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {

		// invoke super authentication to validate tickets
		SimpleAuthenticationInfo authInfo = (SimpleAuthenticationInfo) super.doGetAuthenticationInfo(authcToken);

		if (authInfo != null && null != authInfo.getPrincipals()) {

			String loginName = (String) authInfo.getPrincipals().getPrimaryPrincipal();

			if (StringUtils.isEmpty(loginName)) {

				return null;
			}
			User user = getSystemService().getUserByLoginName(loginName);
			if (user == null) {
				return null;
			}
			if (user.getRoleList() == null || user.getRoleList().isEmpty()) {
				throw new AuthenticationException("role can not be null!");
			}

			return new SimpleAuthenticationInfo(new Principal(user, false), authcToken.getCredentials(), getName());
		}

		return null;

	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		Principal usr = (Principal) principals.getPrimaryPrincipal();

		User user = getSystemService().getUserByLoginName(usr.getLoginName());

		if (user != null) {

			// CacheManager.putCache(com.framework.osp.common.chche.CacheManager.CACHE_USER,
			// user);

			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<Menu> list = UserUtils.getMenuList();
			// List<Menu> list = getContextService().getCurrentUserMenuList();
			for (Menu menu : list) {
				if (StringUtils.isNotBlank(menu.getPermission())) {
					// 添加基于Permission的权限信息
					for (String permission : StringUtils.split(menu.getPermission(), ",")) {
						info.addStringPermission(permission);
					}
				}
			}

			// 添加用户权限
			info.addStringPermission("user");
			// 添加用户角色信息
			for (Role role : user.getRoleList()) {
				info.addRole(role.getEnname());
			}

			// 更新本次登录信息
			user.setLoginIp(UserUtils.getSession().getHost());
			user.setLoginDate(new Date());
			// 更新登录IP和时间
			systemService.updateUserLoginInfo(user);
			// 记录登录日志
			LogUtils.saveLog(Servlets.getRequest(), "系统登录");
			return info;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.bailiangroup.osp.app.modules.sys.service.RealmEventListener#
	 * clearCachedAuthorizationInfo(java.lang.String)
	 */
	@Override
	public void clearCachedAuthorizationInfo(String principal) {

		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.bailiangroup.osp.app.modules.sys.service.RealmEventListener#
	 * clearAllCachedAuthorizationInfo()
	 */
	@Override
	public void clearAllCachedAuthorizationInfo() {

		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.shiro.cas.CasRealm#onInit()
	 */
	@Override
	protected void onInit() {
		super.onInit();

		RealmEventManager.getInstance().registReamlInstance(this);
	}

	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {

		if (systemService == null) {
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}

	// public ContextService getContextService() {
	//
	// if (contextService == null){
	// contextService = SpringContextHolder.getBean(ContextService.class);
	// }
	// return contextService;
	// }
	//
	// public void setContextService(ContextService contextService) {
	// this.contextService = contextService;
	// }

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
}
