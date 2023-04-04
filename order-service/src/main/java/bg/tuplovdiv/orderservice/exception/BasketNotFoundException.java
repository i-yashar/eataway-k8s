package bg.tuplovdiv.orderservice.exception;

import java.io.Serial;

public class BasketNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -2884624031411785034L;

    public BasketNotFoundException(String message) {
        super(message);
    }
}
