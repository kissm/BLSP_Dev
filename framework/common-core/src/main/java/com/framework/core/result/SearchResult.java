package com.framework.core.result;


import com.framework.core.base.page.SearchResultPage;
import com.framework.core.constants.ComErrorCodeConstants;

public class SearchResult<T> extends AbstractResult {
	private static final long serialVersionUID = 1L;
	public SearchResult (){
		this.resCode = ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode();
		this.msg = ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getMemo();
	}
	
	public SearchResult(ComErrorCodeConstants.ErrorCode code){
		this.resCode = code.getErrorCode();
		this.msg = code.getMemo();
	}
	
	public SearchResult(String resCode, String msg){
		this.resCode = resCode;
		this.msg = msg;
	}
	
	private SearchResultPage<T> obj;

	public SearchResultPage<T> getObj() {
		return obj;
	}

	public void setObj(SearchResultPage<T> obj) {
		this.obj = obj;
	}
	
	

}
