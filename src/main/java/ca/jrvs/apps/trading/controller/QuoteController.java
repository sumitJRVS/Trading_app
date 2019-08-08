package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.dao.MarketDataDao_v1_springboot;
import ca.jrvs.apps.trading.dao.QuoteDao_v1_jdbcCrudDao;
import ca.jrvs.apps.trading.modelRepo.dto.IexQuote;
import ca.jrvs.apps.trading.modelRepo.dto.Quote;
import ca.jrvs.apps.trading.services.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/quote")
public class QuoteController {

    private QuoteService quoteService;
    private QuoteDao_v1_jdbcCrudDao quoteDao;
    private MarketDataDao_v1_springboot marketDataDao;

    @Autowired
    public QuoteController(QuoteService quoteService,
                           QuoteDao_v1_jdbcCrudDao quoteDaov1jdbcCrudDao,
                           MarketDataDao_v1_springboot marketDataDaoV1Springboot) {
        this.quoteService = quoteService;
        this.quoteDao = quoteDaov1jdbcCrudDao;
        this.marketDataDao = marketDataDaoV1Springboot;
    }


    @PutMapping(path = "/iexMarketData")
    @ResponseStatus(HttpStatus.OK)
    public void updateMarketData() {
        try {
            quoteService.updateMarketDataOFjdbc();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

/*
    @PutMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public void putQuote(@RequestBody Quote quote) {
        try {
            quoteDao.update(Collections.singletonList(quote));
        } catch (Exception e) {
         eption(e);
        }
    }
*/

    @PostMapping(path = "/tickerId/{tickerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuote(@PathVariable String tickerId) {
        try {
            quoteService.initQuote(tickerId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping(path = "/dailyList")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Quote> getDailyList() {
        try {
            return quoteDao.findAllQuotesList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping(path = "/iex/tickerId/{ticker}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IexQuote getSingleQuote(@PathVariable String ticker) {
        try {
            return marketDataDao.findIexQuoteByOneTicker(ticker);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping(path = "/iex/tickerBatch/{ticker}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<IexQuote> getBatchQuote(@PathVariable String ticker) {
        try {
            List<String> tickers = new ArrayList<String>(Arrays.asList(ticker.split(",")));

            return marketDataDao.findIexQuoteByTickerList(tickers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}




