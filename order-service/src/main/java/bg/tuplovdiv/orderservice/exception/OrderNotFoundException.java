package bg.tuplovdiv.orderservice.exception;

import java.io.Serial;

public class OrderNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -1774485852278435611L;

    public OrderNotFoundException(String message) {
        super(message);
    }
}
