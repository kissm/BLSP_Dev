/**
 * @Probject Name: quartz-monitor
 * @Path: com.framework.base.supportBleException.java
 * @Create By mogu
 * @Create In 2014年12月23日 下午1:07:05
 * TODO
 */
package com.framework.base.support;

/**
 * @Class Name BaseException
 * @Author mogu
 * @Create In 2014年12月23日
 */
public class BaseException extends RuntimeException{
    /**
     * @Field long serialVersionUID 
     */
    private static final long serialVersionUID = 2177465700058573980L;
    private String code;

    public BaseException() {

    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
