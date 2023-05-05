package bg.tuplovdiv.apigateway.security.user.impl;

import bg.tuplovdiv.apigateway.model.entity.UserRole;
import bg.tuplovdiv.apigateway.security.user.AuthenticatedUser;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public class EatawayUser implements AuthenticatedUser {

    @JsonProperty("sub")
    private String userId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    private Collection<UserRole> roles;

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

    public Collection<UserRole> getRoles() {
        return roles;
    }

    public EatawayUser setRoles(Collection<UserRole> roles) {
        this.roles = roles;
        return this;
    }
}
