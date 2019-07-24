package ca.jrvs.apps.trading.services;


import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.modelRepo.dto.IexQuote;
import ca.jrvs.apps.trading.modelRepo.dto.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

@Service
public class QuoteService{

    private QuoteDao qtDAO;
    private MarketDataDao mktDAO;
    Logger logger = LoggerFactory.getLogger(QuoteService.class);

    //Constructor
    @Autowired
    private QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDaotDAO) {
        this.qtDAO = quoteDao;
        this.mktDAO = marketDataDaotDAO;
    }

    /**
     * Helper method. Map a IexQuote to a Quote entity.
     * Note: `iexQuote.getLatestPrice() == null` if the stock market is closed.
     * Make sure set a default value for number field(s).
     */
    public Quote buildIEXquoteTOquote(IexQuote iexQuote){
        Quote quote = new Quote();

        if (iexQuote.getLatestPrice() == null) {
            System.out.println("Good night! you better sleep too..after 4:30pm!");}
        else {
            logger.info("you have enteered Quote correctly, now connecting IEXquote to myquote into Quote");
            quote.setAskPrice(iexQuote.getIexAskPrice());
            quote.setAskSize(iexQuote.getIexAskSize());
            quote.setBidPrice(iexQuote.getIexBidPrice());
            quote.setBidSize(iexQuote.getIexBidSize());
            quote.setLastPrice(iexQuote.getLatestPrice());
            quote.setTicker(iexQuote.getSymbol());
            quote.setId(iexQuote.getSymbol());
        }
        return quote;
    }






}
