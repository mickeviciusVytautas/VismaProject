package com.visma.fishing.camel;

import com.visma.fishing.EJB.impl.LogbookEJBImpl;
import com.visma.fishing.resources.LogbookResource;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;
import com.visma.fishing.EJB.LogbookEJB;
import javax.inject.Inject;

public class DataSaveContext {

    @Inject
    LogbookEJB logbookEJB;

    public void start() {


        try {
            CamelContext camelContext = new DefaultCamelContext();
            camelContext.addRoutes(new DataSaveRouteBuilder());
            camelContext.start();
            Thread.sleep(6000);
            camelContext.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
