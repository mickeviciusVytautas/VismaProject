package com.visma.fishing.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.File;

public class DataSaveProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        File file = exchange.getIn().getBody(File.class);


    }
}
