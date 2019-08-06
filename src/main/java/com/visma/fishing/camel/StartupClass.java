package com.visma.fishing.camel;

import com.visma.fishing.camel.routes.JsonParseRoute;
import com.visma.fishing.camel.routes.ZipCsvParseRoute;
import com.visma.fishing.model.security.Role;
import com.visma.fishing.model.security.User;
import com.visma.fishing.security.utils.RoleName;
import com.visma.fishing.security.service.AuthenticationService;
import io.xlate.inject.Property;
import io.xlate.inject.PropertyResource;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.ArrayList;

@Singleton
@Startup
public class StartupClass {

    private final Logger log = LogManager.getLogger(StartupClass.class);

    private CamelContext context = new DefaultCamelContext();
    @Inject
    @Property(name = "inboxFolder",
            resource = @PropertyResource("classpath:application.properties"),
            defaultValue = "C:\\dev\\inbox\\")
    private String inboxFolder;

    @Inject
    private ZipCsvParseRoute zipCsvParseRoute;

    @Inject
    private AuthenticationService authenticationService;

    @PostConstruct
    public void init() {

        createUser();

        log.info("Create CamelContext and register Camel Router.");
        try {
            context.addRoutes(zipCsvParseRoute);
            context.addRoutes(new JsonParseRoute(inboxFolder));
            context.start();
        } catch (Exception e) {
            log.error("Failed to create CamelContext and register Camel Router.", e);
        }
    }

    private void createUser() {
        authenticationService.createRole(new Role(RoleName.MANAGER));
        Role role = authenticationService.findRoleByName(RoleName.MANAGER);
        authenticationService.createUser(
                new User("admin", "password",
                        new ArrayList() {{
                            add(role);
                        }}
                )
        );
    }

    @PreDestroy
    public void shutdown() {
        boolean isSuccessful = true;
        try {
            context.stop();
        } catch (Exception e) {
            log.error("CamelContext stopping failed.", e);
            isSuccessful = false;
        }
        if (isSuccessful) {
            log.info("CamelContext stopped");
        }
    }
}
