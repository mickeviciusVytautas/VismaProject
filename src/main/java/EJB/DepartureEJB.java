package EJB;

import model.Departure;

import java.util.List;

public interface DepartureEJB {
    List findAll();
    Departure findById(Long id);
    void create(Departure departure);
    void update(Long id, Departure departure);
    void remove(Long id);
}
