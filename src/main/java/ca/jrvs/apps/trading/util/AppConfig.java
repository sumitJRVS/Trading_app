package ca.jrvs.apps.trading.util;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class AppConfig {
    private Logger logging = LoggerFactory.getLogger(AppConfig.class);

    // Creating ApacheData Source connection.
    private static final String JDBC_URL = "jdbc:posttgresql://localhost:5432/jrvstrading_test";
    private static final String DB_USER ="postgres";
    private static final String DB_PASS ="password";

    public DataSource dataSource (){
        logging.info("Creating ApacheData Source connection");
        BasicDataSource datasource = new BasicDataSource();
        datasource.setUrl(JDBC_URL);
        datasource.setUsername(DB_USER);
        datasource.setPassword(DB_PASS);
        logging.debug("JDBC URL" , datasource.getUrl(), "Username", datasource.getUsername(), "Password", datasource.getPassword() );

        return datasource;

    }


}
