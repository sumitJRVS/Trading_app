package ca.jrvs.apps.trading.modelRepo.domain;

import ca.jrvs.apps.trading.modelRepo.dto.Entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountId",
        "position",
        "ticker"
})
public class Position implements Entity<Integer> {

    @JsonProperty("accountId")
    private Integer accountId;
    @JsonProperty("position")
    private Integer position;
    @JsonProperty("ticker")
    private String ticker;

    @JsonProperty("accountId")
    public Integer getAccountId() {
        return accountId;
    }

    @JsonProperty("accountId")
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @JsonProperty("position")
    public Integer getPosition() {
        return position;
    }

    @JsonProperty("position")
    public void setPosition(Integer position) {
        this.position = position;
    }

    @JsonProperty("ticker")
    public String getTicker() {
        return ticker;
    }

    @JsonProperty("ticker")
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("accountId", accountId).append("position", position).append("ticker", ticker).toString();
    }

    @Override
    public Integer getID() {
        return accountId;
    }

    @Override
    public void setID(Integer intId) {
        this.accountId = intId;

    }
}