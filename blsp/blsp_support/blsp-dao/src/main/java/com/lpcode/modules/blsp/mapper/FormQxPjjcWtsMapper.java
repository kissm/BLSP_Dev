package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FormQxPjjcWts;
import com.lpcode.modules.blsp.entity.FormQxPjjcWtsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormQxPjjcWtsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    int countByExample(FormQxPjjcWtsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    int deleteByExample(FormQxPjjcWtsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    int insert(FormQxPjjcWts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    int insertSelective(FormQxPjjcWts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    List<FormQxPjjcWts> selectByExample(FormQxPjjcWtsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    FormQxPjjcWts selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormQxPjjcWts record, @Param("example") FormQxPjjcWtsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    int updateByExample(@Param("record") FormQxPjjcWts record, @Param("example") FormQxPjjcWtsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    int updateByPrimaryKeySelective(FormQxPjjcWts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    int updateByPrimaryKey(FormQxPjjcWts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    List<FormQxPjjcWts> selectByEntitySelective(FormQxPjjcWts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    FormQxPjjcWts selectOneByEntitySelective(FormQxPjjcWts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FormQxPjjcWts> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    Page<FormQxPjjcWts> pagedSelectByExample(@Param("example") FormQxPjjcWtsExample example, @Param("page") Page<FormQxPjjcWts> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    Page<FormQxPjjcWts> pagedSelectByEntity(@Param("record") FormQxPjjcWts record, @Param("page") Page<FormQxPjjcWts> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_pjjc_wts
     *
     * @mbggenerated Mon Jun 27 13:58:36 CST 2016
     */
    List<FormQxPjjcWts> SelectByPrimaryKeyList(List<Long> idList);
}