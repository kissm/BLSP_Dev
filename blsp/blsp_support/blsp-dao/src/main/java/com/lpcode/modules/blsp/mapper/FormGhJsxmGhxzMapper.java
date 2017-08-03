package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FormGhJsxmGhxz;
import com.lpcode.modules.blsp.entity.FormGhJsxmGhxzExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormGhJsxmGhxzMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    int countByExample(FormGhJsxmGhxzExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    int deleteByExample(FormGhJsxmGhxzExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    int insert(FormGhJsxmGhxz record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    int insertSelective(FormGhJsxmGhxz record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    List<FormGhJsxmGhxz> selectByExample(FormGhJsxmGhxzExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    FormGhJsxmGhxz selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormGhJsxmGhxz record, @Param("example") FormGhJsxmGhxzExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    int updateByExample(@Param("record") FormGhJsxmGhxz record, @Param("example") FormGhJsxmGhxzExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    int updateByPrimaryKeySelective(FormGhJsxmGhxz record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    int updateByPrimaryKey(FormGhJsxmGhxz record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    List<FormGhJsxmGhxz> selectByEntitySelective(FormGhJsxmGhxz record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    FormGhJsxmGhxz selectOneByEntitySelective(FormGhJsxmGhxz record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FormGhJsxmGhxz> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    Page<FormGhJsxmGhxz> pagedSelectByExample(@Param("example") FormGhJsxmGhxzExample example, @Param("page") Page<FormGhJsxmGhxz> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    Page<FormGhJsxmGhxz> pagedSelectByEntity(@Param("record") FormGhJsxmGhxz record, @Param("page") Page<FormGhJsxmGhxz> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsxm_ghxz
     *
     * @mbggenerated Thu Jul 14 10:39:00 CST 2016
     */
    List<FormGhJsxmGhxz> SelectByPrimaryKeyList(List<Long> idList);
}