package com.framework.core.result;

import com.framework.core.base.page.Page;
import com.framework.core.constants.ComErrorCodeConstants;

public class PageResult<T> extends AbstractResult {
	
	private static final long serialVersionUID = 1L;

	public PageResult (){
		this.resCode = ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode();
		this.msg = ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getMemo();
	}
	
	public PageResult(ComErrorCodeConstants.ErrorCode code){
		this.resCode = code.getErrorCode();
		this.msg = code.getMemo();
	}
	
	public PageResult(String resCode, String msg){
		this.resCode = resCode;
		this.msg = msg;
	}
	
	private Page<T> obj;

	public Page<T> getObj() {
		return obj;
	}

	public void setObj(Page<T> obj) {
		this.obj = obj;
	}
	
	

}
