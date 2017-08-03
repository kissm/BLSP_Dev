package com.framework.core.solr;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.core.utils.StringUtil;

/**
 * 
 * @author luoyonghong 2010-12-28
 */
public class HttpSolrServerFactoryBean extends BaseSolrServerFactoryBean {

	private static Logger logger = LoggerFactory.getLogger(HttpSolrServerFactoryBean.class);
	/**
	 * The URL of the Solr server.
	 */
	private String baseURL;

	private String core;
	/**
	 * 
	 */
	private boolean allowCompression;
	/**
	 * 
	 */
	private int soTimeout;
	/**
	 * 
	 */
	private int connectionTimeout;
	/**
	 * 
	 */
	private int maxConnectionsPerHost;
	/**
	 * 
	 */
	private boolean followRedirects;
	/**
	 * 
	 */
	private int maxRetries;
	/**
	 * 
	 */
	private int maxTotalConnection;

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public void setAllowCompression(boolean allowCompression) {
		this.allowCompression = allowCompression;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	public void setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
	}

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	public void setMaxTotalConnection(int maxTotalConnection) {
		this.maxTotalConnection = maxTotalConnection;
	}

	/**
	 * 
	 */
	public void afterPropertiesSet() throws Exception {
		try {
			solrServer = getHttpSolrServer();
		} catch (Exception e) {
			logger.warn("SOLR_URL出错！");
			e.printStackTrace();
		}

	}

	/**
	 * CommonsHttpSolrServer is thread-safe and if you are using the following
	 * constructor, you *MUST* re-use the same instance for all requests. If
	 * instances are created on the fly, it can cause a connection leak. The
	 * recommended practice is to keep a static instance of
	 * CommonsHttpSolrServer per solr server url and share it for all requests.
	 * See https://issues.apache.org/jira/browse/SOLR-861 for more details
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	public HttpSolrServer getHttpSolrServer() throws MalformedURLException {
		if (StringUtil.isEmpty(baseURL)) {
			throw new IllegalArgumentException("please set baseURL");
		}
		String solrServerUrl = baseURL;
		if (StringUtil.isNotEmpty(core)) {
			if (baseURL.endsWith("/")) {
				solrServerUrl = baseURL + core;
			} else {
				solrServerUrl = baseURL + "/" + core;
			}
		}
		HttpSolrServer solrServer = new HttpSolrServer(solrServerUrl);
		if (solrServer != null) {
			solrServer.setAllowCompression(allowCompression);
			if (soTimeout > 0) {
				logger.debug("soTimeout: " + soTimeout);
				solrServer.setSoTimeout(soTimeout);
			}
			if (connectionTimeout > 0) {
				logger.debug("connectionTimeout: " + connectionTimeout);
				solrServer.setConnectionTimeout(connectionTimeout);
			}
			if (maxConnectionsPerHost > 0) {
				logger.debug("maxConnectionsPerHost: " + maxConnectionsPerHost);
				solrServer.setDefaultMaxConnectionsPerHost(maxConnectionsPerHost);
			}
			solrServer.setFollowRedirects(followRedirects);
			if (maxRetries > 0) {
				logger.debug("maxRetries: " + maxRetries);
				solrServer.setMaxRetries(maxRetries);
			}
			if (maxTotalConnection > 0) {
				logger.debug("maxTotalConnection: " + maxTotalConnection);
				solrServer.setMaxTotalConnections(maxTotalConnection);
			}
			logger.info("服务 " + baseURL + " 加载完毕");
		}
		return solrServer;
	}

	public void setCore(String core) {
		this.core = core;
	}

}
