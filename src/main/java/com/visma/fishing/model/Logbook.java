package com.visma.fishing.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visma.fishing.mixins.BaseMixIn;
import com.visma.fishing.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@NamedQueries(
        @NamedQuery(name = "logbook.findAll", query = "SELECT l FROM Logbook l")
)

public class Logbook extends BaseEntity {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Departure departure;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private EndOfFishing endOfFishing;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Arrival arrival;
    @NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Catch> catchList = new ArrayList<>();
    @NotNull
    @Enumerated(EnumType.STRING)
    private CommunicationType communicationType;

    @Column(name = "CREATION_DATE", nullable = false)
    private Date creationDate = new Date();

    @Version
    private Long version;

    public Logbook(Departure departure, EndOfFishing endOfFishing, Arrival arrival, List<Catch> catchList, String connectionType) {
        this.departure = departure;
        this.catchList = catchList;
        this.arrival = arrival;
        this.endOfFishing = endOfFishing;
        this.communicationType = CommunicationType.valueOf(connectionType);
    }

    public String toStringNoId() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(BaseEntity.class, BaseMixIn.class);
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setCatchList(List<Catch> list) {
        this.catchList.clear();
        if (list != null) {
            this.catchList.addAll(list);
        }
    }


    public static class LogbookBuilder {

        private Departure departure;
        private EndOfFishing endOfFishing;
        private Arrival arrival;
        private List<Catch> catchList;
        private CommunicationType communicationType;
        private String id;

        public LogbookBuilder withDeparture(Departure departure) {
            this.departure = departure;
            return this;
        }

        public LogbookBuilder withEndOfFishing(EndOfFishing endOfFishing) {
            this.endOfFishing = endOfFishing;
            return this;
        }

        public LogbookBuilder withArrival(Arrival arrival) {
            this.arrival = arrival;
            return this;
        }

        public LogbookBuilder withCatchList(List<Catch> catchList) {
            this.catchList = catchList;
            return this;
        }

        public LogbookBuilder withCommunicationType(CommunicationType communicationType) {
            this.communicationType = communicationType;
            return this;
        }

        public LogbookBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public Logbook build() {
            Logbook logbook = new Logbook();
            logbook.setDeparture(departure);
            logbook.setArrival(arrival);
            logbook.setCatchList(catchList);
            logbook.setCommunicationType(communicationType);
            logbook.setEndOfFishing(endOfFishing);
            return logbook;
        }

    }
}
