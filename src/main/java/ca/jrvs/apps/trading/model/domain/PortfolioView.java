package ca.jrvs.apps.trading.model.domain;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "securityRows"
})
public class PortfolioView {

    @JsonProperty("securityRows")
    private List<SecurityRow> securityRows = null;

    @JsonProperty("securityRows")
    public List<SecurityRow> getSecurityRows() {
        return securityRows;
    }

    @JsonProperty("securityRows")
    public void setSecurityRows(List<SecurityRow> securityRows) {
        this.securityRows = securityRows;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("securityRows", securityRows).toString();
    }

}
