package cashiersystem.service.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
    }

    public InvalidEmailException(String message) {
        super(message);
    }

    public InvalidEmailException(String message, Exception cause) {
        super(message, cause);
    }
}
