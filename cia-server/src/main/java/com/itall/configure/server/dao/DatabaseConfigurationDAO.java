package com.itall.configure.server.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.itall.configure.client.models.Config;

/**
 * Database backed implementation of {@link ConfigurationDAO}
 * 
 * This DAO gets, updates, and preserves history of configurations in a database
 * 
 * @author kglover
 * 
 */
public class DatabaseConfigurationDAO implements ConfigurationDAO {

	private final JdbcTemplate jdbcTemplate;

	private final String getAllWithOverrides = "select ge.name, ge.globalValue,ge.environmentValue,a.value as applicationValue, ge.environment, a.application, a.environment as applicationEnvironment from "
			+ "(select g.name,g.value as globalValue,e.value as environmentValue,e.environment from global g left outer join environmentOverrides e on g.name = e.name and e.environment = ?) as ge "
			+ "left outer join applicationOverrides a on ge.name = a.name and a.environment=? and a.application= ?";

	private final String getSpecificValue = getAllWithOverrides + " where ge.name = ?";

	public DatabaseConfigurationDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public List<Config> getByApplication(String environment, String application) {
		List<Config> configs = jdbcTemplate.query(getAllWithOverrides, new ResolvingConfigMapper(), environment, environment, application);
		return configs;
	}

	@Override
	public Config getValue(String environment, String application, String name) {
		Config config = jdbcTemplate.queryForObject(getSpecificValue, new ResolvingConfigMapper(), environment, environment, application, name);
		return config;
	}

	@Override
	public void upsert(Config config) {
//		String key = config.getKey();
//		List<Config> configs = jdbcTemplate.query(getSpecificValue, new ResolvingConfigMapper(), null, null, null, key);
//		if(configs.size() == 1)
		 
		if(config.getEnvironment() == null && config.getApplication() == null){
			// upsert to global only
		}else if(config.getEnvironment() != null && config.getApplication() == null){
			// upsert to global to ensure a default is provided
			// upsert  value to environment table 
		}else if(config.getEnvironment() != null && config.getApplication() != null){
			// upsert to global to ensure a default is provided
			// upsert value to application table 
		}else{
			//not a valid combination of environment and application so no updates should be made and an exception should be thrown
			//TODO : better exception then this garbage
			throw new RuntimeException("bad combination of parameters : [" + config.getEnvironment() +","
						+config.getApplication() + "] are not valid " );
		}
	}
}
