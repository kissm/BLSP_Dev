/**
 * 
 */
package com.framework.osp.modules.sys.service;

import java.util.List;

import com.framework.core.constants.BaseCode;
import com.framework.osp.modules.sys.entity.Area;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.osp.common.service.TreeService;
import com.framework.osp.modules.sys.dao.OfficeDao;
import com.framework.osp.modules.sys.entity.Office;
import com.framework.osp.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author osp
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		office.setParentIds(office.getParentIds()+"%");
		return dao.findByParentIdsLike(office);
	}

	@Transactional(readOnly = false)
	public void save(Office office) {
		//区域默认写错,此系统不维护区域,预设一个中国做为所有区域的值
		Area area = new Area();
		area.setId(BaseCode.ROOT_AREA_ID);
		office.setArea(area);
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
}
