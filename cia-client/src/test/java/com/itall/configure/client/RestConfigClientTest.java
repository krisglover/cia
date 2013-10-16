package com.itall.configure.client;

import org.junit.Test;

public class RestConfigClientTest {

	//NOTE : This is actually a funcitonal test and does not belong here. It's just being used for dev purposes and should be removed
	// or refactored into a true unit test
	@Test
	public void fetchTest(){ 
		
		RestConfigClient rcc = new RestConfigClient("http://localhost:8080/configuration");
		rcc.getAll("prod", "ssa");
		
	}
	
}
