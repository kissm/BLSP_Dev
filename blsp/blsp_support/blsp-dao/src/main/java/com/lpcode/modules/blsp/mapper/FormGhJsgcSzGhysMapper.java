package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FormGhJsgcSzGhys;
import com.lpcode.modules.blsp.entity.FormGhJsgcSzGhysExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormGhJsgcSzGhysMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    int countByExample(FormGhJsgcSzGhysExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    int deleteByExample(FormGhJsgcSzGhysExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    int insert(FormGhJsgcSzGhys record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    int insertSelective(FormGhJsgcSzGhys record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    List<FormGhJsgcSzGhys> selectByExample(FormGhJsgcSzGhysExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    FormGhJsgcSzGhys selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormGhJsgcSzGhys record, @Param("example") FormGhJsgcSzGhysExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    int updateByExample(@Param("record") FormGhJsgcSzGhys record, @Param("example") FormGhJsgcSzGhysExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    int updateByPrimaryKeySelective(FormGhJsgcSzGhys record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    int updateByPrimaryKey(FormGhJsgcSzGhys record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    List<FormGhJsgcSzGhys> selectByEntitySelective(FormGhJsgcSzGhys record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    FormGhJsgcSzGhys selectOneByEntitySelective(FormGhJsgcSzGhys record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FormGhJsgcSzGhys> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    Page<FormGhJsgcSzGhys> pagedSelectByExample(@Param("example") FormGhJsgcSzGhysExample example, @Param("page") Page<FormGhJsgcSzGhys> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    Page<FormGhJsgcSzGhys> pagedSelectByEntity(@Param("record") FormGhJsgcSzGhys record, @Param("page") Page<FormGhJsgcSzGhys> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_sz_ghys
     *
     * @mbggenerated Mon Jun 27 14:25:19 CST 2016
     */
    List<FormGhJsgcSzGhys> SelectByPrimaryKeyList(List<Long> idList);
}