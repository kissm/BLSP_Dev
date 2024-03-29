package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FormRfBjsh;
import com.lpcode.modules.blsp.entity.FormRfBjshExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormRfBjshMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    int countByExample(FormRfBjshExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    int deleteByExample(FormRfBjshExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    int insert(FormRfBjsh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    int insertSelective(FormRfBjsh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    List<FormRfBjsh> selectByExample(FormRfBjshExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    FormRfBjsh selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormRfBjsh record, @Param("example") FormRfBjshExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    int updateByExample(@Param("record") FormRfBjsh record, @Param("example") FormRfBjshExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    int updateByPrimaryKeySelective(FormRfBjsh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    int updateByPrimaryKey(FormRfBjsh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    List<FormRfBjsh> selectByEntitySelective(FormRfBjsh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    FormRfBjsh selectOneByEntitySelective(FormRfBjsh record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FormRfBjsh> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    Page<FormRfBjsh> pagedSelectByExample(@Param("example") FormRfBjshExample example, @Param("page") Page<FormRfBjsh> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    Page<FormRfBjsh> pagedSelectByEntity(@Param("record") FormRfBjsh record, @Param("page") Page<FormRfBjsh> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_bjsh
     *
     * @mbggenerated Mon Jun 27 13:41:51 CST 2016
     */
    List<FormRfBjsh> SelectByPrimaryKeyList(List<Long> idList);
}