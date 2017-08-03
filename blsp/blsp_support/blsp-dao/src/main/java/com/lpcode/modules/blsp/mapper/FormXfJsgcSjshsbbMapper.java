package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FormXfJsgcSjshsbb;
import com.lpcode.modules.blsp.entity.FormXfJsgcSjshsbbExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormXfJsgcSjshsbbMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int countByExample(FormXfJsgcSjshsbbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int deleteByExample(FormXfJsgcSjshsbbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int insert(FormXfJsgcSjshsbb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int insertSelective(FormXfJsgcSjshsbb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    List<FormXfJsgcSjshsbb> selectByExample(FormXfJsgcSjshsbbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    FormXfJsgcSjshsbb selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormXfJsgcSjshsbb record, @Param("example") FormXfJsgcSjshsbbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int updateByExample(@Param("record") FormXfJsgcSjshsbb record, @Param("example") FormXfJsgcSjshsbbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int updateByPrimaryKeySelective(FormXfJsgcSjshsbb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int updateByPrimaryKey(FormXfJsgcSjshsbb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    List<FormXfJsgcSjshsbb> selectByEntitySelective(FormXfJsgcSjshsbb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    FormXfJsgcSjshsbb selectOneByEntitySelective(FormXfJsgcSjshsbb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FormXfJsgcSjshsbb> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    Page<FormXfJsgcSjshsbb> pagedSelectByExample(@Param("example") FormXfJsgcSjshsbbExample example, @Param("page") Page<FormXfJsgcSjshsbb> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    Page<FormXfJsgcSjshsbb> pagedSelectByEntity(@Param("record") FormXfJsgcSjshsbb record, @Param("page") Page<FormXfJsgcSjshsbb> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    List<FormXfJsgcSjshsbb> SelectByPrimaryKeyList(List<Long> idList);
}