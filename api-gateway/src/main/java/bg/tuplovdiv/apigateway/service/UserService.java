package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.security.user.impl.EatawayUser;

import java.util.Collection;

public interface UserService {
    void registerUser(EatawayUser user);
    void setUserRoles(EatawayUser user);
    Collection<EatawayUser> getAllUsers();
    void registerUserAsEmployee(String userId);
    void deregisterUserAsEmployee(String userId);
}
