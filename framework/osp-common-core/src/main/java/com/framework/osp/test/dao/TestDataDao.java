/**
 * 
 */
package com.framework.osp.test.dao;

import com.framework.osp.common.persistence.CrudDao;
import com.framework.osp.common.persistence.annotation.MyBatisDao;
import com.framework.osp.test.entity.TestData;

/**
 * 单表生成DAO接口
 * @author osp
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestDataDao extends CrudDao<TestData> {
	
}