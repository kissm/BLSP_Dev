/**
 * @Probject Name: blgroup-osp-web
 * @Path: com.bailiangroup.osp.app.modules.sys.serviceRealmEventManager.java
 * @Create By feelyn
 * @Create In 2014-12-18 下午7:45:16
 */
package com.framework.osp.common.security;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @Class Name RealmEventManager
 * @Author feelyn
 * @Create In 2014-12-18
 */
public class RealmEventManager {

	private static RealmEventManager instance = new RealmEventManager();

	private List<RealmEventListener> registedRealms = Lists.newLinkedList();

	public static RealmEventManager getInstance() {

		return instance;
	}

	public void registReamlInstance(RealmEventListener listener) {

		registedRealms.add(listener);
	}

	public void unRegistRealmInstance(RealmEventListener listener) {

		registedRealms.remove(listener);
	}

	public void clearCachedAuthorizationInfo(String principal) {

		for (RealmEventListener item : registedRealms) {

			item.clearCachedAuthorizationInfo(principal);
		}
	}

	public void clearAllCachedAuthorizationInfo() {

		for (RealmEventListener item : registedRealms) {

			item.clearAllCachedAuthorizationInfo();
		}
	}

}
