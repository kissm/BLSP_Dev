package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FormZjGczlZszrcns;
import com.lpcode.modules.blsp.entity.FormZjGczlZszrcnsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormZjGczlZszrcnsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    int countByExample(FormZjGczlZszrcnsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    int deleteByExample(FormZjGczlZszrcnsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    int insert(FormZjGczlZszrcns record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    int insertSelective(FormZjGczlZszrcns record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    List<FormZjGczlZszrcns> selectByExample(FormZjGczlZszrcnsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    FormZjGczlZszrcns selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormZjGczlZszrcns record, @Param("example") FormZjGczlZszrcnsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    int updateByExample(@Param("record") FormZjGczlZszrcns record, @Param("example") FormZjGczlZszrcnsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    int updateByPrimaryKeySelective(FormZjGczlZszrcns record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    int updateByPrimaryKey(FormZjGczlZszrcns record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    List<FormZjGczlZszrcns> selectByEntitySelective(FormZjGczlZszrcns record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    FormZjGczlZszrcns selectOneByEntitySelective(FormZjGczlZszrcns record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FormZjGczlZszrcns> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    Page<FormZjGczlZszrcns> pagedSelectByExample(@Param("example") FormZjGczlZszrcnsExample example, @Param("page") Page<FormZjGczlZszrcns> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    Page<FormZjGczlZszrcns> pagedSelectByEntity(@Param("record") FormZjGczlZszrcns record, @Param("page") Page<FormZjGczlZszrcns> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_gczl_zszrcns
     *
     * @mbggenerated Mon Jun 27 11:22:06 CST 2016
     */
    List<FormZjGczlZszrcns> SelectByPrimaryKeyList(List<Long> idList);
}