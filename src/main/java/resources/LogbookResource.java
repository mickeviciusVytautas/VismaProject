package resources;

import model.Catch;
import model.Departure;
import model.Logbook;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.Date;

@Path("/logbook")
public class LogbookResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Logbook getLogbooks() {
        return new Logbook(new Departure(new Date(), "port"), new Catch("afrikines silkes", 1L  ));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Logbook createLogbook(Logbook logbook){
        return logbook;
    }
}