package jung.exceptions;
/**
 * Custom exception class for Jung application-specific errors.
 * Used to indicate business logic violations or invalid user operations.
 */
public class JungException extends Exception {

    /**
     * Creates a new JungException with the specified error message.
     *
     * @param message Description of what went wrong
     */
    public JungException(String message) {
        super(message);
    }

    /**
     * Creates a new JungException with message and underlying cause.
     *
     * @param message Description of what went wrong
     * @param cause The underlying exception that caused this error
     */
    public JungException(String message, Throwable cause) {
        super(message, cause);
    }
}
