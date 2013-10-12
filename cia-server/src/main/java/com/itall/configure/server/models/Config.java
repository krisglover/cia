package com.itall.configure.server.models;

public class Config {
	
	private String key;
	private String value;
	private String environment;
	
	public Config(){}
	
	public Config(String key, String value, String environment){
		this(key,value);
		this.environment = environment;
	}
	
	public Config(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
}
