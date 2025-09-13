package jung.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * JavaFX Application class that launches the Jung GUI.
 * Sets up the main window and injects the Jung instance.
 */
public class JungGui extends Application {

    private Jung jung = new Jung();

    /**
     * Starts the JavaFX application by setting up the main window.
     *
     * @param stage The primary stage for the application
     */
    @Override
    public void start(Stage stage) {
        try {
            setupMainWindow(stage);
        } catch (IOException e) {
            handleStartupError(e);
        }
    }

    /**
     * Sets up the main application window with FXML layout.
     */
    private void setupMainWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JungGui.class.getResource("/view/MainWindow.fxml"));
        AnchorPane rootPane = fxmlLoader.load();

        Scene scene = new Scene(rootPane);
        stage.setScene(scene);
        stage.setTitle("Jung Task Manager");

        MainWindow controller = fxmlLoader.getController();
        controller.setJung(jung);

        stage.show();
    }

    /**
     * Handles startup errors by printing to console.
     * In a production app, this might show an error dialog.
     */
    private void handleStartupError(IOException e) {
        System.err.println("Failed to start Jung GUI: " + e.getMessage());
        e.printStackTrace();
    }
}
