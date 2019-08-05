package com.visma.fishing.strategy;

import com.visma.fishing.model.Logbook;
import com.visma.fishing.services.impl.LogbookServiceEJB;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.transaction.Transactional;
import java.io.FileWriter;
import java.io.IOException;

import static com.visma.fishing.messages.Messages.LOGBOOK_SAVE_FILESYSTEM_SUCCESS_MSG;

public class FileSavingStrategy implements SavingStrategy {
    private final Logger log = LogManager.getLogger(LogbookServiceEJB.class);

    private String path = "C:\\dev\\database\\";

    public FileSavingStrategy(String path) {
        if (StringUtils.isNotBlank(path)) {
            this.path = path;
        }
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void save(Logbook logbook) {
        boolean isSaved = true;
        String filePath = path + System.currentTimeMillis() + ".json";
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            log.info("Writing logbook to file system...");
            fileWriter.write(logbook.toString());
        } catch (IOException e) {
            log.error(e.toString());
            isSaved = false;
        }
        if (isSaved) {
            log.info(LOGBOOK_SAVE_FILESYSTEM_SUCCESS_MSG);

        }
    }
}
