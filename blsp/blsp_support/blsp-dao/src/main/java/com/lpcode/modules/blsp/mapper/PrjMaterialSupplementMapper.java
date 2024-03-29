package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.PrjMaterialSupplement;
import com.lpcode.modules.blsp.entity.PrjMaterialSupplementExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PrjMaterialSupplementMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    int countByExample(PrjMaterialSupplementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    int deleteByExample(PrjMaterialSupplementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    int insert(PrjMaterialSupplement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    int insertSelective(PrjMaterialSupplement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    List<PrjMaterialSupplement> selectByExample(PrjMaterialSupplementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    PrjMaterialSupplement selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    int updateByExampleSelective(@Param("record") PrjMaterialSupplement record, @Param("example") PrjMaterialSupplementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    int updateByExample(@Param("record") PrjMaterialSupplement record, @Param("example") PrjMaterialSupplementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    int updateByPrimaryKeySelective(PrjMaterialSupplement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    int updateByPrimaryKey(PrjMaterialSupplement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    List<PrjMaterialSupplement> selectByEntitySelective(PrjMaterialSupplement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    PrjMaterialSupplement selectOneByEntitySelective(PrjMaterialSupplement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<PrjMaterialSupplement> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    Page<PrjMaterialSupplement> pagedSelectByExample(@Param("example") PrjMaterialSupplementExample example, @Param("page") Page<PrjMaterialSupplement> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    Page<PrjMaterialSupplement> pagedSelectByEntity(@Param("record") PrjMaterialSupplement record, @Param("page") Page<PrjMaterialSupplement> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_stage_material_supplement
     *
     * @mbggenerated Thu Jul 28 15:49:16 CST 2016
     */
    List<PrjMaterialSupplement> SelectByPrimaryKeyList(List<Long> idList);
}