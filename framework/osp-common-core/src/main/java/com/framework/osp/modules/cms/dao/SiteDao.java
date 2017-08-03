/**
 * 
 */
package com.framework.osp.modules.cms.dao;

import com.framework.osp.common.persistence.CrudDao;
import com.framework.osp.common.persistence.annotation.MyBatisDao;
import com.framework.osp.modules.cms.entity.Site;

/**
 * 站点DAO接口
 * @author osp
 * @version 2013-8-23
 */
@MyBatisDao
public interface SiteDao extends CrudDao<Site> {

}
