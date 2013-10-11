package com.itall.configure.server.dao;

import java.util.Map;

/**
 * DAO to get or update configuration values
 * 
 * @author kglover
 *
 */
public interface ConfigurationDAO {
	
	/**
	 * Get all configs for the specified environment from the backing data store
	 * @param environment
	 * @return
	 */
	public Map<String,String> getByEnvironment(String environment);
	
	/**
	 * Get all configs for the specified environment with specific application overrides from the backing data store
	 * @param environment
	 * @param application
	 * @return
	 */
	public Map<String,String> getByApplication(String environment, String application);

	/**
	 * insert or update specified config by environment
	 * 
	 * @param key
	 * @param value
	 * @param environment
	 */
	public void upsertByEnvironment(String key, String value, String environment);
	
	/**
	 * 
	 * insert or update specified config by environment with special application override
	 * 
	 * @param key
	 * @param value
	 * @param environment
	 * @param application
	 */
	public void upsertByApplication(String key, String value, String environment, String application);
}
