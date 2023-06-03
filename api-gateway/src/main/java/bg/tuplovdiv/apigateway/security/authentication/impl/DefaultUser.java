package bg.tuplovdiv.apigateway.security.authentication.impl;

import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;

import java.util.Collection;
import java.util.Collections;

public class DefaultUser implements AuthenticatedUser {
    @Override
    public String getUserId() {
        return "default_user";
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public Collection<String> getRoles() {
        return Collections.emptyList();
    }
}
