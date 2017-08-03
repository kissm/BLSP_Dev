package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.LogTpl;
import com.lpcode.modules.blsp.entity.LogTplExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogTplMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int countByExample(LogTplExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int deleteByExample(LogTplExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int insert(LogTpl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int insertSelective(LogTpl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<LogTpl> selectByExampleWithBLOBs(LogTplExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<LogTpl> selectByExample(LogTplExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    LogTpl selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByExampleSelective(@Param("record") LogTpl record, @Param("example") LogTplExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByExampleWithBLOBs(@Param("record") LogTpl record, @Param("example") LogTplExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByExample(@Param("record") LogTpl record, @Param("example") LogTplExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByPrimaryKeySelective(LogTpl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(LogTpl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByPrimaryKey(LogTpl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<LogTpl> selectByEntitySelective(LogTpl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    LogTpl selectOneByEntitySelective(LogTpl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<LogTpl> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    Page<LogTpl> pagedSelectByExample(@Param("example") LogTplExample example, @Param("page") Page<LogTpl> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    Page<LogTpl> pagedSelectByEntity(@Param("record") LogTpl record, @Param("page") Page<LogTpl> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mg_log_tpl
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<LogTpl> SelectByPrimaryKeyList(List<String> idList);
}