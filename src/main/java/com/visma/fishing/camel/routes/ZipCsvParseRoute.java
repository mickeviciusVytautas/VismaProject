package com.visma.fishing.camel.routes;

import com.visma.fishing.camel.converters.ArrivalRecordConverter;
import com.visma.fishing.camel.converters.CatchRecordConverter;
import com.visma.fishing.camel.converters.DepartureRecordConverter;
import com.visma.fishing.camel.converters.EndOfFishingConverter;
import com.visma.fishing.camel.converters.LogbookRecordConverter;
import com.visma.fishing.model.Arrival;
import com.visma.fishing.model.Catch;
import com.visma.fishing.model.Departure;
import com.visma.fishing.model.EndOfFishing;
import com.visma.fishing.model.Logbook;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.ZipFileDataFormat;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Stateless
public class ZipCsvParseRoute extends RouteBuilder {
    private static final String HTTP_POST_LOGBOOK = "http://localhost:8080/fishing/api/logbook/";
    private static final String CAMEL_FILE_NAME = "CamelFileName";

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

        from("file:C:\\dev\\data")
                .choice()
                .when(header(CAMEL_FILE_NAME).isEqualTo("Arrival.csv"))
                    .unmarshal(csvArrivalFormat)
                    .split(body())
                    .process(exchange ->
                        arrivalMap.putAll(exchange.getIn().getBody(Map.class))
                    )
                .endChoice()
                    .when(header(CAMEL_FILE_NAME).isEqualTo("Catch.csv"))
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
                    })
                    .endChoice()
                    .when(header(CAMEL_FILE_NAME).isEqualTo("Departure.csv"))
                        .unmarshal(csvDepartureFormat)
                        .split(body())
                        .process(exchange ->
                            departureMap.putAll(exchange.getIn().getBody(Map.class)))
                    .endChoice()
                    .when(header(CAMEL_FILE_NAME).isEqualTo("EndOfFishing.csv"))
                        .unmarshal(csvEndOfFishingFormat)
                        .split(body())
                        .process(exchange ->
                            endOfFishingMap.putAll(exchange.getIn().getBody(Map.class)))
                    .endChoice()
                    .when(header(CAMEL_FILE_NAME).isEqualTo("Logbook.csv"))
                        .unmarshal(csvLogbookFormat)
                        .split(body())
                        .process(exchange -> {
                            Logbook logbook = exchange.getIn().getBody(Logbook.class);
                            String id = logbook.getId();
                            logbook.setArrival(arrivalMap.get(id));
                            logbook.setEndOfFishing(endOfFishingMap.get(id));
                            logbook.setDeparture(departureMap.get(id));
                            logbook.setCatchList(catchMap.get(id));
                            exchange.getOut().setBody(logbook.toString());
                        })
                        .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                        .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                        .to(HTTP_POST_LOGBOOK)
                    .endChoice();
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
