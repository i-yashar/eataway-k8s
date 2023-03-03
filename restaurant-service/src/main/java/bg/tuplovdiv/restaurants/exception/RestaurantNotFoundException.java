package bg.tuplovdiv.restaurants.exception;

import java.io.Serial;

public class RestaurantNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 3438807078231332443L;

    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
