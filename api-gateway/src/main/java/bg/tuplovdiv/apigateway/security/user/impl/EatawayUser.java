package bg.tuplovdiv.apigateway.security.user.impl;

import bg.tuplovdiv.apigateway.security.user.AuthenticatedUser;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EatawayUser implements AuthenticatedUser {

    @JsonProperty("sub")
    private String userId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @Override
    public String getUserId() {
        return userId;
    }

    public EatawayUser setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public EatawayUser setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public EatawayUser setEmail(String email) {
        this.email = email;
        return this;
    }
}
