package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.generator.ArchitectureGenerator;
import it.polimi.adaptanalyzertool.model.Architecture;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * <p>
 * This class represents the controller for the new generated architecture window, thus contains all the functions required to
 * handle the creation of the new generated architecture.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class GeneratedArchitectureWindowController extends NewModalWindowController {

    private static final String NUMBER_REGEX = "^\\d+";
    private static final String SEED_REGEX = "^\\d*";

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

    private boolean checkInputFields() {
        return !architectureNameTextField.getText().trim().equals("") &&
                numberOfComponentsTextField.getText().trim().matches(NUMBER_REGEX) &&
                numberOfReqFunctionsTextField.getText().trim().matches(NUMBER_REGEX) &&
                adaptabilityDegreeTextField.getText().trim().matches(NUMBER_REGEX) &&
                seedTextField.getText().trim().matches(SEED_REGEX);
    }

    Architecture getArchitecture() {
        return architecture;
    }
}
