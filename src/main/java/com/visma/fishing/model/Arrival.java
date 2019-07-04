package com.visma.fishing.model;

import com.visma.fishing.auxilary.LocalDateDeserializer;
import com.visma.fishing.auxilary.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table
public class Arrival implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;
    @NotNull
    @Column
    private String port;
    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    public Arrival(String port, LocalDate date) {
        this.port = port;
        this.date = date;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
                .add("port", port)
                .add("date", date.toString())
                .build();
    }
}
