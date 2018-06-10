package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.model.Path;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


/**
 * <p>
 * This class represents the controller for the new component window, thus contains all the functions required to
 * handle the creation of the new component.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class NewPathWindowController extends NewModalWindowController {

    private static final String NINETYNINE_REGEX = "^0?\\.\\d+";
    private Path path;
    @FXML
    private Label errorLabel;

    @FXML
    private TextField pathNameTextField;
    @FXML
    private TextField executionProbabilityTextField;

    @FXML
    private void enterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            newWorkflowSubmitted();
        }
    }

    @FXML
    private void newWorkflowSubmitted() {
        String pathName = pathNameTextField.getText().trim();
        if (!pathName.equals("")) {
            if (executionProbabilityTextField.getText().matches(NINETYNINE_REGEX)) {
                path = new Path(pathName, Double.valueOf(executionProbabilityTextField.getText()));
                stage.close();
            } else {
                errorLabel.setText("Wrong input in execution probability");
            }
        } else {
            pathNameTextField.clear();
            errorLabel.setText("No name specified for the Path");
        }
    }

    Path getPath() {
        return path;
    }
}
