package bg.tuplovdiv.apigateway.security.authentication;

import org.springframework.security.core.Authentication;

public interface AuthenticatedUserProviderFactory {
    AuthenticatedUserProvider getProvider();
    AuthenticatedUserProvider getProvider(Authentication authentication);
}
