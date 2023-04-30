package bg.tuplovdiv.restaurants.exception;

import java.io.Serial;

public class MenuNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -2996019722537199431L;

    public MenuNotFoundException(String message) {
        super(message);
    }
}
