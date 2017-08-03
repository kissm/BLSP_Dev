/**
 * 
 */
package com.framework.osp.modules.test.dao;

import com.framework.osp.common.persistence.CrudDao;
import com.framework.osp.common.persistence.annotation.MyBatisDao;
import com.framework.osp.modules.test.entity.Test;

/**
 * 测试DAO接口
 * @author osp
 * @version 2013-10-17
 */
@MyBatisDao
public interface TestDao extends CrudDao<Test> {
	
}
