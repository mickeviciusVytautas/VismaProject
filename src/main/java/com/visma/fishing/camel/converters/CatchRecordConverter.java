package com.visma.fishing.camel.converters;

import com.visma.fishing.model.Catch;
import org.apache.camel.dataformat.csv.CsvRecordConverter;
import org.apache.commons.csv.CSVRecord;

import java.util.HashMap;
import java.util.Map;

public class CatchRecordConverter implements CsvRecordConverter<Map.Entry<String, Catch>> {
    @Override
    public Map.Entry<String, Catch> convertRecord(CSVRecord record) {
        Catch aCatch = new Catch(record.get("ID"), record.get("species"), Long.parseLong(record.get("weight")));
        return new HashMap.SimpleEntry<>(record.get("logbookID"), aCatch);
    }
}
