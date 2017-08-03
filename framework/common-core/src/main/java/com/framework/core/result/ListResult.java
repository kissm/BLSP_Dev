package com.framework.core.result;

import java.util.ArrayList;
import java.util.List;

import com.framework.core.constants.ComErrorCodeConstants;

public class ListResult<T> extends AbstractResult {
	private static final long serialVersionUID = 1L;

	public ListResult() {
		resCode = ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode();
		msg = ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getMemo();
	}

	public ListResult(ComErrorCodeConstants.ErrorCode code) {
		resCode = code.getErrorCode();
		msg = code.getMemo();
	}

	public ListResult(String resCode, String msg) {
		this.resCode = resCode;
		this.msg = msg;
	}

	public List<T> obj = new ArrayList<T>();

	public List<T> getObj() {
		return obj;
	}

	public void setObj(List<T> obj) {
		this.obj = obj;
	}

}
