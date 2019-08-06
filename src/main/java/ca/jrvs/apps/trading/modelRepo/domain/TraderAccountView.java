package ca.jrvs.apps.trading.modelRepo.domain;

import ca.jrvs.apps.trading.modelRepo.dto.Account;
import ca.jrvs.apps.trading.modelRepo.dto.Trader;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "account",
        "trader"
})
public class TraderAccountView {

    @JsonProperty("account")
    private Account account;
    @JsonProperty("trader")
    private Trader trader;

    @JsonProperty("account")
    public Account getAccount() {
        return account;
    }

    @JsonProperty("account")
    public void setAccount(Account account) {
        this.account = account;
    }

    @JsonProperty("trader")
    public Trader getTrader() {
        return trader;
    }

    @JsonProperty("trader")
    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("account", account).append("trader", trader).toString();
    }

}