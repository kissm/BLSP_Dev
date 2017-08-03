/**
 * @Probject Name: blgroup-osp-web
 * @Path: com.bailiangroup.osp.app.modules.sys.serviceRealmEventListener.java
 * @Create By feelyn
 * @Create In 2014-12-18 下午7:50:26
 */
package com.framework.osp.common.security;

/**
 * @Class Name RealmEventListener
 * @Author feelyn
 * @Create In 2014-12-18
 */
public interface RealmEventListener {

	/***
	 *
	 * @Methods Name clearCachedAuthorizationInfo
	 * @Create In 2014-12-18 By feelyn void
	 */
	public void clearCachedAuthorizationInfo(String principal);

	/***
	 *
	 * @Methods Name clearAllCachedAuthorizationInfo
	 * @Create In 2014-12-18 By feelyn void
	 */
	public void clearAllCachedAuthorizationInfo();
}
