package bg.tuplovdiv.apigateway.security.authentication;

import bg.tuplovdiv.apigateway.security.user.AuthenticatedUser;

public interface AuthenticatedUserProvider {
    AuthenticatedUser provide();
}
