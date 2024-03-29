package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FormHbJsxmGyxmCl;
import com.lpcode.modules.blsp.entity.FormHbJsxmGyxmClExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormHbJsxmGyxmClMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int countByExample(FormHbJsxmGyxmClExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int deleteByExample(FormHbJsxmGyxmClExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int deleteByPrimaryKey(Long mId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int insert(FormHbJsxmGyxmCl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int insertSelective(FormHbJsxmGyxmCl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    List<FormHbJsxmGyxmCl> selectByExample(FormHbJsxmGyxmClExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    FormHbJsxmGyxmCl selectByPrimaryKey(Long mId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormHbJsxmGyxmCl record, @Param("example") FormHbJsxmGyxmClExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int updateByExample(@Param("record") FormHbJsxmGyxmCl record, @Param("example") FormHbJsxmGyxmClExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int updateByPrimaryKeySelective(FormHbJsxmGyxmCl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int updateByPrimaryKey(FormHbJsxmGyxmCl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    List<FormHbJsxmGyxmCl> selectByEntitySelective(FormHbJsxmGyxmCl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    FormHbJsxmGyxmCl selectOneByEntitySelective(FormHbJsxmGyxmCl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FormHbJsxmGyxmCl> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    Page<FormHbJsxmGyxmCl> pagedSelectByExample(@Param("example") FormHbJsxmGyxmClExample example, @Param("page") Page<FormHbJsxmGyxmCl> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    Page<FormHbJsxmGyxmCl> pagedSelectByEntity(@Param("record") FormHbJsxmGyxmCl record, @Param("page") Page<FormHbJsxmGyxmCl> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_cl
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    List<FormHbJsxmGyxmCl> SelectByPrimaryKeyList(List<Long> mIdList);
}