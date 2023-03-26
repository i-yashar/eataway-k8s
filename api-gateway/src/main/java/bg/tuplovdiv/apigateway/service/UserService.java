package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.security.user.impl.EatawayUser;

public interface UserService {
    void registerUser(EatawayUser user);
}
