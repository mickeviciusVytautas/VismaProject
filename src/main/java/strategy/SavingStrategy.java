package strategy;

import model.Logbook;

public interface SavingStrategy {
    void save(Logbook logbook);
}
