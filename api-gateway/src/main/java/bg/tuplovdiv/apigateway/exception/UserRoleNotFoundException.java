package bg.tuplovdiv.apigateway.exception;

import java.io.Serial;

public class UserRoleNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6578011822146836443L;

    public UserRoleNotFoundException(String message) {
        super(message);
    }
}
