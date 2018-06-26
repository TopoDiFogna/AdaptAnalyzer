package it.polimi.adaptanalyzertool.gui;

import it.polimi.adaptanalyzertool.model.Component;
import it.polimi.adaptanalyzertool.model.ProvidedService;
import it.polimi.adaptanalyzertool.model.RequiredService;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Collection;

/**
 * <p>
 * This class represents the controller for the new component window, thus contains all the functions required to
 * handle the creation of the new component.
 * </p>
 *
 * @author Paolo Paterna
 * @version 0.1
 */
public class NewComponentWindowController extends NewModalWindowController {

    private static final String DOUBLE_REGEX = "(?:\\d*\\.)?\\d+";
    private static final String NINETYNINE_REGEX = "^0?\\.\\d+";
    /*static
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
                    Collection<ProvidedService> providedServices = newComponent.getProvidedServices();
                    Collection<RequiredService> requiredServices = newComponent.getRequiredServices();
                    newComponent = new Component(newComponentName,
                            Double.parseDouble(componentCostTextField.getText().trim()),
                            Double.parseDouble(componentAvailabilityTextField.getText().trim()),
                            usedCheckBox.isSelected(),
                            componentColorPicker.getValue().getRed(), componentColorPicker.getValue().getGreen(),
                            componentColorPicker.getValue().getBlue(), componentColorPicker.getValue().getOpacity());
                    for (ProvidedService ps : providedServices) {
                        newComponent.addProvidedService(ps);
                    }
                    for (RequiredService rs : requiredServices) {
                        newComponent.addRequiredService(rs);
                    }
                }
                stage.close();

            }
        } else {
            componentErrorLabel.setText("Empty name");
        }
    }

    private boolean validateComponentInput() {
        if (!componentCostTextField.getText().trim().matches(DOUBLE_REGEX) ||
                !componentAvailabilityTextField.getText().trim().matches(NINETYNINE_REGEX)) {
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
