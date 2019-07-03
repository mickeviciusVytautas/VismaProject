package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

@Data
@NoArgsConstructor
@Entity
public class Catch {


    @Id
    @XmlTransient
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
                .add("Species", species)
                .add("Weight", weight)
                .build();
    }

}
