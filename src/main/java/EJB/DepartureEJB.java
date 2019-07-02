package EJB;

import model.Departure;
import model.Logbook;

import java.util.List;

public interface DepartureEJB {
    List findAll();
    void create(Departure departure);
    void update(Long id, Departure departure);
    void remove(Long id);
}
