/**
 * 
 */
package com.framework.osp.modules.sys.dao;

import com.framework.osp.common.persistence.TreeDao;
import com.framework.osp.common.persistence.annotation.MyBatisDao;
import com.framework.osp.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @author osp
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}
