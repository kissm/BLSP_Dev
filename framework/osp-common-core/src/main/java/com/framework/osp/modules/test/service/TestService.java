/**
 * 
 */
package com.framework.osp.modules.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.osp.common.service.CrudService;
import com.framework.osp.modules.test.dao.TestDao;
import com.framework.osp.modules.test.entity.Test;

/**
 * 测试Service
 * @author osp
 * @version 2013-10-17
 */
@Service
@Transactional(readOnly = true)
public class TestService extends CrudService<TestDao, Test> {

}
