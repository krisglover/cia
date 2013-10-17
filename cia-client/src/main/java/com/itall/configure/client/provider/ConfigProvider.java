package com.itall.configure.client.provider;

import java.util.List;

import com.itall.configure.client.models.Config;

/**
 * Interface which defines the entry point to the configuration client. Any implementation of this interface should build
 * a {@link TypedMap} that contains all configuration values
 * 
 * @author kglover
 *
 */
public interface ConfigProvider {

		/**
		 * Fetches all configs applicable to the associated environment and application
		 * Exactly 1 config for each key/value should be returned by this method. 
		 * 
		 * TODO : possibly need to change this return type to a SET to enforce the one config constraint 
		 * 
		 * @param environment
		 * @param application
		 * @return
		 */
		public List<Config> getAll(String environment,String application);
	
}
