package ca.jrvs.apps.trading;


import ca.jrvs.apps.trading.dao.MarketDataDao_v1_springboot;
import ca.jrvs.apps.trading.services.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import javax.sql.DataSource;
import java.util.List;

//below lines were ref to Edward in 1.3.1.4
@SpringBootApplication(exclude = {JdbcTemplateAutoConfiguration.class, DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class Application implements CommandLineRunner {
    @Autowired
    private MarketDataDao_v1_springboot marketDataDao;
    private Logger logger = LoggerFactory.getLogger((Application.class));
    @Autowired
    private DataSource dataSource;

    @Value("aapl,fb")
    private String[] dailyList;

    @Autowired
    private QuoteService quoteService;

    @Value("aapl,fb")
    private List<String> symbols;

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Application.class);
        //Turn off web
        app.run(args);
    }


    //I dont care for below CommandLineRunner void run method implementation, we dont care for now().
    @Override
    public void run(String... args) throws Exception {
    }
}

// http://localhost:8080/swagger-ui.html



