package jung.gui;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
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
    private ChangeListener<Number> scrollListener;

    /**
     * Initializes the GUI components and sets up auto-scrolling behavior.
     */
    @FXML
    public void initialize() {
        loadImages();
        setupScrollPane();
        setupInputField();
        setupSendButton();
        applyStyles();
    }

    /**
     * Loads images with proper error handling.
     */
    private void loadImages() {
        try {
            userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
            if (userImage.isError()) {
                System.out.println("Warning: Could not load user image, using default");
                userImage = createDefaultImage();
            }
        } catch (Exception e) {
            System.out.println("Warning: Could not load user image: " + e.getMessage());
            userImage = createDefaultImage();
        }

        try {
            jungImage = new Image(this.getClass().getResourceAsStream("/images/jung.jpg"));
            if (jungImage.isError()) {
                System.out.println("Warning: Could not load Jung image, using default");
                jungImage = createDefaultImage();
            }
        } catch (Exception e) {
            System.out.println("Warning: Could not load Jung image: " + e.getMessage());
            jungImage = createDefaultImage();
        }
    }

    /**
     * Creates a default image when profile images can't be loaded.
     */
    private Image createDefaultImage() {
        // Create a simple colored rectangle as fallback
        return new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8/5+hHgAHggJ/PchI7wAAAABJRU5ErkJggg==");
    }

    /**
     * Sets up the scroll pane with proper auto-scrolling that doesn't conflict with binding.
     */
    private void setupScrollPane() {
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Remove the problematic binding and use a listener instead
        scrollListener = (observable, oldValue, newValue) -> {
            // Auto-scroll to bottom when content height changes
            Platform.runLater(() -> {
                scrollPane.setVvalue(1.0);
            });
        };

        dialogContainer.heightProperty().addListener(scrollListener);
    }

    /**
     * Sets up the input field with better UX features.
     */
    /**
     * Sets up the input field with better UX features.
     */
    private void setupInputField() {
        userInput.setPromptText("Type your command here... (e.g., todo buy groceries)");

        // Add focus listener to clear error styling when user starts typing
        userInput.textProperty().addListener((observable, oldValue, newValue) -> {
            clearInputErrorState();
        });

        // Enable Enter key to send message
        userInput.setOnKeyPressed(event -> {
            switch (event.getCode()) {
            case ENTER:
                handleUserInput();
                break;
            default:
                break;
            }
        });
    }

    /**
     * Sets up the send button with improved styling.
     */
    private void setupSendButton() {
        sendButton.setDefaultButton(true);
        sendButton.getStyleClass().add("send-button");
    }

    /**
     * Applies custom CSS styles with better error handling.
     */
    private void applyStyles() {
        try {
            String css = """
                .root {
                    -fx-base: #ffffff;
                    -fx-background-color: #f8f9fa;
                    -fx-font-family: 'Segoe UI', system-ui, sans-serif;
                    -fx-font-size: 13px;
                }
                
                .text-field {
                    -fx-background-color: white;
                    -fx-border-color: #ced4da;
                    -fx-border-width: 1.5px;
                    -fx-border-radius: 10px;
                    -fx-background-radius: 10px;
                    -fx-padding: 12px 16px;
                    -fx-font-size: 14px;
                    -fx-prompt-text-fill: #6c757d;
                    -fx-text-fill: #495057;
                }
                
                .text-field:focused {
                    -fx-border-color: #ed1c24;
                    -fx-background-color: #ffffff;
                }
                
                .text-field.error {
                    -fx-border-color: #dc3545;
                    -fx-background-color: #fff8f8;
                }
                
                .send-button {
                    -fx-background-color: #ed1c24;
                    -fx-text-fill: white;
                    -fx-border-radius: 10px;
                    -fx-background-radius: 10px;
                    -fx-padding: 12px 24px;
                    -fx-font-size: 14px;
                    -fx-font-weight: bold;
                    -fx-cursor: hand;
                }
                
                .send-button:hover {
                    -fx-background-color: #c41e3a;
                }
                
                .send-button:pressed {
                    -fx-background-color: #a01729;
                }
                
                .send-button:disabled {
                    -fx-background-color: #6c757d;
                    -fx-text-fill: rgba(255, 255, 255, 0.7);
                }
                
                .dialog-container {
                    -fx-spacing: 12px;
                    -fx-padding: 20px;
                    -fx-background-color: transparent;
                }
                """;

            this.setStyle(css);
            dialogContainer.getStyleClass().add("dialog-container");

        } catch (Exception e) {
            System.out.println("Warning: Could not apply custom styling: " + e.getMessage());
        }
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

        if (userInputText.trim().isEmpty()) {
            showInputError();
            return;
        }

        // Disable input while processing
        setInputEnabled(false);

        // Process command
        CommandResult result = processUserCommand(userInputText);
        displayConversation(userInputText, result);
        handlePostCommandActions(result);

        // Re-enable input
        setInputEnabled(true);
        userInput.requestFocus();
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
        } catch (JungException e) {
            return new CommandResult("Aiyo! " + e.getMessage(), false, true);
        } catch (IOException e) {
            return new CommandResult("Wah lau, got problem saving your data leh! " + e.getMessage(), false, true);
        } catch (Exception e) {
            return new CommandResult("Alamak, something went wrong sia! Try again?", false, true);
        }
    }

    /**
     * Displays both user input and Jung's response in the dialog container.
     */
    private void displayConversation(String userInput, CommandResult result) {
        try {
            DialogBox userDialog = DialogBox.getUserDialog(userInput, userImage);
            DialogBox jungDialog = DialogBox.getJungDialog(result.getMessage(), jungImage, result.isError());

            dialogContainer.getChildren().addAll(userDialog, jungDialog);

            // Scroll to bottom after a brief delay to allow layout
            Platform.runLater(() -> {
                try {
                    scrollPane.setVvalue(1.0);
                } catch (Exception e) {
                    // Ignore scroll errors - not critical
                }
            });

        } catch (Exception e) {
            System.err.println("Error displaying conversation: " + e.getMessage());
        }
    }


    /**
     * Shows visual feedback for empty input error.
     */
    private void showInputError() {
        userInput.getStyleClass().add("error");
        userInput.setPromptText("Eh, type something lah!");

        // Shake animation effect
        Platform.runLater(() -> {
            userInput.requestFocus();
        });
    }

    /**
     * Clears the error state from the input field.
     */
    private void clearInputErrorState() {
        userInput.getStyleClass().remove("error");
        userInput.setPromptText("Type your command here... (e.g., todo buy groceries)");
    }

    /**
     * Enables or disables the input controls.
     */
    private void setInputEnabled(boolean enabled) {
        userInput.setDisable(!enabled);
        sendButton.setDisable(!enabled);

        if (enabled) {
            sendButton.setText("Send");
        } else {
            sendButton.setText("...");
        }
    }

    /**
     * Handles any post-command actions like application exit.
     */
    private void handlePostCommandActions(CommandResult result) {
        if (result.shouldExit()) {
            Platform.runLater(() -> {
                try {
                    Thread.sleep(1500); // Give time to read farewell message
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                closeApplication();
            });
        }
    }

    /**
     * Shows initialization messages including welcome and any loading errors.
     */
    private void showInitializationMessages() throws IOException {
        String initResult = jung.initialize();
        String welcomeMessage = jung.getWelcomeMessage();

        // Show welcome message
        DialogBox welcomeDialog = DialogBox.getJungDialog(welcomeMessage, jungImage, false);
        dialogContainer.getChildren().add(welcomeDialog);

        // Show any initialization errors
        if (!initResult.isEmpty()) {
            DialogBox errorDialog = DialogBox.getJungDialog(initResult, jungImage, true);
            dialogContainer.getChildren().add(errorDialog);
        }

        // Focus on input field
        Platform.runLater(() -> userInput.requestFocus());
    }

    /**
     * Closes the application window.
     */
    private void closeApplication() {
        Stage stage = (Stage) userInput.getScene().getWindow();
        stage.close();
    }

    /**
     * Cleanup method to prevent memory leaks.
     */
    public void cleanup() {
        if (scrollListener != null && dialogContainer != null) {
            dialogContainer.heightProperty().removeListener(scrollListener);
        }
    }
}

