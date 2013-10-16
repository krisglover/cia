package com.itall.configure.server.service;

import java.util.List;

import com.itall.configure.client.models.Config;
import com.itall.configure.server.dao.ConfigurationDAO;
import com.itall.configure.server.exception.ConfiurationException;

/**
 * Default service implementation.
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

	
	@Override
	public void upsertConfigs(List<Config> configs) {
		for(Config config : configs){
			this.configurationDAO.upsert(config);
		}
	}
}
