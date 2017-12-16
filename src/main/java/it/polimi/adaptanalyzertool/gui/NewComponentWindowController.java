package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.model.Component;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NewComponentWindowController extends NewModalWindowController {
    private final String doubleRegex = "(?:\\d*\\.)?\\d+";
    /*
       New Component
    */
    @FXML
    private TextField componentTextField;
    @FXML
    private TextField componentCostTextField;
    @FXML
    private TextField componentAvailabilityTextField;
    @FXML
    private ColorPicker componentColorPicker;
    @FXML
    private Label componentErrorLabel;

    private Component newComponent;

    @FXML
    private void saveComponent() {
        componentErrorLabel.setText("");
        String newComponentName = componentTextField.getText().trim();
        if (!"".equals(newComponentName)) {
            if (validateComponentInput()) {
                newComponent = new Component(newComponentName,
                        Double.parseDouble(componentCostTextField.getText().trim()),
                        Double.parseDouble(componentAvailabilityTextField.getText().trim()),
                        componentColorPicker.getValue());
                stage.close();

            }
        } else {
            componentErrorLabel.setText("Empty name");
        }
    }

    private boolean validateComponentInput() {
        if (!componentCostTextField.getText().trim().matches(doubleRegex) ||
                !componentAvailabilityTextField.getText().trim().matches(doubleRegex)) {
            componentErrorLabel.setText("Check the inputs for mistakes");
            return false;
        } else {
            componentErrorLabel.setText("");
            return true;
        }
    }

    public Component getNewComponent() {
        return newComponent;
    }
}
