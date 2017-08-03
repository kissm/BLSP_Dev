package com.framework.osp.modules.weboffice.exception;

/**
 * @author wangyj
 *
 */
public class OfficeServerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5825073307966785592L;

	public OfficeServerException(String message) {
		super(message);
	}

	public OfficeServerException(String message, Throwable cause) {
		super(message, cause);
	}
}
