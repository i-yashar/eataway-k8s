package bg.tuplovdiv.apigateway.exception;

import java.io.Serial;

public class LocationHeaderNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -1069900529747639024L;

    public LocationHeaderNotFoundException(String message) {
        super(message);
    }
}
