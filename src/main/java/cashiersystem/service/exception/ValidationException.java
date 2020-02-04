package cashiersystem.service.exception;

public class ValidationException extends RuntimeException {

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Exception cause) {
        super(message, cause);
    }

}
