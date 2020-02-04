package cashiersystem.dao.exception;

public class NotEnoughItemsException extends RuntimeException {
    public NotEnoughItemsException() {
    }

    public NotEnoughItemsException(String message) {
        super(message);
    }

    public NotEnoughItemsException(String message, Exception cause) {
        super(message, cause);
    }
}
