package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.generator.ArchitectureGenerator;
import it.polimi.adaptanalyzertool.model.Architecture;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GeneratedArchitectureWindowController extends NewModalWindowController {

    @FXML
    private TextField numberOfComponentsTextField;
    @FXML
    private TextField numberOfReqFunctionsTextField;
    @FXML
    private TextField adaptabilityDegreeTextField;
    @FXML
    private TextField seedTextField;
    @FXML
    private Label errorLabel;

    @FXML
    private TextField architectureNameTextField;

    private Architecture architecture;

    @FXML
    private void newArchitectureSubmitted() {
        if (checkInputFields()) {
            ArchitectureGenerator generator;
            if (seedTextField.getText().trim().equals("")) {
                generator = new ArchitectureGenerator(Integer.valueOf(numberOfComponentsTextField.getText()),
                        Integer.valueOf(numberOfReqFunctionsTextField.getText()),
                        Integer.valueOf(adaptabilityDegreeTextField.getText()));
            } else {
                generator = new ArchitectureGenerator(Integer.valueOf(numberOfComponentsTextField.getText()),
                        Integer.valueOf(numberOfReqFunctionsTextField.getText()),
                        Integer.valueOf(adaptabilityDegreeTextField.getText()),
                        Long.valueOf(seedTextField.getText()));
            }
            architecture = generator.generateArchitecture(architectureNameTextField.getText());
            stage.close();
        } else {
            architectureNameTextField.clear();
            errorLabel.setText("Check inputs for mistakes!");
        }
    }

    @FXML
    private void enterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            newArchitectureSubmitted();
        }
    }

    private boolean checkInputFields() { //TODO check for correct numbers
        return !architectureNameTextField.getText().trim().equals("") &&
                !numberOfComponentsTextField.getText().trim().equals("") &&
                !numberOfReqFunctionsTextField.getText().trim().equals("") &&
                !adaptabilityDegreeTextField.getText().trim().equals("");
    }

    Architecture getArchitecture() {
        return architecture;
    }
}
