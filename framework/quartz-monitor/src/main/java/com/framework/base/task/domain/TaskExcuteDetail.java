package com.framework.base.task.domain;

import java.util.Date;

public class TaskExcuteDetail {
    
    private Long id;

    private String status;

    private String descCode;

    private String descMsg;

    private String receiptParas;

    private String receiptIp;

    private Long jobId;

    private Date createDate;
    
    private String createDateStr;
    
    private Date updateDate;

    private String updateDateStr;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescCode() {
        return descCode;
    }

    public void setDescCode(String descCode) {
        this.descCode = descCode;
    }

    public String getDescMsg() {
        return descMsg;
    }

    public void setDescMsg(String descMsg) {
        this.descMsg = descMsg;
    }

    public String getReceiptParas() {
        return receiptParas;
    }

    public void setReceiptParas(String receiptParas) {
        this.receiptParas = receiptParas;
    }

    public String getReceiptIp() {
        return receiptIp;
    }

    public void setReceiptIp(String receiptIp) {
        this.receiptIp = receiptIp;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @Return the String createDateStr
     */
    public String getCreateDateStr() {
        return createDateStr;
    }

    /**
     * @Param String createDateStr to set
     */
    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    /**
     * @Return the String updateDateStr
     */
    public String getUpdateDateStr() {
        return updateDateStr;
    }

    /**
     * @Param String updateDateStr to set
     */
    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    /**
     * format current object
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TaskExcuteDetail [id=");
        builder.append(id);
        builder.append(", status=");
        builder.append(status);
        builder.append(", descCode=");
        builder.append(descCode);
        builder.append(", descMsg=");
        builder.append(descMsg);
        builder.append(", receiptParas=");
        builder.append(receiptParas);
        builder.append(", receiptIp=");
        builder.append(receiptIp);
        builder.append(", jobId=");
        builder.append(jobId);
        builder.append(", createDate=");
        builder.append(createDate);
        builder.append(", createDateStr=");
        builder.append(createDateStr);
        builder.append(", updateDate=");
        builder.append(updateDate);
        builder.append(", updateDateStr=");
        builder.append(updateDateStr);
        builder.append("]");
        return builder.toString();
    }
    
}