package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domainDto.IexQuote;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MarketDataDao {

    // final static variables defined;
    // URL base
    private static final String TRADETOKEN = System.getenv("tradetoken");
    private static String URL_ROOT = ("https://cloud.iexapis.com/stable/stock/market/batch?symbols=%s&types=quote&token=" + TRADETOKEN);

    private HttpClientConnectionManager httpClientConnectionManager;

    // constructure
    public MarketDataDao(HttpClientConnectionManager htCliConMgr) {
        this.httpClientConnectionManager = htCliConMgr;
    }



    ///this is just a helper function=client connection manager helping to make stable connection and limit 50 connection ON all time
    public HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        return cm;
    }

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

        List<String> abccc = new ArrayList<>();
        abccc.add("aapl");

        String tick = String.join(",", abccc);
        String url = String.format(URL_ROOT, tick);

        CloseableHttpResponse res = responseBack(url);
        String qutStr = EntityUtils.toString(res.getEntity());

        System.out.println(qutStr);

        // move string response of quote(s) to JSONobject while checking the conditions
        JSONObject iexQuoteJson = new JSONObject(qutStr);
        if (iexQuoteJson.length() == 0) {
            System.out.println("not found");
        }
        if (iexQuoteJson.length() != tickerToQuote.size()) {
            System.out.println("invalid ticker Symbol");
        }

        //start Unmarshall from Edward video
        List<IexQuote> iexQuoteList = new ArrayList<>();
        iexQuoteJson.keys().forEachRemaining((ticker -> {
            String qtstr = ((JSONObject) iexQuoteJson.get(tick)).get("quote").toString();
            IexQuote iexQuote = toObjectFromJson(qtstr, IexQuote.class);
            iexQuoteList.add(iexQuote);
        }));

    }

    //copied from twitter
    public IexQuote toObjectFromJson(String json, Class clazz) {
        ObjectMapper objmapToOBJ = new ObjectMapper(); // created a new object name= objmap
        try {
            return (IexQuote) objmapToOBJ.readValue(json, clazz);  // T is imp otherwise class is object and string all mixed up

        } catch (JsonProcessingException err404) {
            err404.printStackTrace();
            throw new RuntimeException(err404);
        } catch (IOException err404) {
            err404.printStackTrace();
            throw new RuntimeException(err404);
        }
    }


    public static void main(String[] args) throws IOException, URISyntaxException {
        HttpClientConnectionManager newObjCMG = new PoolingHttpClientConnectionManager();
        MarketDataDao objectMktDAO = new MarketDataDao(newObjCMG);
        List<String> a = new ArrayList<>();

        objectMktDAO.findIexQuoteByTicker(a);
    }



}