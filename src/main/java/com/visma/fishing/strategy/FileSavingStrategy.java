package com.visma.fishing.strategy;

import com.visma.fishing.model.Logbook;

import javax.ws.rs.core.Response;
import java.io.FileWriter;
import java.io.IOException;

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
    public Response save(Logbook logbook) {
        String filePath = this.path + System.currentTimeMillis() + ".json";
        try (FileWriter fileWriter = new FileWriter(filePath)){
            fileWriter.write(logbook.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.status(Response.Status.CREATED).entity("Successfully saved logbook to file system.").build();
    }

}
