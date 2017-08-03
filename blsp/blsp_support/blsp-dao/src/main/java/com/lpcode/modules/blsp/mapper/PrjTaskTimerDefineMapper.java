package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.PrjTaskTimerDefine;
import com.lpcode.modules.blsp.entity.PrjTaskTimerDefineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PrjTaskTimerDefineMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    int countByExample(PrjTaskTimerDefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    int deleteByExample(PrjTaskTimerDefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    int insert(PrjTaskTimerDefine record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    int insertSelective(PrjTaskTimerDefine record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    List<PrjTaskTimerDefine> selectByExample(PrjTaskTimerDefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    PrjTaskTimerDefine selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    int updateByExampleSelective(@Param("record") PrjTaskTimerDefine record, @Param("example") PrjTaskTimerDefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    int updateByExample(@Param("record") PrjTaskTimerDefine record, @Param("example") PrjTaskTimerDefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    int updateByPrimaryKeySelective(PrjTaskTimerDefine record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    int updateByPrimaryKey(PrjTaskTimerDefine record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    List<PrjTaskTimerDefine> selectByEntitySelective(PrjTaskTimerDefine record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    PrjTaskTimerDefine selectOneByEntitySelective(PrjTaskTimerDefine record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<PrjTaskTimerDefine> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    Page<PrjTaskTimerDefine> pagedSelectByExample(@Param("example") PrjTaskTimerDefineExample example, @Param("page") Page<PrjTaskTimerDefine> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    Page<PrjTaskTimerDefine> pagedSelectByEntity(@Param("record") PrjTaskTimerDefine record, @Param("page") Page<PrjTaskTimerDefine> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_timer_define
     *
     * @mbggenerated Tue Jun 21 17:04:50 CST 2016
     */
    List<PrjTaskTimerDefine> SelectByPrimaryKeyList(List<Long> idList);
}