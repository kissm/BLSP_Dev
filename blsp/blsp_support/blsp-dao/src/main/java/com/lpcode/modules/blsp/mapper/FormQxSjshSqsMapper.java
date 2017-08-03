package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FormQxSjshSqs;
import com.lpcode.modules.blsp.entity.FormQxSjshSqsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormQxSjshSqsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    int countByExample(FormQxSjshSqsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    int deleteByExample(FormQxSjshSqsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    int insert(FormQxSjshSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    int insertSelective(FormQxSjshSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    List<FormQxSjshSqs> selectByExample(FormQxSjshSqsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    FormQxSjshSqs selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormQxSjshSqs record, @Param("example") FormQxSjshSqsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    int updateByExample(@Param("record") FormQxSjshSqs record, @Param("example") FormQxSjshSqsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    int updateByPrimaryKeySelective(FormQxSjshSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    int updateByPrimaryKey(FormQxSjshSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    List<FormQxSjshSqs> selectByEntitySelective(FormQxSjshSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    FormQxSjshSqs selectOneByEntitySelective(FormQxSjshSqs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FormQxSjshSqs> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    Page<FormQxSjshSqs> pagedSelectByExample(@Param("example") FormQxSjshSqsExample example,@Param("page") Page<FormQxSjshSqs> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    Page<FormQxSjshSqs> pagedSelectByEntity(@Param("record") FormQxSjshSqs record,@Param("page") Page<FormQxSjshSqs> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_qx_sjsh_sqs
     *
     * @mbggenerated Mon Jun 20 18:32:05 CST 2016
     */
    List<FormQxSjshSqs> SelectByPrimaryKeyList(List<Long> idList);
}