/**
 * @Probject Name: 00_product_common
 * @Path: com.bailian.core.framework.exceptionMyExceptionHandle.java
 * @Create By wangfei
 * @Create In 2014年9月28日 下午6:30:58
 */
package com.framework.core.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.framework.core.constants.ComErrorCodeConstants;

/**
 * @Class Name MyExceptionHandle
 * @Author wangfei
 * @Create In 2014年9月28日
 */
@Deprecated
public class JsonResponseExHandler implements HandlerExceptionResolver {
	private static final Logger loggger = LoggerFactory.getLogger(JsonResponseExHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		loggger.error("controller调用异常，{}", ex);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("success", "false");
		// 根据不同错误转向不同页面
		if (ex instanceof BusinessException) {
			model.put("resCode", ((BusinessException) ex).getCode());
			model.put("msg", ((BusinessException) ex).getMessage());
		} else {
			model.put("resCode", ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode());
			model.put("msg", ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getMemo());
		}
		return new ModelAndView("exception/jsonResponse", model);
	}
}
