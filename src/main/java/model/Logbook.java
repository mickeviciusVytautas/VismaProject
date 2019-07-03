package model;

import auxilary.ConnectionType;
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
public class Logbook {

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
    @NotNull
    @Enumerated(EnumType.STRING)
    private ConnectionType connectionType;
    public Logbook(Departure departure){
        this.departure = departure;
    }
    public Logbook(Departure departure, Catch aCatch, Arrival arrival, EndOfFishing endOfFishing, String connectionType) {
        this.departure = departure;
        this.aCatch = aCatch;
        this.arrival = arrival;
        this.endOfFishing = endOfFishing;
        this.connectionType = ConnectionType.valueOf(connectionType);
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
