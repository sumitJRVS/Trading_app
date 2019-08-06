package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.modelRepo.dto.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbcCrudDao<E extends Entity, ID> implements CrudRepo<E, ID> {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

    abstract public JdbcTemplate getJdbcTemplate();

    abstract public SimpleJdbcInsert getSimpleJdbcInsert();

    abstract public String getTableName();

    abstract public String getIdName();

    abstract Class getEntityClass();

    @SuppressWarnings("unchecked")
    @Override
    public E save(E entity) {

        try {
            System.out.println(entity);
            SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);

            getSimpleJdbcInsert().execute(parameterSource);
            System.out.println(entity);
            // entity.setID(newId.intValue());
            return entity;
        }

        catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Suckk my dick");
        }
    }

    @Override
    public E findById(ID id) {
        return findById(getIdName(), id, false, getEntityClass());
    }

    @Override
    public E findById(String idName, ID id, boolean forUpdate, Class clazz) {
        E t = null;
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + idName + " =?";

        //Advanced: handle read + update race condition
        if (forUpdate) {
            selectSql += " for update";
        }
        logger.info(selectSql);

        try {
            t = (E) getJdbcTemplate()
                    .queryForObject(selectSql,
                            BeanPropertyRowMapper.newInstance(clazz), id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Can't find trader id:" + id, e);
        }
        if (t == null) {
            throw new IllegalArgumentException("Resource not found");
        }
        return t;
    }

    @Override
    public boolean existsById(ID id) {
        return existsById(getIdName(), id);
    }
    @Override
    public boolean existsById(String idName, ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        String selectSql = "SELECT count(*) FROM " + getTableName() + " WHERE " + idName + " =?";
        logger.info(selectSql);
        Integer count = getJdbcTemplate()
                .queryForObject(selectSql,
                        Integer.class, id);
        return count != 0;
    }

    @Override
    public void deleteById(ID id) {
        deleteById(getIdName(), id);
    }
    @Override
    public void deleteById(String idName, ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }
        String deleteSql = "DELETE FROM " + getTableName() + " WHERE " + idName + " =?";
        logger.info(deleteSql);
        getJdbcTemplate().update(deleteSql, id);
    }
}