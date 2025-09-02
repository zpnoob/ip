package jung.gui;

import java.util.Scanner;

public class Ui {

    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Reads a command line input from the user.
     * @return trimmed user input string.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }
}

