package bg.tuplovdiv.orderservice.exception;

import java.io.Serial;

public class BasketEmptyException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4289480227271826683L;

    public BasketEmptyException(String message) {
        super(message);
    }
}
