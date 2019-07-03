package strategy;

import model.Logbook;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.io.FileWriter;
import java.io.IOException;

public class FileSavingStrategy implements SavingStrategy {
    @Override
    public Response save(Logbook logbook) {
        try (FileWriter file = new FileWriter("C:\\Users\\vytautas.mickevicius\\Desktop\\employees.json")) {
            file.write(logbook.toJson().toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.ok("Successfully saved logbook to file system.").build();
    }
}
