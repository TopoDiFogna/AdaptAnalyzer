package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.model.Message;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.Set;

/**
 * <p>
 * This class represents the controller for the new message window, thus contains all the functions required to
 * handle the creation of the new message.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class NewMessageWindowController extends NewModalWindowController {

    private Message message;

    @FXML
    private Label errorLabel;

    @FXML
    private ChoiceBox<String> startingServiceChoiceBox;

    @FXML
    private ChoiceBox<String> endingServiceChoiceBox;

    @FXML
    private CheckBox isReturningCheckBox;

    @FXML
    private void saveMessage() {
        if (startingServiceChoiceBox.getValue() != null && endingServiceChoiceBox.getValue() != null) {
            message = new Message(startingServiceChoiceBox.getValue(), endingServiceChoiceBox.getValue(), isReturningCheckBox.isSelected());
            stage.close();
        } else {
            errorLabel.setText("Check if components have been selected");
        }
    }

    Message getMessage() {
        return this.message;
    }

    void setAvailableComponents(Set<String> components) {
        startingServiceChoiceBox.setItems(FXCollections.observableArrayList(components).sorted());
        endingServiceChoiceBox.setItems(FXCollections.observableArrayList(components).sorted());
    }
}
