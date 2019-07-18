package com.visma.fishing.model;

import com.visma.fishing.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQueries(
        @NamedQuery(name = "endOfFishing.findAll", query = "SELECT e FROM EndOfFishing e")
)
public class EndOfFishing extends BaseEntity {

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    public EndOfFishing(String id, Date date) {
        setId(id);
        this.date = date;
    }

}
