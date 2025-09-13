package jung;

import javafx.application.Application;
import jung.gui.JungGui;

/**
 * Application entry point that launches the Jung GUI.
 * This is the main class that should be run to start the application.
 */
public class Launcher {

    /**
     * Main method that starts the Jung task manager application.
     * Launches the JavaFX GUI version.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Application.launch(JungGui.class, args);
    }
}


