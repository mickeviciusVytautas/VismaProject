package com.visma.fishing.model;

import com.visma.fishing.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@NamedQueries(
        @NamedQuery(name = "catch.findAll", query = "SELECT c FROM Catch c")
)
public class Catch extends BaseEntity {

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

}
