package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.PrjTaskMessage;
import com.lpcode.modules.blsp.entity.PrjTaskMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PrjTaskMessageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    int countByExample(PrjTaskMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    int deleteByExample(PrjTaskMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    int insert(PrjTaskMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    int insertSelective(PrjTaskMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    List<PrjTaskMessage> selectByExample(PrjTaskMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    PrjTaskMessage selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    int updateByExampleSelective(@Param("record") PrjTaskMessage record, @Param("example") PrjTaskMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    int updateByExample(@Param("record") PrjTaskMessage record, @Param("example") PrjTaskMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    int updateByPrimaryKeySelective(PrjTaskMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    int updateByPrimaryKey(PrjTaskMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    List<PrjTaskMessage> selectByEntitySelective(PrjTaskMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    PrjTaskMessage selectOneByEntitySelective(PrjTaskMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<PrjTaskMessage> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    Page<PrjTaskMessage> pagedSelectByExample(@Param("example") PrjTaskMessageExample example, @Param("page") Page<PrjTaskMessage> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    Page<PrjTaskMessage> pagedSelectByEntity(@Param("record") PrjTaskMessage record, @Param("page") Page<PrjTaskMessage> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_message
     *
     * @mbggenerated Tue Jun 21 17:05:38 CST 2016
     */
    List<PrjTaskMessage> SelectByPrimaryKeyList(List<Long> idList);
}