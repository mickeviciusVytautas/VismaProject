package com.visma.fishing.strategy;

import com.visma.fishing.model.Logbook;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;

public class FileSavingStrategy implements SavingStrategy {

    private Logger log = LogManager.getLogger(FileSavingStrategy.class);

    private String path;

    public FileSavingStrategy(String path) {
        if (StringUtils.isBlank(path)) {
            this.path = path;
        } else {
            this.path = "C:\\dev\\database\\";
        }
    }

    @Override
    public Logbook save(Logbook logbook) {
        String filePath = path + System.currentTimeMillis() + ".json";
        try (FileWriter fileWriter = new FileWriter(filePath)){
            log.info("Writing logbook to file system...");
            fileWriter.write(logbook.toString());
        } catch (IOException e) {
            log.error(e.toString());
        }
        return logbook;
    }
}
