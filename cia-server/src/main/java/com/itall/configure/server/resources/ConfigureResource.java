package com.itall.configure.server.resources;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/configuration")
public class ConfigureResource {

	private static Logger logger = LoggerFactory.getLogger(ConfigureResource.class);
	
	/**
	 * Returns all default configuration values for the specified environment
	 * @param env
	 * @return
	 */
	@GET
	@Path("/{env}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String,String> getEnvironmentalConfigs(@PathParam("env") String env){
	
		logger.info("Environment : " + env);
		
		//Create DAO
		
		//Call DAO and return configs
		
		Map<String,String> configs = new HashMap<String,String>();
		configs.put("hello", "world");
		configs.put("key1", "value1");
		configs.put("key2", "value2");
		
		return configs;	
	}
	
	/**
	 * Returns all configuration values for the specified environment including specific application overrides
	 * @param env
	 * @return
	 */
	@GET
	@Path("/{env}/{application}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String,String> getApplicationConfigs(@PathParam("env") String env,@PathParam("application") String application){
	
		logger.info("Environment : " + env);
		logger.info("Application : " + application);
		
		Map<String,String> configs = new HashMap<String,String>();
		configs.put("hello", "world");
		configs.put("key1", "value1");
		configs.put("key2", "value2");
		
		return configs;	
	}
	
}
