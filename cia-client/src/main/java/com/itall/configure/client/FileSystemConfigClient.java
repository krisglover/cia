package com.itall.configure.client;

import java.util.Map;

import com.itall.configure.client.models.Config;

/**
 * This implementation of {@link ConfigClient} is responsible for pulling configs from the filesystem
 * 
 * @author kglover
 *
 */
public class FileSystemConfigClient implements ConfigClient{

	private String path = "override.json";
	
	public FileSystemConfigClient(){}
	
	public FileSystemConfigClient(String filePath){
		this.path = filePath;
	}
	
	@Override
	public Map<String, Config> getAll(String environment, String application) {
		// TODO Auto-generated method stub
		return null;
	}

}
