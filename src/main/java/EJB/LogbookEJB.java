package EJB;

import model.Logbook;
import strategy.SavingStrategy;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

public interface LogbookEJB {
    List findAll();
    Optional<Logbook> findById(Long id);
    Response create(Logbook logbook);
    void update(Long id, Logbook logbook);
    void remove(Long id);

}
