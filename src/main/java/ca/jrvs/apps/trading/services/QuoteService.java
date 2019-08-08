package ca.jrvs.apps.trading.services;

import ca.jrvs.apps.trading.dao.MarketDataDao_v1_springboot;
import ca.jrvs.apps.trading.dao.QuoteDao_v1_jdbcCrudDao;
import ca.jrvs.apps.trading.modelRepo.dto.IexQuote;
import ca.jrvs.apps.trading.modelRepo.dto.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class QuoteService {

    Logger logger = LoggerFactory.getLogger(QuoteService.class);
    private QuoteDao_v1_jdbcCrudDao quoteDao_v1_jdbcCrudDao;
    private MarketDataDao_v1_springboot marketDataDao_v1_springboot;

    //Constructor
    @Autowired
    private QuoteService(QuoteDao_v1_jdbcCrudDao quoteDaoV1jdbcCrudDao, MarketDataDao_v1_springboot marketDataDaotDAO) {
        this.quoteDao_v1_jdbcCrudDao = quoteDaoV1jdbcCrudDao;
        this.marketDataDao_v1_springboot = marketDataDaotDAO;
    }

    /**
     * Helper mapping method that maps IexQuote to a Quote entity.
     * Added condition, if iexQuote.getLatestPrice() == null` means the stock market is closed.
     */
    public static Quote buildQuoteFromIEXquote(IexQuote iexQuote) {
        Quote quote = new Quote();

        if (iexQuote.getLatestPrice() == null) {
            throw new IllegalArgumentException("The stock market is closed, Quote found is Null! Good night! you better sleep too..after 4:30pm ");
        } else {
            quote.setAskPrice(iexQuote.getIexAskPrice());
            quote.setAskSize(iexQuote.getIexAskSize());
            quote.setBidPrice(iexQuote.getIexBidPrice());
            quote.setBidSize(iexQuote.getIexBidSize());
            quote.setLastPrice(iexQuote.getLatestPrice());
            quote.setTicker(iexQuote.getSymbol());
            quote.setID(iexQuote.getSymbol());
        }
        return quote;
    }

    /**
     * Get single iexQuote, convert it to Quote entity using build function, saving quote to db
     */
    public void initQuote(String tickerId) throws IOException {
        IexQuote iexQuote = marketDataDao_v1_springboot.findIexQuoteByOneTicker(tickerId);
        Quote quote = buildQuoteFromIEXquote(iexQuote);
        quoteDao_v1_jdbcCrudDao.save(quote);
    }

    /**
     * Add a list of new tickers to the quote table.
     * Get iexQuote, Convert it to Quote entity, Saving quote to db
     */
    public void initQuotes(List<String> listTickers) throws IOException {
        List<IexQuote> iexQuoteslist = marketDataDao_v1_springboot.findIexQuoteByTickerList(listTickers);
        iexQuoteslist.stream().map(QuoteService::buildQuoteFromIEXquote).map(quoteDao_v1_jdbcCrudDao::save);
    }

    /**
     * Update quote table from db and get IEX source quote and update it.
     * Steps: search/get/collect all db's quote's ticker names
     * For each ticker convert iexquote entity-->quote entity-->db (complete 1 cycle)
     */
    public void updateMarketDataOFjdbc() throws IOException {
        initQuotes(quoteDao_v1_jdbcCrudDao.findAllTickerList(quoteDao_v1_jdbcCrudDao.findAllQuotesList()));
    }

}

