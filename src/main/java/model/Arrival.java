package model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Arrival {

    private String port;
    private Date date;
}
