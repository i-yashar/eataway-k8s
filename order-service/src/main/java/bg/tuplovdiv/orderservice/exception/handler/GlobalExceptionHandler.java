package bg.tuplovdiv.orderservice.exception.handler;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleInvalidMethodArguments(MethodArgumentNotValidException e) {
        return e.getMessage();
    }

    private String buildErrorMessage(MethodArgumentNotValidException exception) {
        StringBuilder builder = new StringBuilder();

        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            builder.append(error.getDefaultMessage()).append(";");
        }

        return builder.toString();
    }
}
