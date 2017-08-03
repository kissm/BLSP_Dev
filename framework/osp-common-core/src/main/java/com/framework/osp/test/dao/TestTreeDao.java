/**
 * 
 */
package com.framework.osp.test.dao;

import com.framework.osp.common.persistence.TreeDao;
import com.framework.osp.common.persistence.annotation.MyBatisDao;
import com.framework.osp.test.entity.TestTree;

/**
 * 树结构生成DAO接口
 * @author osp
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	
}