package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.model.Component;
import it.polimi.adaptanalyzertool.model.ProvidedService;
import it.polimi.adaptanalyzertool.model.RequiredService;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.HashMap;

public class NewComponentWindowController extends NewModalWindowController {

    private final String doubleRegex = "(?:\\d*\\.)?\\d+";
    private final String ninetynineRegex = "^0?\\.\\d+";
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
    private CheckBox usedCheckBox;
    @FXML
    private ColorPicker componentColorPicker;
    @FXML
    private Label componentErrorLabel;

    private Component newComponent;

    void initializeFields() {
        if (newComponent != null) {
            componentTextField.setText(newComponent.getName());
            componentCostTextField.setText(String.valueOf(newComponent.getCost()));
            componentAvailabilityTextField.setText(String.valueOf(newComponent.getAvailability()));
            usedCheckBox.setSelected(newComponent.isUsed());
            componentColorPicker.setValue(newComponent.getColor());
        }
    }

    @FXML
    private void saveComponent() {
        componentErrorLabel.setText("");
        String newComponentName = componentTextField.getText().trim();
        if (!"".equals(newComponentName)) {
            if (validateComponentInput()) {
                if (newComponent == null) {
                    newComponent = new Component(newComponentName,
                            Double.parseDouble(componentCostTextField.getText().trim()),
                            Double.parseDouble(componentAvailabilityTextField.getText().trim()),
                            usedCheckBox.isSelected(),
                            componentColorPicker.getValue().getRed(), componentColorPicker.getValue().getGreen(),
                            componentColorPicker.getValue().getBlue(), componentColorPicker.getValue().getOpacity());
                } else {
                    HashMap<String, ProvidedService> providedServiceHashMap = newComponent.getProvidedServices();
                    HashMap<String, RequiredService> requiredServices = newComponent.getRequiredServices();
                    newComponent = new Component(newComponentName,
                            Double.parseDouble(componentCostTextField.getText().trim()),
                            Double.parseDouble(componentAvailabilityTextField.getText().trim()),
                            usedCheckBox.isSelected(),
                            componentColorPicker.getValue().getRed(), componentColorPicker.getValue().getGreen(),
                            componentColorPicker.getValue().getBlue(), componentColorPicker.getValue().getOpacity(),
                            providedServiceHashMap, requiredServices);
                }
                stage.close();

            }
        } else {
            componentErrorLabel.setText("Empty name");
        }
    }

    private boolean validateComponentInput() {
        if (!componentCostTextField.getText().trim().matches(doubleRegex) ||
                !componentAvailabilityTextField.getText().trim().matches(ninetynineRegex)) {
            componentErrorLabel.setText("Check the inputs for mistakes");
            return false;
        } else {
            componentErrorLabel.setText("");
            return true;
        }
    }

    Component getNewComponent() {
        return newComponent;
    }

    void setNewComponent(Component newComponent) {
        this.newComponent = newComponent;
    }
}
