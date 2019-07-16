package com.visma.fishing.camel.converters;

import com.visma.fishing.model.EndOfFishing;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.dataformat.csv.CsvRecordConverter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class EndOfFishingConverter implements CsvRecordConverter<Map<String, EndOfFishing>> {
    @Override
    public Map<String, EndOfFishing> convertRecord(CSVRecord record) {
        Map<String, EndOfFishing> map = new HashMap<>();
        try {
            EndOfFishing endOfFishing = new EndOfFishing(record.get("ID"), DateUtils.parseDate(record.get("date"), "yyyy-MM-dd"));
            map.put(record.get("logbookID"), endOfFishing);
        } catch (ParseException e) {
            log.warn(e.toString());
        }
        return map;
    }
}
