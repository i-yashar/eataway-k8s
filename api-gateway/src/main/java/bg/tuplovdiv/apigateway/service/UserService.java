package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.security.authentication.impl.EatawayUser;

import java.util.Collection;
import java.util.UUID;

public interface UserService {
    void registerUser(EatawayUser user);
    Collection<EatawayUser> getAllUsers();
    void registerUserAsEmployee(String userId, UUID restaurantId);
    void deregisterUserAsEmployee(String userId);
}
