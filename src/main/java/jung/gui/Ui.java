package jung.gui;

import java.util.Scanner;

/**
 * Simple console-based user interface for text input.
 * Used by the legacy console version of Jung.
 */
public class Ui {

    private final Scanner scanner;

    /**
     * Creates a new UI instance with system input scanner.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Reads a command from the console input.
     *
     * @return Trimmed user input string
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Closes the scanner resource.
     * Should be called when the UI is no longer needed.
     */
    public void close() {
        scanner.close();
    }
}

