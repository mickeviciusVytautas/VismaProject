package com.visma.fishing.camel.converters;

import com.visma.fishing.model.EndOfFishing;
import org.apache.camel.dataformat.csv.CsvRecordConverter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class EndOfFishingConverter implements CsvRecordConverter<Map.Entry<String, EndOfFishing>> {
    private final Logger log = LogManager.getLogger(EndOfFishingConverter.class);

    @Override
    public Map.Entry<String, EndOfFishing> convertRecord(CSVRecord record) {
        Map.Entry<String, EndOfFishing> entry = null;
        try {
            EndOfFishing endOfFishing = new EndOfFishing(record.get("ID"), DateUtils.parseDate(record.get("date"), "yyyy-MM-dd"));
            entry = new HashMap.SimpleEntry<>(record.get("logbookID"), endOfFishing);
        } catch (ParseException e) {
            log.warn(e.toString());
        }
        return entry;
    }
}
