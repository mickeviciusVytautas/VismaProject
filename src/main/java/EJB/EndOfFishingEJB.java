package EJB;

import model.EndOfFishing;

import java.util.List;

public interface EndOfFishingEJB {
    List findAll();
    EndOfFishing findById(Long id);
    void create(EndOfFishing endOfFishing);
    void update(Long id, EndOfFishing endOfFishing);
    void remove(Long id);
}
