package com.visma.fishing.camel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visma.fishing.model.Logbook;
import io.xlate.inject.Property;
import io.xlate.inject.PropertyResource;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import javax.inject.Inject;
import java.io.File;

public class DataSaveRouteBuilder extends RouteBuilder {

    @Inject
    @Property(name = "inboxFolder",
            resource = @PropertyResource("classpath:application.properties"))
    private String inboxPath;

    private static final String TIMER_CONFIGURATION = "timer://dataTimer?fixedRate=true&period=10000&delay=5s";
    private static final String FILE_LOCATION = "file:C:\\dev\\inbox\\?delete=true&noop=false";
    private static final String HTTP_POST_LOGBOOK = "http://localhost:8080/exploded/api/logbook/";

    @Override
    public void configure() {
        from(TIMER_CONFIGURATION)
                .pollEnrich(FILE_LOCATION)
                .process(exchange -> {
                    File file = exchange.getIn().getBody(File.class);
                    ObjectMapper mapper = new ObjectMapper();
                    Logbook logbook;
                    logbook = mapper.readValue(file, Logbook.class);
                    exchange.getOut().setBody(logbook.toString());
                })
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to(HTTP_POST_LOGBOOK);
    }

}
