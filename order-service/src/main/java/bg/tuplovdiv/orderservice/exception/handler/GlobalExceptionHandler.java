package bg.tuplovdiv.orderservice.exception.handler;

import bg.tuplovdiv.orderservice.dto.error.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static bg.tuplovdiv.orderservice.dto.error.ErrorCode.INVALID_CREATE_ORDER_REQUEST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleInvalidMethodArguments(MethodArgumentNotValidException e) {
        String errorMessage = buildErrorMessage(e);
        ErrorDTO errorDTO = new ErrorDTO()
                .setCode(INVALID_CREATE_ORDER_REQUEST)
                .setMessage(errorMessage);

        return new ResponseEntity<>(errorDTO, BAD_REQUEST);
    }

    private String buildErrorMessage(MethodArgumentNotValidException exception) {
        StringBuilder builder = new StringBuilder();

        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            builder.append(error.getDefaultMessage()).append(";");
        }

        return builder.toString();
    }
}
