package com.itall.configure.server.system;

import com.yammer.dropwizard.config.Configuration;

/**
 *
 * The Configure It All bootstrap configuration object
 * 
 * @author kglover
 *
 */
public class CIAConfiguration extends Configuration {

	private String springContextFile;
	private String jdbcUrl;

	public CIAConfiguration(){
		this.springContextFile = "ciaServerContext.xml";
		this.jdbcUrl = "jdbc:mysql://127.0.0.1/";
	}

	public String getSpringContextFile() {
		return springContextFile;
	}

	public void setSpringContextFile(String springContextFile) {
		this.springContextFile = springContextFile;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
}
