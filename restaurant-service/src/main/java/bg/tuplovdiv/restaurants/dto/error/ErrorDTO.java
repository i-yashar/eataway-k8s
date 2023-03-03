package bg.tuplovdiv.restaurants.dto.error;

public class ErrorDTO {
    private String message;
    private ErrorCode code;

    public String getMessage() {
        return message;
    }

    public ErrorDTO setMessage(String message) {
        this.message = message;
        return this;
    }

    public ErrorCode getCode() {
        return code;
    }

    public ErrorDTO setCode(ErrorCode code) {
        this.code = code;
        return this;
    }
}
