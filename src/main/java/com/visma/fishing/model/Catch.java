package com.visma.fishing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Data
@NoArgsConstructor
@Entity
public class Catch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @NotNull
    private String species;
    @NotNull
    private Long weight;

    public Catch(String species, Long weight) {
        this.species = species;
        this.weight = weight;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
                .add("species", species)
                .add("weight", weight)
                .build();
    }

    public Long getId(){
        return id;
    }
}
