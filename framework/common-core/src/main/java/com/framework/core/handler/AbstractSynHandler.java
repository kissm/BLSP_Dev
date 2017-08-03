package com.framework.core.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author wangchaochao
 *
 */
public abstract class AbstractSynHandler implements SynHandler {

	private static final Logger logger = LoggerFactory
			.getLogger(AbstractSynHandler.class);

	public void synData(Object target, Object[] args, Object data, String type) {
		Object body = null;
		boolean flag = false;
		try {
			body = marshaller(target, args, data, type);
			if (body != null) {
				send(body, type);
				flag = true;
			}
			log(body, flag, null, type);
		} catch (Exception e) {
			flag = false;
			logger.error("同步handler[" + getHandlerName() + "]出错:", e);
			log(body, flag, e, type);
		}
	}

	public abstract void log(Object body, boolean flag, Throwable t, String type);

	public abstract Object marshaller(Object target, Object[] args,
			Object data, String type);

	public abstract boolean send(Object body, String type) throws Exception;

	public abstract String getHandlerName();

}
