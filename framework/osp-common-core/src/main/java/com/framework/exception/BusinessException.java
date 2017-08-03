package com.framework.exception;

import com.framework.core.constants.IErrorCode;

public class BusinessException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 8710396445793589764L;
    private String code = null;

    public BusinessException() {
    }

    public BusinessException(IErrorCode error){
        super(error.getMemo());
        this.code=error.getCode();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
