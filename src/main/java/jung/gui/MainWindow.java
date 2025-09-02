package jung.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jung.exceptions.JungException;
import jung.parser.Parser;

/**
 * Controller for the main GUI window.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Jung jung;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private Image jungImage = new Image(this.getClass().getResourceAsStream("/images/jung.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the main chatbot instance.
     * @param j The Jung chatbot instance
     */
    public void setJung(Jung j) throws IOException {
        this.jung = j;
        // show welcome or loading error messages from initialization
        String initMessage = jung.initialize();
        dialogContainer.getChildren().add(
            DialogBox.getJungDialog(jung.getWelcomeMessage(), jungImage)
        );

    }

    /**
     * Handles input from the user, displays user and chatbot dialogs.
     * Closes application if the exit command is detected.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        try {
            String response = jung.getResponse(input);
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getJungDialog(response, jungImage)
            );
            userInput.clear();
            // exit immediately when user inputs "bye"
            if (Parser.parse(input).isExit()) {
                Stage stage = (Stage) userInput.getScene().getWindow();
                stage.close();
            }
        } catch (JungException e) {
            dialogContainer.getChildren().add(
                    DialogBox.getJungDialog("Oops! " + e.getMessage(), jungImage)
            );
            userInput.clear();
        } catch (Exception e) {
            dialogContainer.getChildren().add(
                    DialogBox.getJungDialog("An unexpected error occurred.", jungImage)
            );
            userInput.clear();
        }
    }
}

