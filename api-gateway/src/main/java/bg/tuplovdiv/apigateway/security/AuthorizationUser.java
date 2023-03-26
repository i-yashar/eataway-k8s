package bg.tuplovdiv.apigateway.security;

public interface AuthorizationUser {
    String getUserId();
    String getName();
    String getEmail();
}
