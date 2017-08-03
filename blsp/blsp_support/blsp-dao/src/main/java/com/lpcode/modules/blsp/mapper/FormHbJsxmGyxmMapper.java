package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FormHbJsxmGyxm;
import com.lpcode.modules.blsp.entity.FormHbJsxmGyxmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormHbJsxmGyxmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int countByExample(FormHbJsxmGyxmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int deleteByExample(FormHbJsxmGyxmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int insert(FormHbJsxmGyxm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int insertSelective(FormHbJsxmGyxm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    List<FormHbJsxmGyxm> selectByExample(FormHbJsxmGyxmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    FormHbJsxmGyxm selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormHbJsxmGyxm record, @Param("example") FormHbJsxmGyxmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int updateByExample(@Param("record") FormHbJsxmGyxm record, @Param("example") FormHbJsxmGyxmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int updateByPrimaryKeySelective(FormHbJsxmGyxm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    int updateByPrimaryKey(FormHbJsxmGyxm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    List<FormHbJsxmGyxm> selectByEntitySelective(FormHbJsxmGyxm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    FormHbJsxmGyxm selectOneByEntitySelective(FormHbJsxmGyxm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FormHbJsxmGyxm> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    Page<FormHbJsxmGyxm> pagedSelectByExample(@Param("example") FormHbJsxmGyxmExample example, @Param("page") Page<FormHbJsxmGyxm> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    Page<FormHbJsxmGyxm> pagedSelectByEntity(@Param("record") FormHbJsxmGyxm record, @Param("page") Page<FormHbJsxmGyxm> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    List<FormHbJsxmGyxm> SelectByPrimaryKeyList(List<Long> idList);
}