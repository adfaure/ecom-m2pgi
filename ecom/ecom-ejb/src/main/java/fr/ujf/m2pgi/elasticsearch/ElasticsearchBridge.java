package fr.ujf.m2pgi.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import fr.ujf.m2pgi.properties.IProperties;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.collect.HppcMaps;
import org.elasticsearch.common.inject.internal.InternalContext;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * A connection to Elasticsearch implemented as a Singleton Session Bean 
 * I started to add listeners on entities to automatically index or remove documents in Elasticsearch.
 */
@Singleton
@Startup// The EJB container must initialize the singleton session bean upon application startup. 
public class ElasticsearchBridge {
	
	private Client client;

	@Inject // TODO for the moment its better than hardcode paths
	private IProperties applicationProperties;

	//@Inject
	//Logger logger;
	
	@PostConstruct// on startup
	void init() {
		Properties prop = applicationProperties.getApplicationProperties();
		String host = prop.getProperty("elastic_search_hostname");
		String sport = prop.getProperty("elastic_search_listen_port");
		int port = Integer.parseInt(sport);
		try {
			client = TransportClient.builder().build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
			//logger.info("ElasticSearch client started successfully!");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			//logger.severe("ElasticSearch client failed to start!");
		}
	}
	
    @PreDestroy// on shutdown
	void kill() {
    	client.close();
    	//logger.info("ElasticSearch client closed successfully!");
	}

	public Client getClient() {
		return client;
	}
}
