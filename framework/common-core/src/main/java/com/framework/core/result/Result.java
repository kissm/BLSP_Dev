package com.framework.core.result;

import com.framework.core.constants.ComErrorCodeConstants;


public class Result<T> extends AbstractResult {
	private static final long serialVersionUID = 1L;
	public Result (){
		this.resCode = ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode();
		this.msg = ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getMemo();
	}

	public Result(T obj) {
    this.resCode = ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode();
    this.msg = ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getMemo();
		this.obj = obj;
	}

	public Result(ComErrorCodeConstants.ErrorCode code){
		this.resCode = code.getErrorCode();
		this.msg = code.getMemo();
	}
	
	public Result(String resCode, String msg){
		this.resCode = resCode;
		this.msg = msg;
	}
	
	private T obj;

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

}
