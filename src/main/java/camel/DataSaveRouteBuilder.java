package camel;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.RouteContext;

import java.util.List;
import java.util.Map;

public class DataSaveRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:C:\\Program Files\\wildfly-9.0.2.Final\\bin\\files\\")
//                .process(new Processor() {
//                    public void process(Exchange exchange) throws Exception {
//
//                        String message = exchange.getIn().getBody(String.class);
//                        message = message + "a";
//                        exchange.getOut().setBody(message);
//
//                    }
//                })
                .to("file:C:\\Program Files\\wildfly-9.0.2.Final\\bin\\files1\\");
    }

}
