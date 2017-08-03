package com.framework.core.utils;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


/**
 * http工具类
 * @Class Name HttpMethod
 * @Author guoxun
 * @Create In 2015年3月02日
 */
public class HttpMethod {

	 /**
     * 发送post请求工具方法
     * @Methods Name HttpPost
     * @Create In 2014年10月28日 By wangfei
     * @param url
     * @param method
     * @param paramMap
     * @return String
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public static String HttpPost(String url, String method, Map paramMap) {
		
		//设置编码格式
        String encoding = "GBK";
        String webUrl = url + "/" + method;
        if (encoding == null || "".equals(encoding))
            encoding = "GBK";
        String queryString = createLinkString(paramMap);
        webUrl = webUrl+"?"+queryString;
        StringBuffer sBuffer = new StringBuffer();
        // 构造HttpClient的实例
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod=null;
        // httpClient.set
        try {
		URI uri = new URI(webUrl,false,encoding);
        postMethod = new PostMethod(uri.toString());
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000); // 连接5秒超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);// 读取30秒超时

            // 执行getMethod
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + postMethod.getStatusLine());
                sBuffer = new StringBuffer();
            } else {
                sBuffer = new StringBuffer(postMethod.getResponseBodyAsString() + "");
            }
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            // 释放连接
        	postMethod.releaseConnection();
        }
        return sBuffer.toString();
    }
    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
}
