package com.visma.fishing.model;

import com.visma.fishing.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Lob;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Archive extends BaseEntity {

    private Date archivingDate;

    @Lob
    private String serializedLogbook;

    public Archive(String serializedLogbook) {
        this.serializedLogbook = serializedLogbook;
        archivingDate = new Date();
    }
}
