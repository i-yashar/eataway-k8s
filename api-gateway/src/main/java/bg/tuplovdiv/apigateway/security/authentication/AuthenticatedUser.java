package bg.tuplovdiv.apigateway.security.authentication;

import java.util.Collection;

public interface AuthenticatedUser {
    String getUserId();
    String getName();
    String getEmail();
    Collection<String> getRoles();
}
