package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FormXfJsgcSjshsbbDw;
import com.lpcode.modules.blsp.entity.FormXfJsgcSjshsbbDwExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormXfJsgcSjshsbbDwMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int countByExample(FormXfJsgcSjshsbbDwExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int deleteByExample(FormXfJsgcSjshsbbDwExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int deleteByPrimaryKey(Long unitId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int insert(FormXfJsgcSjshsbbDw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int insertSelective(FormXfJsgcSjshsbbDw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    List<FormXfJsgcSjshsbbDw> selectByExample(FormXfJsgcSjshsbbDwExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    FormXfJsgcSjshsbbDw selectByPrimaryKey(Long unitId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormXfJsgcSjshsbbDw record, @Param("example") FormXfJsgcSjshsbbDwExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int updateByExample(@Param("record") FormXfJsgcSjshsbbDw record, @Param("example") FormXfJsgcSjshsbbDwExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int updateByPrimaryKeySelective(FormXfJsgcSjshsbbDw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    int updateByPrimaryKey(FormXfJsgcSjshsbbDw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    List<FormXfJsgcSjshsbbDw> selectByEntitySelective(FormXfJsgcSjshsbbDw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    FormXfJsgcSjshsbbDw selectOneByEntitySelective(FormXfJsgcSjshsbbDw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FormXfJsgcSjshsbbDw> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    Page<FormXfJsgcSjshsbbDw> pagedSelectByExample(@Param("example") FormXfJsgcSjshsbbDwExample example, @Param("page") Page<FormXfJsgcSjshsbbDw> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    Page<FormXfJsgcSjshsbbDw> pagedSelectByEntity(@Param("record") FormXfJsgcSjshsbbDw record, @Param("page") Page<FormXfJsgcSjshsbbDw> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_xf_jsgc_sjshsbb_dw
     *
     * @mbggenerated Mon Jun 27 11:38:40 CST 2016
     */
    List<FormXfJsgcSjshsbbDw> SelectByPrimaryKeyList(List<Long> unitIdList);
}