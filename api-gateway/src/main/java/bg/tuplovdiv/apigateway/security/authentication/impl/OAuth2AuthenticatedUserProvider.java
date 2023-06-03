package bg.tuplovdiv.apigateway.security.authentication.impl;

import bg.tuplovdiv.apigateway.model.entity.UserEntity;
import bg.tuplovdiv.apigateway.repository.UserRepository;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OAuth2AuthenticatedUserProvider extends DefaultAuthenticatedUserProvider {

    private final UserRepository userRepository;

    protected OAuth2AuthenticatedUserProvider(Jackson2ObjectMapperBuilder mapperBuilder, UserRepository userRepository) {
        super(mapperBuilder, OAuth2AuthenticationException.class);
        this.userRepository = userRepository;
    }

    @Override
    protected AuthenticatedUser getUser() {
        if(getPrincipal() instanceof OAuth2AuthenticationToken token) {
            EatawayUser user = getMapper().convertValue(token.getPrincipal().getAttributes(), EatawayUser.class);
            Optional<UserEntity> optUser = userRepository.findByUserId(user.getUserId());

            if(optUser.isPresent()) {
                Set<String> roles = optUser.get().getUserRoles()
                        .stream()
                        .map(role -> role.getUserRole().name())
                        .collect(Collectors.toSet());

                user.setRoles(roles);
            }

            return user;
        }

        return getDefaultAuthorizationUser();
    }

}
