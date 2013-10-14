package com.itall.configure.server.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itall.configure.server.exception.ConfiurationException;
import com.itall.configure.server.models.Config;
import com.itall.configure.server.models.Response;
import com.itall.configure.server.models.Status;
import com.itall.configure.server.service.ConfigureService;

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
	@Path("/{env}/{application}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpecificConfig(@PathParam("env") String env, @PathParam("application") String application, @PathParam("name") String name)
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
	@Path("/{env}/{application}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApplicationConfigs(@PathParam("env") String env, @PathParam("application") String application) throws ConfiurationException {

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
}
