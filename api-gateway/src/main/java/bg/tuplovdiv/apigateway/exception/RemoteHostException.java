package bg.tuplovdiv.apigateway.exception;

import java.io.Serial;

public class RemoteHostException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 7366781788261533797L;

    public RemoteHostException(String message) {
        super(message);
    }
}
