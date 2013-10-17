package com.itall.configure.client.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itall.configure.client.models.Config;

/**
 * This implementation of {@link ConfigProvider} allows configs to be provided up front and resolved as needed
 * 
 * @author kglover
 *
 */
public class LocalConfigProvider implements ConfigProvider{

	private final List<Config> allConfigs;
	
	public LocalConfigProvider(List<Config> allConfigs){
		this.allConfigs = allConfigs;
	}
	
	@Override
	public List<Config> getAll(String environment, String application) {
		// TODO : make this a configurable strategy so It can be used everywhere 
		// Could sort and pick in a guaranteed O(nlogn) solution
		// This should be an efficient O(n) solution. 1 pass over configs loaded,
		// 1 map lookup per object (ideally O(1) each time but worst case O(n))
		// then 2 max comparisons per object
		Map<String, Config> resolvedConfigs = new HashMap<>();
		for (Config config : this.allConfigs) {

			// only eligible if env == null && app == null or env == val && app == null or env == val && app = val
			if ((config.getApplication() == null && config.getEnvironment() == null)
					|| (config.getEnvironment().equalsIgnoreCase(environment) && config.getApplication() == null)
					|| (config.getEnvironment().equalsIgnoreCase(environment) && config.getApplication().equalsIgnoreCase(application))) {

				// if one does exist then check to see if which is a better fit
				Config currentBestConfig = resolvedConfigs.get(config.getKey());

				//Check for best fit
				if ((currentBestConfig == null) || 
					(currentBestConfig.getEnvironment() == null && config.getEnvironment() != null) ||
					(currentBestConfig.getApplication() == null && config.getApplication() != null)) {
					//config is better then existing best
					resolvedConfigs.put(config.getKey(), config);
				}
			}
		}
		return new ArrayList<Config>(resolvedConfigs.values());
	}

}
