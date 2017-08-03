package com.framework.core.cache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.framework.core.utils.CacheUtils;

/**
 * 缓存自动提交 一次HTTP请求处于同一线程,该线程的service line上的所有缓存临时数据通过该Adapter统一提交
 * 
 * 接口执行效率统计信息可以通过preHandle方法和postHandle方法组合使用来统计
 * 
 * 注意(非常重要):所有Controller和Service都必须把异常(包括架构异常和业务异常)抛至上一层,最终由Controller抛出,否则不能使用该种方式管理缓存提交
 * 
 * @author wangchaochao
 *
 */
public class CacheCommitInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 请求在被处理前执行,可以在这里统计请求执行时间
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO 这里可以对服务接口做控制,统一关闭某个接口,不接收服务
		// 1.对URL做处理,关闭一个URL服务
		// 2.通配URL,关闭一个PATH下的所有服务
		// 3.获取调用者IP等信息,对某个调用者发起的请求做控制,拒绝链接等
		// 4.添加一些预处理内容,在控制层方法执行之前处理
		return true;
	}

	/**
	 * 请求处理后执行,缓存自动提交.
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 提交缓存
		try {
			CacheUtils.flushAll();
		} catch (Exception e) {
			// TODO: 提交缓存失败如何处理
		}
	}

	/**
	 * 最终方法,可以根据ex是否为null判断是否发生了异常.
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 清空缓存
		try {
			CacheUtils.removeAll();
		} catch (Exception e) {
			// TODO: 清空缓存失败如何处理
		}
	}

}
