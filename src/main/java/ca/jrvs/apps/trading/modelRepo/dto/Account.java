
package ca.jrvs.apps.trading.modelRepo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sun.xml.internal.bind.v2.model.core.ID;
import io.swagger.models.auth.In;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "amount",
        "id",
        "traderId"
})
public class Account implements Entity<Integer> {

    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("traderId")
    private Integer traderId;

    @JsonProperty("amount")
    public Double getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this.amount = amount;
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

    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public void setID(Integer intID) {
        this.id = intID;

    }
}