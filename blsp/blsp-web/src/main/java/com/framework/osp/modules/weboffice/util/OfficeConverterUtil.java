package com.framework.osp.modules.weboffice.util;

import java.io.File;

import com.framework.osp.modules.weboffice.exception.OfficeConvertException;
import com.framework.osp.modules.weboffice.exception.OfficeServerException;
import com.framework.osp.modules.weboffice.server.OfficeServer;

/**
 * @author wangyj
 * 
 */
public class OfficeConverterUtil {

	/**
	 * 在指定的文档格式之间进行转换
	 * @throws OfficeConvertException
	 */
	public static void convert(File inputFile, File outputFile)
			throws OfficeConvertException {
		try {
			OfficeServer server = OfficeServer.getInstance();
			server.convert(inputFile, outputFile);
		} catch (OfficeServerException e) {
			throw new OfficeConvertException(e.getMessage(), e.getCause());
		}
	}

}
