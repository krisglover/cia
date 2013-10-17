package com.itall.configure.server.system;

import java.util.Collections;
import java.util.List;

import com.itall.configure.client.models.Config;
import com.yammer.dropwizard.config.Configuration;

/**
 * 
 * The Configure It All bootstrap configuration object
 * 
 * @author kglover
 * 
 */

// TODO : make this part of a common service lib... or maybe part of the client ... or maybe just leave as service

public class CIAConfiguration extends Configuration {

	private String springContextFile;
	private String serviceEnv;
	private String serviceApp;
	private ConfigProperties configProperties = new ConfigProperties(); // default
	private List<Config> localConfigs = Collections.emptyList(); // default

	public CIAConfiguration() {
		this.springContextFile = "applicationContext.xml";
	}

	public String getSpringContextFile() {
		return springContextFile;
	}

	public void setSpringContextFile(String springContextFile) {
		this.springContextFile = springContextFile;
	}

	public String getServiceEnv() {
		return serviceEnv;
	}

	public void setServiceEnv(String serviceEnv) {
		this.serviceEnv = serviceEnv;
	}

	public String getServiceApp() {
		return serviceApp;
	}

	public void setServiceApp(String serviceApp) {
		this.serviceApp = serviceApp;
	}

	public List<Config> getLocalConfigs() {
		return localConfigs;
	}

	public void setLocalConfigs(List<Config> localConfigs) {
		this.localConfigs = localConfigs;
	}

	public ConfigProperties getConfigProperties() {
		return configProperties;
	}

	public void setConfigProperties(ConfigProperties configProperties) {
		this.configProperties = configProperties;
	}

	public static class ConfigProperties {
		private boolean configServiceEnabled = true;
		private String configRestUrl;

		public String getConfigRestUrl() {
			return configRestUrl;
		}

		public void setConfigRestUrl(String configRestUrl) {
			this.configRestUrl = configRestUrl;
		}

		public boolean isConfigServiceEnabled() {
			return configServiceEnabled;
		}

		public void setConfigServiceEnabled(boolean configServiceEnabled) {
			this.configServiceEnabled = configServiceEnabled;
		}

	}
}
