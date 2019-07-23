package com.visma.fishing.strategy;

import com.visma.fishing.model.Logbook;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;

import static com.visma.fishing.messages.Messages.LOGBOOK_SAVE_SUCCESS_MSG;
import static com.visma.fishing.messages.Messages.TO_FILE_SYSTEM;

public class FileSavingStrategy implements SavingStrategy {

    private Logger log = LogManager.getLogger(FileSavingStrategy.class);

    private String path = "C:\\dev\\database\\";


    public FileSavingStrategy(String path) {
        if (StringUtils.isNotBlank(path)) {
            this.path = path;
        }
    }

    @Override
    public Logbook save(Logbook logbook) {
        boolean isSaved = true;
        String filePath = path + System.currentTimeMillis() + ".json";
        try (FileWriter fileWriter = new FileWriter(filePath)){
            log.info("Writing logbook to file system...");
            fileWriter.write(logbook.toString());
        } catch (IOException e) {
            log.error(e.toString());
            isSaved = false;
        }
        if(isSaved){
            log.info(LOGBOOK_SAVE_SUCCESS_MSG + TO_FILE_SYSTEM, logbook.getId());

        }
        return logbook;
    }
}
