package com.visma.fishing.services.impl.integration;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ArchiveServiceEJBIntegrationTest {
    @Deployment
    public static WebArchive createDeployment() {
        System.out.println(2);
        return null;
    }
}
