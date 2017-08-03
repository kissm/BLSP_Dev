package com.framework.osp.modules.web.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class RequestUtil {
	private static Logger logger = LoggerFactory.getLogger(RequestUtil.class);

	public static String unloadFile(CommonsMultipartFile file, HttpServletRequest request) {
		String fileName = file.getFileItem().getName();
		Date d = new Date();
		SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy");
		String dirTemp = myFmt2.format(d);
		String servletPath = request.getRealPath("/") + "/" + dirTemp;
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyyMMddHHmmss");
		fileName = myFmt.format(d) + "-" + fileName;
		if (!(StringUtils.isBlank(fileName))) {
			logger.debug("保存上传文件到临时目录：{}", fileName);
			File outFile = new File(buildOutputFileName(servletPath));
			try {
				BufferedInputStream bis = new BufferedInputStream(file.getFileItem().getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(buildOutputFileName(servletPath) + "/" + fileName)));
				Streams.copy(bis, bos, true);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("转存文件发生错误：{}：{}======错误信息：{}", "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), outFile.getAbsolutePath(), e.getMessage());
			}
			logger.debug("临时文件保存路径：{}:{}", "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), outFile.getAbsolutePath());
		}
		return "/" + dirTemp + "/" + fileName;
	}

	private static String buildOutputFileName(String uploadFileName) {
		StringBuilder path = new StringBuilder(255);
		File saveFile = new File(uploadFileName);
		if (!(saveFile.exists())) {
			saveFile.mkdirs();
		}

		if (!(path.toString().endsWith("/"))) {
			path.append("/");
		}
		path.append(uploadFileName);

		return path.toString();
	}

	/**
	 * 文件下载
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 */
	public static void download(HttpServletRequest request, HttpServletResponse response, String filePath, String fileName) {
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			String name = fileName;
			filePath = request.getServletContext().getRealPath(filePath);
			fis = new FileInputStream(filePath);
			os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");// firefox浏览器
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
			response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");// 设定输出文件头
			if (name.indexOf("pdf") > 0) {
				response.setContentType("application/pdf");
			} else if (name.indexOf("doc") > 0) {
				response.setContentType("application/msword");
			} else if (name.indexOf("zip") > 0) {
				response.setContentType("application/zip");
			} else if (name.indexOf("xls") > 0) {
				response.setContentType("application/vnd.ms-excel");
			} else {
				response.setContentType("application/x-download");
			}
			byte[] mybyte = new byte[8192];
			int len = 0;
			while ((len = fis.read(mybyte)) != -1) {
				os.write(mybyte, 0, len);
			}
			os.flush();
			os.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
			;
		}
	}

	public static void main(String[] str) {
	}
}
