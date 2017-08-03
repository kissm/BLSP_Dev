package com.lpcode.modules.service.impl.project.util.bean;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RequestEntity {
	//请求的路径url(此为必填项)
	private String url;
	//请求超时时间(此为必填项)
	private int timeOut;
	//连接超时时间（以毫秒为单位）
	private int connTimeOut = 1000*60*60;
	//该值就是从连接池中取出连接的超时时间，此处设置为1秒
	private int connManagerTimeout = 1000;
	//最大连接数
	private int maxConnNumber= 160;
	//默认的最大连接数
	private int maxHostConnections = 80;
	//检查的boolean值
	private boolean status = true;
	//请求的内容(此为必填项)
	private String body;
	//请求的消息头（含有headerName与headerValue） (此为必填项)
	private Map<String, String> headerMap;
	//请求的编码格式
	private String encoding = "utf-8";
	//请求的内容格式
	private String contentType = "text/xml";
	
	public RequestEntity() {
		super();
	}
	
	public RequestEntity(String url, int timeOut, Map<String, String> headerMap) {
		super();
		this.url = url;
		this.timeOut = timeOut;
//		this.body = body;
		this.headerMap = headerMap;
	}

	public RequestEntity(String url, int timeOut,
						 String body, Map<String, String> headerMap) {
		super();
		this.url = url;
		this.timeOut = timeOut;
		this.body = body;
		this.headerMap = headerMap;
	}

	public RequestEntity(String url, int timeOut, int connTimeOut,
			int connManagerTimeout, int maxConnNumber, int maxHostConnections,
			boolean status, Map<String, String> headerMap,
			String encoding, String contentType) {
		super();
		this.url = url;
		this.timeOut = timeOut;
		this.connTimeOut = connTimeOut;
		this.connManagerTimeout = connManagerTimeout;
		this.maxConnNumber = maxConnNumber;
		this.maxHostConnections = maxHostConnections;
		this.status = status;
		this.headerMap = headerMap;
		this.encoding = encoding;
		this.contentType = contentType;
	}

	/**
	 * 获取请求的路径：url
	 * @return	String
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置请求的路径：url (此为必填项)
	 * @return	void
	 */
	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * 获取请求的消息头 ：headerMap
	 * @param 含有headName与headValue两个 内容
	 * @return	Map<String,String>
	 */
	public Map<String, String> getHeaderMap() {
		return headerMap;
	}

	/**
	 * 设置请求的消息头：headerMap (此为必填项)
	 * @return	void
	 */
	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap = headerMap;
	}

	/**
	 * 获取请求的超时时间的字符串：timeOut 
	 * @return	int
	 */
	public int getTimeOut() {
		return timeOut;
	}

	/**
	 * 设置请求的超时时间的字符串：timeOut (此为必填项)
	 * @return	void
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	
	/**
	 * 获取连接超时时间：connTimeOut(以毫秒为单位)
	 * @return	int
	 */
	public int getConnTimeOut() {
		return connTimeOut;
	}
	
	/**
	 * 设置连接超时时间：connTimeOut(以毫秒为单位)
	 * @return	void
	 */
	public void setConnTimeOut(int connTimeOut) {
		this.connTimeOut = connTimeOut;
	}
	
	/**
	 * 获取从连接池中取出连接的超时时间	connManagerTimeout
	 * @return	int
	 */
	public int getConnManagerTimeout() {
		return connManagerTimeout;
	}
	
	/**
	 * 设置从连接池中取出连接的超时时间	connManagerTimeout
	 * @return	void
	 */
	public void setConnManagerTimeout(int connManagerTimeout) {
		this.connManagerTimeout = connManagerTimeout;
	}
	
	/**
	 * 设置最大连接数	maxConnNumber
	 * @return	void
	 */
	public void setMaxConnNumber(int maxConnNumber) {
		this.maxConnNumber = maxConnNumber;
	}
	
	/**
	 * 获取最大连接数	maxConnNumber
	 * @return	int
	 */
	public int getMaxConnNumber() {
		return maxConnNumber;
	}
	
	/**
	 * 设置默认最大连接数	maxHostConnections
	 * @return	void
	 */
	public void setMaxHostConnections(int maxHostConnections) {
		this.maxHostConnections = maxHostConnections;
	}
	
	/**
	 * 获取默认最大连接数	maxHostConnections
	 * @return	int
	 */
	public int getMaxHostConnections() {
		return maxHostConnections;
	}
	
	/**
	 * 设置检查 状态	status	
	 * @return	void
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	/**
	 * 获取检查状态	status
	 * @return	boolean
	 */
	public boolean getStatus() {
		return status;
	}
	
	

	/**
	 * 获取请求的编码集：encoding
	 * @return	String
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * 设置请求的编码集：encoding
	 * @return	void
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 获取请求的内容格式：returnFrom
	 * @return	String
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * 设置请求的内容格式：returnFrom
	 * @return	void
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Iterator<Entry<String, String>> it = (Iterator<Entry<String, String>>)this.headerMap.entrySet().iterator();
		Entry<String,String> entry = null;
		while(it.hasNext()){
			entry = it.next();
			builder.append(entry.getKey()+":"+entry.getValue()+",");
		}
		String str = builder.substring(0, builder.length()-1);
		
		return "EntrustRequestEntity [url=" + url + ", timeOut=" + timeOut
				+ ", headerMap=" + str
				+ "]";
	}
	
	
	
}
