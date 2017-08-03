package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FormRfJsxmqkbQk;
import com.lpcode.modules.blsp.entity.FormRfJsxmqkbQkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormRfJsxmqkbQkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    int countByExample(FormRfJsxmqkbQkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    int deleteByExample(FormRfJsxmqkbQkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    int deleteByPrimaryKey(Long cId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    int insert(FormRfJsxmqkbQk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    int insertSelective(FormRfJsxmqkbQk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    List<FormRfJsxmqkbQk> selectByExample(FormRfJsxmqkbQkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    FormRfJsxmqkbQk selectByPrimaryKey(Long cId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormRfJsxmqkbQk record, @Param("example") FormRfJsxmqkbQkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    int updateByExample(@Param("record") FormRfJsxmqkbQk record, @Param("example") FormRfJsxmqkbQkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    int updateByPrimaryKeySelective(FormRfJsxmqkbQk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    int updateByPrimaryKey(FormRfJsxmqkbQk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    List<FormRfJsxmqkbQk> selectByEntitySelective(FormRfJsxmqkbQk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    FormRfJsxmqkbQk selectOneByEntitySelective(FormRfJsxmqkbQk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FormRfJsxmqkbQk> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    Page<FormRfJsxmqkbQk> pagedSelectByExample(@Param("example") FormRfJsxmqkbQkExample example, @Param("page") Page<FormRfJsxmqkbQk> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    Page<FormRfJsxmqkbQk> pagedSelectByEntity(@Param("record") FormRfJsxmqkbQk record, @Param("page") Page<FormRfJsxmqkbQk> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_rf_jsxmqkb_qk
     *
     * @mbggenerated Wed Jun 29 17:50:33 CST 2016
     */
    List<FormRfJsxmqkbQk> SelectByPrimaryKeyList(List<Long> cIdList);
}