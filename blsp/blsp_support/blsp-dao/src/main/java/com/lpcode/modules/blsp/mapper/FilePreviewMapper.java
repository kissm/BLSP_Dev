package com.lpcode.modules.blsp.mapper;

import com.framework.core.base.page.Page;
import com.framework.mybatis.annotation.Batch;
import com.lpcode.modules.blsp.entity.FilePreview;
import com.lpcode.modules.blsp.entity.FilePreviewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FilePreviewMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    int countByExample(FilePreviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    int deleteByExample(FilePreviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    int insert(FilePreview record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    int insertSelective(FilePreview record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    List<FilePreview> selectByExample(FilePreviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    FilePreview selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    int updateByExampleSelective(@Param("record") FilePreview record, @Param("example") FilePreviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    int updateByExample(@Param("record") FilePreview record, @Param("example") FilePreviewExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    int updateByPrimaryKeySelective(FilePreview record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    int updateByPrimaryKey(FilePreview record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    List<FilePreview> selectByEntitySelective(FilePreview record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    FilePreview selectOneByEntitySelective(FilePreview record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    @Batch
    int insertSelectiveBatch(List<FilePreview> records);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    Page<FilePreview> pagedSelectByExample(@Param("example") FilePreviewExample example, @Param("page") Page<FilePreview> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    Page<FilePreview> pagedSelectByEntity(@Param("record") FilePreview record, @Param("page") Page<FilePreview> page);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_file_preview
     *
     * @mbggenerated Mon Jul 18 16:05:37 CST 2016
     */
    List<FilePreview> SelectByPrimaryKeyList(List<Long> idList);
}