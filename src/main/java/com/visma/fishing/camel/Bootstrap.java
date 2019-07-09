package com.visma.fishing.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;


@Singleton
@Startup
public class Bootstrap {

    private CamelContext context = new DefaultCamelContext();

    private Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    @PostConstruct
    public void init (){
        logger.info(">> Create CamelContext and register Camel Router.");
        try {
            context.addRoutes(new DataSaveRouteBuilder());
            context.start();
        } catch (Exception e) {
            logger.error(">> Failed to create CamelContext and register Camel Router.", e);

        }
    }

    @PreDestroy
    public void shutdown(){
        try {
            context.stop();
        } catch (Exception e) {
            logger.error(">> CamelContext stopping failed.", e);
        }
        logger.info(">> CamelContext stopped.");
    }
}
