package ca.jrvs.apps.trading.modelRepo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "country",
        "dob",
        "email",
        "firstName",
        "id",
        "lastName"
})
public class Trader implements Entity<Integer> {

    @JsonProperty("country")
    private String country;


    //this is very important concept for type casting date to string format using JsonFormat ,serialize deserialize class
    @JsonProperty("dob")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dob;

    @JsonProperty("email")
    private String email;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("dob")
    public LocalDate getDob() {
        return dob;
    }

    @JsonProperty("dob")
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("firstName", firstName)
                .append("lastName", lastName).append("dob", dob.toString()).append("country", country)
                .append("email", email).toString();
    }


    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public void setID(Integer idToset) {
        this.id = idToset;
    }


}