/**
 * 
 */
package com.framework.osp.modules.gen.dao;

import com.framework.osp.common.persistence.CrudDao;
import com.framework.osp.common.persistence.annotation.MyBatisDao;
import com.framework.osp.modules.gen.entity.GenScheme;

/**
 * 生成方案DAO接口
 * @author osp
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenSchemeDao extends CrudDao<GenScheme> {
	
}
