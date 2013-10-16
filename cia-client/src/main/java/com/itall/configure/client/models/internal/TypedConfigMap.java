package com.itall.configure.client.models.internal;

/**
 * A typed map interface that is intended to read values from an underlying data structure in a map-like way
 * 
 * @author kglover
 *
 */
public interface TypedConfigMap {
	
	public String getAsString(String key);
	
	public Integer getAsInt(String key);
	
	public Boolean getAsBoolean(String key);
	
	public Long getAsLong(String key);

}
