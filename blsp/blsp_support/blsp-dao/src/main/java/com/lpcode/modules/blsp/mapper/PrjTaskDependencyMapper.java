package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.PrjTaskDependency;
import com.lpcode.modules.blsp.entity.PrjTaskDependencyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PrjTaskDependencyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int countByExample(PrjTaskDependencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int deleteByExample(PrjTaskDependencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int insert(PrjTaskDependency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int insertSelective(PrjTaskDependency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<PrjTaskDependency> selectByExample(PrjTaskDependencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    PrjTaskDependency selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByExampleSelective(@Param("record") PrjTaskDependency record, @Param("example") PrjTaskDependencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByExample(@Param("record") PrjTaskDependency record, @Param("example") PrjTaskDependencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByPrimaryKeySelective(PrjTaskDependency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByPrimaryKey(PrjTaskDependency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<PrjTaskDependency> selectByEntitySelective(PrjTaskDependency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    PrjTaskDependency selectOneByEntitySelective(PrjTaskDependency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<PrjTaskDependency> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    Page<PrjTaskDependency> pagedSelectByExample(@Param("example") PrjTaskDependencyExample example, @Param("page") Page<PrjTaskDependency> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    Page<PrjTaskDependency> pagedSelectByEntity(@Param("record") PrjTaskDependency record, @Param("page") Page<PrjTaskDependency> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_dependency
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<PrjTaskDependency> SelectByPrimaryKeyList(List<Long> idList);
}