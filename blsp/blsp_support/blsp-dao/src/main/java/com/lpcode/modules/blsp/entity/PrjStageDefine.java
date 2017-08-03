package com.lpcode.modules.blsp.entity;

import java.util.Date;
项目的四个阶段
public class PrjStageDefine {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prj_stage_define.ID
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prj_stage_define.IS_DELETE
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    private String isDelete;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prj_stage_define.STAGE_NAME
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    项目的环节  id 1,政府项目， 其他是企业项目。11-15
    private String stageName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prj_stage_define.PRE_STAGE_ID
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    private Long preStageId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prj_stage_define.STAGE_TYPE
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    政府项目或者企业项目
    private String stageType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prj_stage_define.STAGE_DESC
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    private String stageDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prj_stage_define.TIME_TYPE
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    工作日或自然日
    private String timeType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prj_stage_define.STAGE_TIME_LIMIT
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    项目阶段日期限制
    private Integer stageTimeLimit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prj_stage_define.USER_MATERIAL_DAYS
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    默认2天
    private Integer userMaterialDays;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prj_stage_define.CREAT_TIME
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    private Date creatTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prj_stage_define.CREATOR
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prj_stage_define.UPDATE_TIME
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prj_stage_define.UPDATOR
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    private String updator;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prj_stage_define.ID
     *
     * @return the value of prj_stage_define.ID
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public Long getId() {

        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prj_stage_define.ID
     *
     * @param id the value for prj_stage_define.ID
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prj_stage_define.IS_DELETE
     *
     * @return the value of prj_stage_define.IS_DELETE
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public String getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prj_stage_define.IS_DELETE
     *
     * @param isDelete the value for prj_stage_define.IS_DELETE
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prj_stage_define.STAGE_NAME
     *
     * @return the value of prj_stage_define.STAGE_NAME
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public String getStageName() {
        return stageName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prj_stage_define.STAGE_NAME
     *
     * @param stageName the value for prj_stage_define.STAGE_NAME
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prj_stage_define.PRE_STAGE_ID
     *
     * @return the value of prj_stage_define.PRE_STAGE_ID
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public Long getPreStageId() {
        return preStageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prj_stage_define.PRE_STAGE_ID
     *
     * @param preStageId the value for prj_stage_define.PRE_STAGE_ID
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setPreStageId(Long preStageId) {
        this.preStageId = preStageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prj_stage_define.STAGE_TYPE
     *
     * @return the value of prj_stage_define.STAGE_TYPE
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public String getStageType() {
        return stageType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prj_stage_define.STAGE_TYPE
     *
     * @param stageType the value for prj_stage_define.STAGE_TYPE
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setStageType(String stageType) {
        this.stageType = stageType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prj_stage_define.STAGE_DESC
     *
     * @return the value of prj_stage_define.STAGE_DESC
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public String getStageDesc() {
        return stageDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prj_stage_define.STAGE_DESC
     *
     * @param stageDesc the value for prj_stage_define.STAGE_DESC
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setStageDesc(String stageDesc) {
        this.stageDesc = stageDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prj_stage_define.TIME_TYPE
     *
     * @return the value of prj_stage_define.TIME_TYPE
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public String getTimeType() {
        return timeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prj_stage_define.TIME_TYPE
     *
     * @param timeType the value for prj_stage_define.TIME_TYPE
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prj_stage_define.STAGE_TIME_LIMIT
     *
     * @return the value of prj_stage_define.STAGE_TIME_LIMIT
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public Integer getStageTimeLimit() {
        return stageTimeLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prj_stage_define.STAGE_TIME_LIMIT
     *
     * @param stageTimeLimit the value for prj_stage_define.STAGE_TIME_LIMIT
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setStageTimeLimit(Integer stageTimeLimit) {
        this.stageTimeLimit = stageTimeLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prj_stage_define.USER_MATERIAL_DAYS
     *
     * @return the value of prj_stage_define.USER_MATERIAL_DAYS
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public Integer getUserMaterialDays() {
        return userMaterialDays;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prj_stage_define.USER_MATERIAL_DAYS
     *
     * @param userMaterialDays the value for prj_stage_define.USER_MATERIAL_DAYS
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setUserMaterialDays(Integer userMaterialDays) {
        this.userMaterialDays = userMaterialDays;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prj_stage_define.CREAT_TIME
     *
     * @return the value of prj_stage_define.CREAT_TIME
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public Date getCreatTime() {
        return creatTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prj_stage_define.CREAT_TIME
     *
     * @param creatTime the value for prj_stage_define.CREAT_TIME
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prj_stage_define.CREATOR
     *
     * @return the value of prj_stage_define.CREATOR
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prj_stage_define.CREATOR
     *
     * @param creator the value for prj_stage_define.CREATOR
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prj_stage_define.UPDATE_TIME
     *
     * @return the value of prj_stage_define.UPDATE_TIME
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prj_stage_define.UPDATE_TIME
     *
     * @param updateTime the value for prj_stage_define.UPDATE_TIME
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prj_stage_define.UPDATOR
     *
     * @return the value of prj_stage_define.UPDATOR
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prj_stage_define.UPDATOR
     *
     * @param updator the value for prj_stage_define.UPDATOR
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setUpdator(String updator) {
        this.updator = updator;
    }
}