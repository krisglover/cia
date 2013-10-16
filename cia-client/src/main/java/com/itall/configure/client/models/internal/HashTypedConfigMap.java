package com.itall.configure.client.models.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itall.configure.client.models.Config;

/**
 * A {@link TypedMap} backed by a {@link HashMap}
 * 
 * If the datatype cannot be converted to the desired type a NULL will be returned and a conversion error will be logged. 
 * This class prevents exceptions from being thrown due to incompatible types. If an exception is desired you should use an alternate implementation.  
 * 
 * This implementation is immutable and should be considered thread safe
 * 
 * @author kglover
 *
 */
public class HashTypedConfigMap implements TypedConfigMap{

	private static Logger logger = LoggerFactory.getLogger(HashTypedConfigMap.class);
	
	private final Map<String,Config> backingMap ;
	
	public HashTypedConfigMap(Map<String,Config> backingMap){
		this.backingMap = Collections.unmodifiableMap(backingMap);
	}
	
	@Override
	public String getAsString(String key) {
		Config config = backingMap.get(key);
		return config != null ? config.getValue() : null;
	}

	@Override
	public Integer getAsInt(String key) {
		
		Config config = backingMap.get(key);
		try{
			return config != null ? Integer.parseInt(config.getValue()) : null;
		}catch(Exception e){
			logger.error("key : {}  value : {} could not be converted to an Integer" , key , config) ;
			return null; 
		}
	}

	@Override
	public Boolean getAsBoolean(String key) {
	
		String value = backingMap.get(key);
		try{
			return Boolean.parseBoolean(value);
		}catch(Exception e){
			logger.error("key : {}  value : {} could not be converted to an Boolean" , key ,value) ;
			return null; 
		}
	}

	@Override
	public Long getAsLong(String key) {
		String value = backingMap.get(key);
		try{
			return Long.parseLong(value);
		}catch(Exception e){
			logger.error("key : {}  value : {} could not be converted to an Long" , key ,value) ;
			return null; 
		}
	}
}
