package cashiersystem.service.validator.exception;

public class InvalidFieldException extends RuntimeException {

    private final String field;

    public InvalidFieldException(String field) {
        this.field = field;
    }

    public InvalidFieldException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
