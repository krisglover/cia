package com.itall.configure.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class RestConfigClient implements ConfigClient {

	private static Logger logger = LoggerFactory.getLogger(RestConfigClient.class);
	
	private final Client client ;
	private final WebResource baseConfigWebResource;
	
	public RestConfigClient(String urlToConfig){
		ClientConfig cc = new DefaultClientConfig(JacksonJsonProvider.class);
		client = Client.create(cc);
		
		this.baseConfigWebResource = client.resource(urlToConfig);	
	}
	
	@Override
	public Map<String,Config> getAll(String environment, String application) {

		WebResource webResource = baseConfigWebResource.path(environment).path(application);
		
		try{
			ClientResponse clientResponse = webResource.accept("application/json").get(ClientResponse.class);
			if (clientResponse .getStatus() != 200) {
				throw new HTTPException(clientResponse.getStatus());
			}
			
			ResponseAllConfigs allConfigsResponse = clientResponse.getEntity(ResponseAllConfigs.class);
			
			if(!Status.SUCCESS.toString().equalsIgnoreCase(allConfigsResponse.getStatus())){
				//TODO : this should be some type of service exception
				throw new Exception("Service exception [status :  " + allConfigsResponse.getStatus() + " , message : " + allConfigsResponse.getMessage()+"]");
			}
			
			List<Config> configs = allConfigsResponse.getData();
			Map<String,Config> configMap = new HashMap<String,Config>();
			for(Config config : configs)
				configMap.put(config.getKey(), config);
			
			//TODO : if REST read is succesful turn configs into map
			//TODO : get overrides from filesystem ---- new ConfigClient
			//TODO : write the new object to the filesystem 
			
			// Rest / Filesys / overrides
			
			//TODO : may want to wrap in a helper so config values can be fetched as the data type they're intended to be used as
			return configMap;
		}catch(HTTPException he){
			logger.error("Couldn't deserialize configuration webrequest from {}",webResource.getURI().toString() );
		}catch (Exception e){
			logger.error("Couldn't deserialize configuration webrequest from {}",webResource.getURI().toString(), e );
		}
	
		//TODO : If REST Read fails, attempt to read old file on filesystem
		
		
		logger.error("FATAL : There are no configurations available." +
				" The global store is unresponsive and the local configuration file is either missing or corrupt");
		
		throw new RuntimeException("FATAL : There are no configurations available" );
	}

}
