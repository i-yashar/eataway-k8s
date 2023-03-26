package bg.tuplovdiv.apigateway.security.authentication.impl;

import bg.tuplovdiv.apigateway.security.user.impl.EatawayUser;
import bg.tuplovdiv.apigateway.security.user.AuthenticatedUser;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class OAuth2AuthenticatedUserProvider extends DefaultAuthenticatedUserProvider {

    protected OAuth2AuthenticatedUserProvider(Jackson2ObjectMapperBuilder mapperBuilder) {
        super(mapperBuilder, OAuth2AuthenticationException.class);
    }

    @Override
    protected AuthenticatedUser getUser() {
        if(getPrincipal() instanceof OAuth2AuthenticationToken token) {
            return getMapper().convertValue(token.getPrincipal().getAttributes(), EatawayUser.class);
        }

        return getDefaultAuthorizationUser();
    }
}
