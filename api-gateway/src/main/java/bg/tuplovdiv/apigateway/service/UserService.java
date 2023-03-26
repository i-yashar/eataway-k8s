package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.security.EatawayUser;

public interface UserService {
    void registerUser(EatawayUser user);
}
