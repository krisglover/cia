package com.itall.configure.client;

import java.util.Properties;

/**
 * Interface that describes the client facing entry point to the resolved configuration values
 * 
 * @author kglover
 *
 */
public interface Manager {

	public String getAsString(String key);
	
	public Integer getAsInt(String key);
	
	public Boolean getAsBoolean(String key);
	
	public Long getAsLong(String key);
	
	public Properties getAsProperties();
	
}
