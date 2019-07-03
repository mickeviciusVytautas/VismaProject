package strategy;

import model.Logbook;

import javax.ejb.Stateless;
import java.io.FileWriter;
import java.io.IOException;

@Stateless
public class FileSavingStrategy implements SavingStrategy {
    @Override
    public void save(Logbook logbook) {
        try (FileWriter file = new FileWriter("employees.json")) {

            file.write(logbook.toJson().toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
