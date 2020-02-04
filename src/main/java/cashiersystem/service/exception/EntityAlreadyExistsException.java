package cashiersystem.service.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    private final int statusCode;

    public EntityAlreadyExistsException(int statusCode) {
        this.statusCode = statusCode;
    }

    public EntityAlreadyExistsException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
