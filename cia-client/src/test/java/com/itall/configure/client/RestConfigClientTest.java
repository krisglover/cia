package com.itall.configure.client;

import org.junit.Test;

import com.itall.configure.client.models.Config;
import com.itall.configure.client.provider.FileConfigProvider;

public class RestConfigClientTest {

	//NOTE : This is actually a funcitonal test and does not belong here. It's just being used for dev purposes and should be removed
	// or refactored into a true unit test
	@Test
	public void fetchTest(){ 
		
//		FileConfigProvider fcp = new FileConfigProvider("overrides.json");
//		for(Config config : fcp.getAll("prod", "ssa")){
//			System.out.println(config);
//		}
		
//		RestConfigProvider rcc = new RestConfigProvider("http://localhost:8080/configuration");
//		rcc.getAll("prod", "ssa");
//		
	}
	
}
