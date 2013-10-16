package com.itall.configure.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import com.itall.configure.client.models.Config;

/**
 * Resolves proper config value based on whether or not an override is present in result set.
 * 
 * @author kglover
 *
 */
public class ResolvingConfigMapper implements RowMapper<Config>{
	
	@Override
	public Config mapRow(ResultSet paramResultSet, int paramInt) throws SQLException {
		
		String name = paramResultSet.getString("name");

		String value = paramResultSet.getString("applicationValue");
		String environment = paramResultSet.getString("applicationEnvironment");
		String application = paramResultSet.getString("application");

		if(StringUtils.isEmpty(value)){
			value = paramResultSet.getString("environmentValue");
			environment = paramResultSet.getString("environment");
		}
		if(StringUtils.isEmpty(value)){
			value = paramResultSet.getString("globalValue");
		}
		return new Config(name,value,environment,application);
	}
}
