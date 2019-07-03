package EJB;

import model.Logbook;
import java.util.List;
import java.util.Optional;

public interface LogbookEJB {
    List findAll();
    Optional<Logbook> findById(Long id);
    void create(Logbook logbook, String location);
    void update(Long id, Logbook logbook);
    void remove(Long id);

}
