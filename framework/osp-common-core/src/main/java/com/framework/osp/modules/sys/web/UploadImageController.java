package com.framework.osp.modules.sys.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.framework.core.result.ListResult;
import com.framework.fdfs.images.BmpFile;
import com.framework.fdfs.images.DocFile;
import com.framework.fdfs.images.DocxFile;
import com.framework.fdfs.images.FastDFSFileFactory;
import com.framework.fdfs.images.FastDFSImage;
import com.framework.fdfs.images.GifFile;
import com.framework.fdfs.images.JpegFile;
import com.framework.fdfs.images.PdfFile;
import com.framework.fdfs.images.PngFile;
import com.framework.fdfs.images.RarFile;
import com.framework.fdfs.images.TifFile;
import com.framework.fdfs.images.ZipFile;

@Controller
public class UploadImageController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(UploadImageController.class);

	@ResponseBody
	@RequestMapping(value = "uploadImage")
	public ListResult<String> uploadImage(HttpServletRequest request, Model model) {

		List<String> pathList = new ArrayList<String>();

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					try {
						String substring = file.getOriginalFilename()
								.substring(file.getOriginalFilename().lastIndexOf(".") + 1).toUpperCase();
						FastDFSImage j = null;
						if ("PDF".equals(substring)) {
							j = new PdfFile();
						} else if ("TIF".equals(substring)) {
							j = new TifFile();
						} else if ("GIF".equals(substring)) {
							j = new GifFile();
						} else if ("JPEG".equals(substring)) {
							j = new JpegFile();
						} else if ("PNG".equals(substring)) {
							j = new PngFile();
						} else if ("DOCX".equals(substring)) {
							j = new DocxFile();
						} else if ("DOC".equals(substring)) {
							j = new DocFile();
						} else if ("BMP".equals(substring)) {
							j = new BmpFile();
						} else if ("RAR".equals(substring)) {
							j = new RarFile();
						} else if ("ZIP".equals(substring)) {
							j = new ZipFile();
						} else {
							j = new FastDFSImage(substring) {
								private static final long serialVersionUID = 1L;
							};
						}
						FastDFSFileFactory.getInstance().saveFile(file.getInputStream(), j);
						pathList.add(j.getUrl());
						pathList.add(file.getOriginalFilename());
					} catch (Exception e) {
						// logger.error(e.getMessage(), e);
					}
				}
			}
		}
		ListResult<String> result = new ListResult<>();
		result.setObj(pathList);
		return result;
	}

	@RequestMapping("download")
	public void download(//
			HttpServletRequest request, //
			HttpServletResponse response, //
			String pathUrl, String coi) throws Exception {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			URL u = new URL(pathUrl);
			URLConnection connection = u.openConnection();
			connection.setReadTimeout(100000);
			InputStream is = connection.getInputStream();
			response.setContentType("text/html;charset=UTF-8");
			response.setContentType("application/x-msdownload");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(coi.getBytes("gb2312"), "ISO8859-1") + pathUrl.substring(pathUrl.lastIndexOf(".")));

			in = new BufferedInputStream(is);
			out = new BufferedOutputStream(response.getOutputStream());

			byte[] data = new byte[1024];
			int len = 0;
			while (-1 != (len = in.read(data, 0, data.length))) {
				out.write(data, 0, len);
			}
		} catch (Exception e) {
			// logger.error(e.getMessage(), e);
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}

	}
}
