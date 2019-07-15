package com.visma.fishing.strategy;

import com.visma.fishing.model.Logbook;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class FileSavingStrategy implements SavingStrategy {

    private String path;

    public FileSavingStrategy(String path) {
        if(path != null) {
            this.path = path;
        } else {
            this.path = "C:\\dev\\database\\";
        }
    }

    @Override
    public void save(Logbook logbook) {
        String filePath = this.path + System.currentTimeMillis() + ".json";
        try (FileWriter fileWriter = new FileWriter(filePath)){
            log.info("Writing logbook to file system...");
            fileWriter.write(logbook.toString());
        } catch (IOException e) {
            log.error(e.toString());
        }

    }

}
