package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Data
@NoArgsConstructor
@XmlRootElement
@Entity
@NamedQuery(name = Logbook.FIND_ALL, query = "select g from Logbook g")
public class Logbook {

    public static final String FIND_ALL = "findAll";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;
    @NotNull
    @OneToOne
    private Departure departure;
    @NotNull
    @OneToOne
    private Catch aCatch;
    @NotNull
    @OneToOne
    private Arrival arrival;
    @NotNull
    @OneToOne
    private EndOfFishing endOfFishing;

    public Logbook(Departure departure){
        this.departure = departure;
    }
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
