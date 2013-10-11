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

	public CIAConfiguration(){
		this.springContextFile = "ciaServerContext.xml";
	}

	public String getSpringContextFile() {
		return springContextFile;
	}

	public void setSpringContextFile(String springContextFile) {
		this.springContextFile = springContextFile;
	}
}
