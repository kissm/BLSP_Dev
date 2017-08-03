package com.framework.osp.modules.web.sys;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.framework.core.result.ListResult;
import com.framework.core.result.Result;
import com.framework.core.utils.Base58;
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
import com.framework.osp.common.config.Global;
import com.framework.osp.modules.weboffice.util.OfficeConverterUtil;
import com.lpcode.modules.dto.weboffice.FilePreviewDto;
import com.lpcode.modules.service.weboffice.FilePreviewService;

@Controller
@RequestMapping(value = "${adminPath}/sys")
public class UploadController {

	@Autowired
	FilePreviewService filePreviewService;
	
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
			/*
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(coi.getBytes("gb2312"), "ISO8859-1") + pathUrl.substring(pathUrl.lastIndexOf(".")));
			 */
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
	

	@RequestMapping(value = "filePreview")
	public String filePreview(HttpServletRequest request, Model model) throws Exception {
		String pathUrl = request.getParameter("pathUrl");
		model.addAttribute("pathUrl",pathUrl);
		return "modules/weboffice/filePreview";
	}
	@ResponseBody
	@RequestMapping(value = "flag")
	public Result<String> flag(HttpServletRequest request, Model model) throws Exception {
		Result<String> result = new Result<String>();
		String pathUrl = request.getParameter("pathUrl");
		URL u = new URL(pathUrl);
		HttpURLConnection connection = (HttpURLConnection) u.openConnection();
		if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
			result.setObj("true");
		}else{
			result.setObj("false");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "convert")
	public Result<String> convert(HttpServletRequest request, Model model) throws Exception {
		Result<String> result = new Result<String>();
		String pathUrl = request.getParameter("pathUrl");
		String filetype = pathUrl.substring(pathUrl.lastIndexOf(".")+1);
		String filetypes = Global.getConfig("openoffice.converter.filetypes");
		if(filetypes.indexOf(filetype)!=-1){
			ListResult<FilePreviewDto> filePreview = filePreviewService.findFilePreview(pathUrl);
			if(filePreview.getObj().size()>0){
				result.setObj("true");
			}else{
				String basePath = request.getSession().getServletContext().getRealPath("/");
				if (!basePath.endsWith(File.separator)) {
					basePath += File.separator;
				}
				String UUID = Base58.compressedUUID();
				String inputFilePath = "";
				inputFilePath = basePath + "down" + File.separator + UUID+"."+filetype;
				URL u = new URL(pathUrl);
				URLConnection connection = u.openConnection();
				connection.setReadTimeout(100000);
				InputStream is = connection.getInputStream();
				BufferedInputStream in = new BufferedInputStream(is);
				File inputFile = new File(inputFilePath);
				FileOutputStream out = new FileOutputStream(inputFile);
				try {
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
				String outputFilePath = basePath + "down" + File.separator + UUID+".pdf";
				File outputFile = new File(outputFilePath);
				OfficeConverterUtil.convert(inputFile, outputFile);
				FastDFSImage j = new PdfFile();
				FastDFSFileFactory.getInstance().saveFile(new FileInputStream(outputFile), j);
				FilePreviewDto filePreviewDto = new FilePreviewDto();
				filePreviewDto.setFileUrl(pathUrl);
				filePreviewDto.setViewUrl(j.getUrl());
				filePreviewService.save(filePreviewDto);
				result.setObj(j.getUrl());
			}
		}else{
			if(filetype.equalsIgnoreCase("pdf")){
				result.setObj("true");
			}else{
				result.setObj("false");
			}
		}
		return result;
	}
	
	@RequestMapping(value = "preview")
	public String preview(HttpServletRequest request, Model model) throws Exception {
		String pathUrl = request.getParameter("pathUrl");
		Result<String> result = new Result<String>();
		String filetype = pathUrl.substring(pathUrl.lastIndexOf(".")+1);
		String url = "";
		if(filetype.equalsIgnoreCase("pdf")){
			url = request.getContextPath()+Global.getAdminPath()+"/sys/download?pathUrl="+pathUrl+"&coi=preview.pdf";
		}else{
			ListResult<FilePreviewDto> filePreview = filePreviewService.findFilePreview(pathUrl);
			if(filePreview.getObj().size()>0){
				url = request.getContextPath()+Global.getAdminPath()+"/sys/download?pathUrl="+filePreview.getObj().get(0).getViewUrl()+"&coi=preview.pdf";
			}
		}
		result.setObj(url);
		model.addAttribute("filePreview",result);
		return "modules/weboffice/viewer";
	}
	
}
