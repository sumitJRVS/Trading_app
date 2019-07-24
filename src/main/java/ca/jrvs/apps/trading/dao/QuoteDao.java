package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.services.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class QuoteDao implements CrudRepo{
    Logger logger = LoggerFactory.getLogger(QuoteDao.class);
private final static String TABLE_NAME = "quote";
private final static String ID_NAME = "ticker";


    //Constructor
    @Autowired
    public QuoteDao(DataSource dataSrc){
        logger.info("Short story long: JdbcTemplate  jdbctempl= new JdbcTemplate();\n" +
                "jdbctempl = new JdbcTemplate(dataSrc);");

        JdbcTemplate jdbcTmp = new JdbcTemplate(dataSrc);
        SimpleJdbcInsert simpleJDBCins  = new SimpleJdbcInsert(dataSrc).withTableName(TABLE_NAME);

    }



    @Override
    public Object get(Object entity) {
        return null;
    }










    @Override
    public Object findById(Object o) {
        return null;
    }

    @Override
    public Boolean existById(Object o) {
        return false; // "null" is not a good option here.
    }

    @Override
    public void deleteByID(Object o) {

    }
}
