package com.visma.fishing.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.visma.fishing.auxilary.LocalDateDeserializer;
import com.visma.fishing.auxilary.LocalDateSerializer;
import com.visma.fishing.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@NamedQueries(
        @NamedQuery(name = "endOfFishing.findAll", query = "SELECT e FROM EndOfFishing e")
)
public class EndOfFishing extends BaseEntity {

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    public EndOfFishing(Date date) {
        this.date = date;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
                .add("date", date.toString())
                .build();
    }

}
