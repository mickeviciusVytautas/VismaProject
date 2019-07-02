package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

@Data
@NoArgsConstructor
public class Logbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;
    @NotNull
    private Departure departure;
    @NotNull
    private Catch aCatch;
    @NotNull
    private Arrival arrival;
    @NotNull
    private EndOfFishing endOfFishing;

    public Logbook(Departure departure, Catch aCatch, Arrival arrival, EndOfFishing endOfFishing) {
        this.departure = departure;
        this.aCatch = aCatch;
        this.arrival = arrival;
        this.endOfFishing = endOfFishing;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("departure", Json.createObjectBuilder()
                    .add("port", departure.getPort())
                    .add("date", departure.getDate().toString())
                    .build())
                .add("catch",  Json.createObjectBuilder()
                    .add("species", aCatch.getSpecies())
                    .add("weight", aCatch.getWeight())
                    .build())
                .add("arrival", Json.createObjectBuilder()
                    .add("port", arrival.getPort())
                    .add("date", arrival.getDate().toString())
                    .build())
                .add("endOfFishing", Json.createObjectBuilder()
                    .add("date", endOfFishing.getDate().toString())
                    .build())
                .build();
    }
}
