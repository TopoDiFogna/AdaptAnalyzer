package it.polimi.adaptanalyzertool.gui.newarchitecturewindow;

import it.polimi.adaptanalyzertool.model.Architecture;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class NewArchitectureWindowController {

    @FXML
    private Label errorLabel;

    @FXML
    private TextField architectureNameTextField;

    @FXML
    private Button submitButton;

    private Architecture architecture;

    @FXML
    public void newArchitectureSubmitted() {
        if (!architectureNameTextField.getText().trim().equals("")) {
            architecture = new Architecture(architectureNameTextField.getText().trim());
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        } else {
            architectureNameTextField.clear();
            errorLabel.setText("No name specified for the architecture");
        }
    }

    public Architecture getArchitecture() {
        return architecture;
    }

    @FXML
    public void enterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            newArchitectureSubmitted();
        }
    }
}
