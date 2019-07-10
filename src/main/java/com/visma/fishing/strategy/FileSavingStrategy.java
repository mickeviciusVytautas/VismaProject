package com.visma.fishing.strategy;

import com.visma.fishing.model.Logbook;

import javax.ws.rs.core.Response;
import java.io.FileWriter;
import java.io.IOException;

public class FileSavingStrategy implements SavingStrategy {

    private String FILE_PATH;

    public FileSavingStrategy(String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
    }

    @Override
    public Response save(Logbook logbook) {
        String localDate = logbook.getDeparture().getDate().toString();
        String filePath = FILE_PATH + System.currentTimeMillis() + ".json";
        try (FileWriter fileWriter = new FileWriter(filePath)){
            fileWriter.write(logbook.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.CREATED).entity("Successfully saved logbook to file system.").build();
    }

}
