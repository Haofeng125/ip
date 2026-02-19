package james.gui;

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
 * Represents a custom dialog box control using JavaFX.
 * This control consists of an ImageView to represent the speaker's face
 * and a label containing text from the speaker. It applies dynamic CSS styling
 * based on whether the speaker is the user or James.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Initializes a DialogBox with the specified text, image, and speaker type.
     * Loads the FXML layout, applies circular clipping to the avatar, and sets
     * the appropriate CSS classes for chat bubbles.
     *
     * @param text The message content to be displayed in the dialog box.
     * @param img The profile picture of the speaker.
     * @param isUser A boolean indicating if the speaker is the user (true) or James (false).
     */
    private DialogBox(String text, Image img, boolean isUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        // Make the profile pictures circular
        Circle clip = new Circle(25, 25, 25);
        displayPicture.setClip(clip);

        // Apply the base chat bubble style
        dialog.getStyleClass().add("chat-bubble");

        // Differentiate colors for User vs James
        if (isUser) {
            dialog.getStyleClass().add("user-bubble");
        } else {
            dialog.getStyleClass().add("james-bubble");
        }
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     * Used to differentiate James's messages from the user's messages.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Factory method to create a dialog box representing the user's input.
     * The profile picture remains on the right side.
     *
     * @param text The user's input string.
     * @param img The user's profile picture.
     * @return A custom DialogBox configured for the user.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img, true);
    }

    /**
     * Factory method to create a dialog box representing James's response.
     * Flips the dialog box to place the profile picture on the left side.
     *
     * @param text James's response string.
     * @param img James's profile picture.
     * @return A custom DialogBox configured for James, flipped horizontally.
     */
    public static DialogBox getJamesDialog(String text, Image img) {
        var db = new DialogBox(text, img, false);
        db.flip();
        return db;
    }
}
