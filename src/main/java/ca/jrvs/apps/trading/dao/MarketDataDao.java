package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.modelRepo.dto.IexQuote;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MarketDataDao {

    // final static variables defined;    // URL base
    private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private static final String TRADETOKEN = System.getenv("tradetoken");
    private static String URL_ROOT = ("https://cloud.iexapis.com/stable/stock/market/batch?symbols=%s&types=quote&token=" + TRADETOKEN);

    private HttpClientConnectionManager httpClientConnectionManager;

    // constructure
    public MarketDataDao(HttpClientConnectionManager htCliConMgr) {
        this.httpClientConnectionManager = htCliConMgr;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        HttpClientConnectionManager newObjCMG = new PoolingHttpClientConnectionManager();
        MarketDataDao objectMktDAO = new MarketDataDao(newObjCMG);
        List<String> a = new ArrayList<>();

        objectMktDAO.findIexQuoteByTicker(a);
    }

    ///this is just a helper function=client connection manager helping to make stable connection and limit 50 connection ON all time
    public HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        return cm;
    }

    //HTtp client borrowing 1 connection from connection manager
    public CloseableHttpClient httpClient() {
        CloseableHttpClient httpcli = HttpClients.custom().setConnectionManager(httpClientConnectionManager()).build();
        return httpcli;
    }

    public CloseableHttpResponse responseBack(String url) throws IOException {
        // to make httpResponse, First you have to have 1 object of client http thing then response comes in place, so making 1 object for that
        CloseableHttpClient closeableHttpClient = httpClient();
        CloseableHttpResponse res = closeableHttpClient.execute(new HttpGet(url));
        return res;

    }

    public void findIexQuoteByTicker(List<String> tickerToQuote) throws IOException {

        List<String> iexquotesinList = new ArrayList<>();
        iexquotesinList.add("aapl");

        String tickaddedTOlistofQuotes = String.join(",", iexquotesinList);
        String url = String.format(URL_ROOT, tickaddedTOlistofQuotes);

        CloseableHttpResponse res = responseBack(url);
        String iexQtStr = EntityUtils.toString(res.getEntity());

        // move string response of quote(s) to JSONobject while checking the conditions
        JSONObject iexQtJson = new JSONObject(iexQtStr);


        logger.info("tickaddedTolistofQuotes%%", tickaddedTOlistofQuotes);
        logger.debug("url%%" + url);
        logger.debug("res%%" + res);
        logger.debug("iexQtStr" + iexQtStr);
        logger.debug("iexQtStr%%" + iexQtStr);



        if (iexQtJson.length() == 0) {
            System.out.println("not found");
        }

        //start Unmarshall Json object from output var=iexQtJson
        // move string response of quote(s) to JSONobject while checking the conditions

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
    }

    public <T> T toObjectFromJson(String json, Class clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return (T) mapper.readValue(json, clazz);
    }


}