package bg.tuplovdiv.apigateway.security.authentication.impl;

import bg.tuplovdiv.apigateway.model.entity.UserEntity;
import bg.tuplovdiv.apigateway.repository.UserRepository;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OAuth2AuthenticatedUserProvider extends BaseAuthenticatedUserProvider {

    private final UserRepository userRepository;

    protected OAuth2AuthenticatedUserProvider(Jackson2ObjectMapperBuilder mapperBuilder, UserRepository userRepository) {
        super(mapperBuilder);
        this.userRepository = userRepository;
    }

    @Override
    protected Optional<AuthenticatedUser> getUser() {
        if(getPrincipal() instanceof OAuth2AuthenticationToken token) {
            EatawayUser user = extractUserFromToken(token);

            setUserRoles(user);

            return Optional.of(user);
        }

        return Optional.empty();
    }

    private EatawayUser extractUserFromToken(OAuth2AuthenticationToken token) {
        Map<String, Object> attributes = token.getPrincipal().getAttributes();
        return mapper.convertValue(attributes, EatawayUser.class);
    }

    private void setUserRoles(EatawayUser user) {
        Optional<UserEntity> optUser = userRepository.findByUserId(user.getUserId());

        if(optUser.isPresent()) {
            Set<String> roles = optUser.get().getUserRoles()
                    .stream()
                    .map(role -> role.getUserRole().name())
                    .collect(Collectors.toSet());

            user.setRoles(roles);
        }
    }
}
