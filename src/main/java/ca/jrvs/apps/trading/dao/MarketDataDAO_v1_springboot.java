package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.modelRepo.dto.IexQuote;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class MarketDataDAO_v1_springboot {

    private static final String TRADETOKEN = System.getenv("tradetoken");
    private static String URL_ROOT = ("https://cloud.iexapis.com/stable/stock/market/batch?symbols=%s&types=quote&token=" + TRADETOKEN);
    // final static variables defined;    // URL base
    private Logger logger = LoggerFactory.getLogger(MarketDataDAO_v1_springboot.class);
    private HttpClientConnectionManager httpClientConnectionManager;

    // constructor
    @Autowired
    public MarketDataDAO_v1_springboot(HttpClientConnectionManager htCliConMgr) {
        this.httpClientConnectionManager = htCliConMgr;
    }

    /**
     * Below main method is just for testing MarketDataDao.java
     */
    /*
    public static void main(String[] args) throws IOException {
        HttpClientConnectionManager newObjCMG = new PoolingHttpClientConnectionManager();
        MarketDataDAO_v1_springboot objectMktDAO = new MarketDataDAO_v1_springboot(newObjCMG);

        List<String> a = new ArrayList<>();
        a.add("amzn");
        //objectMktDAO.findIexQuoteByTickerList(a);

        List<String > abc = new ArrayList<String>();
        abc.add("msft");
        abc.add("amzn");

        objectMktDAO.findIexQuoteByOneTicker("ADBE");
        //objectMktDAO.findIexQuoteByTickerList(abc);
    }
    */

    //Http client borrowing 1 connection from connection manager
    public CloseableHttpClient httpClient() {
        CloseableHttpClient httpcli = HttpClients.custom().setConnectionManager(httpClientConnectionManager).build();
        return httpcli;
    }

    public CloseableHttpResponse httpResponseBack(String url) throws IOException {
        // to make httpResponse, First you have to have 1 object of client http thing then response comes in place, so making 1 object for that
        CloseableHttpClient closeableHttpClient = httpClient();
        CloseableHttpResponse res = closeableHttpClient.execute(new HttpGet(url));
        return res;

    }

    public <T> T toObjectFromJson(String json, Class clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return (T) mapper.readValue(json, clazz);
    }

    public List<IexQuote> findIexQuoteByTickerList(List<String> tickerToQuote) throws IOException {

        /* List<String> iexQuotesInList = new ArrayList<>();
         iexQuotesInList.add(tickerToQuote);
         This is transferred to main application.java so user arguments taken + recorded at that point
        */

        String tickeraddedTolistofQuotes = String.join(",", tickerToQuote);
        String url = String.format(URL_ROOT, tickeraddedTolistofQuotes);

        CloseableHttpResponse res = httpResponseBack(url);
        String iexQtStr = EntityUtils.toString(res.getEntity());

        // move string response of quote(s) to JSONobject while checking the conditions
        JSONObject iexQtJson = new JSONObject(iexQtStr);


        logger.info("tickaddedTolistofQuotes%%", tickeraddedTolistofQuotes);
        logger.debug("url%%" + url);
        logger.debug("res%%" + res);
        logger.debug("iexQtStr" + iexQtStr);


        if (iexQtJson.length() == 0) {
            System.out.println("JSON outcome not found, something wrong in input");
        }

        //start Unmarshall Json object from output var=iexQtJson.
        // move string response of quote(s) to JSONobject while checking the conditions.

        // Created IexQuote type list for storing-adding results of each user entered ticker args's response.
        List<IexQuote> iexQuoteList = new ArrayList<>();
        iexQtJson.keys().forEachRemaining(ticker -> {
            String qtstr = ((JSONObject) iexQtJson.get(ticker)).get("quote").toString();
            IexQuote iexQuote = null;
            try {
                iexQuote = toObjectFromJson(qtstr, IexQuote.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            iexQuoteList.add(iexQuote);
        });
        System.out.println("MktDAO=" + iexQuoteList);
        return iexQuoteList;
    }

    /**
     * @param ticker
     * @return Only 1 ticker's IexQuote, so returning 0th element of the list
     * @throws IOException
     */
    public IexQuote findIexQuoteByOneTicker(String ticker) throws IOException {

        List<IexQuote> iexQuote = findIexQuoteByTickerList(Arrays.asList(ticker));
        if (iexQuote.size() == 0) {
            System.out.println("JSON output not found, something wrong in input");
        }
        //no need to iterate the list as it has only 1 element and we know location as 0th.
        return iexQuote.get(0);
    }


}