package com.streetbees;

import com.codahale.metrics.health.HealthCheck;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.streetbees.config.AppConfig;
import com.streetbees.spring.SpringContextLoaderListener;
import io.dropwizard.Application;
import io.dropwizard.jersey.sessions.HttpSessionProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.eclipse.jetty.server.session.SessionHandler;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.*;
import javax.ws.rs.Path;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by streetbees on 12/09/2014.
 */
public class AdminConsoleService extends Application<AppConfig> {

    public static void main(String[] args) throws Exception {
        new AdminConsoleService().run(args);
    }

    @Override
    public String getName() {
        return "base-rest-application";
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        bootstrap.addBundle(new ViewBundle());
        bootstrap.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //serves up HTML.
//        bootstrap.addBundle(new AssetsBundle("/assets/", "/myapp/"));
    }

    @Override
    public void run(AppConfig configuration, Environment environment) throws Exception {
        // Init Spring context before we init the app context, we have to create a parent context with all the
        // config objects others rely on to get initialized
        AnnotationConfigWebApplicationContext parent = new AnnotationConfigWebApplicationContext();
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();

        parent.refresh();
        parent.getBeanFactory().registerSingleton("configuration", configuration);
        parent.registerShutdownHook();
        parent.start();

        //the real main app context has a link to the parent context
        ctx.setParent(parent);
        ctx.register(MyAppSpringConfiguration.class);
        ctx.refresh();
        ctx.registerShutdownHook();
        ctx.start();

        //now that Spring is started, let's get all the beans that matter into DropWizard
        //health checks
//        Map<String, HealthCheck> healthChecks = ctx.getBeansOfType(HealthCheck.class);
//        for(Map.Entry<String,HealthCheck> entry : healthChecks.entrySet()) {
//            environment.addHealthCheck(entry.getValue());
//        }


        //resources
        Map<String, Object> resources = ctx.getBeansWithAnnotation(Path.class);
        for(Map.Entry<String,Object> entry : resources.entrySet()) {
            environment.jersey().register(entry.getValue());
        }

        environment.jersey().register(HttpSessionProvider.class);
        environment.servlets().setSessionHandler(new SessionHandler());

        //last, but not least, let's link Spring to the embedded Jetty in Dropwizard
        environment.servlets().addServletListeners(new SpringContextLoaderListener(ctx));

        //activate Spring Security filter
        FilterRegistration.Dynamic filterRegistration = environment.servlets().addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
        filterRegistration.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
    }
}
