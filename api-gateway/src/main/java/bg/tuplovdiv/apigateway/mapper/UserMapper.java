package bg.tuplovdiv.apigateway.mapper;

import bg.tuplovdiv.apigateway.model.entity.UserEntity;
import bg.tuplovdiv.apigateway.model.entity.UserRole;
import bg.tuplovdiv.apigateway.security.authentication.impl.EatawayUser;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserEntity toEntity(EatawayUser user) {
        return new UserEntity()
                .setUserId(user.getUserId())
                .setName(user.getName())
                .setEmail(user.getEmail());
    }

    public EatawayUser toDTO(UserEntity entity) {
        return new EatawayUser()
                .setUserId(entity.getUserId())
                .setName(entity.getName())
                .setEmail(entity.getEmail())
                .setRoles(mapToUserRoles(entity.getUserRoles()));
    }

    private Collection<String> mapToUserRoles(Set<UserRole> roles) {
        return roles.stream()
                .map(role -> role.getUserRole().name())
                .collect(Collectors.toList());
    }
}
