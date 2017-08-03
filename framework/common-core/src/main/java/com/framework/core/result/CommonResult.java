package com.framework.core.result;

import com.framework.core.constants.ComErrorCodeConstants;

public class CommonResult extends AbstractResult {
	private static final long serialVersionUID = 1L;
	public CommonResult (){
		this.resCode = ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode();
		this.msg = ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getMemo();
	}
	
	public CommonResult(ComErrorCodeConstants.ErrorCode code){
		this.resCode = code.getErrorCode();
		this.msg = code.getMemo();
	}
	
	public CommonResult(String resCode, String msg){
		this.resCode = resCode;
		this.msg = msg;
	}

}
