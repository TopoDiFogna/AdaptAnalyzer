package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.model.Architecture;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * <p>
 * This class represents the controller for the new architecture window, thus contains all the functions required to
 * handle the creation of the new architecture.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class NewArchitectureWindowController extends NewModalWindowController {

    @FXML
    private Label errorLabel;

    @FXML
    private TextField architectureNameTextField;

    private Architecture architecture;

    @FXML
    private void newArchitectureSubmitted() {
        String architectureName = architectureNameTextField.getText().trim();
        if (!architectureName.equals("")) {
            architecture = new Architecture(architectureName);
            stage.close();
        } else {
            architectureNameTextField.clear();
            errorLabel.setText("No name specified for the architecture");
        }
    }

    @FXML
    private void enterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            newArchitectureSubmitted();
        }
    }

    Architecture getArchitecture() {
        return architecture;
    }
}
