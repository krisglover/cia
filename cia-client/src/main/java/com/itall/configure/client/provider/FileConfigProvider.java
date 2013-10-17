package com.itall.configure.client.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itall.configure.client.models.Config;

/**
 * This implementation of {@link ConfigClient} is responsible for pulling configs from the filesystem in a JSON list
 * 
 * expected file format [ { "key": "jarName", "value": "sampleSsa.jar", "environment": "prod", "application": "ssa" }, { "key":"timeoutMS", "value":"-1" } ]
 * 
 * @author kglover
 * 
 */
public class FileConfigProvider implements ConfigProvider {

	private static Logger logger = LoggerFactory.getLogger(FileConfigProvider.class);

	private final String filePath;

	public FileConfigProvider(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public List<Config> getAll(String environment, String application) {

		File configFile = new File(this.filePath);
		try (FileInputStream fis = new FileInputStream(configFile);) {
			List<Config> configs = deserializeJSONConfigs(fis);
			Map<String, Config> resolvedConfigs = new HashMap<>();

			// TODO : make this a configurable strategy so It can be used everywhere 
			// Could sort and pick in a guaranteed O(nlogn) solution
			// This should be an efficient O(n) solution. 1 pass over configs loaded,
			// 1 map lookup per object (ideally O(1) each time but worst case O(n))
			// then 2 max comparisons per object
			for (Config config : configs) {

				// only eligible if env == null && app == null or env == val && app == null or env == val && app = val
				if ((config.getApplication() == null && config.getEnvironment() == null)
						|| (config.getEnvironment().equalsIgnoreCase(environment) && config.getApplication() == null)
						|| (config.getEnvironment().equalsIgnoreCase(environment) && config.getApplication().equalsIgnoreCase(application))) {

					// if one does exist then check to see if which is a better fit
					Config currentBestConfig = resolvedConfigs.get(config.getKey());

					//Check for best fit
					if ((currentBestConfig == null) || 
						(currentBestConfig.getEnvironment() == null && config.getEnvironment() != null) ||
						(currentBestConfig.getApplication() == null && config.getApplication() != null)) {
						//config is better then existing best
						resolvedConfigs.put(config.getKey(), config);
					}
				}
			}
			return new ArrayList<Config>(resolvedConfigs.values());
		} catch (FileNotFoundException e) {
			logger.error("File {} not found", this.filePath, e);
		} catch (IOException e) {
			logger.error("Failed to parse json file ", e);
		}
		
		//TODO : throw something meaningful here
		return null;
	}

	/**
	 * TODO : this should probably be turned into an external json parsing function
	 * 
	 * @param fis
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	private List<Config> deserializeJSONConfigs(InputStream fis) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonParser jp;

		jp = mapper.getJsonFactory().createJsonParser(fis);
		return mapper.readValue(jp, new TypeReference<List<Config>>() {
		});
	}
}
