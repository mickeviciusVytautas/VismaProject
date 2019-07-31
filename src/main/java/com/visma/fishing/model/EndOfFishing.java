package com.visma.fishing.model;

import com.visma.fishing.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
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

    @Version
    private Long version;

    public EndOfFishing(Date date) {
        this.date = date;
    }
}

