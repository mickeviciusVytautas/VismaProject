package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Departure {
    public Departure() {
    }

    public Departure(Date date, String port) {
        this.date = date;
        this.port = port;
    }

    private Date date;
    private String port;

}
