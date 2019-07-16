package com.visma.fishing.model;

import com.visma.fishing.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@NamedQueries(
        @NamedQuery(name = "catch.findAll", query = "SELECT c FROM Catch c")
)
public class Catch extends BaseEntity {

    @NotNull
    private String species;
    @NotNull
    private Long weight;

    public Catch(String id, String species, Long weight) {
        setId(id);
        this.species = species;
        this.weight = weight;
    }

}
