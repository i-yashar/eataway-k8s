package bg.tuplovdiv.apigateway.security.authentication;

public interface AuthenticatedUserProvider {
    AuthenticatedUser provide();
}
