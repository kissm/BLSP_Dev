package com.lpcode.modules.service.impl.project.util;

import java.util.Date;

public class FormUtils {
	public static String getFormNo() {
		StringBuffer sb = new StringBuffer();
		java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyyMMddhhmmss");
		sb.append(format2.format(new Date()));
		return sb.toString();
	}

	public static String getFormNo(String bussiness) {
		StringBuffer sb = new StringBuffer();
		sb.append(bussiness);
		sb.append("-");
		sb.append(getFormNo());
		return sb.toString();
	}

	public static void main(String[] args) {
		String code = "JWZF201602001";
		code.substring(4, 8);
		System.out.println(code.substring(10));
	}
}
