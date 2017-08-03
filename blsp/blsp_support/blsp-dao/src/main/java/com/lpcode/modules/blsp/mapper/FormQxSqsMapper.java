package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FormQxSqs;
import com.lpcode.modules.blsp.entity.FormQxSqsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormQxSqsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    int countByExample(FormQxSqsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    int deleteByExample(FormQxSqsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    int insert(FormQxSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    int insertSelective(FormQxSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    List<FormQxSqs> selectByExample(FormQxSqsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    FormQxSqs selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormQxSqs record, @Param("example") FormQxSqsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    int updateByExample(@Param("record") FormQxSqs record, @Param("example") FormQxSqsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    int updateByPrimaryKeySelective(FormQxSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    int updateByPrimaryKey(FormQxSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    List<FormQxSqs> selectByEntitySelective(FormQxSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    FormQxSqs selectOneByEntitySelective(FormQxSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FormQxSqs> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    Page<FormQxSqs> pagedSelectByExample(@Param("example") FormQxSqsExample example, Page<FormQxSqs> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    Page<FormQxSqs> pagedSelectByEntity(@Param("record") FormQxSqs record, Page<FormQxSqs> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sqs
     *
     * @mbggenerated Tue Jun 21 17:46:15 CST 2016
     */
    List<FormQxSqs> SelectByPrimaryKeyList(List<Long> idList);
}