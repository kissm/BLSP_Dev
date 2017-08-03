package com.framework.core.base.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.framework.core.constants.ComErrorCodeConstants;
import com.framework.core.exception.BusinessException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Controller基类
 *
 * @Author wangfei, zhangjie
 * @Create In 2014年10月8日
 */

public class BaseController {

	public static final String VIEW = "view";
	public static final String LIST = "list";
	public static final String STATUS = "resCode";
	// public static final String WARN = "warn";
	// public static final String ERROR = "error";
	public static final String MESSAGE = "msg";
	public static final String MESSAGES = "msgs";
	public static final String CONTENT = "content";

	protected Logger logger = LoggerFactory.getLogger(BaseController.class);

	private static Gson GSON = new GsonBuilder().enableComplexMapKeySerialization().setDateFormat("yyyy-MM-dd HH:mm:ss")
			.create();

	/**
	 * AJAX输出，返回null
	 *
	 * @param content
	 * @param type
	 * @return
	 */
	public String ajax(HttpServletResponse response, String content, String type) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			logger.error("IOException:", e);
		}
		return null;
	}

	/**
	 * AJAX输出文本，返回null
	 *
	 * @param text
	 * @return
	 */
	public String ajaxText(HttpServletResponse response, String text) {
		return ajax(response, text, "text/plain");
	}

	/**
	 * AJAX输出HTML，返回null
	 *
	 * @param html
	 * @return
	 */
	public String ajaxHtml(HttpServletResponse response, String html) {
		return ajax(response, html, "text/html");
	}

	/**
	 * AJAX输出XML，返回null
	 *
	 * @param xml
	 * @return
	 */
	public String ajaxXml(HttpServletResponse response, String xml) {
		return ajax(response, xml, "text/xml");
	}

	/**
	 * 根据字符串输出JSON，返回null
	 *
	 * @param jsonString
	 * @return
	 */
	public String ajaxJson(HttpServletResponse response, String jsonString) {
		return ajax(response, jsonString, "text/html");
	}

	/**
	 * 根据Map输出JSON，返回null
	 *
	 * @param jsonMap
	 * @return
	 */
	public String ajaxJson(HttpServletResponse response, Map<String, String> jsonMap) {
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON警告消息，返回null
	 *
	 * @param message
	 * @return
	 */
	public String ajaxJsonWarnMessage(HttpServletResponse response, String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, ComErrorCodeConstants.ErrorCode.COMMON_WARN.getErrorCode());
		jsonMap.put(MESSAGE, message);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON警告消息，返回null
	 *
	 * @param messages
	 * @return
	 */
	public String ajaxJsonWarnMessages(HttpServletResponse response, List<String> messages) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS, ComErrorCodeConstants.ErrorCode.COMMON_WARN.getErrorCode());
		jsonMap.put(MESSAGES, messages);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON成功消息，返回null
	 *
	 * @param message
	 * @return
	 */
	public String ajaxJsonSuccessMessage(HttpServletResponse response, String message) {

		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode());
		jsonMap.put(MESSAGE, message);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON成功消息，返回null
	 *
	 * @param messages
	 * @return
	 */
	public String ajaxJsonSuccessMessages(HttpServletResponse response, List<String> messages) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS, ComErrorCodeConstants.ErrorCode.SYSTEM_SUCCESS.getErrorCode());
		jsonMap.put(MESSAGES, messages);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON错误消息，返回null
	 *
	 * @param message
	 * @return
	 */
	public String ajaxJsonErrorMessage(HttpServletResponse response, String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode());
		jsonMap.put(MESSAGE, message);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 输出JSON错误消息，返回null
	 *
	 * @param messages
	 * @return
	 */
	public String ajaxJsonErrorMessages(HttpServletResponse response, List<String> messages) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS, ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode());
		jsonMap.put(MESSAGES, messages);
		return ajax(response, GSON.toJson(jsonMap), "text/html");
	}

	/**
	 * 设置页面不缓存
	 */
	public void setResponseNoCache(HttpServletResponse response) {
		response.setHeader("progma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
	}

	/**
	 * 根据Object输出JSON字符串
	 */
	public String getJson(Object jsonObject) {
		return GSON.toJson(jsonObject);
	}

	/**
	 * 根据字符串输出JSON，返回null
	 *
	 * @param jsonString
	 * @return
	 */
	public String ajaxJsonCache(HttpServletResponse response, String jsonString, String cacheTime) {
		return ajaxCache(response, jsonString, "text/html", cacheTime);
	}

	/**
	 * AJAX输出，返回null
	 *
	 * @param content
	 * @param type
	 * @return
	 */
	public String ajaxCache(HttpServletResponse response, String content, String type, String cacheTime) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			setCache(response, cacheTime);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public void setCache(HttpServletResponse response, String cacheTime) {
		long now = System.currentTimeMillis();
		long cacheTimeLong = Long.parseLong(cacheTime);
		response.setDateHeader("Expires", now + cacheTimeLong);
		response.setDateHeader("Last-Modified", now - now % cacheTimeLong);
		response.setHeader("Cache-Control", "max-age=" + cacheTime);
		response.setHeader("Pragma", "Pragma");
	}

	/**
	 * 公共校验参数方法
	 *
	 * @Methods Name validateParas
	 * @Create In 2014年10月8日 By wangfei
	 * @param parametersBindingResult
	 * @param map
	 * @return boolean
	 */
	protected boolean validateParas(BindingResult parametersBindingResult, Map<String, Object> map) {
		if (parametersBindingResult.hasErrors()) {
			List<FieldError> fes = parametersBindingResult.getFieldErrors();
			String checkMsg = fes.get(0).getDefaultMessage();
			map.put(STATUS, ComErrorCodeConstants.ErrorCode.VALIDATE_ERROR.getErrorCode());
			map.put(MESSAGE, checkMsg);
			return false;
		}
		return true;
	}

	/**
	 * spring API请求格式通用处理
	 *
	 * @Methods Name handleHttpMessageConversionException
	 * @Create In 2014年10月29日 By wangfei
	 * @param error
	 * @return String
	 */
	@ExceptionHandler({ BusinessException.class, BindException.class, MethodArgumentNotValidException.class })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	protected Map<String, Object> handleBusException(Exception ex) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.error(ex.getMessage(), ex);
		if (ex instanceof BindException) {
			BindingResult result = ((BindException) ex).getBindingResult();
			List<FieldError> fes = result.getFieldErrors();
			String checkMsg = fes.get(0).getDefaultMessage();
			map.put(STATUS, ComErrorCodeConstants.ErrorCode.VALIDATE_ERROR.getErrorCode());
			map.put("msg", checkMsg);
		}
		// 判断异常类型
		else if (ex instanceof MethodArgumentNotValidException) {
			// org.springframework.validation.BindException
			BindingResult result = ((MethodArgumentNotValidException) ex).getBindingResult();
			List<FieldError> fes = result.getFieldErrors();
			String checkMsg = fes.get(0).getDefaultMessage();
			map.put(STATUS, ComErrorCodeConstants.ErrorCode.VALIDATE_ERROR.getErrorCode());
			map.put("msg", checkMsg);
		} else {
			logger.info("throw BleException!cause:{}", ex);
			map.put("resCode", ((BusinessException) ex).getCode());
			map.put("msg", ((BusinessException) ex).getMessage());
		}
		return map;
	}

	/**
	 * spring API请求格式通用处理
	 *
	 * @Methods Name handleHttpMessageConversionException
	 * @Create In 2014年10月29日 By wangfei
	 * @param error
	 * @return String
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	protected Map<String, Object> handleCommonException(Exception ex) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.error(ex.getMessage(), ex);
		if (ex instanceof HttpMessageConversionException) {
			map.put(STATUS, ComErrorCodeConstants.ErrorCode.PARA_NORULE_WARN.getErrorCode());
			map.put(MESSAGE, ex.getMessage());
		} else if (ex instanceof HttpMediaTypeException) {
			// 请求类型有误
			map.put(STATUS, "");
			// map.put("msg", "请求类型有误!");
			map.put(MESSAGE, ex.getMessage());
		} else if (ex instanceof TypeMismatchException) {
			// 请求类型有误
			map.put(STATUS, "");
			// map.put("msg", "参数类型不匹配!");
			map.put(MESSAGE, ex.getMessage());
		} else if (ex instanceof MissingServletRequestParameterException) {
			map.put(STATUS, "");
			// map.put("msg", "请检查必传参数!");
			map.put(MESSAGE, ex.getMessage());
		} else if (ex instanceof BusinessException) {
			map.put(STATUS, ((BusinessException) ex).getCode());
			// map.put("msg", ((BleException) ex).getMessage());
			map.put(MESSAGE, ex.getMessage());
		} else {
			map.put(STATUS, ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode());
			map.put(MESSAGE, ex.getMessage());
		}
		return map;
	}
}
