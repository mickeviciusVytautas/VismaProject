package EJB;

import model.Catch;
import model.Logbook;

import java.util.List;

public interface CatchEjb {
    List<Catch> findAll();
    void create(Catch aCatch);
    void update(Long id, Catch aCatch);
    void remove(Long id);
}
