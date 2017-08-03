/**
 * 
 */
package com.framework.osp.modules.cms.dao;

import com.framework.osp.common.persistence.CrudDao;
import com.framework.osp.common.persistence.annotation.MyBatisDao;
import com.framework.osp.modules.cms.entity.Comment;

/**
 * 评论DAO接口
 * @author osp
 * @version 2013-8-23
 */
@MyBatisDao
public interface CommentDao extends CrudDao<Comment> {

}
