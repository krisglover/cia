package com.itall.configure.server.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.base.Optional;
import com.itall.configure.client.Manager;
import com.itall.configure.client.ResolvingManager;
import com.itall.configure.client.models.User;
import com.itall.configure.client.provider.ConfigProvider;
import com.itall.configure.client.provider.LocalConfigProvider;
import com.itall.configure.client.provider.RestConfigProvider;
import com.itall.configure.spring.CIAPropertyPlaceholderConfigurer;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;
import com.yammer.dropwizard.auth.basic.BasicAuthProvider;
import com.yammer.dropwizard.auth.basic.BasicCredentials;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.tasks.Task;
import com.yammer.metrics.core.HealthCheck;

/**
 * Generic DropWizard bootstrap service that integrates Spring and a CIA property configurer
 * 
 * @author kglover
 *
 */

//TODO : externalize this. It's a generic service class that can be reused.

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
		
		// NOTE : we're intentionally using the default constructor so that the container does not call #refresh immediately. 
		// We need to inject the property configurer first
		
		//TODO : need to put a different auth provider in here
	    environment.addProvider(new BasicAuthProvider<User>(new SimpleAuthenticator(), "Auth provider"));
		
		@SuppressWarnings("resource")
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
		
		//TODO : figure out if I should add InjectableProviders for auth reasons
//		Map<String, Object> injectableProviders = ctx.getBeansOfType(Provider.class);
//		for (Map.Entry<String, Object> entry : providers.entrySet()) {
//			environment.addProvider(entry.getValue());
//		}
	}

	public static void main(String[] args) throws Exception {
		new SpringBootstrapService().run(args);
	}
	
	private static class SimpleAuthenticator implements Authenticator<BasicCredentials, User> {
	    @Override
	    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
	        if ("secret".equals(credentials.getPassword())) {
	            return Optional.of(new User(credentials.getUsername()));
	        }
	        return Optional.absent();
	    }
	}
}
