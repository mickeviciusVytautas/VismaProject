package com.visma.fishing.camel;

import com.visma.fishing.services.LogbookService;
import io.xlate.inject.Property;
import io.xlate.inject.PropertyResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
@Slf4j
public class Bootstrap {

    private CamelContext context = new DefaultCamelContext();
    private CamelContext zipContext = new DefaultCamelContext();
    @Inject
    @Property(name = "inboxFolder",
            resource = @PropertyResource("classpath:application.properties"),
            defaultValue = "C:\\dev\\inbox\\")
    private String inboxFolder;

    @Inject
    private LogbookService logbookService;

    @PostConstruct
    public void init (){
        log.info("Create CamelContext and register Camel Router.");
        try {
            zipContext.addRoutes(new ZipRouteBuilder(logbookService));
            context.addRoutes(new DataSaveRouteBuilder(inboxFolder));
            zipContext.start();
            context.start();
        } catch (Exception e) {
            log.error("Failed to create CamelContext and register Camel Router.", e);
        }
    }

    @PreDestroy
    public void shutdown(){
        try {
            zipContext.stop();
            context.stop();
        } catch (Exception e) {
            log.error("CamelContext stopping failed.", e);
        }
    }
}
