package com.visma.fishing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.visma.fishing.auxilary.LocalDateDeserializer;
import com.visma.fishing.auxilary.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class EndOfFishing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    public EndOfFishing(LocalDate date) {
        this.date = date;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
                .add("date", date.toString())
                .build();
    }

    public Long getId(){
        return id;
    }
}
