package com.framework.core.handler;

/**
 * 
 * @author wangchaochao
 *
 */
public interface SynHandler {

	/**
	 * 
	 * @param target
	 *            目标服务实例
	 * @param args
	 *            服务实例方法入参
	 * @param data
	 *            服务实例方法返回
	 * @param type
	 *            同步类型
	 */
	public void synData(Object target, Object[] args, Object data,String type);

}
