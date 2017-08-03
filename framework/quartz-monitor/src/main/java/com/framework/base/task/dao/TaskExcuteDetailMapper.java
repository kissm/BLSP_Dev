package com.framework.base.task.dao;

import java.util.List;

import com.framework.base.task.domain.TaskExcuteDetail;

public interface TaskExcuteDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TaskExcuteDetail record);

    int insertSelective(TaskExcuteDetail record);

    TaskExcuteDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskExcuteDetail record);

    int updateByPrimaryKey(TaskExcuteDetail record);
    
    List<TaskExcuteDetail> queryDetailList(TaskExcuteDetail record);
}