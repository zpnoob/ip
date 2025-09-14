package jung.gui;

import java.io.IOException;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Enhanced dialog box component with better error handling and resource management.
 * Fixed version that addresses CSS conflicts and resource loading issues.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Creates a dialog box with the specified text, speaker image, and optional error styling.
     */
    private DialogBox(String text, Image speakerImage, boolean isError) {
        try {
            loadFXMLLayout();
            setupDialogContent(text, speakerImage);
            applyStyles(isError);
            setupImageStyling();
        } catch (Exception e) {
            System.err.println("Error creating DialogBox: " + e.getMessage());
            createFallbackDialogBox(text, isError);
        }
    }

    /**
     * Creates a dialog box for user messages (image on right, text on left).
     */
    public static DialogBox getUserDialog(String text, Image userImage) {
        return new DialogBox(text, userImage, false);
    }

    /**
     * Creates a dialog box for Jung's responses (image on left, text on right).
     */
    public static DialogBox getJungDialog(String text, Image jungImage, boolean isError) {
        DialogBox dialogBox = new DialogBox(text, jungImage, isError);
        dialogBox.flip();
        return dialogBox;
    }

    /**
     * Overloaded method for backward compatibility.
     */
    public static DialogBox getJungDialog(String text, Image jungImage) {
        return getJungDialog(text, jungImage, false);
    }

    /**
     * Loads the FXML layout with better error handling.
     */
    private void loadFXMLLayout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("Failed to load DialogBox FXML: " + e.getMessage());
            throw new RuntimeException("Could not load FXML layout", e);
        } catch (Exception e) {
            System.err.println("Unexpected error loading FXML: " + e.getMessage());
            throw new RuntimeException("Unexpected FXML loading error", e);
        }
    }

    /**
     * Creates a fallback dialog box when FXML loading fails.
     */
    private void createFallbackDialogBox(String text, boolean isError) {
        // Create components programmatically as fallback
        dialog = new Label(text);
        dialog.setWrapText(true);
        dialog.setMaxWidth(350);

        displayPicture = new ImageView();
        displayPicture.setFitWidth(45);
        displayPicture.setFitHeight(45);
        displayPicture.setPreserveRatio(true);

        this.getChildren().addAll(dialog, displayPicture);
        this.setSpacing(12);
        this.setAlignment(Pos.CENTER_RIGHT);

        System.out.println("Using fallback DialogBox layout");
    }

    /**
     * Sets up the dialog content with null-safety checks.
     */
    private void setupDialogContent(String text, Image speakerImage) {
        if (dialog != null) {
            dialog.setText(text != null ? text : "");
        }

        if (displayPicture != null && speakerImage != null) {
            displayPicture.setImage(speakerImage);
        }
    }

    /**
     * Applies safer CSS styles that won't conflict with JavaFX defaults.
     */
    private void applyStyles(boolean isError) {
        try {
            String baseStyle = """
                -fx-background-color: white;
                -fx-background-radius: 12px;
                -fx-padding: 16px;
                -fx-spacing: 12px;
                -fx-alignment: center-left;
                """;

            String errorStyle = """
                -fx-background-color: #fff5f5;
                -fx-border-color: #ff6b6b;
                -fx-border-width: 1px;
                -fx-border-radius: 12px;
                """;

            String successStyle = """
                -fx-background-color: #ffffff;
                -fx-border-color: #e9ecef;
                -fx-border-width: 1px;
                -fx-border-radius: 12px;
                """;

            this.setStyle(baseStyle + (isError ? errorStyle : successStyle));

            // Style the text label safely
            if (dialog != null) {
                String textStyle = """
                    -fx-font-family: 'Segoe UI', system-ui, sans-serif;
                    -fx-font-size: 14px;
                    -fx-wrap-text: true;
                    """;

                if (isError) {
                    textStyle += "-fx-text-fill: #d32f2f; -fx-font-weight: bold;";
                } else {
                    textStyle += "-fx-text-fill: #2c3e50;";
                }

                dialog.setStyle(textStyle);
            }

        } catch (Exception e) {
            System.out.println("Warning: Could not apply dialog styling: " + e.getMessage());
        }
    }

    /**
     * Sets up image styling with error handling.
     */
    private void setupImageStyling() {
        if (displayPicture == null) {
            return;
        }

        try {
            double imageSize = 45.0;
            displayPicture.setFitWidth(imageSize);
            displayPicture.setFitHeight(imageSize);
            displayPicture.setPreserveRatio(true);
            displayPicture.setSmooth(true);

            // Create circular clip safely
            Circle clip = new Circle(imageSize / 2, imageSize / 2, imageSize / 2);
            displayPicture.setClip(clip);

        } catch (Exception e) {
            System.out.println("Warning: Could not apply image styling: " + e.getMessage());
        }
    }

    /**
     * Flips the dialog box layout with error handling.
     */
    private void flip() {
        try {
            ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
            Collections.reverse(tmp);
            getChildren().setAll(tmp);
            setAlignment(Pos.CENTER_LEFT);
        } catch (Exception e) {
            System.out.println("Warning: Could not flip dialog layout: " + e.getMessage());
        }
    }
}