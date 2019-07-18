package com.visma.fishing.model;

import com.visma.fishing.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
public class Departure extends BaseEntity {

    @NotNull
    private String port;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    public Departure(String id, String port, Date date) {
        setId(id);
        this.date = date;
        this.port = port;
    }

}
