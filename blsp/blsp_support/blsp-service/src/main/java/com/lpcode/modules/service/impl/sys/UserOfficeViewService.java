package com.lpcode.modules.service.impl.sys;

import com.framework.core.constants.BaseCode;
import com.framework.osp.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.lpcode.modules.blsp.entity.UserOfficeView;
import com.lpcode.modules.blsp.entity.UserOfficeViewExample;
import com.lpcode.modules.blsp.mapper.UserOfficeViewMapper;
import com.lpcode.modules.service.sys.IUserOfficeViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Pengs
 * @package com.lpcode.modules.service.impl.sys
 * @fileName UserOfficeViewService
 * @date 16/7/19.
 */
@Service
@Transactional(readOnly = false)
public class UserOfficeViewService implements IUserOfficeViewService {
    @Autowired
    private UserOfficeViewMapper mapper;


    @Override
    public void saveOrUpdate(String userId, List<String> officeIds) {
        UserOfficeViewExample example = new UserOfficeViewExample();
        example.createCriteria().andUserIdEqualTo(userId).andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
        mapper.deleteByExample(example);
        UserOfficeView userOfficeView = new UserOfficeView();
        userOfficeView.setUserId(userId);
        for(String officeId : officeIds){
            userOfficeView.setOfficeId(officeId);
            userOfficeView.setIsDelete(BaseCode.DEL_FLAG_NORMAL);
            userOfficeView.setCreatTime(new Date());
            userOfficeView.setCreator(UserUtils.getUser().getId());
            userOfficeView.setUpdateTime(new Date());
            userOfficeView.setUpdator(UserUtils.getUser().getId());
            mapper.insert(userOfficeView);
        }
    }

    @Override
    public List<String> getOfficeIDByUserId(String userId) {
        UserOfficeViewExample example = new UserOfficeViewExample();
        example.createCriteria().andUserIdEqualTo(userId).andIsDeleteEqualTo(BaseCode.DEL_FLAG_NORMAL);
        List<UserOfficeView> list = mapper.selectByExample(example);
        List<String> officeIdList = Lists.newArrayList();
        for(UserOfficeView userOfficeView : list){
            officeIdList.add(userOfficeView.getOfficeId());
        }
        return officeIdList;
    }


}
