package com.itall.configure.server.dao;

import java.util.List;

import com.itall.configure.server.models.Config;

/**
 * DAO to get or update configuration values
 * 
 * @author kglover
 *
 */
public interface ConfigurationDAO {
	
	/**
	 * Get specific value based on the given parameters 
	 * 
	 * @param environment
	 * @param application
	 * @param name
	 * @return
	 */
	public Config getValue(String environment, String application,String name);
	
	/**
	 * Get all configs for the specified environment with specific application overrides from the backing data store
	 * @param environment
	 * @param application
	 * @return
	 */
	public List<Config> getByApplication(String environment, String application);

	/**
	 * insert or update specified config 
	 * @param config
	 */
	public void upsert(Config config);
	
}
