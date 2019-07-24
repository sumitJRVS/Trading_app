package ca.jrvs.apps.trading.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "askPrice",
        "askSize",
        "bidPrice",
        "bidSize",
        "id",
        "lastPrice",
        "ticker"
})
public class Quote implements Entity<String> {
    @JsonProperty("ticker")
    private String ticker;
    @JsonProperty("lastPrice")
    private Double lastPrice;
    @JsonProperty("bidPrice")
    private Double bidPrice;
    @JsonProperty("bidSize")
    private Long bidSize;
    @JsonProperty("askPrice")
    private Double askPrice;
    @JsonProperty("askSize")
    private Long askSize;
    @JsonProperty("id")
    private String id;

    public Quote() {
        super();
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Long getBidSize() {
        return bidSize;
    }

    public void setBidSize(Long bidSize) {
        this.bidSize = bidSize;
    }

    public Double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }

    public Long getAskSize() {
        return askSize;
    }

    public void setAskSize(Long askSize) {
        this.askSize = askSize;
    }

    public String getId() {
        return ticker;
    }

    public void setId(String id) {
        this.ticker = id;
    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public void setID(String s) {

    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public String toString() {
        return "Quote{" +
                "ticker='" + ticker + '\'' +
                ", lastPrice=" + lastPrice +
                ", bidPrice=" + bidPrice +
                ", bidSize=" + bidSize +
                ", askPrice=" + askPrice +
                ", askSize=" + askSize +
                ", id='" + id + '\'' +
                '}';
    }


}


