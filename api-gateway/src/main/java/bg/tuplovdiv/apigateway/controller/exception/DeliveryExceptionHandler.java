package bg.tuplovdiv.apigateway.controller.exception;

import bg.tuplovdiv.apigateway.exception.DeliveryDriverNotFreeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class DeliveryExceptionHandler {

    @ExceptionHandler(DeliveryDriverNotFreeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleDriverNotFree(DeliveryDriverNotFreeException e) {
        return new ModelAndView("delivery-driver-not-free");
    }
}
