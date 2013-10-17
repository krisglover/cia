package com.itall.configure.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itall.configure.client.models.Config;
import com.itall.configure.client.provider.ConfigProvider;

/**
 * Implementation of {@link Manager} that resolves configuration values from multiple providers and in the following specified priority.
 * 
 * First Priority - FileSystem {@link Config} overrides Second Priority - Global store {@link Config}'s
 * 
 * This is an aggressive implementation so instantiation will fail during construction if configs cannot be resolved
 * 
 * @author kglover
 * 
 */
public class ResolvingManager implements Manager {

	private static Logger logger = LoggerFactory.getLogger(ResolvingManager.class);

	private final List<ConfigProvider> prioritizedConfigProviders ;
	private final Map<String, Config> resolvedConfigs = new HashMap<>();

	/**
	 * 
	 * Prioritized {@link ConfigProvider} is in reverse priority order 
	 * EX : cp 1, cp 2, cp 3 -- cp 1 has lowest priority and cp3 will override anything that's come before it
	 * 
	 * @param environment
	 * @param application
	 * @param prioritizedConfigProviders 
	 */
	public ResolvingManager(String environment, String application, List<ConfigProvider> prioritizedConfigProviders){
		if (environment == null || application == null)
			throw new RuntimeException("Environment and application values cannot be null");
		
		if(prioritizedConfigProviders == null || prioritizedConfigProviders.isEmpty())
			throw new RuntimeException("No valid config providers were found. Must have at least 1 config provider");
		
		this.prioritizedConfigProviders = prioritizedConfigProviders;
		init(environment,application);
	}
	
	public void init(String environment, String application) {
	
		//load configs. Each provider gets higher priority then the last
		for(ConfigProvider configProvider : this.prioritizedConfigProviders){
			try {
				logger.info("Attempting to configuration from provider " + configProvider.getClass());
				List<Config> baseConfigs = configProvider.getAll(environment, application);
				for (Config baseConfig : baseConfigs) {
					resolvedConfigs.put(baseConfig.getKey(), baseConfig);
				}
				logger.info("Successfully loaded configs from " + configProvider.getClass());
			} catch (Exception e) {
				logger.error("Couldn't load configs", e);
			}
		}
	}

	@Override
	public String getAsString(String key) {
		try {
			Config config = resolvedConfigs.get(key);
			return config.getValue();
		} catch (Exception e) {
			logger.error("Config : " + key + " does not exist", e);
			return null;
		}
	}

	@Override
	public Integer getAsInt(String key) {
		try {
			Config config = resolvedConfigs.get(key);
			return Integer.parseInt(config.getValue());
		} catch (NullPointerException npe) {
			logger.error("Config : " + key + " does not exist", npe);
		} catch (Exception e) {
			logger.error("There was a problem parsing Config : " + key, e);
		}
		return null;
	}

	@Override
	public Boolean getAsBoolean(String key) {
		try {
			Config config = resolvedConfigs.get(key);
			return Boolean.parseBoolean(config.getValue());
		} catch (NullPointerException npe) {
			logger.error("Config : " + key + " does not exist", npe);
		} catch (Exception e) {
			logger.error("There was a problem parsing Config : " + key, e);
		}
		return null;
	}

	@Override
	public Long getAsLong(String key) {
		try {
			Config config = resolvedConfigs.get(key);
			return Long.parseLong(config.getValue());
		} catch (NullPointerException npe) {
			logger.error("Config : " + key + " does not exist", npe);
		} catch (Exception e) {
			logger.error("There was a problem parsing Config : " + key, e);
		}
		return null;
	}

	@Override
	public Properties getAsProperties() {
		Properties properties = new Properties();

		for (Entry<String,Config> configEntry : this.resolvedConfigs.entrySet()) {
			properties.put(configEntry.getKey(), configEntry.getValue().getValue());
		}
		return properties;
	}
}
