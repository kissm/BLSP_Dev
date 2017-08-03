package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.PrjCodeGenerator;
import com.lpcode.modules.blsp.entity.PrjCodeGeneratorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PrjCodeGeneratorMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int countByExample(PrjCodeGeneratorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int deleteByExample(PrjCodeGeneratorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int insert(PrjCodeGenerator record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int insertSelective(PrjCodeGenerator record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<PrjCodeGenerator> selectByExample(PrjCodeGeneratorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    PrjCodeGenerator selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByExampleSelective(@Param("record") PrjCodeGenerator record, @Param("example") PrjCodeGeneratorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByExample(@Param("record") PrjCodeGenerator record, @Param("example") PrjCodeGeneratorExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByPrimaryKeySelective(PrjCodeGenerator record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByPrimaryKey(PrjCodeGenerator record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<PrjCodeGenerator> selectByEntitySelective(PrjCodeGenerator record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    PrjCodeGenerator selectOneByEntitySelective(PrjCodeGenerator record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<PrjCodeGenerator> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    Page<PrjCodeGenerator> pagedSelectByExample(@Param("example") PrjCodeGeneratorExample example, @Param("page") Page<PrjCodeGenerator> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    Page<PrjCodeGenerator> pagedSelectByEntity(@Param("record") PrjCodeGenerator record, @Param("page") Page<PrjCodeGenerator> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_code_generator
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<PrjCodeGenerator> SelectByPrimaryKeyList(List<Integer> idList);
}