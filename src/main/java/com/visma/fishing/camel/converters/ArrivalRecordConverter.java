package com.visma.fishing.camel.converters;

import com.visma.fishing.model.Arrival;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.dataformat.csv.CsvRecordConverter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ArrivalRecordConverter implements CsvRecordConverter<Map<String, Arrival>> {
    @Override
    public Map<String, Arrival> convertRecord(CSVRecord record) {
        Map<String, Arrival> map = new HashMap<>();
        try {
            Arrival arrival = new Arrival(record.get("ID"), record.get("port"), DateUtils.parseDate(record.get("date"), "yyyy-MM-dd"));
            map.put(record.get("logbookID"), arrival);
        } catch (ParseException e) {
            log.warn(e.toString());
        }
        return map;
    }
}
