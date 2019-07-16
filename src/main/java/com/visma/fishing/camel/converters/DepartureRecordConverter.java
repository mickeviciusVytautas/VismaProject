package com.visma.fishing.camel.converters;

import com.visma.fishing.model.Departure;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.dataformat.csv.CsvRecordConverter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DepartureRecordConverter implements CsvRecordConverter<Map<String, Departure>> {
    @Override
    public Map<String, Departure> convertRecord(CSVRecord record) {
        ;
        Map<String, Departure> map = new HashMap<>();
        try {
            Departure departure = new Departure(record.get("ID"), record.get("port"), DateUtils.parseDate(record.get("date"), "yyyy-MM-dd"));
            map.put(record.get("logbookID"), departure);
        } catch (ParseException e) {
            log.warn(e.toString());
        }
        return map;
    }
}
