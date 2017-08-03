/**
 * 
 */
package com.framework.osp.modules.sys.dao;

import java.util.List;

import com.framework.osp.common.persistence.CrudDao;
import com.framework.osp.common.persistence.annotation.MyBatisDao;
import com.framework.osp.modules.sys.entity.Dict;

/**
 * 字典DAO接口
 * @author osp
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);
	
}
