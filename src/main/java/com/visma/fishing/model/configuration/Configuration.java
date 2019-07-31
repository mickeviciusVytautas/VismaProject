package com.visma.fishing.model.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.validation.constraints.NotNull;

/*
 * https://stackoverflow.com/questions/2324109/what-is-the-best-way-to-manage-configuration-data
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMapping(name = "ConfigurationValueByKey", classes = {
        @ConstructorResult(targetClass = Configuration.class,
                columns = {@ColumnResult(name = "mode"), @ColumnResult(name = "key"), @ColumnResult(name = "key")})
})
public class Configuration {

    @NotNull
    private String mode;

    @Id
    @NotNull
    private String key;

    @NotNull
    private String value;

}
