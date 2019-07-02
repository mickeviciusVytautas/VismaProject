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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Data
@NoArgsConstructor
@XmlRootElement
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
                .add("Logbook", Json.createObjectBuilder()
                    .add("Departure", departure.toJson())
                    .add("Catch",  aCatch.toJson())
                    .add("Arrival", arrival.toJson())
                    .add("EndOfFishing", endOfFishing.toJson())
                        .build())
                .build();
    }
}
