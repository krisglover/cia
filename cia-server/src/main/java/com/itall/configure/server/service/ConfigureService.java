package com.itall.configure.server.service;

import java.util.List;

import com.itall.configure.client.models.Config;
import com.itall.configure.server.exception.ConfiurationException;

public interface ConfigureService {

	/**
	 * Service method that delivers configs to the controller 
	 * 
	 * @param environment
	 * @param application
	 * @return
	 */
	public List<Config> fetchConfigs(String environment,String application)  throws ConfiurationException;
	
	/**
	 * Service method that fetches a specific config
	 * @param environment
	 * @param application
	 * @param name
	 * @return
	 * @throws ConfiurationException
	 */
	public Config fetchConfig(String environment,String application, String name)  throws ConfiurationException;
	
	/**
	 * Upsert configurations
	 * 
	 * @param configs - The configuration values to update
	 */
	public void upsertConfigs(List<Config> configs);
}
