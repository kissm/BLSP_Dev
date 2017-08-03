package com.framework.core.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.core.base.page.Page;
import com.framework.core.constants.ComErrorCodeConstants;

/**
 * 格式化响应信息的工具类
 *
 * @author kongxiangshuai
 * @date 2013-12-12
 * @modify
 */
public class ResultUtil {

	@SuppressWarnings("rawtypes")
	public static Map<String, Object> crePageSucResult(Page page) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resCode", ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode());
		resultMap.put("obj", page);
		return resultMap;
	}

	public static Map<String, Object> creObjSucResult(Object obj) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resCode", ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode());
		resultMap.put("obj", obj);
		return resultMap;
	}

	public static Map<String, Object> creComSucResult() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resCode", ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode());
		return resultMap;
	}

	/**
	 * 格式化非分页返回list的json响应
	 *
	 * @Methods Name creListSucResult
	 * @Create In 2014年12月12日 By wangfei
	 * @param list
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> creListSucResult(List list) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resCode", ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode());
		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("list", list);
		resultMap.put("obj", listMap);
		return resultMap;
	}

	// public static Map<String, Object> creComErrorResult(String message) {
	// if(message == null || message.equals("") ) {
	// return creComErrorResult();
	// } else {
	// Map<String, Object> resultMap = new HashMap<String, Object>();
	// resultMap.put("resCode",
	// ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode());
	// resultMap.put("msg", message);
	// return resultMap;
	// }
	// }

	// /**
	// * 返回空查询结果
	// * @return
	// */
	// public static Map<String, Object> creComEmptyResult(){
	// Map<String, Object> resultMap = new HashMap<String, Object>();
	// resultMap.put("resCode",
	// ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode());
	// resultMap.put("msg",
	// ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
	// return resultMap;
	// }

	//
	// public static Map<String, Object> creComErrorResult(String
	// errorCode,String message) {
	// if(message == null || message.equals("") ) {
	// return creComErrorResult();
	// } else {
	// Map<String, Object> resultMap = new HashMap<String, Object>();
	// resultMap.put("resCode", errorCode);
	// resultMap.put("msg", message);
	// return resultMap;
	// }
	// }

	// public static Map<String, Object> creComErrorResult() {
	// Map<String, Object> resultMap = new HashMap<String, Object>();
	// resultMap.put("resCode",
	// ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getErrorCode());
	// resultMap.put("msg",
	// ComErrorCodeConstants.ErrorCode.DATA_EMPTY_ERROR.getMemo());
	// return resultMap;
	// }

	/**
	 * 格式化非分页返回list的json响应
	 *
	 * @param <T>
	 * @Methods Name creListErrorResult
	 * @Create In Dec 23, 2014 By guowenqin
	 * @param list
	 * @return Map<String,Object>
	 */
	// public static <T> Map<String, Object> creListErrorResult(List<T> list) {
	// Map<String, Object> resultMap = new HashMap<String, Object>();
	// resultMap.put("resCode",
	// ComErrorCodeConstants.ErrorCode.APPLICATION_OPER_ERROR.getErrorCode());
	// Map<String, Object> listMap = new HashMap<String, Object>();
	// listMap.put("list", list);
	// resultMap.put("obj",listMap);
	// return resultMap;
	// }

}
