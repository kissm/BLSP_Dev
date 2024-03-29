package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FormZjJsgcZljdzcbSg;
import com.lpcode.modules.blsp.entity.FormZjJsgcZljdzcbSgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormZjJsgcZljdzcbSgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    int countByExample(FormZjJsgcZljdzcbSgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    int deleteByExample(FormZjJsgcZljdzcbSgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    int deleteByPrimaryKey(Long cId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    int insert(FormZjJsgcZljdzcbSg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    int insertSelective(FormZjJsgcZljdzcbSg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    List<FormZjJsgcZljdzcbSg> selectByExample(FormZjJsgcZljdzcbSgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    FormZjJsgcZljdzcbSg selectByPrimaryKey(Long cId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormZjJsgcZljdzcbSg record, @Param("example") FormZjJsgcZljdzcbSgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    int updateByExample(@Param("record") FormZjJsgcZljdzcbSg record, @Param("example") FormZjJsgcZljdzcbSgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    int updateByPrimaryKeySelective(FormZjJsgcZljdzcbSg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    int updateByPrimaryKey(FormZjJsgcZljdzcbSg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    List<FormZjJsgcZljdzcbSg> selectByEntitySelective(FormZjJsgcZljdzcbSg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    FormZjJsgcZljdzcbSg selectOneByEntitySelective(FormZjJsgcZljdzcbSg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FormZjJsgcZljdzcbSg> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    Page<FormZjJsgcZljdzcbSg> pagedSelectByExample(@Param("example") FormZjJsgcZljdzcbSgExample example, @Param("page") Page<FormZjJsgcZljdzcbSg> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    Page<FormZjJsgcZljdzcbSg> pagedSelectByEntity(@Param("record") FormZjJsgcZljdzcbSg record, @Param("page") Page<FormZjJsgcZljdzcbSg> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    List<FormZjJsgcZljdzcbSg> SelectByPrimaryKeyList(List<Long> cIdList);
}