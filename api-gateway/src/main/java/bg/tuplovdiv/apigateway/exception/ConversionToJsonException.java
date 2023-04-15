package bg.tuplovdiv.apigateway.exception;

import java.io.Serial;

public class ConversionToJsonException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4073176472466917098L;

    public ConversionToJsonException(String message) {
        super(message);
    }
}
