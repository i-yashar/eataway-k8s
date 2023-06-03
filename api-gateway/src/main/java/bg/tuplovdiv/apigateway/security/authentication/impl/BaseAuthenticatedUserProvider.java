package bg.tuplovdiv.apigateway.security.authentication.impl;

import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

abstract class BaseAuthenticatedUserProvider implements AuthenticatedUserProvider {

    protected final ObjectMapper mapper;

    BaseAuthenticatedUserProvider(Jackson2ObjectMapperBuilder mapperBuilder) {
        this.mapper = mapperBuilder.build();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public AuthenticatedUser provide() {
        Optional<AuthenticatedUser> user = getUser();

        if(user.isEmpty()) {
            return getDefaultUser();
        }

        return user.get();
    }

    protected abstract Optional<AuthenticatedUser> getUser();

    protected Principal getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private AuthenticatedUser getDefaultUser() {
        return new DefaultUser();
    }
}
