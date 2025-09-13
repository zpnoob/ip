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

/**
 * Custom UI component representing a dialog box with speaker image and text.
 * Used to display conversations between the user and Jung.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Creates a dialog box with the specified text and speaker image.
     *
     * @param text The message to display
     * @param speakerImage Image representing the speaker
     */
    private DialogBox(String text, Image speakerImage) {
        loadFXMLLayout();
        setupDialogContent(text, speakerImage);
    }

    /**
     * Creates a dialog box for user messages (image on right, text on left).
     *
     * @param text User's message
     * @param userImage User's profile image
     * @return DialogBox configured for user display
     */
    public static DialogBox getUserDialog(String text, Image userImage) {
        return new DialogBox(text, userImage);
    }

    /**
     * Creates a dialog box for Jung's responses (image on left, text on right).
     *
     * @param text Jung's response message
     * @param jungImage Jung's profile image
     * @return DialogBox configured for Jung display
     */
    public static DialogBox getJungDialog(String text, Image jungImage) {
        DialogBox dialogBox = new DialogBox(text, jungImage);
        dialogBox.flip();
        return dialogBox;
    }

    /**
     * Loads the FXML layout for the dialog box component.
     */
    private void loadFXMLLayout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("Failed to load DialogBox FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Sets up the dialog content with text and image.
     */
    private void setupDialogContent(String text, Image speakerImage) {
        dialog.setText(text);
        displayPicture.setImage(speakerImage);
    }

    /**
     * Flips the dialog box layout so the image appears on the left.
     * Used for Jung's responses to distinguish from user messages.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }
}

