package com.visma.fishing.resources;

import com.visma.fishing.EJB.LogbookEJB;
import com.visma.fishing.auxilary.ConnectionType;
import com.visma.fishing.auxilary.DateFormatter;
import com.visma.fishing.model.*;
import sun.rmi.runtime.Log;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Path("/logbook")
public class LogbookResource {

    @Inject
    LogbookEJB logbookEJB;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLogbook(@Valid Logbook logbook){
        return logbookEJB.create(logbook);
    }

    @POST
    @Path("/satellite")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createLogbookToFileSystem(@Valid Logbook logbook){
        logbook.setConnectionType(ConnectionType.SATELLITE);
        String localDate = DateFormatter.formatLocalDateTime(LocalDateTime.now());
        String filePath = "C:\\dev\\inbox\\" + localDate + ".json";
        try (FileWriter fileWriter = new FileWriter(filePath)){
            fileWriter.write(logbook.toJson().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok("Successfully sent Logbook to inbox directory.").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getLogbook(@PathParam("id") Long id) {
        Optional<Logbook> optionalLogbook = logbookEJB.findById(id);
        if(optionalLogbook.isPresent()) {
            return Response.ok(optionalLogbook.get().toJson())
                    .build();
        }
        return Response.noContent().build();

    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogbooks() {
        List<Logbook> logbookList = logbookEJB.findAll();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        logbookList.forEach(l -> jsonArrayBuilder.add(l.toJson()));
        return Response.ok(jsonArrayBuilder.build())
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteLogbookById(@PathParam("id")Long id) {
        logbookEJB.remove(id);
        return Response.ok().build();
    }

}