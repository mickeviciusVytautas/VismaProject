package camel;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;
import resources.LogbookResource;

public class DataSaveContext {

    public static void start() {


        try {
//            JndiContext jndiContext = new JndiContext();
//            jndiContext.bind("Logbook", new LogbookResource());
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
