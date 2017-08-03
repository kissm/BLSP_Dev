package com.framework.base.task.dao;

import java.util.List;

import com.framework.base.task.domain.ScheduleJob;

public interface ScheduleJobMapper {
    
    int deleteByPrimaryKey(Long jobId);

    int insert(ScheduleJob record);

    int insertSelective(ScheduleJob record);

    ScheduleJob selectByPrimaryKey(Long jobId);

    int updateByPrimaryKeySelective(ScheduleJob record);

    int updateByPrimaryKey(ScheduleJob record);
    
    List<ScheduleJob> getAll();
    
    List<ScheduleJob> queryTaskList(ScheduleJob record);
    
    List<ScheduleJob> queryTaskListPage(ScheduleJob record);
    
    int queryTaskListCount(ScheduleJob record);
}