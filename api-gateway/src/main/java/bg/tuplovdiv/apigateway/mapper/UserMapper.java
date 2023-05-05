package bg.tuplovdiv.apigateway.mapper;

import bg.tuplovdiv.apigateway.model.entity.UserEntity;
import bg.tuplovdiv.apigateway.security.user.impl.EatawayUser;
import org.springframework.stereotype.Component;

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
                .setRoles(entity.getUserRoles());
    }
}
