package com.framework.osp.modules.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.framework.osp.modules.web.remote.come.Input;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMakerUtils {
	private static Configuration configuration = null;
	private static Map<String, Template> templates = null;

	public static void init() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("gb2312");
		configuration.setClassForTemplateLoading(FreeMakerUtils.class, "/freemaker/");
		templates = new HashMap<>();
		try {
			templates.put("word", configuration.getTemplate("word.doc","gb2312"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static File createDoc(Map<?, ?> dataMap, String type) {
		String name = "temp" + (int) (Math.random() * 100000) + ".doc";
		File f = new File(name);
		Template t = templates.get(type);
		try {
			Writer w = new OutputStreamWriter(new FileOutputStream(f), "gb2312");
			t.process(dataMap, w);
			w.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return f;
	}

	public static void service(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> map, String name) {
		init();
		File file = null;
		InputStream fin = null;
		ServletOutputStream out = null;
		try {
			file = FreeMakerUtils.createDoc(map, "word");
			try {
				fin = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			resp.setCharacterEncoding("gb2312");
			resp.setContentType("application/msword");
			try {
				resp.addHeader("Content-Disposition", "attachment;filename=" +new String(name.getBytes("gb2312"), "ISO8859-1" ));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				out = resp.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte[] buffer = new byte[1024]; // 缓冲区
			int bytesToRead = -1;
			try {
				while ((bytesToRead = fin.read(buffer)) != -1) {
					out.write(buffer, 0, bytesToRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {
			if (fin != null)
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (file != null)
				file.delete();
		}
	}
	public static void main(String[] str){
		String sss="{\"reqHeader\":{\"version\":\"1\",\"reqno\":\"1000\",\"timestamp\":\"20160115131625\",\"platformId\":\"2\",\"username\":\"user\",\"password\":\"pass\",\"proportion\":\"1:1\",\"scale\":\"1\"},\"data\":{\"sblsh\":\"788\"}";
		Input input=JSON.parseObject(sss, Input.class);
		System.out.println(input.getData().getSblsh());
	}
}
