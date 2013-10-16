package com.itall.configure.client;

import java.util.Map;

import com.itall.configure.client.models.Config;

/**
 * Interface which defines the entry point to the configuration client. Any implementation of this interface should build
 * a {@link TypedMap} that contains all configuration values
 * 
 * @author kglover
 *
 */
public interface ConfigClient {

		public Map<String,Config> getAll(String environment,String application);
	
}
