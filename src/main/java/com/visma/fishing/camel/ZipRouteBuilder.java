package com.visma.fishing.camel;

import com.visma.fishing.camel.converters.*;
import com.visma.fishing.model.*;
import com.visma.fishing.services.LogbookService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.ZipFileDataFormat;
import org.apache.camel.util.jndi.JndiContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ZipRouteBuilder extends RouteBuilder {
    private LogbookService logbookService;

    private Map<String, Arrival> arrivalMap = new HashMap<>();
    private Map<String, Departure> departureMap = new HashMap<>();
    private Map<String, EndOfFishing> endOfFishingMap = new HashMap<>();
    private Map<String, List<Catch>> catchMap = new HashMap<>();
    private List<Logbook> logbooks = new ArrayList<>();

    public ZipRouteBuilder(LogbookService logbookService) {
        this.logbookService = logbookService;
    }


    @Override
    public void configure() throws Exception {
        JndiContext context = new JndiContext();
        context.bind("service", logbookService);
        CsvDataFormat csvArrival = new CsvDataFormat();
        csvArrival.setSkipHeaderRecord(true).setDelimiter(';').setRecordConverter(new ArrivalRecordConverter());
        CsvDataFormat csvDeparture = new CsvDataFormat();
        csvDeparture.setSkipHeaderRecord(true).setDelimiter(';').setRecordConverter(new DepartureRecordConverter());
        CsvDataFormat csvEndOfFishing = new CsvDataFormat();
        csvEndOfFishing.setSkipHeaderRecord(true).setDelimiter(';').setRecordConverter(new EndOfFishingConverter());
        CsvDataFormat csvCatch = new CsvDataFormat();
        csvCatch.setSkipHeaderRecord(true).setDelimiter(';').setRecordConverter(new CatchRecordConverter());
        CsvDataFormat csvLogbook = new CsvDataFormat();
        csvLogbook.setSkipHeaderRecord(true).setRecordConverter(new LogbookRecordConverter());

        ZipFileDataFormat zipFile = new ZipFileDataFormat();
        zipFile.setUsingIterator(true);

        from("file:C:\\dev?noop=true")
                .unmarshal(zipFile)
                .split(body())
                .streaming()
                .to("file:C:\\dev\\data");
        from("file:C:\\dev\\data?noop=true&fileName=Catch.csv")
                .unmarshal(csvCatch)
                .split(body())
                .process(exchange -> {
                    Entry<String, Catch> entry = (exchange.getIn().getBody(Entry.class));
                    if (catchMap.containsKey(entry.getKey())) {
                        catchMap.get(entry.getKey()).add(entry.getValue());
                    } else {
                        List<Catch> catches = new ArrayList<>();
                        catches.add(entry.getValue());
                        catchMap.put(entry.getKey(), catches);
                    }
                });
        from("file:C:\\dev\\data?noop=true&fileName=Arrival.csv")
                .unmarshal(csvArrival)
                .split(body())
                .process(exchange -> {
                    arrivalMap.putAll((Map<String, Arrival>) exchange.getIn().getBody());
                });
        from("file:C:\\dev\\data?noop=true&fileName=Departure.csv")
                .unmarshal(csvDeparture)
                .split(body())
                .process(exchange -> {
                    departureMap.putAll(exchange.getIn().getBody(Map.class));
                });
        from("file:C:\\dev\\data?noop=true&fileName=EndOfFishing.csv")
                .unmarshal(csvEndOfFishing)
                .split(body())
                .process(exchange -> {
                    endOfFishingMap.putAll((Map<String, EndOfFishing>) exchange.getIn().getBody());
                });

        from("file:C:\\dev\\data?noop=true&fileName=Logbook.csv")
                .unmarshal(csvLogbook)
                .split(body())
                .process(exchange -> {
                    Logbook logbook = exchange.getIn().getBody(Logbook.class);
                    String id = logbook.getId();
                    logbook.setArrival(arrivalMap.get(id));
                    logbook.setEndOfFishing(endOfFishingMap.get(id));
                    logbook.setDeparture(departureMap.get(id));
                    logbook.setCatchList(catchMap.get(id));
                    exchange.getOut().setBody(logbook);
                })
                .bean(logbookService, "create");

    }

}
