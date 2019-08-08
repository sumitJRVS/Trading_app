package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.modelRepo.dto.Quote;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class QuoteDao_v0 implements CrudRepo<Quote, ID> {
    private final static String TABLE_NAME = "quote";
    private final static String ID_NAME = "ticker";
    Logger logger = LoggerFactory.getLogger(QuoteDao_v0.class);
    private JdbcTemplate jdbcTmp;
    private SimpleJdbcInsert simpleJDBCins;

    //Constructor
    @Autowired
    public QuoteDao_v0(DataSource dataSrc) {
        logger.info("Short story long: JdbcTemplate  jdbctempl= new JdbcTemplate();\n" +
                "jdbctempl = new JdbcTemplate(dataSrc);");

        this.jdbcTmp = new JdbcTemplate(dataSrc);
        this.simpleJDBCins = new SimpleJdbcInsert(dataSrc).withTableName(TABLE_NAME);
    }


    @Override
    public Quote save(Quote thingsFromQuote) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(thingsFromQuote);
        // Number assignedNo : this  will be used for making other funcn.
        Number assignedNo = this.simpleJDBCins.executeAndReturnKey(sqlParameterSource);
        return thingsFromQuote;
    }

    @Override
    public Quote findById(ID id) {
        return null;
    }

    @Override
    public Quote findById(String str, ID id, boolean truefalse, Class classname) {
        return null;
    }

    @Override
    public boolean existsById(ID id) {
        return false;
    }

    @Override
    public boolean existsById(String str, ID id) {
        return false;
    }

    @Override
    public void deleteById(ID id) {

    }

    @Override
    public void deleteById(String str, ID id) {
    }

}

