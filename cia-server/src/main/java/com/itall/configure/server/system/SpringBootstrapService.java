package com.itall.configure.server.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itall.configure.client.Manager;
import com.itall.configure.client.ResolvingManager;
import com.itall.configure.client.provider.ConfigProvider;
import com.itall.configure.client.provider.LocalConfigProvider;
import com.itall.configure.client.provider.RestConfigProvider;
import com.itall.configure.spring.CIAPropertyPlaceholderConfigurer;
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

		// ENVIRONMENTAL CONFIGS
		// configuration.getHttpConfiguration().setPort(port)

		// APPLICATION PARAMETERS

		//NOTE: If there are objects that need to be consumed by the application we can create a parent context that makes the configuration available.
		
		@SuppressWarnings("resource")
		// Suppressing because the resource is properly deconstructed via the shutdown hook. The compiler can't see that
		// NOTE : we're intentionally using the default constructor so that the container does not call #refresh immediately. 
		// We need to inject the property configurer first
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
		ctx.setConfigLocation(configuration.getSpringContextFile());

		//Dropwizard bootstraped property configurer
		if (configuration.getConfigProperties().isConfigServiceEnabled()) {
			List<ConfigProvider> prioritizedConfigProviders = new ArrayList<ConfigProvider>();
			if (configuration.getConfigProperties().getConfigRestUrl() != null) {
				ConfigProvider cp = new RestConfigProvider(configuration.getConfigProperties().getConfigRestUrl());
				prioritizedConfigProviders.add(cp);
			}
			ConfigProvider cp = new LocalConfigProvider(configuration.getLocalConfigs());
			prioritizedConfigProviders.add(cp);
			Manager dropWizardManager = new ResolvingManager(configuration.getServiceEnv(), configuration.getServiceApp(), prioritizedConfigProviders);
			PropertyPlaceholderConfigurer ciaPlaceholderConfiguer = new CIAPropertyPlaceholderConfigurer(dropWizardManager );
			ctx.addBeanFactoryPostProcessor(ciaPlaceholderConfiguer);
		}

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

	public static void main(String[] args) throws Exception {
		new SpringBootstrapService().run(args);
	}
}
