package ca.jrvs.apps.trading.services;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.modelRepo.dto.IexQuote;
import ca.jrvs.apps.trading.modelRepo.dto.Quote;
import com.sun.org.apache.xpath.internal.operations.Quo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.modelRepo.dto.IexQuote;
import ca.jrvs.apps.trading.modelRepo.dto.Quote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ca.jrvs.apps.trading.AppConfigTest.class,
        loader = AnnotationConfigContextLoader.class)
public class QuoteServiceTest {


    @Autowired
    private QuoteService quoteService;
    private MarketDataDao marketservice;

    IexQuote testIexQt = new IexQuote();
    Quote testQuote = new Quote();


    @Test
    public void buildIEXquoteTOquote() {


        testIexQt.setIexBidPrice(50.0);
        testIexQt.setIexBidSize(10L);
        testIexQt.setIexAskPrice(40.0);
        testIexQt.setIexAskSize(20L);
        testIexQt.setSymbol("tsla");

        testQuote.setBidPrice(50.0);
        testQuote.setBidSize(10L);
        testQuote.setAskPrice(40.0);
        testQuote.setAskSize(20L);
        testQuote.setID("tsla");


        Quote expectedQuote = quoteService.buildIEXquoteTOquote(testIexQt);
        assertEquals(testQuote.getAskPrice(), expectedQuote.getAskPrice());
        assertEquals(testQuote.getAskSize(), expectedQuote.getAskSize());
        assertEquals(testIexQt.getIexBidPrice(), expectedQuote.getBidPrice());
        assertEquals(testIexQt.getIexBidSize(), expectedQuote.getBidSize());
        assertEquals(testIexQt.getSymbol(), expectedQuote.getID());

    }


}


