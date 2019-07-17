package com.visma.fishing.camel.routes;

import com.visma.fishing.camel.converters.*;
import com.visma.fishing.model.*;
import com.visma.fishing.services.LogbookService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.ZipFileDataFormat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Stateless
public class ZipCsvParseRoute extends RouteBuilder {
    @Inject
    private LogbookService logbookService;

    private Map<String, Arrival> arrivalMap = new HashMap<>();
    private Map<String, Departure> departureMap = new HashMap<>();
    private Map<String, EndOfFishing> endOfFishingMap = new HashMap<>();
    private Map<String, List<Catch>> catchMap = new HashMap<>();

    private CsvDataFormat csvArrivalFormat = new CsvDataFormat();
    private CsvDataFormat csvDepartureFormat = new CsvDataFormat();
    private CsvDataFormat csvEndOfFishingFormat = new CsvDataFormat();
    private CsvDataFormat csvCatchFormat = new CsvDataFormat();
    private CsvDataFormat csvLogbookFormat = new CsvDataFormat();

    @Override
    public void configure() {

        setupCsvDataFormat();

        ZipFileDataFormat zipFile = new ZipFileDataFormat();
        zipFile.setUsingIterator(true);

        from("file:C:\\dev")
                .unmarshal(zipFile)
                .split(body())
                .streaming()
                .to("file:C:\\dev\\data");
        from("file:C:\\dev\\data?fileName=Catch.csv")
                .unmarshal(csvCatchFormat)
                .split(body())
                .process(exchange -> {
                    Entry<String, Catch> entry = exchange.getIn().getBody(Entry.class);
                    if (catchMap.containsKey(entry.getKey())) {
                        catchMap.get(entry.getKey()).add(entry.getValue());
                    } else {
                        List<Catch> catches = new ArrayList<>();
                        catches.add(entry.getValue());
                        catchMap.put(entry.getKey(), catches);
                    }
                });
        from("file:C:\\dev\\data?noop=true&fileName=Arrival.csv")
                .unmarshal(csvArrivalFormat)
                .split(body())
                .process(exchange ->
                    arrivalMap.putAll(exchange.getIn().getBody(Map.class))
                );
        from("file:C:\\dev\\data?fileName=Departure.csv")
                .unmarshal(csvDepartureFormat)
                .split(body())
                .process(exchange ->
                    departureMap.putAll(exchange.getIn().getBody(Map.class))
                );
        from("file:C:\\dev\\data?noop=true&fileName=EndOfFishing.csv")
                .unmarshal(csvEndOfFishingFormat)
                .split(body())
                .process(exchange ->
                    endOfFishingMap.putAll(exchange.getIn().getBody(Map.class))
                );

        from("file:C:\\dev\\data?fileName=Logbook.csv")
                .unmarshal(csvLogbookFormat)
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

    private void setupCsvDataFormat() {
        csvArrivalFormat
                .setSkipHeaderRecord(true)
                .setDelimiter(';')
                .setRecordConverter(new ArrivalRecordConverter());
        csvDepartureFormat
                .setSkipHeaderRecord(true)
                .setDelimiter(';')
                .setRecordConverter(new DepartureRecordConverter());
        csvEndOfFishingFormat
                .setSkipHeaderRecord(true)
                .setDelimiter(';')
                .setRecordConverter(new EndOfFishingConverter());
        csvCatchFormat
                .setSkipHeaderRecord(true)
                .setDelimiter(';')
                .setRecordConverter(new CatchRecordConverter());
        csvLogbookFormat
                .setSkipHeaderRecord(true)
                .setRecordConverter(new LogbookRecordConverter());
    }

}
