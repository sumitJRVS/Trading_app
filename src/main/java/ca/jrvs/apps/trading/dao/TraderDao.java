package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.modelRepo.dto.Trader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;


@Repository
public class TraderDao extends JdbcCrudDao<Trader, Integer> {
    private static final String TABLE_NAME = "trader";
    private static final String ID_NAME = "id";
    private Logger logger = LoggerFactory.getLogger(TraderDao.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    //Constructor
    @Autowired
    public TraderDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate((dataSource));
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_NAME);
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
        return ID_NAME;
    }

    @Override
    Class getEntityClass() {
        return TraderDao.class;
    }


    //Method that takes ID + Amount and returns the updated Account
    public Trader createTrader(String first_name, String last_name, String dob, String country, String email) {
        Trader trader = new Trader();
        trader.setFirstName(first_name);
        trader.setLastName(last_name);
        trader.setDob(dob);
        trader.setCountry(country);
        trader.setEmail(email);
        return trader;
    }

    @Override
    public Trader save(Trader entity) {
        return super.save(entity);
    }

    @Override
    public Trader findById(Integer integer) {
        return super.findById(integer);
    }

    @Override
    public Trader findById(String idName, Integer integer, boolean forUpdate, Class clazz) {
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
}
