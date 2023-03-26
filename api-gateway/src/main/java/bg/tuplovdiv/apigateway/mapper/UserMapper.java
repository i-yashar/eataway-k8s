package bg.tuplovdiv.apigateway.mapper;

import bg.tuplovdiv.apigateway.model.entity.UserEntity;
import bg.tuplovdiv.apigateway.security.EatawayUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(EatawayUser user) {
        return new UserEntity()
                .setUserId(user.getUserId())
                .setName(user.getName())
                .setEmail(user.getEmail());
    }
}
