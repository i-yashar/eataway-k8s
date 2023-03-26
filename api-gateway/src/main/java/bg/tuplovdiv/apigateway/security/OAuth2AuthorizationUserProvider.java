package bg.tuplovdiv.apigateway.security;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class OAuth2AuthorizationUserProvider extends DefaultAuthorizationUserProvider {

    protected OAuth2AuthorizationUserProvider(Jackson2ObjectMapperBuilder mapperBuilder) {
        super(mapperBuilder, OAuth2AuthenticationException.class);
    }

    @Override
    protected AuthorizationUser getUser() {
        if(getPrincipal() instanceof OAuth2AuthenticationToken token) {
            return getMapper().convertValue(token.getPrincipal().getAttributes(), EatawayUser.class);
        }

        return getDefaultAuthorizationUser();
    }
}
