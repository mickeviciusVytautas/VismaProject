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
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Departure departure;
    @NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Catch> catchList = new ArrayList<>();
    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Arrival arrival;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
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
                .add("logbook", Json.createObjectBuilder()
                    .add("departure", departure.toJson())
                    .add("catchList",  jsonArrayBuilder.build())
                    .add("arrival", arrival.toJson())
                    .add("endOfFishing", endOfFishing.toJson())
                        .build())
                .build();
    }
}
