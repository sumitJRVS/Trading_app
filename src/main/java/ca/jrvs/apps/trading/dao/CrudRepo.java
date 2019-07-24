package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.modelRepo.dto.Quote;

/**
 * This is interface for generic CRUD operation
 * In the interface definition: E type is assigned for Entity , ID type assigned to id.
 */
    public interface CrudRepo <E, ID> {


    /**
     * Gets an entity from given entity.
     * @param entity input must not be null.
     * @return not null Entity
     * Throws illegalArguments Exception (if argument is invalid) and SQL Exceptions (if SQL statement execution fails).
     */
    E get(E entity);


    /**
     * Find aan entity by its ID.
     * @param id must not be null.
     * @return not null Entity
     * Throws ResourceNotFound Exception (if no entity found) and SQL Exception (if SQL statement execution fails).
     */
    E findById(ID id);


    /**
     * @param id must not be null.
     * @return the entity from given id (if an entity exist).
     * Throws ReseurceNotFound Exception (if no entity found) and SQL Exception (if SQL statement execution fails).
     */
    Boolean existById(ID id);


    /**
     * @param id must not be null.
     * @return void cant return anything !
     * Throws IllegalArgument Exception (if ID==null or Entity==null) SQL Exception (if SQL statement execution fails) ResourceNotFound Exception if
     */
    void deleteByID(ID id);






}
