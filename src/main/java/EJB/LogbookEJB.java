package EJB;

import model.Logbook;
import java.util.List;

public interface LogbookEJB {
    List findAll();
    void create(Logbook logbook);
    void update(Long id, Logbook logbook);
    void remove(Long id);
}
