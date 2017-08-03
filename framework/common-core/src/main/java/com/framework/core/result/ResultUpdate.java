package com.framework.core.result;

import com.framework.core.constants.ComErrorCodeConstants;

/**
 * Package: com.mogubang.dto Created by 解建君. Date: 15-5-28 Time: 下午5:32
 */
public class ResultUpdate extends AbstractResult {
	private static final long serialVersionUID = 1L;

	public ResultUpdate(String resCode, String msg) {
		this.resCode = resCode;
		this.msg = msg;
	}

	public ResultUpdate() {
		resCode = ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode();
		msg = ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getMemo();
	}
}
