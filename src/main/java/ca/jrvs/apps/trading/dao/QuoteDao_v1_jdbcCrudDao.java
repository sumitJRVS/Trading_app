package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.modelRepo.dto.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class QuoteDao_v1_jdbcCrudDao extends JdbcCrudDao<Quote, String> {
    private static final Logger logger = LoggerFactory.getLogger(QuoteDao_v1_jdbcCrudDao.class);
    private final static String TABLE_NAME = "quote";
    private final static String ID_NAME = "ticker";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    //Constructor
    @Autowired
    public QuoteDao_v1_jdbcCrudDao(DataSource dataSrc) {
        this.jdbcTemplate = new JdbcTemplate(dataSrc);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSrc).withTableName(TABLE_NAME);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Class getEntityClass() {
        return Quote.class;
    }

    @Override
    public String getIdName() {
        return ID_NAME;
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }


    public Quote saveQuote(Quote entity) {

        try {
            SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
            getSimpleJdbcInsert().execute(parameterSource);
            return entity;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Shit something wrong! There is illegal argument");
        }
    }

    /**
     * Steps: search/get/collect all db's quote's ticker names.
     * This function will be used for - QuoteDaoServices
     */
    public List<Quote> findAllQuotesList() {
        // you DONT need ';' at the end of this statement NOTICE that because of jdbctemplate;
        String queryStBuild = ("SELECT * FROM" + " " + TABLE_NAME);
        List<Quote> quote = this.jdbcTemplate.query(queryStBuild, new BeanPropertyRowMapper(Quote.class));
        return quote;
    }

    /**
     * This function will be used for - QuoteDaoServices
     */
    public List<String> findTickerList(List<Quote> quote) {
        List<String> listing = quote.stream().map(Quote::getTicker).collect(Collectors.toList());
        return listing;
    }
}

