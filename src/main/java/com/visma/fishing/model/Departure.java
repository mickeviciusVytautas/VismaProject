package com.visma.fishing.model;

import com.visma.fishing.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@NamedQueries(
        @NamedQuery(name = "departure.findAll", query = "SELECT d FROM Departure d")
)
public class Departure extends BaseEntity {

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;
    @NotNull
    private String port;

    public Departure(String port, Date date) {
        this.date = date;
        this.port = port;
    }

}
