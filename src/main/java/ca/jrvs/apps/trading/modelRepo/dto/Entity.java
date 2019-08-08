package ca.jrvs.apps.trading.modelRepo.dto;

public interface Entity<ID> {
    ID getID();

    void setID(ID id);
}
