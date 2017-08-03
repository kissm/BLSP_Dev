package com.lpcode.modules.blsp.entity;

import com.framework.mybatis.interceptor.Identity;
import java.util.Date;

public class ServiceBasicinfoPushLog extends Identity {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_basicinfo_push_log.sb_id
     *
     * @mbggenerated Thu Dec 08 18:40:59 CST 2016
     */
    private String sbId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_basicinfo_push_log.sb_basic_id
     *
     * @mbggenerated Thu Dec 08 18:40:59 CST 2016
     */
    private String sbBasicId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_basicinfo_push_log.is_success
     *
     * @mbggenerated Thu Dec 08 18:40:59 CST 2016
     */
    private Byte isSuccess;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_basicinfo_push_log.creation_time
     *
     * @mbggenerated Thu Dec 08 18:40:59 CST 2016
     */
    private Date creationTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column service_basicinfo_push_log.last_push_time
     *
     * @mbggenerated Thu Dec 08 18:40:59 CST 2016
     */
    private Date lastPushTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_basicinfo_push_log.sb_id
     *
     * @return the value of service_basicinfo_push_log.sb_id
     *
     * @mbggenerated Thu Dec 08 18:40:59 CST 2016
     */
    public String getSbId() {
        return sbId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_basicinfo_push_log.sb_id
     *
     * @param sbId the value for service_basicinfo_push_log.sb_id
     *
     * @mbggenerated Thu Dec 08 18:40:59 CST 2016
     */
    public void setSbId(String sbId) {
        this.sbId = sbId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_basicinfo_push_log.sb_basic_id
     *
     * @return the value of service_basicinfo_push_log.sb_basic_id
     *
     * @mbggenerated Thu Dec 08 18:40:59 CST 2016
     */
    public String getSbBasicId() {
        return sbBasicId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_basicinfo_push_log.sb_basic_id
     *
     * @param sbBasicId the value for service_basicinfo_push_log.sb_basic_id
     *
     * @mbggenerated Thu Dec 08 18:40:59 CST 2016
     */
    public void setSbBasicId(String sbBasicId) {
        this.sbBasicId = sbBasicId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_basicinfo_push_log.is_success
     *
     * @return the value of service_basicinfo_push_log.is_success
     *
     * @mbggenerated Thu Dec 08 18:40:59 CST 2016
     */
    public Byte getIsSuccess() {
        return isSuccess;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_basicinfo_push_log.is_success
     *
     * @param isSuccess the value for service_basicinfo_push_log.is_success
     *
     * @mbggenerated Thu Dec 08 18:40:59 CST 2016
     */
    public void setIsSuccess(Byte isSuccess) {
        this.isSuccess = isSuccess;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_basicinfo_push_log.creation_time
     *
     * @return the value of service_basicinfo_push_log.creation_time
     *
     * @mbggenerated Thu Dec 08 18:40:59 CST 2016
     */
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_basicinfo_push_log.creation_time
     *
     * @param creationTime the value for service_basicinfo_push_log.creation_time
     *
     * @mbggenerated Thu Dec 08 18:40:59 CST 2016
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column service_basicinfo_push_log.last_push_time
     *
     * @return the value of service_basicinfo_push_log.last_push_time
     *
     * @mbggenerated Thu Dec 08 18:40:59 CST 2016
     */
    public Date getLastPushTime() {
        return lastPushTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column service_basicinfo_push_log.last_push_time
     *
     * @param lastPushTime the value for service_basicinfo_push_log.last_push_time
     *
     * @mbggenerated Thu Dec 08 18:40:59 CST 2016
     */
    public void setLastPushTime(Date lastPushTime) {
        this.lastPushTime = lastPushTime;
    }
}