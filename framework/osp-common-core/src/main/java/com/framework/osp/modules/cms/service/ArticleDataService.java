/**
 * 
 */
package com.framework.osp.modules.cms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.osp.common.service.CrudService;
import com.framework.osp.modules.cms.dao.ArticleDataDao;
import com.framework.osp.modules.cms.entity.ArticleData;

/**
 * 站点Service
 * @author osp
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleDataService extends CrudService<ArticleDataDao, ArticleData> {

}
