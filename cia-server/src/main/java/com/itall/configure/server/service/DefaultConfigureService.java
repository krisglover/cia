package com.itall.configure.server.service;

import java.util.List;

import com.itall.configure.client.models.Config;
import com.itall.configure.server.dao.ConfigurationDAO;
import com.itall.configure.server.exception.ConfiurationException;

/**
 * Default service implementation.
 * 
 * This implementation simply delegates to the DAO layer and assumes necessary business logic is contained there. 
 * This is not the best way to abstract functionality and it introduces a strong dependency between implementations.
 * It would be better to abstract to the business logic in an alternate implementation of ConfigureService and 
 * implement a simplified agnostic DAO layer. We'll do this when we have some more time...
 * 
 * @author kglover
 *
 */
public class DefaultConfigureService implements ConfigureService{

	private final ConfigurationDAO configurationDAO;
	
	private DefaultConfigureService(ConfigurationDAO configurationDAO){
		this.configurationDAO = configurationDAO;
	}
	
	@Override
	public List<Config> fetchConfigs(String environment, String application) throws ConfiurationException {

		List<Config> configs = this.configurationDAO.getByApplication(environment, application);
		return configs;
	}

	@Override
	public Config fetchConfig(String environment, String application,String name) throws ConfiurationException {
		Config config = this.configurationDAO.getValue(environment, application, name);
		return config;
	}

	/**
	 * {@inheritDoc} 
	 * 
	 * This implementation will put null place holders in values that are broader in scope then the provided scope if none exist.
	 * For example :
	 * if the key doesn't exist but an override for a specific application is requested then the global and environment values will be null
	 */
	@Override
	public void upsertConfigs(List<Config> configs) {
		for(Config config : configs){
			this.configurationDAO.upsert(config);
		}
	}
}
