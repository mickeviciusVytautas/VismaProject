package com.visma.fishing.camel.converters;

import com.visma.fishing.model.CommunicationType;
import com.visma.fishing.model.Logbook;
import org.apache.camel.dataformat.csv.CsvRecordConverter;
import org.apache.commons.csv.CSVRecord;

public class LogbookRecordConverter implements CsvRecordConverter<Logbook> {
    @Override
    public Logbook convertRecord(CSVRecord record) {
        Logbook logbook = new Logbook();
        logbook.setId(record.get("ID"));
        logbook.setCommunicationType(CommunicationType.valueOf(record.get("communicationType")));
        return logbook;
    }
}
