package ca.jrvs.apps.trading.dao;


import ca.jrvs.apps.trading.modelRepo.domain.SecurityOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder, Integer> {

    private static final String TABLE_NAME = "security_order";
    private static final String ID_NAME = "id";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public SecurityOrderDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_NAME);

    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return null;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return null;
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public String getIdName() {
        return null;
    }

    @Override
    Class getEntityClass() {
        return null;
    }


    public void updateSecurityStatus(SecurityOrder securityOrder) {
        String query = "UPDATE" + " " + TABLE_NAME + " " + "SET status=? WHERE account_id=? AND ticker=?";
        Integer update = jdbcTemplate.update(query, securityOrder.getStatus(), securityOrder.getAccountId(), securityOrder.getTicker());
    }

}
