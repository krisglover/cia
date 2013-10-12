package com.itall.configure.server.dao;

import java.util.List;

import com.itall.configure.server.models.Config;

/**
 * Database backed implemenation of {@link ConfigurationDAO}
 * 
 * @author kglover
 *
 */
public class DatabaseConfigurationDAO implements ConfigurationDAO{

	@Override
	public List<Config> getByEnvironment(String environment) {
		
		return null;
	}

	@Override
	public List<Config> getByApplication(String environment, String application) {
		
		return null;
	}

	@Override
	public void upsert(Config config) {
		
		
	}

}
