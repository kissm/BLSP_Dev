package com.lpcode.modules.blsp.entity;

public class LogEmailWithBLOBs extends LogEmail {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mg_log_email.data
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    private String data;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mg_log_email.receivers
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    private String receivers;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mg_log_email.message
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    private String message;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mg_log_email.content
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    private String content;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mg_log_email.data
     *
     * @return the value of mg_log_email.data
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public String getData() {
        return data;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mg_log_email.data
     *
     * @param data the value for mg_log_email.data
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mg_log_email.receivers
     *
     * @return the value of mg_log_email.receivers
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public String getReceivers() {
        return receivers;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mg_log_email.receivers
     *
     * @param receivers the value for mg_log_email.receivers
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mg_log_email.message
     *
     * @return the value of mg_log_email.message
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public String getMessage() {
        return message;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mg_log_email.message
     *
     * @param message the value for mg_log_email.message
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mg_log_email.content
     *
     * @return the value of mg_log_email.content
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mg_log_email.content
     *
     * @param content the value for mg_log_email.content
     *
     * @mbggenerated Mon Jun 20 17:28:14 CST 2016
     */
    public void setContent(String content) {
        this.content = content;
    }
}