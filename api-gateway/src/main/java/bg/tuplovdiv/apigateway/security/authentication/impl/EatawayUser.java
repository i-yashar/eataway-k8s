package bg.tuplovdiv.apigateway.security.authentication.impl;

import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public class EatawayUser implements AuthenticatedUser {

    @JsonProperty("sub")
    private String userId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    private Collection<String> roles;

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

    @Override
    public Collection<String> getRoles() {
        return roles;
    }

    public EatawayUser setRoles(Collection<String> roles) {
        this.roles = roles;
        return this;
    }
}
