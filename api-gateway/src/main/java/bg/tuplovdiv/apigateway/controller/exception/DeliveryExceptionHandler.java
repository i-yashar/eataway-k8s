package bg.tuplovdiv.apigateway.controller.exception;

import bg.tuplovdiv.apigateway.exception.DeliveryDriverNotFreeException;
import bg.tuplovdiv.apigateway.security.authentication.AuthenticatedUserProviderFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class DeliveryExceptionHandler {

    private final AuthenticatedUserProviderFactory authenticatedUserProviderFactory;

    public DeliveryExceptionHandler(AuthenticatedUserProviderFactory authenticatedUserProviderFactory) {
        this.authenticatedUserProviderFactory = authenticatedUserProviderFactory;
    }

    @ExceptionHandler(DeliveryDriverNotFreeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleDriverNotFree(DeliveryDriverNotFreeException e) {
        ModelAndView modelAndView = new ModelAndView("delivery-driver-not-free");
        modelAndView.addObject("user", authenticatedUserProviderFactory.getProvider().provide());
        return modelAndView;
    }
}
