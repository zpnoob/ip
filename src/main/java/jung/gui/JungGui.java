package jung.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Enhanced JavaFX Application class with proper error handling and resource management.
 * Simplified version that avoids CSS conflicts while maintaining good UX.
 */
public class JungGui extends Application {

    private Jung jung = new Jung();

    /**
     * Starts the JavaFX application with comprehensive error handling.
     */
    @Override
    public void start(Stage stage) {
        try {
            Scene scene = setupMainWindow();
            configureWindow(stage, scene);
            stage.show();
        } catch (Exception e) {
            handleStartupError(e);
        }
    }

    /**
     * Sets up the main application window and returns the scene.
     */
    private Scene setupMainWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JungGui.class.getResource("/view/MainWindow.fxml"));
        AnchorPane rootPane = fxmlLoader.load();

        Scene scene = new Scene(rootPane);

        // Apply minimal, safe CSS
        applySafeTheme(scene);

        // Inject Jung instance into controller
        MainWindow controller = fxmlLoader.getController();
        controller.setJung(jung);

        return scene;
    }

    /**
     * Configures window properties with proper error handling.
     */
    private void configureWindow(Stage stage, Scene scene) {
        // Basic window setup
        stage.setTitle("Jung Task Manager - Your Unwilling Assistant");
        stage.setScene(scene);

        // Try to set window icon
        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/jung.jpg"));
            if (!icon.isError()) {
                stage.getIcons().add(icon);
            }
        } catch (Exception e) {
            System.out.println("Could not load window icon: " + e.getMessage());
        }

        // Window sizing and behavior
        stage.setMinWidth(420.0);
        stage.setMinHeight(520.0);
        stage.setWidth(520.0);
        stage.setHeight(720.0);
        stage.centerOnScreen();
        stage.setResizable(true);

        // Proper close behavior
        stage.setOnCloseRequest(event -> {
            System.exit(0);
        });
    }

    /**
     * Applies safe CSS that won't cause conflicts with JavaFX internals.
     */
    private void applySafeTheme(Scene scene) {
        try {
            String safeCss = """
                .root {
                    -fx-base: #ffffff;
                    -fx-background-color: #f8f9fa;
                    -fx-font-family: 'Segoe UI', system-ui, sans-serif;
                    -fx-font-size: 13px;
                }
                """;

            scene.getRoot().setStyle(safeCss);

        } catch (Exception e) {
            System.out.println("Warning: Could not apply theme: " + e.getMessage());
            // Continue without custom styling - JavaFX will use defaults
        }
    }

    /**
     * Handles startup errors with user-friendly messages.
     */
    private void handleStartupError(Exception e) {
        System.err.println("Alamak! Failed to start Jung GUI:");
        System.err.println("Error: " + e.getMessage());

        if (e instanceof IOException) {
            System.err.println("This looks like a file loading problem.");
            System.err.println("Please check that all FXML files are in the correct location:");
            System.err.println("  - /view/MainWindow.fxml");
            System.err.println("  - /view/DialogBox.fxml");
        }

        System.err.println("\nFull error details:");
        e.printStackTrace();

        System.exit(1);
    }

    /**
     * Application entry point.
     */
    public static void main(String[] args) {
        launch(args);
    }
}