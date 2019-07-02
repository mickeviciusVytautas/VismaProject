package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

@Data
@NoArgsConstructor
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
