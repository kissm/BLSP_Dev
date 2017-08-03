package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.UserOfficeView;
import com.lpcode.modules.blsp.entity.UserOfficeViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserOfficeViewMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    int countByExample(UserOfficeViewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    int deleteByExample(UserOfficeViewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    int insert(UserOfficeView record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    int insertSelective(UserOfficeView record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    List<UserOfficeView> selectByExample(UserOfficeViewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    UserOfficeView selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    int updateByExampleSelective(@Param("record") UserOfficeView record, @Param("example") UserOfficeViewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    int updateByExample(@Param("record") UserOfficeView record, @Param("example") UserOfficeViewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    int updateByPrimaryKeySelective(UserOfficeView record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    int updateByPrimaryKey(UserOfficeView record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    List<UserOfficeView> selectByEntitySelective(UserOfficeView record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    UserOfficeView selectOneByEntitySelective(UserOfficeView record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<UserOfficeView> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    Page<UserOfficeView> pagedSelectByExample(@Param("example") UserOfficeViewExample example, @Param("page") Page<UserOfficeView> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    Page<UserOfficeView> pagedSelectByEntity(@Param("record") UserOfficeView record, @Param("page") Page<UserOfficeView> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_office_view
     *
     * @mbggenerated Tue Jul 19 15:03:22 CST 2016
     */
    List<UserOfficeView> SelectByPrimaryKeyList(List<Long> idList);
}