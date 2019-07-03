package camel;

import org.apache.camel.*;
import org.apache.camel.spi.RouteContext;

import java.util.List;
import java.util.Map;

public class Route implements RoutesBuilder {

    @Override
    public void addRoutesToCamelContext(CamelContext context) throws Exception {
        System.out.println("Hello world");
    }
}
