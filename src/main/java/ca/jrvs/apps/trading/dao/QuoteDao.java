package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.modelRepo.dto.Entity;
import ca.jrvs.apps.trading.modelRepo.dto.Quote;
import ca.jrvs.apps.trading.services.QuoteService;
import com.sun.org.apache.xpath.internal.operations.Quo;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.sql.DataSource;

@Component
public class QuoteDao implements CrudRepo<Quote, ID>{
    Logger logger = LoggerFactory.getLogger(QuoteDao.class);
    private final static String TABLE_NAME = "quote";
    private final static String ID_NAME = "ticker";

    private JdbcTemplate jdbcTmp;
    private SimpleJdbcInsert simpleJDBCins;
    //Constructor
    @Autowired
    public QuoteDao(DataSource dataSrc){
        logger.info("Short story long: JdbcTemplate  jdbctempl= new JdbcTemplate();\n" +
                "jdbctempl = new JdbcTemplate(dataSrc);");

        this.jdbcTmp = new JdbcTemplate(dataSrc);
        this.simpleJDBCins  = new SimpleJdbcInsert(dataSrc).withTableName(TABLE_NAME);
    }


    @Override
    public Quote get(Quote thingsFromQuote) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(thingsFromQuote);
        Number assignedNo = this.simpleJDBCins.executeAndReturnKey(sqlParameterSource);
        return thingsFromQuote;
    }

    @Override
    public Quote findById(ID id) {
        return null;
    }

    @Override
    public Boolean existById(ID id) {
        return null;
    }

    @Override
    public void deleteByID(ID id) {

    }
}
