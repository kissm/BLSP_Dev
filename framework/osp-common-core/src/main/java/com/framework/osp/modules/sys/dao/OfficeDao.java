/**
 *
 */
package com.framework.osp.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.framework.osp.common.persistence.TreeDao;
import com.framework.osp.common.persistence.annotation.MyBatisDao;
import com.framework.osp.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * 
 * @author osp
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * 
	 * @param userId,loginName
	 * @return
	 */
	List<Office> findByUserList(@Param("userId") String userId);

	/**
	 * 通过父类部门ID得到部门列表
	 * 
	 * @param parentId
	 * @return
	 */
	List<Office> findOfficeByParent(@Param("parentId") String parentId);

    /**
     * 批量保存排序
     * @param office
     * @return
     */
    int updateSort(Office office);
}
