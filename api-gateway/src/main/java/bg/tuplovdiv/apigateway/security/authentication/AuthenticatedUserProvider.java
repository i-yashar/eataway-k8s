package bg.tuplovdiv.apigateway.security.authentication;

import org.springframework.security.core.Authentication;

public interface AuthenticatedUserProvider {
    boolean supportsAuthentication(Authentication authentication);
    AuthenticatedUser provide();
}
