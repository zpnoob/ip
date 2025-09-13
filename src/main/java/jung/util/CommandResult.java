package jung.util;

/**
 * Represents the result of executing a command, including the response message
 * and any special flags for application flow control.
 */
public class CommandResult {
    private final String message;
    private final boolean shouldExit;
    private final boolean isError;

    public CommandResult(String message, boolean shouldExit, boolean isError) {
        this.message = message;
        this.shouldExit = shouldExit;
        this.isError = isError;
    }

    public CommandResult(String message, boolean shouldExit) {
        this(message, shouldExit, false);
    }

    public CommandResult(String message) {
        this(message, false, false);
    }

    public String getMessage() {
        return message;
    }

    public boolean shouldExit() {
        return shouldExit;
    }

    public boolean isError() {
        return isError;
    }
}
