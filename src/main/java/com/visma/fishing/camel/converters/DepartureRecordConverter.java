package com.visma.fishing.camel.converters;

import com.visma.fishing.model.Departure;
import org.apache.camel.dataformat.csv.CsvRecordConverter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class DepartureRecordConverter implements CsvRecordConverter<Map.Entry<String, Departure>> {
    private final Logger log = LogManager.getLogger(DepartureRecordConverter.class);

    @Override
    public Map.Entry<String, Departure> convertRecord(CSVRecord record) {
        Map.Entry<String, Departure> entry = null;
        try {
            Departure departure = new Departure(record.get("port"), DateUtils.parseDate(record.get("date"), "yyyy-MM-dd"));
            entry = new HashMap.SimpleEntry<>(record.get("logbookID"), departure);
        } catch (ParseException e) {
            log.warn(e.toString());
        }
        return entry;
    }
}
