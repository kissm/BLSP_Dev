package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FormZjSgxkSqbSg;
import com.lpcode.modules.blsp.entity.FormZjSgxkSqbSgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FormZjSgxkSqbSgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    int countByExample(FormZjSgxkSqbSgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    int deleteByExample(FormZjSgxkSqbSgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    int deleteByPrimaryKey(Long cId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    int insert(FormZjSgxkSqbSg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    int insertSelective(FormZjSgxkSqbSg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    List<FormZjSgxkSqbSg> selectByExample(FormZjSgxkSqbSgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    FormZjSgxkSqbSg selectByPrimaryKey(Long cId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    int updateByExampleSelective(@Param("record") FormZjSgxkSqbSg record, @Param("example") FormZjSgxkSqbSgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    int updateByExample(@Param("record") FormZjSgxkSqbSg record, @Param("example") FormZjSgxkSqbSgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    int updateByPrimaryKeySelective(FormZjSgxkSqbSg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    int updateByPrimaryKey(FormZjSgxkSqbSg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    List<FormZjSgxkSqbSg> selectByEntitySelective(FormZjSgxkSqbSg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    FormZjSgxkSqbSg selectOneByEntitySelective(FormZjSgxkSqbSg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FormZjSgxkSqbSg> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    Page<FormZjSgxkSqbSg> pagedSelectByExample(@Param("example") FormZjSgxkSqbSgExample example, @Param("page") Page<FormZjSgxkSqbSg> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    Page<FormZjSgxkSqbSg> pagedSelectByEntity(@Param("record") FormZjSgxkSqbSg record, @Param("page") Page<FormZjSgxkSqbSg> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_sgxk_sqb_sg
     *
     * @mbggenerated Fri Jun 24 13:12:10 CST 2016
     */
    List<FormZjSgxkSqbSg> SelectByPrimaryKeyList(List<Long> cIdList);
}