package com.visma.fishing.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.visma.fishing.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@NamedQueries(
        @NamedQuery(name = "arrival.findAll", query = "SELECT A FROM Arrival A")
)
@CsvRecord(separator = ",", skipFirstLine = true)
public class Arrival extends BaseEntity {

    @NotNull
    @DataField(pos = 1)
    private String port;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @DataField(pos = 2)
    protected Date date;

    public Arrival(String port, Date date) {
        this.port = port;
        this.date = date;
    }

    public Arrival(String id, String port, Date date) {
        setId(id);
        this.port = port;
        this.date = date;
    }

}
