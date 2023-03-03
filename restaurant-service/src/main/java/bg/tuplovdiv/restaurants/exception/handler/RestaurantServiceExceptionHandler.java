package bg.tuplovdiv.restaurants.exception.handler;

import bg.tuplovdiv.restaurants.dto.error.ErrorCode;
import bg.tuplovdiv.restaurants.dto.error.ErrorDTO;
import bg.tuplovdiv.restaurants.exception.RestaurantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static bg.tuplovdiv.restaurants.dto.error.ErrorCode.RESTAURANT_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RestaurantServiceExceptionHandler {

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleRestaurantNotFound(RestaurantNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO().setMessage(e.getMessage()).setCode(RESTAURANT_NOT_FOUND);

        return new ResponseEntity<ErrorDTO>(errorDTO, NOT_FOUND);
    }
}
