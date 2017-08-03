package com.framework.core.solr;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 
 * @author luoyh
 * 
 */
public abstract class BaseSolrServerFactoryBean implements InitializingBean, DisposableBean, FactoryBean<SolrServer> {
	protected SolrServer solrServer = null;

	public SolrServer getObject() throws Exception {
		return solrServer;
	}

	public Class<SolrServer> getObjectType() {
		return SolrServer.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public SolrServer getSolrServer() {
		return solrServer;
	}

	public void destroy() throws Exception {
		if (solrServer != null) {
			solrServer.shutdown();
		}

	}

}
