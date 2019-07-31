package com.visma.fishing.camel.converters;

import com.visma.fishing.model.Arrival;
import org.apache.camel.dataformat.csv.CsvRecordConverter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map.Entry;


public class ArrivalRecordConverter implements CsvRecordConverter<Entry<String, Arrival>> {
    private final Logger log = LogManager.getLogger(ArrivalRecordConverter.class);

    @Override
    public Entry<String, Arrival> convertRecord(CSVRecord record) {
        Entry<String, Arrival> entry = null;
        try {
            Arrival arrival = new Arrival(record.get("port"), DateUtils.parseDate(record.get("date"), "yyyy-MM-dd"));
            entry = new HashMap.SimpleEntry<>(record.get("logbookID"), arrival);
        } catch (ParseException e) {
            log.warn(e.toString());
        }
        return entry;
    }
}
