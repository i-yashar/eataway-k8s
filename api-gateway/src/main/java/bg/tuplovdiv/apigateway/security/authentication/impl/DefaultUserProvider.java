package bg.tuplovdiv.apigateway.security.authentication.impl;

import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import org.springframework.security.core.Authentication;

class DefaultUserProvider implements AuthenticatedUserProvider {

    @Override
    public boolean supportsAuthentication(Authentication authentication) {
        return false;
    }

    @Override
    public AuthenticatedUser provide() {
        return new DefaultUser();
    }
}
