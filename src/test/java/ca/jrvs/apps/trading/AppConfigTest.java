package ca.jrvs.apps.trading;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"ca.jrvs.apps.trading.dao", "ca.jrvs.apps.trading.services"})
public class AppConfigTest {
    // Creating ApacheData Source connection.
    private static final String JDBC_URL = "jdbc:posttgresql://localhost:5432/jrvstrading_test";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "password";
    private Logger logging = LoggerFactory.getLogger(AppConfigTest.class);

    @Bean
    public DataSource dataSource() {
        logging.info("Creating ApacheData Source connection");
        BasicDataSource datasource = new BasicDataSource();
        datasource.setUrl(JDBC_URL);
        datasource.setUsername(DB_USER);
        datasource.setPassword(DB_PASS);
        logging.debug("JDBC URL", datasource.getUrl(), "Username", datasource.getUsername(), "Password", datasource.getPassword());

        return datasource;

    }

    ///this is just a helper function=client connection manager helping to make stable connection and limit 50 connection ON all time
    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        return cm;
    }


}
