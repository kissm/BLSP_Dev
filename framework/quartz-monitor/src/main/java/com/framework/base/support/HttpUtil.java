package com.framework.base.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 


import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http工具类
 * @Class Name HttpUtil
 * @Author mogu
 * @Create In 2014年10月28日
 */
public class HttpUtil {
	// 日志记录器
    private static final Logger LOG = LoggerFactory.getLogger(HttpUtil.class);
    
    private static final CloseableHttpClient httpClient;
    public static final String CHARSET = "UTF-8";
 
    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }
 
    public static String HttpGet(String url, Map<String, String> params){
        return HttpGet(url, params,CHARSET);
    }
    public static String HttpPost(String url, Map<String, String> params){
        return HttpPost(url, params,CHARSET);
    }
    /**
     * HTTP Get 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String HttpGet(String url,Map<String,String> params,String charset){
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            if(params != null && !params.isEmpty()){
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            LOG.debug("invoke url is=",url);
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            LOG.debug("url is {},result is {}",url,result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     public static String doPost(String url,String json){
    	 if(StringUtils.isBlank(url)){
             return null;
         }
         try {
             HttpPost httpPost = new HttpPost(url);
             httpPost.setEntity(new StringEntity(json,ContentType.create("application/json", "UTF-8")));
             CloseableHttpResponse response = httpClient.execute(httpPost);
             int statusCode = response.getStatusLine().getStatusCode();
             if (statusCode != 200) {
                 httpPost.abort();
                 throw new RuntimeException("HttpClient,error status code :" + statusCode);
             }
             LOG.debug("invoke url is {},json is {}",url,json);
             HttpEntity entity = response.getEntity();
             String result = null;
             if (entity != null){
                 result = EntityUtils.toString(entity, "utf-8");
             }
             EntityUtils.consume(entity);
             response.close();
             LOG.debug("invoke url is {},json is,result is {}",url,json,result);
             return result;
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String HttpPost(String url,Map<String,String> params,String charset){
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if(params != null && !params.isEmpty()){
                pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if(pairs != null && pairs.size() > 0){
                httpPost.setEntity(new UrlEncodedFormEntity(pairs,CHARSET));
            }
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
//    public static void main(String []args){
//        String getData = HttpGet("http://www.suning.com/",null);
//        System.out.println(getData);
//        System.out.println("----------------------分割线-----------------------");
//        String postData = HttpPost("http://www.oschina.net/",null);
//        System.out.println(postData);
//        String doPostDate=doPost("http://192.168.100.126:19080/blgroup-osp-site-api/site/store/range","{\"storeType\":\"1020\"}");
//        System.out.println(doPostDate);
//    }
//     

}
