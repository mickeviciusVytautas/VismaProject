package com.visma.fishing.strategy;

import com.visma.fishing.auxilary.DateFormatter;
import com.visma.fishing.model.Logbook;

import javax.ws.rs.core.Response;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileSavingStrategy implements SavingStrategy {

    @Override
    public Response save(Logbook logbook) {
        String localDate = DateFormatter.formatLocalDateTime(LocalDateTime.now());
        String filePath = "C:\\dev\\files\\" + localDate + ".json";
        try (FileWriter fileWriter = new FileWriter(filePath)){
            fileWriter.write(logbook.toJson().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok("Successfully saved logbook to file system.").build();
    }

}
