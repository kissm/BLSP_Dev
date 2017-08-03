/**
 * 
 */
package com.framework.osp.modules.sys.dao;

import com.framework.osp.common.persistence.CrudDao;
import com.framework.osp.common.persistence.annotation.MyBatisDao;
import com.framework.osp.modules.sys.entity.Log;

/**
 * 日志DAO接口
 * @author osp
 * @version 2014-05-16
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

}
