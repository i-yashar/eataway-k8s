package bg.tuplovdiv.apigateway.exception;

import java.io.Serial;

public class ResponseBodyParsingException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -8268975093308949893L;

    public ResponseBodyParsingException(String message) {
        super(message);
    }
}
