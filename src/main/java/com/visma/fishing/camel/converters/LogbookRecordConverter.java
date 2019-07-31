package com.visma.fishing.camel.converters;

import com.visma.fishing.model.CommunicationType;
import com.visma.fishing.model.Logbook;
import org.apache.camel.dataformat.csv.CsvRecordConverter;
import org.apache.commons.csv.CSVRecord;

import java.util.AbstractMap;
import java.util.Map;

public class LogbookRecordConverter implements CsvRecordConverter<Map.Entry<String, Logbook>> {
    @Override
    public Map.Entry<String, Logbook> convertRecord(CSVRecord record) {
        Logbook logbook = new Logbook();
        logbook.setCommunicationType(CommunicationType.valueOf(record.get("communicationType")));
        return new AbstractMap.SimpleEntry<>(record.get("ID"), logbook);
    }
}
