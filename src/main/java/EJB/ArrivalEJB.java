package EJB;

import model.Arrival;

import java.util.List;

public interface ArrivalEJB {
    List findAll();
    void create(Arrival arrival);
    void update(Long id, Arrival arrival);
    void remove(Long id);
}
