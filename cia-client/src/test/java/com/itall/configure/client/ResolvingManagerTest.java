package com.itall.configure.client;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.itall.configure.client.provider.ConfigProvider;
import com.itall.configure.client.provider.FileConfigProvider;
import com.itall.configure.client.provider.RestConfigProvider;

public class ResolvingManagerTest {
	
	//this is another damn functional test
	@Test
	public void managerTest(){
		List<ConfigProvider> providers = new ArrayList<>();
		providers.add(new RestConfigProvider("http://localhost:8080/configuration"));
		providers.add(new FileConfigProvider("overrides.json"));
		ResolvingManager rm = new ResolvingManager("prod", "ssa", providers);
		
		String jarName = rm.getAsString("jarName");
		System.out.println(jarName);
		
		Long timeout = rm.getAsLong("timeoutMS");
		System.out.println(timeout);
		
		Long brokenJarName = rm.getAsLong("jarName");
		
		System.out.println(brokenJarName);
	}

}
