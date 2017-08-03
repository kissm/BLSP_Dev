package com.framework.base.task.domain;

import java.util.Date;

import com.framework.base.support.Entity;

public class ScheduleJob extends Entity{
    private Long jobId;

    private String jobGroup;

    private String jobName;

    private String jobStatus;

    private String cronExpression;

    private String description;

    private String isConcurrent;

    private String url;

    private String paras;

    private String paratype;

    private Date createTime;

    private Date updateTime;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsConcurrent() {
        return isConcurrent;
    }

    public void setIsConcurrent(String isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParas() {
        return paras;
    }

    public void setParas(String paras) {
        this.paras = paras;
    }

    public String getParatype() {
        return paratype;
    }

    public void setParatype(String paratype) {
        this.paratype = paratype;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * format current object！
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ScheduleJob [jobId=" + jobId + ", jobGroup=" + jobGroup + ", jobName=" + jobName
                + ", jobStatus=" + jobStatus + ", cronExpression=" + cronExpression
                + ", description=" + description + ", isConcurrent=" + isConcurrent + ", url="
                + url + ", paras=" + paras + ", paratype=" + paratype + ", createTime="
                + createTime + ", updateTime=" + updateTime + "]";
    }
    
}