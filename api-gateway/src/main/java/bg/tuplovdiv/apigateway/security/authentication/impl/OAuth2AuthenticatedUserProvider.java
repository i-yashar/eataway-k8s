package bg.tuplovdiv.apigateway.security.authentication.impl;

import bg.tuplovdiv.apigateway.model.entity.UserEntity;
import bg.tuplovdiv.apigateway.repository.UserRepository;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUser;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProvider;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
class OAuth2AuthenticatedUserProvider implements AuthenticatedUserProvider {

    private final UserRepository userRepository;
    private final ObjectMapper mapper;

    OAuth2AuthenticatedUserProvider(Jackson2ObjectMapperBuilder mapperBuilder, UserRepository userRepository) {
        this.mapper = mapperBuilder.build();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsAuthentication(Authentication authentication) {
        return authentication instanceof OAuth2AuthenticationToken;
    }

    @Override
    public AuthenticatedUser provide() {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        EatawayUser user = extractUserFromToken(token);

        setUserRoles(user);

        return user;
    }

    private EatawayUser extractUserFromToken(OAuth2AuthenticationToken token) {
        Map<String, Object> attributes = token.getPrincipal().getAttributes();
        return mapper.convertValue(attributes, EatawayUser.class);
    }

    private void setUserRoles(EatawayUser user) {
        Optional<UserEntity> optUser = userRepository.findByUserId(user.getUserId());

        if (optUser.isPresent()) {
            Set<String> roles = optUser.get().getUserRoles()
                    .stream()
                    .map(role -> role.getUserRole().name())
                    .collect(Collectors.toSet());

            user.setRoles(roles);
        }
    }
}
