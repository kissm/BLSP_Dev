/**
 *
 */
package com.framework.osp.common.web;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.framework.core.constants.ComErrorCodeConstants;
import com.framework.exception.BusinessException;
import com.framework.osp.common.beanvalidator.BeanValidators;
import com.framework.osp.common.mapper.JsonMapper;
import com.framework.osp.common.utils.DateUtils;

/**
 * 控制器支持类
 *
 * @author osp
 * @version 2013-3-23
 */
public abstract class BaseController {
	public static final String STATUS = "resCode";
	public static final String MESSAGE = "msg";
	public static final String CONTENT = "content";

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 管理基础路径
	 */
	@Value("${adminPath}")
	protected String adminPath;

	/**
	 * 前端基础路径
	 */
	@Value("${frontPath}")
	protected String frontPath;

	/**
	 * 前端URL后缀
	 */
	@Value("${urlSuffix}")
	protected String urlSuffix;

	/**
	 * 验证Bean实例对象
	 */
	@Autowired
	protected Validator validator;

	/**
	 * 服务端参数有效性验证
	 *
	 * @param object
	 *            验证的实体对象
	 * @param groups
	 *            验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
	 */
	protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
		try {
			BeanValidators.validateWithException(validator, object, groups);
		} catch (ConstraintViolationException ex) {
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(model, list.toArray(new String[] {}));
			return false;
		}
		return true;
	}

	/**
	 * 服务端参数有效性验证
	 *
	 * @param object
	 *            验证的实体对象
	 * @param groups
	 *            验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
	 */
	protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
		try {
			BeanValidators.validateWithException(validator, object, groups);
		} catch (ConstraintViolationException ex) {
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(redirectAttributes, list.toArray(new String[] {}));
			return false;
		}
		return true;
	}

	/**
	 * 服务端参数有效性验证
	 *
	 * @param object
	 *            验证的实体对象
	 * @param groups
	 *            验证组，不传入此参数时，同@Valid注解验证
	 * @return 验证成功：继续执行；验证失败：抛出异常跳转400页面。
	 */
	protected void beanValidator(Object object, Class<?>... groups) {
		BeanValidators.validateWithException(validator, object, groups);
	}

	/**
	 * 添加Model消息
	 *
	 * @param messages
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages) {
			sb.append(message).append(messages.length > 1 ? "<br/>" : "");
		}
		model.addAttribute("message", sb.toString());
	}

	/**
	 * 添加Flash消息
	 *
	 * @param messages
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages) {
			sb.append(message).append(messages.length > 1 ? "<br/>" : "");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}

	/**
	 * 客户端返回JSON字符串
	 *
	 * @param response
	 * @param object
	 * @return
	 */
	protected String renderString(HttpServletResponse response, Object object) {
		return renderString(response, JsonMapper.toJsonString(object), "application/json");
	}

	/**
	 * 客户端返回字符串
	 *
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
			response.setContentType(type);
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 参数绑定异常
	 */
	@ExceptionHandler({ BindException.class, ConstraintViolationException.class, ValidationException.class })
	public String bindException() {
		return "error/400";
	}

	/**
	 * 授权登录异常
	 */
	@ExceptionHandler({ AuthenticationException.class })
	public String authenticationException() {
		return "error/403";
	}

	@InitBinder
	public void initListBinder(WebDataBinder binder) {
		// 设置需要包裹的元素个数，默认为256
		binder.setAutoGrowCollectionLimit(2048);
	}

	/**
	 * 初始化数据绑定 1. 将所有传递进来的String进行HTML编码，防止XSS攻击 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {

		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}

			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
			// @Override
			// public String getAsText() {
			// Object value = getValue();
			// return value != null ? DateUtils.formatDateTime((Date)value) :
			// "";
			// }
		});
	}

	/**
	 * spring API请求格式通用处理
	 *
	 * @param ex
	 * @return String
	 * @Methods Name handleHttpMessageConversionException
	 * @Create In 2014年10月29日 By wangfei
	 */
	@ExceptionHandler({ BusinessException.class, MethodArgumentNotValidException.class })
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	protected Map<String, Object> handleBusException(Exception ex) {
		Map<String, Object> map = new HashMap<String, Object>();
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
			map.put("resCode", ((BusinessException) ex).getCode());
			map.put("msg", ((BusinessException) ex).getMessage());
		}
		return map;
	}

	// /**
	// * spring API请求格式通用处理
	// *
	// * @param ex
	// * @return String
	// * @Methods Name handleHttpMessageConversionException
	// * @Create In 2014年10月29日 By wangfei
	// */
	// @ExceptionHandler(Exception.class)
	// @ResponseStatus(value = HttpStatus.OK)
	// @ResponseBody
	// protected Map<String, Object> handleCommonException(Exception ex) {
	// logger.error(ex.getMessage(),ex);
	// Map<String, Object> map = new HashMap<String, Object>();
	// if (ex instanceof HttpMessageConversionException) {
	// map.put(STATUS,
	// ComErrorCodeConstants.ErrorCode.PARA_NORULE_WARN.getErrorCode());
	// map.put(MESSAGE, ex.getMessage());
	// } else if (ex instanceof HttpMediaTypeException) {
	// // 请求类型有误
	// map.put(STATUS, "");
	// // map.put("msg", "请求类型有误!");
	// map.put(MESSAGE, ex.getMessage());
	// } else if (ex instanceof TypeMismatchException) {
	// // 请求类型有误
	// map.put(STATUS, "");
	// // map.put("msg", "参数类型不匹配!");
	// map.put(MESSAGE, ex.getMessage());
	// } else if (ex instanceof MissingServletRequestParameterException) {
	// map.put(STATUS, "");
	// // map.put("msg", "请检查必传参数!");
	// map.put(MESSAGE, ex.getMessage());
	// } else if (ex instanceof BusinessException) {
	// map.put(STATUS, ((BusinessException) ex).getCode());
	// // map.put("msg", ((BleException) ex).getMessage());
	// map.put(MESSAGE, ex.getMessage());
	// } else {
	// map.put(STATUS,
	// ComErrorCodeConstants.ErrorCode.SYSTEM_ERROR.getErrorCode());
	// map.put(MESSAGE, ex.getMessage());
	// }
	// return map;
	// }
}
