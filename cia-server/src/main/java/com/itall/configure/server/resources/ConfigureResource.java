package com.itall.configure.server.resources;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itall.configure.client.models.Config;
import com.itall.configure.client.models.Response;
import com.itall.configure.client.models.Status;
import com.itall.configure.client.models.User;
import com.itall.configure.server.exception.ConfiurationException;
import com.itall.configure.server.service.ConfigureService;
import com.yammer.dropwizard.auth.Auth;

@Path("/configuration")
public class ConfigureResource {

	private static Logger logger = LoggerFactory.getLogger(ConfigureResource.class);
	private final ConfigureService configureService;

	public ConfigureResource(ConfigureService configureService) {
		this.configureService = configureService;
	}

	// TODO : add endpoint that gets entire breakdown with overrides or all

	/**
	 * Returns all default configuration values for the specified environment
	 * 
	 * @param env
	 * @return
	 * @throws ConfiurationException
	 */
	@GET
	@Path("/{env}/{app}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpecificConfig(@PathParam("env") String env, @PathParam("appl") String application, @PathParam("name") String name)
			throws ConfiurationException {

		// TODO : handle errors better here. If nothing found for the specified name should respond with error or something other then 500

		logger.info("Environment : " + env);
		logger.info("Application : " + application);
		logger.info("Name : " + name);

		Response response = new Response();
		try {
			Config config = configureService.fetchConfig(env, application, name);
			response.setData(config);
			response.setStatus(Status.SUCCESS.toString());
		} catch (Exception e) {
			response.setData("Something went really wrong");
			response.setStatus(Status.FAIL.toString());
			response.setCode("999");
		}
		return response;
	}

	/**
	 * Returns all configuration values for the specified environment including specific application overrides
	 * 
	 * @param env
	 * @return
	 * @throws ConfiurationException
	 */
	@GET
	@Path("/{env}/{app}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApplicationConfigs(@Auth(required=true) User user, @PathParam("env") String env, @PathParam("app") String application) throws ConfiurationException {

		logger.info("Environment : " + env);
		logger.info("Application : " + application);

		Response response = new Response();
		try {
			List<Config> configs = configureService.fetchConfigs(env, application);
			response.setData(configs);
			response.setStatus(Status.SUCCESS.toString());
		} catch (Exception e) {
			response.setData("Something went really wrong");
			response.setStatus(Status.FAIL.toString());
			response.setCode("999");
		}
		return response;
	}
	
	/**
	 * Endpoint that restfully updates the config by environment and application 
	 * 
	 * Note : This method reads the value field and ignores all others. 
	 * 
	 * @param user
	 * @param config
	 * @return
	 */
	@POST
	@Path("/{env}/{app}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response upsertEnvAppConfig(@Auth(required = true) User user, @PathParam("env") String env, @PathParam("app") String application,@PathParam("name") String name, Config config){
		
		//Rules - if global isn't defined it's defaulted to null
		//		- if environment isn't defined it's defaulted to null
		//	Each finer config must have a parent value defined
		
		Response response = new Response();
		
		//Use values in config to push 
		// OR use values in path
		
		return response;
	}

	/**
	 * Update the environment specific value of the config 
	 * 
	 * NOTE : This method reads the value field of config and ignores all others
	 * 
	 * @param user
	 * @param config
	 * @return
	 */
	@POST
	@Path("/{env}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response upsertEnvironmentConfig(@Auth(required = true) User user, @PathParam("env") String env,@PathParam("name") String name, Config config){
		
		//Rules - if global isn't defined it's defaulted to null
		//		- if environment isn't defined it's defaulted to null
		//	Each finer config must have a parent value defined
		
		Response response = new Response();
		
		//Use values in config to push 
		// OR use values in path
		
		return response;
	}

	/**
	 * Update the global value for the specified config
	 * 
	 * This method reads the value field and ignores all others
	 * 
	 * @param user
	 * @param config
	 * @return
	 */
	@POST
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response upsertGlobalConfig(@Auth(required = true) User user,@PathParam("name") String name, Config config){
		
		//Rules - if global isn't defined it's defaulted to null
		//		- if environment isn't defined it's defaulted to null
		//	Each finer config must have a parent value defined
		
		Response response = new Response();
		
		//Use values in config to push 
		// OR use values in path
		Config configToUpsert = new Config(name,config.getValue());
		
		configureService.upsertConfigs(Collections.singletonList(configToUpsert));
		
		return response;
	}

	
}
