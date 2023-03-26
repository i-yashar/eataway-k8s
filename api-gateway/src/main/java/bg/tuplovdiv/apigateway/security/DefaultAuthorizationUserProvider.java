package bg.tuplovdiv.apigateway.security;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.InvocationTargetException;
import java.security.Principal;

public abstract class DefaultAuthorizationUserProvider implements AuthorizationUserProvider{
    private final ObjectMapper mapper;
    private final Class<? extends AuthenticationException> exception;

    protected DefaultAuthorizationUserProvider(Jackson2ObjectMapperBuilder mapperBuilder, Class<? extends AuthenticationException> exception) {
        this.mapper = mapperBuilder.build();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.exception = exception;
    }

    public AuthorizationUser provide() {
        checkIfPrincipalPresent(exception);
        return getUser();
    }

    protected void checkIfPrincipalPresent(Class<? extends AuthenticationException> exception) {
        if (getPrincipal() == null) {
            try {
                throw exception.getDeclaredConstructor(String.class).newInstance("No authenticated user found");
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract AuthorizationUser getUser();

    protected Principal getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    protected ObjectMapper getMapper() {
        return this.mapper;
    }

    protected AuthorizationUser getDefaultAuthorizationUser() {
        return new AuthorizationUser() {
            @Override
            public String getUserId() {
                return null;
            }

            @Override
            public String getName() {
                return getPrincipal().getName();
            }

            @Override
            public String getEmail() {
                return null;
            }
        };
    }
}
