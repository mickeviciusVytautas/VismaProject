package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Arrival")
public class Arrival implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;
    @NotNull
    @Column
    private String port;
    @NotNull
    @Column
    private Date date;

    public Arrival(String port, Date date) {
        this.port = port;
        this.date = date;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
                .add("Port", port)
                .add("Date", date.toString())
                .build();
    }
}
