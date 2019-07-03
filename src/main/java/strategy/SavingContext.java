package strategy;

import model.Logbook;

import javax.ws.rs.core.Response;

public class SavingContext {

    private SavingStrategy savingStrategy;

    private Response create(Logbook logbook, String location){
        if(location != null) {
            savingStrategy = new FileSavingStrategy();
        } else {
            savingStrategy = new DBSavingStrategy();
        }
        savingStrategy.save(logbook);
        return Response.ok().build();
    }
}
