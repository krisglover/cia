package com.itall.configure.client.provider;

import java.util.List;

import javax.xml.ws.http.HTTPException;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itall.configure.client.models.Config;
import com.itall.configure.client.models.ResponseAllConfigs;
import com.itall.configure.client.models.Status;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * Restful implemenation of the {@link ConfigClient} responsible for pulling configs from a specified restful host
 * 
 * @author kglover
 * 
 */
public class RestConfigProvider implements ConfigProvider {

	private static Logger logger = LoggerFactory.getLogger(RestConfigProvider.class);

	private final Client client;
	private final WebResource baseConfigWebResource;

	/**
	 * 
	 * @param urlToConfig
	 */
	public RestConfigProvider(String urlToConfig) {
		
		if(urlToConfig == null)
			throw new RuntimeException("Cannot construct the RestConfigProvider without the system property [restConfigUrl]");
		
		ClientConfig cc = new DefaultClientConfig(JacksonJsonProvider.class);
		client = Client.create(cc);

		this.baseConfigWebResource = client.resource(urlToConfig);
	}

	/**
	 * Constructor that attempts to read system property [restConfigUrl]. If not present a runtime exception will be thrown because the configuration store cannot start
	 * without it.
	 * 
	 * @see #RestConfigProvider(String) for the preferred way to construct this class. Even though this is not the preferred constructor it can be useful to bootstrap the
	 *      configuration store in a system like spring
	 */
	public RestConfigProvider() {
		this(System.getProperty("restConfigUrl"));
	}

	@Override
	public List<Config> getAll(String environment, String application) {

		WebResource webResource = baseConfigWebResource.path(environment).path(application);

		try {
			ClientResponse clientResponse = webResource.accept("application/json").get(ClientResponse.class);
			if (clientResponse.getStatus() != 200) {
				throw new HTTPException(clientResponse.getStatus());
			}

			ResponseAllConfigs allConfigsResponse = clientResponse.getEntity(ResponseAllConfigs.class);

			if (!Status.SUCCESS.toString().equalsIgnoreCase(allConfigsResponse.getStatus())) {
				// TODO : this should be some type of service exception
				throw new Exception("Service exception [status :  " + allConfigsResponse.getStatus() + " , message : " + allConfigsResponse.getMessage() + "]");
			}

			List<Config> configs = allConfigsResponse.getData();

			return configs;
		} catch (HTTPException he) {
			logger.error("Couldn't deserialize configuration webrequest from {}", webResource.getURI().toString());
		} catch (Exception e) {
			logger.error("Couldn't deserialize configuration webrequest from {}", webResource.getURI().toString(), e);
		}

		//TODO : should throw something meaningful here
		return null;
	}
}
