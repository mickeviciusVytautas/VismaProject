package model;

import lombok.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class Departure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    @Setter(AccessLevel.NONE)
    private Long id;
    @NotNull
    private Date date;
    @NotNull
    private String port;

    public Departure(Date date, String port) {
        this.date = date;
        this.port = port;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
                .add("date", date.toString())
                .add("port", port)
                .build();
    }

}
