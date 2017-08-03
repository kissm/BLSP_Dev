package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.PrjTaskTransferDetail;
import com.lpcode.modules.blsp.entity.PrjTaskTransferDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PrjTaskTransferDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int countByExample(PrjTaskTransferDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int deleteByExample(PrjTaskTransferDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int insert(PrjTaskTransferDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int insertSelective(PrjTaskTransferDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<PrjTaskTransferDetail> selectByExample(PrjTaskTransferDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    PrjTaskTransferDetail selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByExampleSelective(@Param("record") PrjTaskTransferDetail record, @Param("example") PrjTaskTransferDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByExample(@Param("record") PrjTaskTransferDetail record, @Param("example") PrjTaskTransferDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByPrimaryKeySelective(PrjTaskTransferDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    int updateByPrimaryKey(PrjTaskTransferDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<PrjTaskTransferDetail> selectByEntitySelective(PrjTaskTransferDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    PrjTaskTransferDetail selectOneByEntitySelective(PrjTaskTransferDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<PrjTaskTransferDetail> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    Page<PrjTaskTransferDetail> pagedSelectByExample(@Param("example") PrjTaskTransferDetailExample example, @Param("page") Page<PrjTaskTransferDetail> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    Page<PrjTaskTransferDetail> pagedSelectByEntity(@Param("record") PrjTaskTransferDetail record, @Param("page") Page<PrjTaskTransferDetail> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_task_transfer_detail
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    List<PrjTaskTransferDetail> SelectByPrimaryKeyList(List<Long> idList);
}