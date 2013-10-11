package com.itall.configure.server.system;

import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.tasks.Task;
import com.yammer.metrics.core.HealthCheck;

public class SpringBootstrapService extends Service<CIAConfiguration> {

	@Override
	public void initialize(Bootstrap<CIAConfiguration> bootstrap) {

		bootstrap.setName("Configure It All");
	}

	@Override
	public void run(CIAConfiguration configuration, Environment environment) throws Exception {
		
		//ENVIRONMENTAL CONFIGS
		//configuration.getHttpConfiguration().setPort(port)
		
		//APPLICATION PARAMETERS
		// before we init the app context, we have to create a parent context with all the config objects others rely on to get initialized
		ClassPathXmlApplicationContext parent = new ClassPathXmlApplicationContext();
		
		@SuppressWarnings("resource") // Suppressing because the resource is properly deconstructed via the shutdown hook. The compiler can't see that
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(configuration.getSpringContextFile());

		// make configuration object available
		parent.refresh();
		parent.getBeanFactory().registerSingleton("configuration", configuration);
		parent.registerShutdownHook();
		parent.start();

		// the real main app context has a link to the parent context
		ctx.setParent(parent);
		ctx.refresh();
		ctx.registerShutdownHook();
		ctx.start();

		// now that Spring is started, let's get all the beans that matter into DropWizard

		// health checks
		Map<String, HealthCheck> healthChecks = ctx.getBeansOfType(HealthCheck.class);
		for (Map.Entry<String, HealthCheck> entry : healthChecks.entrySet()) {
			environment.addHealthCheck(entry.getValue());
		}

		// resources
		Map<String, Object> resources = ctx.getBeansWithAnnotation(Path.class);
		for (Map.Entry<String, Object> entry : resources.entrySet()) {
			environment.addResource(entry.getValue());
		}

		// tasks
		Map<String, Task> tasks = ctx.getBeansOfType(Task.class);
		for (Map.Entry<String, Task> entry : tasks.entrySet()) {
			environment.addTask(entry.getValue());
		}

		// JAX-RS providers
		Map<String, Object> providers = ctx.getBeansWithAnnotation(Provider.class);
		for (Map.Entry<String, Object> entry : providers.entrySet()) {
			environment.addProvider(entry.getValue());
		}
	}
	
	public static void main(String[] args) throws Exception{
		new SpringBootstrapService().run(args);
	}
}
