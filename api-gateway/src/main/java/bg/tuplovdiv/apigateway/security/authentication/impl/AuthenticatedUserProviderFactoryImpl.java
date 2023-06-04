package bg.tuplovdiv.apigateway.security.authentication.impl;

import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProviderFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class AuthenticatedUserProviderFactoryImpl implements AuthenticatedUserProviderFactory {

    private final List<AuthenticatedUserProvider> providers;

    AuthenticatedUserProviderFactoryImpl(List<AuthenticatedUserProvider> providers) {
        this.providers = providers;
    }

    @Override
    public AuthenticatedUserProvider getProvider() {
        return getProvider(SecurityContextHolder.getContext().getAuthentication());
    }

    @Override
    public AuthenticatedUserProvider getProvider(Authentication authentication) {
        return providers
                .stream()
                .filter(p -> p.supportsAuthentication(authentication))
                .findFirst()
                .orElseGet(DefaultUserProvider::new);
    }
}
