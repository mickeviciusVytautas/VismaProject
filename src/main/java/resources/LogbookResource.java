package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/logbook")
public class LogbookResource {

    @GET
    public String getLogbooks() {
        return "returning all logbooks";
    }
}