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
import jung.util.CommandResult;

/**
 * Controller for the main GUI window.
 * Manages user interactions and displays conversation between user and Jung.
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

    /**
     * Initializes the GUI components and sets up auto-scrolling behavior.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Jung chatbot instance and displays the welcome message.
     *
     * @param jungInstance The Jung chatbot to interact with
     * @throws IOException If initialization fails
     */
    public void setJung(Jung jungInstance) throws IOException {
        this.jung = jungInstance;
        showInitializationMessages();
    }

    /**
     * Handles user input when the send button is pressed or Enter is hit.
     * Processes the command and updates the dialog display.
     */
    @FXML
    private void handleUserInput() {
        String userInputText = getUserInputText();
        CommandResult result = processUserCommand(userInputText);
        displayConversation(userInputText, result);
        handlePostCommandActions(result);
    }

    /**
     * Gets the current user input and clears the input field.
     */
    private String getUserInputText() {
        String input = userInput.getText();
        userInput.clear();
        return input;
    }

    /**
     * Processes the user command and returns the result.
     */
    private CommandResult processUserCommand(String input) {
        try {
            return jung.processCommand(input);
        } catch (JungException | IOException e) {
            return new CommandResult("Oops! " + e.getMessage(), false, true);
        } catch (Exception e) {
            return new CommandResult("An unexpected error occurred.", false, true);
        }
    }

    /**
     * Displays both user input and Jung's response in the dialog container.
     */
    private void displayConversation(String userInput, CommandResult result) {
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userInput, userImage),
                DialogBox.getJungDialog(result.getMessage(), jungImage)
        );
    }

    /**
     * Handles any post-command actions like application exit.
     */
    private void handlePostCommandActions(CommandResult result) {
        if (result.shouldExit()) {
            closeApplication();
        }
    }

    /**
     * Shows initialization messages including welcome and any loading errors.
     */
    private void showInitializationMessages() throws IOException {
        String welcomeMessage = jung.getWelcomeMessage();
        dialogContainer.getChildren().add(
                DialogBox.getJungDialog(welcomeMessage, jungImage)
        );
    }

    /**
     * Closes the application window.
     */
    private void closeApplication() {
        Stage stage = (Stage) userInput.getScene().getWindow();
        stage.close();
    }
}

