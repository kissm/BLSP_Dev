package com.framework.osp.modules.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;




public class HttpClientRequestUtil{

	private static  Log log_ = LogFactory.getLog(HttpClientRequestUtil.class);
		/**
		 * EntrustService的请求方法
		 * @param requestEntity
		 * @throws HttpException
		 * @throws IOException
		 * @return  String  info 响应的信息
		 */
		public static String RequestService(RequestEntity requestEntity) throws HttpException, IOException{
			log_.info("entrustRequest Method start:"+requestEntity.toString());
			//获取超时时间
			int timeout = requestEntity.getTimeOut();
			//获取连接超时时间
			int connTimeOut = requestEntity.getConnTimeOut();
			//获取等待超时时间
			int connManagerTimeout = requestEntity.getConnManagerTimeout();
			//获取最大连接数
			int maxConnNumber = requestEntity.getMaxConnNumber();
			//获取默认的最大连接数
			int maxHostConnections = requestEntity.getMaxHostConnections();
			//获取是否检查的boolean值
			boolean status = requestEntity.getStatus(); 
			//获取请求的body内容
			String body = requestEntity.getBody();
			//获取请求的编码集
			String encoding = requestEntity.getEncoding();
			//获取请求内容的格式类型
			String contentType = requestEntity.getContentType();
			//创建HttpClient对象
			CloseableHttpClient httpClient = HttpClients.createDefault();
			//创建PostMethod对象
			HttpPost post =  new HttpPost(requestEntity.getUrl());
			
			//获取消息头headerName与headerValue的value值，并设置post的消息头 
			Map<String, String> headerMap = requestEntity.getHeaderMap();
			Iterator<Entry<String, String>> it = (Iterator<Entry<String, String>>)headerMap.entrySet().iterator();
			Entry<String, String> entry = null;
			while(it.hasNext()){
			    entry = it.next();
			    //设置请求消息头信息
				post.addHeader(entry.getKey(), entry.getValue());
			}
			
			//设置 请求的body内容、请求内容的格式类型、请求的编码集
			StringEntity stringEntity = new StringEntity(body,encoding);
			stringEntity.setContentType(contentType);
			post.setEntity(stringEntity);
//			System.out.println("body-->"+body+",encoding-->"+encoding+",returnFrom-->"+returnFrom);
			
			//设置请求超时时间 、连接超时时间检查设置
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout)
			.setConnectTimeout(connTimeOut).setStaleConnectionCheckEnabled(status).build();//设置请求和传输超时时间
			//设置等待的超时时间
			post.getParams().setIntParameter(ClientPNames.CONN_MANAGER_TIMEOUT, connManagerTimeout);
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
			//设置最大连接数 
			cm.setMaxTotal(maxConnNumber);
			//设置默认的最大连接数
			cm.setDefaultMaxPerRoute(maxHostConnections);
			//发送请求 并 获取响应
			HttpResponse httpresponse = httpClient.execute(post);  
			//获取请求状态
			StatusLine statusLine =httpresponse.getStatusLine();
			//使用输入流的方式获取返回数据，并设置字符集  
            HttpEntity entity = httpresponse.getEntity();  
//          System.out.println(entity.getContentType());
            StringBuffer result = new StringBuffer();
            BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent(),encoding));
            String tempLine = rd.readLine();
            while (tempLine != null) {
             result.append(tempLine);
             tempLine = rd.readLine();
            }
            //关闭流
            rd.close();
            String info = result.toString();
            
            //释放连接
            post.releaseConnection();
			log_.info("requestService Method result:"+info);
			//输出警告级别的日志信息 
		    log_.warn("requestService Method Appear warning"); 
			//输出错误级别的日志信息 
		    log_.error("requestService Method Appear error"); 
			//输出致命错误级别的日志信息 
		    log_.fatal("requestService Method Appear fatal"); 
		    //输出debug错误级别的日志信息 
		    log_.debug("requestService Method Appear debug"); 
			return info;
		}
		
		/**
		 * CallingRequestService方法  调用RequestService 传入指定的 url与body的json串  返回请求后的结果
		 * @param bodyJson,
		 * @param url
		 * @throws HttpException
		 * @throws IOException
		 * @return  String  info 响应的信息
		 */
		public static String CallingRequestService(String bodyJson,String url) throws HttpException, IOException{
			int timeOut = 3000*60*60;
			Map<String, String> headMap = new HashMap<String, String>();
			headMap.put("Accept", "application/json");
			headMap.put("Content-Type", "application/json;charset=utf-8");
			RequestEntity requestEntity = new RequestEntity(url,timeOut,bodyJson,headMap);
			return RequestService(requestEntity);
		}
		

	
}
