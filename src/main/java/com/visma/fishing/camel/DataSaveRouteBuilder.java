package com.visma.fishing.camel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visma.fishing.auxilary.ConnectionType;
import com.visma.fishing.model.Logbook;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import java.io.File;

public class DataSaveRouteBuilder extends RouteBuilder {

//    TODO: "&delete=true" doesn't work (.convertBodyTo(String.class) after from is also not helping)
    @Override
    public void configure() throws Exception {
        from("file:C:\\dev\\files\\?noop=false&delete=true")
                .process(exchange -> {
                    File file = exchange.getIn().getBody(File.class);
                    ObjectMapper mapper = new ObjectMapper();
                    Logbook logbook;
                    logbook = mapper.readValue(file, Logbook.class);
                    logbook.setConnectionType(ConnectionType.ONLINE);
                    exchange.getOut().setBody(logbook.toJson().toString());
                })
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("http://localhost:8080/exploded/api/logbook/");
    }

}
