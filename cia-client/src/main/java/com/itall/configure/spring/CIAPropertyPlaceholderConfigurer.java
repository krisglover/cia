package com.itall.configure.spring;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.itall.configure.client.Manager;

/**
 * Spring based property placeholder configurer that is backed by the CIA configuration service
 * 
 * This implementation assumes that two system properties will be available to assist in bootstrapping the configurer [serviceEnv,serviceApp]
 * 
 * @author kglover
 *
 */
public class CIAPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	
	private final Manager configManager;
	
	public CIAPropertyPlaceholderConfigurer(Manager configManager){
		this.configManager = configManager;
	}

	/**
	 * Override default loading strategy and use the provided {@link Manager} to load properties instead
	 */
	@Override
	protected void loadProperties(Properties props) throws IOException {
		Properties properties = this.configManager.getAsProperties();
		props.putAll(properties);
	}

}
