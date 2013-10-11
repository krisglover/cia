package com.itall.configure.server.service;

import java.util.Map;

import com.itall.configure.server.exception.ConfiurationException;

public interface ConfigureService {

	/**
	 * Service method that delivers configs to the controller 
	 * 
	 * @param environment
	 * @param application
	 * @return
	 */
	public Map<String,String> fetchConfigs(String environment,String application)  throws ConfiurationException;
	
}
