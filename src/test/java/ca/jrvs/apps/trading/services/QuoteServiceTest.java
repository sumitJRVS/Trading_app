package ca.jrvs.apps.trading.services;

import ca.jrvs.apps.trading.dao.MarketDataDAO_v1_springboot;
import ca.jrvs.apps.trading.dao.QuoteDao_v1_jdbcCrudDao;
import ca.jrvs.apps.trading.modelRepo.dto.IexQuote;
import ca.jrvs.apps.trading.modelRepo.dto.Quote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ca.jrvs.apps.trading.AppConfigTest.class, loader = AnnotationConfigContextLoader.class)
public class QuoteServiceTest {

    @Autowired
    private QuoteService quoteService;
    private MarketDataDAO_v1_springboot marketDataDao;
    private QuoteDao_v1_jdbcCrudDao quoteDao_v1_jdbcCrudDao;

    @Test
    public void buildQuoteFromIexQuote() {

        IexQuote testIEXQuote = new IexQuote();
        Quote testQuote = new Quote();

        testIEXQuote.setIexAskPrice(10.0);
        testIEXQuote.setIexAskSize(80L);
        testIEXQuote.setIexBidPrice(1000.0);
        testIEXQuote.setIexBidSize(50L);
        testIEXQuote.setSymbol("cpl");
        testIEXQuote.setLatestPrice(20.0);

        testQuote.setAskPrice(10.0);
        testQuote.setAskSize(80L);
        testQuote.setBidPrice(1000.0);
        testQuote.setBidSize(50L);
        testQuote.setTicker("cpl");
        testQuote.setID("cpl");
        testQuote.setLastPrice(20.0);

        Quote expectedQuote = QuoteService.buildQuoteFromIEXquote(testIEXQuote);


        assertEquals(testQuote, expectedQuote);

        System.out.println("**------------------");
        System.out.println(testIEXQuote);
        System.out.println(testQuote);
        System.out.println(expectedQuote);
        System.out.println("**------------------");
    }


}


/*

Additional individual assertEquals can be compared:
        assertEquals(testQuote.getAskPrice(), expectedQuote.getAskPrice());
        assertEquals(testQuote.getAskSize(), expectedQuote.getAskSize());
        assertEquals(testQuote.getBidPrice(), expectedQuote.getBidPrice());
        assertEquals(testQuote.getBidSize(), expectedQuote.getBidSize());
        assertEquals(testQuote.getId(), expectedQuote.getId());
        assertEquals(testQuote.getTicker(), expectedQuote.getTicker());

 */