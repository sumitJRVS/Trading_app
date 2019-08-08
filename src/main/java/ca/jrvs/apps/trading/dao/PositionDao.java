package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.modelRepo.domain.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PositionDao extends JdbcCrudDao<Position, Integer> {

    private static final String TABLE_NAME = "position";
    private static final String ID_NAME = "account_id";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public PositionDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_NAME);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdName() {
        return null;
    }

    @Override
    Class getEntityClass() {
        return Position.class;
    }

    @Override
    public Position save(Position entity) {
        return super.save(entity);
    }

    @Override
    public Position findById(Integer integer) {
        return super.findById(integer);
    }

    @Override
    public Position findById(String idName, Integer integer, boolean forUpdate, Class clazz) {
        return super.findById(idName, integer, forUpdate, clazz);
    }

    @Override
    public boolean existsById(Integer integer) {
        return super.existsById(integer);
    }

    @Override
    public boolean existsById(String idName, Integer integer) {
        return super.existsById(idName, integer);
    }

    @Override
    public void deleteById(Integer integer) {
        super.deleteById(integer);
    }

    @Override
    public void deleteById(String idName, Integer integer) {
        super.deleteById(idName, integer);
    }


    public List<Position> getPosition(Integer id) {
        String query = "SELECT * from " + TABLE_NAME + " WHERE " + ID_NAME + "=?";
        List<Position> positionInList = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Position.class), id);
        System.out.println(query);
        System.out.println(positionInList);
        return positionInList;
    }

    public List<Position> getPosition(Integer id, String ticker) {
        String query = "SELECT * from " + TABLE_NAME + " WHERE " + ID_NAME + "=?" + " and ticker=?";
        List<Position> positionInList = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Position.class), id, ticker);
        System.out.println(query);
        System.out.println(positionInList);
        return positionInList;
    }
}
