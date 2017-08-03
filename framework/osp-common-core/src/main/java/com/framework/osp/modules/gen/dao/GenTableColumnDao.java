/**
 * 
 */
package com.framework.osp.modules.gen.dao;

import com.framework.osp.common.persistence.CrudDao;
import com.framework.osp.common.persistence.annotation.MyBatisDao;
import com.framework.osp.modules.gen.entity.GenTableColumn;

/**
 * 业务表字段DAO接口
 * @author osp
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTableColumnDao extends CrudDao<GenTableColumn> {
	
	public void deleteByGenTableId(String genTableId);
}
