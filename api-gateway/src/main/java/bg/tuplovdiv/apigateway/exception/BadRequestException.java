package bg.tuplovdiv.apigateway.exception;

import java.io.Serial;

public class BadRequestException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -2624289959694548626L;

    public BadRequestException(String message) {
        super(message);
    }
}
