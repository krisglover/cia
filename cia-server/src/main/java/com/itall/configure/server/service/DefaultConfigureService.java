package com.itall.configure.server.service;

import java.util.List;

import org.springframework.util.StringUtils;

import com.itall.configure.server.dao.ConfigurationDAO;
import com.itall.configure.server.exception.ConfiurationException;
import com.itall.configure.server.models.Config;

/**
 * Default service implementation.
 * 
 * @author kglover
 *
 */
public class DefaultConfigureService implements ConfigureService{

	private final ConfigurationDAO configurationDAO;
	
	private DefaultConfigureService(ConfigurationDAO configurationDAO){
		this.configurationDAO = configurationDAO;
	}
	
	@Override
	public List<Config> fetchConfigs(String environment, String application) throws ConfiurationException {

		List<Config> configMap ; 
		
		if(!StringUtils.isEmpty(environment) && !StringUtils.isEmpty(application)){
			configMap = this.configurationDAO.getByApplication(environment, application);
		}else if(!StringUtils.isEmpty(environment)){
			configMap = this.configurationDAO.getByEnvironment(environment);
		}else{
			throw new ConfiurationException("[environment : "+ environment + ", application : " + application +"] is not valid" +
										 " You must provide a valid [environment] or [environment AND application]");
		}
		return configMap;
	}

	@Override
	public void upsertConfigs(List<Config> configs) {
		for(Config config : configs){
			this.configurationDAO.upsert(config);
		}
	}
}
