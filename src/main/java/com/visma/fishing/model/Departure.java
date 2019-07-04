package com.visma.fishing.model;

import com.visma.fishing.auxilary.LocalDateDeserializer;
import com.visma.fishing.auxilary.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@Entity
public class Departure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    @Setter(AccessLevel.NONE)
    private Long id;
    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
    @NotNull
    private String port;

    public Departure(LocalDate date, String port) {
        this.date = date;
        this.port = port;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
                .add("port", port)
                .add("date", date.toString())
                .build();
    }

}
