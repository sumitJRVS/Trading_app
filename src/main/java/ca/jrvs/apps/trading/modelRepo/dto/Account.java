
package ca.jrvs.apps.trading.modelRepo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "amount",
        "id",
        "traderId"
})
public class Account {

    @JsonProperty("amount")
    private Integer amount;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("traderId")
    private Integer traderId;

    @JsonProperty("amount")
    public Integer getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("traderId")
    public Integer getTraderId() {
        return traderId;
    }

    @JsonProperty("traderId")
    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("amount", amount).append("id", id).append("traderId", traderId).toString();
    }

}