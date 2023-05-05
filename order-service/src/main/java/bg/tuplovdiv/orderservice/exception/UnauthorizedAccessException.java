package bg.tuplovdiv.orderservice.exception;

import java.io.Serial;

public class UnauthorizedAccessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5708133613862779844L;

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
