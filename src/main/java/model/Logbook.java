package model;

import auxilary.ConnectionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany
    private List<Catch> catchList = new ArrayList<>();
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
    public Logbook(Departure departure, List<Catch> catchList, Arrival arrival, EndOfFishing endOfFishing, String connectionType) {
        this.departure = departure;
        this.catchList = catchList;
        this.arrival = arrival;
        this.endOfFishing = endOfFishing;
        this.connectionType = ConnectionType.valueOf(connectionType);
    }

    public JsonObject toJson() {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        catchList.forEach(c -> jsonArrayBuilder.add(c.toJson()));
        return Json.createObjectBuilder()
                .add("Logbook", Json.createObjectBuilder()
                    .add("Departure", departure.toJson())
                    .add("CatchList",  jsonArrayBuilder.build())
                    .add("Arrival", arrival.toJson())
                    .add("EndOfFishing", endOfFishing.toJson())
                        .build())
                .build();
    }
}
