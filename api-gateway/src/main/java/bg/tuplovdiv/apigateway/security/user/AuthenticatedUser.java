package bg.tuplovdiv.apigateway.security.user;

public interface AuthenticatedUser {
    String getUserId();
    String getName();
    String getEmail();
}
