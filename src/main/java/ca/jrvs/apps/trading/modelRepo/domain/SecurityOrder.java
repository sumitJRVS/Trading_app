package ca.jrvs.apps.trading.modelRepo.domain;

import ca.jrvs.apps.trading.modelRepo.dto.Entity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountId",
        "id",
        "notes",
        "price",
        "size",
        "status",
        "ticker"
})
public class SecurityOrder implements Entity<Integer> {

    @JsonProperty("accountId")
    private Integer accountId;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("size")
    private Double size;
    @JsonProperty("status")
    private orderStatus status;
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

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @JsonProperty("price")
    public Double getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonProperty("size")
    public Double getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(Double size) {
        this.size = size;
    }

    @JsonProperty("status")
    public orderStatus getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(orderStatus status) {
        this.status = status;
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
        return new ToStringBuilder(this)
                .append("accountId", accountId)
                .append("id", id).append("notes", notes).append("price", price).append("size", size).append("status", status).append("ticker", ticker).toString();
    }

    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public void setID(Integer intID) {
        this.id = intID;
    }


    public enum orderStatus {
        FILLED, CANCELLED, PENDING
    }

}