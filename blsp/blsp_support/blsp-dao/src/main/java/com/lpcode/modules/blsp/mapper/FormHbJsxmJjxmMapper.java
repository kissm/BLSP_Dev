package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FormHbJsxmJjxm;
import com.lpcode.modules.blsp.entity.FormHbJsxmJjxmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormHbJsxmJjxmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    int countByExample(FormHbJsxmJjxmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    int deleteByExample(FormHbJsxmJjxmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    int insert(FormHbJsxmJjxm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    int insertSelective(FormHbJsxmJjxm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    List<FormHbJsxmJjxm> selectByExample(FormHbJsxmJjxmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    FormHbJsxmJjxm selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormHbJsxmJjxm record, @Param("example") FormHbJsxmJjxmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    int updateByExample(@Param("record") FormHbJsxmJjxm record, @Param("example") FormHbJsxmJjxmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    int updateByPrimaryKeySelective(FormHbJsxmJjxm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    int updateByPrimaryKey(FormHbJsxmJjxm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    List<FormHbJsxmJjxm> selectByEntitySelective(FormHbJsxmJjxm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    FormHbJsxmJjxm selectOneByEntitySelective(FormHbJsxmJjxm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FormHbJsxmJjxm> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    Page<FormHbJsxmJjxm> pagedSelectByExample(@Param("example") FormHbJsxmJjxmExample example, @Param("page") Page<FormHbJsxmJjxm> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    Page<FormHbJsxmJjxm> pagedSelectByEntity(@Param("record") FormHbJsxmJjxm record, @Param("page") Page<FormHbJsxmJjxm> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_jjxm
     *
     * @mbggenerated Wed Jul 13 14:32:28 CST 2016
     */
    List<FormHbJsxmJjxm> SelectByPrimaryKeyList(List<Long> idList);
}