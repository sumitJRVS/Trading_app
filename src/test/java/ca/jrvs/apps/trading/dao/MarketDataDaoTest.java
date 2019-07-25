package ca.jrvs.apps.trading.dao;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MarketDataDaoTest {

    @Test
    public void findIexQuoteByTicker() {

        HttpClientConnectionManager newObjCMG = new PoolingHttpClientConnectionManager();
        MarketDataDao objectMktDAO = new MarketDataDao(newObjCMG);
        List<String> a = new ArrayList<>();


        try {
            objectMktDAO.findIexQuoteByTickerList(Arrays.asList("aapl","fb"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}