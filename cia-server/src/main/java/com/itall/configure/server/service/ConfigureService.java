package com.itall.configure.server.service;

import java.util.List;

import com.itall.configure.server.exception.ConfiurationException;
import com.itall.configure.server.models.Config;

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
	 * Upsert configurations
	 * 
	 * @param configs - The configuration values to update
	 */
	public void upsertConfigs(List<Config> configs);
}
