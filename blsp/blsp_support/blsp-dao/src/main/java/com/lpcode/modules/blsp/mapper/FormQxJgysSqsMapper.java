package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FormQxJgysSqs;
import com.lpcode.modules.blsp.entity.FormQxJgysSqsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormQxJgysSqsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    int countByExample(FormQxJgysSqsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    int deleteByExample(FormQxJgysSqsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    int insert(FormQxJgysSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    int insertSelective(FormQxJgysSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    List<FormQxJgysSqs> selectByExample(FormQxJgysSqsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    FormQxJgysSqs selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormQxJgysSqs record, @Param("example") FormQxJgysSqsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    int updateByExample(@Param("record") FormQxJgysSqs record, @Param("example") FormQxJgysSqsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    int updateByPrimaryKeySelective(FormQxJgysSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    int updateByPrimaryKey(FormQxJgysSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    List<FormQxJgysSqs> selectByEntitySelective(FormQxJgysSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    FormQxJgysSqs selectOneByEntitySelective(FormQxJgysSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FormQxJgysSqs> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    Page<FormQxJgysSqs> pagedSelectByExample(@Param("example") FormQxJgysSqsExample example, @Param("page") Page<FormQxJgysSqs> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    Page<FormQxJgysSqs> pagedSelectByEntity(@Param("record") FormQxJgysSqs record, @Param("page") Page<FormQxJgysSqs> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_jgys_sqs
     *
     * @mbggenerated Wed Jun 22 16:16:31 CST 2016
     */
    List<FormQxJgysSqs> SelectByPrimaryKeyList(List<Long> idList);
}