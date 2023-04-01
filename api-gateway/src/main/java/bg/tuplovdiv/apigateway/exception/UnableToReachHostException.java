package bg.tuplovdiv.apigateway.exception;

import java.io.Serial;

public class UnableToReachHostException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 7366781788261533797L;

    public UnableToReachHostException(String message) {
        super(message);
    }
}
