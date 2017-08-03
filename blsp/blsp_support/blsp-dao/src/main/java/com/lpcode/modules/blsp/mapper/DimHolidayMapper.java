package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.DimHoliday;
import com.lpcode.modules.blsp.entity.DimHolidayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DimHolidayMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int countByExample(DimHolidayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int deleteByExample(DimHolidayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int insert(DimHoliday record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int insertSelective(DimHoliday record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<DimHoliday> selectByExampleWithBLOBs(DimHolidayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<DimHoliday> selectByExample(DimHolidayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    DimHoliday selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByExampleSelective(@Param("record") DimHoliday record, @Param("example") DimHolidayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByExampleWithBLOBs(@Param("record") DimHoliday record, @Param("example") DimHolidayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByExample(@Param("record") DimHoliday record, @Param("example") DimHolidayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByPrimaryKeySelective(DimHoliday record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(DimHoliday record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByPrimaryKey(DimHoliday record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<DimHoliday> selectByEntitySelective(DimHoliday record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    DimHoliday selectOneByEntitySelective(DimHoliday record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<DimHoliday> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    Page<DimHoliday> pagedSelectByExample(@Param("example") DimHolidayExample example, @Param("page") Page<DimHoliday> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    Page<DimHoliday> pagedSelectByEntity(@Param("record") DimHoliday record, @Param("page") Page<DimHoliday> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dim_holiday
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<DimHoliday> SelectByPrimaryKeyList(List<Long> idList);
}