package com.visma.fishing.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visma.fishing.auxilary.ConnectionType;
import com.visma.fishing.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@NamedQueries(
        @NamedQuery(name = "logbook.findAll", query = "SELECT l FROM Logbook l")
)
public class Logbook extends BaseEntity {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Departure departure;
    @NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
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

    public Logbook(Departure departure, List<Catch> catchList, Arrival arrival, EndOfFishing endOfFishing, String connectionType) {
        this.departure = departure;
        this.catchList = catchList;
        this.arrival = arrival;
        this.endOfFishing = endOfFishing;
        this.connectionType = ConnectionType.valueOf(connectionType);
    }

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String logbook = null;
        try {
            logbook = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
         return logbook;
    }

}
