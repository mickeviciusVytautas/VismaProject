package camel;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class RouteContext {

    public void cameel(){
        CamelContext camelContext = new DefaultCamelContext();

        try {
            camelContext.addRoutes(new Route());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
