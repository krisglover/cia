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

	//This update will ensure a global value exists for the specified key. If one doesn't exist it will be created. 
	private final String globalKeyEnforcement = "INSERT INTO Global(name,value) VALUES (?,null) ON DUPLICATE KEY UPDATE name=name";
	
	//These will upsert values in respective tables
	private final String mysqlGlobalUpsert = "INSERT INTO Global(name,value) VALUES (?,?) ON DUPLICATE KEY UPDATE value=?";
	private final String mysqlEnvUpsert = "INSERT INTO EnvironmentOverrides(name,value,environment) VALUES (?,?,?) ON DUPLICATE KEY UPDATE value=?";
	private final String mysqlAppUpsert = "INSERT INTO ApplicationOverrides(name,value,environment,application) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE value=?";
	
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
		
		//resolve how config should be inserted into Database. This needs to be atomic because multiple services may be writing concurrently
		if(config.getEnvironment() == null && config.getApplication() == null){
			jdbcTemplate.update(mysqlGlobalUpsert, config.getKey(),config.getValue(),config.getValue());
		}else if(config.getEnvironment() != null && config.getApplication() == null){
			jdbcTemplate.update(globalKeyEnforcement, config.getKey());
			jdbcTemplate.update(mysqlEnvUpsert, config.getKey(),config.getValue(),config.getEnvironment(),config.getValue());
		}else if(config.getEnvironment() != null && config.getApplication() != null){
			jdbcTemplate.update(globalKeyEnforcement, config.getKey());
			jdbcTemplate.update(mysqlAppUpsert, config.getKey(),config.getValue(),config.getEnvironment(),config.getApplication(),config.getValue());
		}else{
			//not a valid combination of environment and application so no updates should be made and an exception should be thrown
			//TODO : better exception then this garbage
			throw new RuntimeException("bad combination of parameters : [" + config.getEnvironment() +","
						+config.getApplication() + "] are not valid " );
		}
	}
}
