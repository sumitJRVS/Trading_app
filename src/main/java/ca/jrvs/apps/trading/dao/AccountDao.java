package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.modelRepo.dto.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AccountDao extends JdbcCrudDao<Account, Integer> {

    private static final String TABLE_NAME = "account";
    private static final String ID_NAME = "id";
    private Logger logger = LoggerFactory.getLogger(ca.jrvs.apps.trading.dao.AccountDao.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    //Constructor
    @Autowired
    public AccountDao(DataSource dataSource) {
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
    public Class getEntityClass() {
        return ca.jrvs.apps.trading.dao.TraderDao.class;
    }

    //Method that takes ID + amount and returns the updated Account
    public Account updateAmountByID(Integer id, Double amount) {
        String query = "UPDATE" + " " + TABLE_NAME + " " + "SET amount=? WHERE id=?";
        Integer update = jdbcTemplate.update(query, amount, id);
        return findById(id);


    }


}
